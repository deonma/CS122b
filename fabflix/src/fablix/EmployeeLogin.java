package fablix;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;  
  
  
public class EmployeeLogin extends HttpServlet {  
public void doGet(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
		AccessDB access = new AccessDB();
		String n,p, gRecaptchaResponse;      
		n=request.getParameter("username");  
		p=request.getParameter("userpass");
		gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		PrintWriter out = response.getWriter();
		if (!VerifyUtils.verify(gRecaptchaResponse))
		{
			request.setAttribute("captchaFlag", false);
			RequestDispatcher rd=request.getRequestDispatcher("employee_login.jsp");  
			rd.include(request,response);  
		}
		else if(access.validateEmployee(n,p)){
			RequestDispatcher rd=request.getRequestDispatcher("Dashboard");  
			rd.include(request,response);
		}  
		else
		{ 
			request.setAttribute("flag", false);
			RequestDispatcher rd=request.getRequestDispatcher("employee_login.jsp");  
			rd.include(request,response);  
		} 
          
    }  
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {  
		doGet(request, response);
	}
}  
