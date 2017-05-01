package fablix;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
public class ShoppingCart extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Getting Cart */
        HttpSession session = request.getSession();
        Cart shoppingCart = (Cart)session.getAttribute("shoppingCart");
    	if (shoppingCart == null) {
    		  shoppingCart = new Cart();
    		  session.setAttribute("shoppingCart", shoppingCart);
    	}
    	request.setAttribute("shop_cart", shoppingCart);

        RequestDispatcher rd=request.getRequestDispatcher("shopping_cart.jsp");
        rd.forward(request,response);
    }

}