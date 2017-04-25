import java.io.IOException;  
import java.io.PrintWriter;  

import javax.servlet.*;
import javax.servlet.http.*;  
  
  
public class AddCart extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
    AccessDB access = new AccessDB();
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
   
    Cookie cart = getCookie(request, "cart");
	String movie = request.getParameter("movieName").replaceAll("\\s", "-");
	System.out.println(movie);
    if (cart != null)
    {
    	String current = cart.getValue();
    	cart.setValue(current + "_" + movie);
    } else {
		cart = new Cookie("cart", movie);
		cart.setMaxAge(60*60*24); 
    }
	response.addCookie(cart);
	RequestDispatcher rd = request.getRequestDispatcher("ShoppingCart");  
	rd.forward(request,response);  
          
    out.close();  
    }  

	public static Cookie getCookie(HttpServletRequest request, String name) {
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
    }

    return null;
}
}  
