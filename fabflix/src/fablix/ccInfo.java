package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;

public class ccInfo extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccessDB access = new AccessDB();
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Cart shoppingCart = (Cart)session.getAttribute("shoppingCart");
        String ccid = request.getParameter("ccid");
        String expiration = request.getParameter("expiration");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        Cookie pageCookie = ProcessCookies.get(request, "email");
        String customerId = access.getCustomerId(pageCookie.getValue());
        for (String item : shoppingCart.items().keySet())
        {
        	for (int i = 0; i < shoppingCart.get(item).getCount(); ++i)
        	{
        		
        	}
        }

        if(access.validateCC(fname, lname, ccid, expiration)){
            RequestDispatcher rd = request.getRequestDispatcher("hello.html");
            rd.include(request, response);
        }
        else{
            request.setAttribute("flag", false);
            RequestDispatcher rd = request.getRequestDispatcher("ccInfo.jsp");
            rd.include(request, response);
        }

    }

}
