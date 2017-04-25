import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;


public class Website extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	    AccessDB access = new AccessDB();
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    Cookie cookie = null;
	    Cookie[] cookies = request.getCookies();
	    if(cookies != null){
		    cookie = cookies[1];
	    }
	    String name = access.getName(cookie.getValue());
	    try{
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            request.setAttribute("first_name", name);
            rd.forward(request, response);
        }catch (Exception e){
            System.out.println(e);
        }	
	}
}
