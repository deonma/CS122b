import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
public class ShoppingCart extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Cookie cartCookie = getCookie(request, "cart");
    	String[] cartArray = cartCookie.getValue().split("_");
    	request.setAttribute("shop_cart", cartArray);
        RequestDispatcher rd=request.getRequestDispatcher("shopping_cart.jsp");
        rd.forward(request,response);
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            } 

        }
        return null; 
    }
}