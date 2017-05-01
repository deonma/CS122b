package fablix;
import java.io.IOException;  
import java.io.PrintWriter;  

import javax.servlet.*;
import javax.servlet.http.*;  
  
  
public class CartFunction extends HttpServlet {  
/**
	 * 
	 */
	private static final long serialVersionUID = -1620230177347787366L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
    response.setContentType("text/html");  
    
    /* Getting Cart */
    HttpSession session = request.getSession();
    Cart shoppingCart = (Cart)session.getAttribute("shoppingCart");
	if (shoppingCart == null) {
		  shoppingCart = new Cart();
		  session.setAttribute("shoppingCart", shoppingCart);
	}

	String method = request.getParameter("cartMethod");
	String movie = request.getParameter("movieName");
	String id = request.getParameter("movieId");
	switch(method){
	case "addCart":
		shoppingCart.addItem(movie, id);
		break;
	case "modifyItem":
		// catches exception for when there is no user input
		try {
			String quantity = request.getParameter("quantity");
			shoppingCart.modifyAmount(movie, Integer.parseInt(quantity));
		} catch(Exception e) {};
		break;
	case "deleteItem":
		shoppingCart.deleteItem(movie);
		break;
	}
	session.setAttribute("shoppingCart", shoppingCart);
	RequestDispatcher rd=request.getRequestDispatcher("ShoppingCart");
	rd.forward(request,response);
    }  

}  
