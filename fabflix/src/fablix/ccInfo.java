package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

public class ccInfo extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccessDB access = new AccessDB(1);
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Cart shoppingCart = (Cart)session.getAttribute("shoppingCart");
        String ccid = request.getParameter("ccid");
        String expiration = request.getParameter("expiration");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        Cookie email = ProcessCookies.get(request, "email");
        String customerId = access.getCustomerId(email.getValue());
        HashMap<String, Integer> purchase = new HashMap<String, Integer>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
        
        for (String item : shoppingCart.items().keySet())
        {
        	int count = shoppingCart.get(item).getCount();
        	for (int i = 0; i < count; ++i)
        	{
        		try {
        			access.insertSales(customerId, shoppingCart.get(item).getId(), date);
        		} catch (Exception e) {
        			
        		}
        	}
        	purchase.put(item, count);
        }
        
        request.setAttribute("date", date);
        request.setAttribute("purchased", purchase);
        request.setAttribute("totalPrice", shoppingCart.getTotalPrice());
        if(access.validateCC(fname, lname, ccid, expiration)){
            session.setAttribute("shoppingCart", new Cart());
            RequestDispatcher rd = request.getRequestDispatcher("confirmation.jsp");
            rd.include(request, response);
        }
        else{
            request.setAttribute("flag", false);
            RequestDispatcher rd = request.getRequestDispatcher("ccInfo.jsp");
            rd.include(request, response);
        }

    }

}
