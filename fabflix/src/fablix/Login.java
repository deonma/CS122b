package fablix;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;  
  
  
public class Login extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
		AccessDB access = new AccessDB(0);
		String n,p, gRecaptchaResponse;      
		n=request.getParameter("username");  
		p=request.getParameter("userpass");
		gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		PrintWriter out = response.getWriter();
		if (!VerifyUtils.verify(gRecaptchaResponse))
		{
			request.setAttribute("captchaFlag", false);
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
			rd.include(request,response);  
		}
		else if(access.validate(n,p)){
			Cookie email = new Cookie("email", n);
			email.setMaxAge(60*60*24);
			response.addCookie(email);
			RequestDispatcher rd=request.getRequestDispatcher("Search");  
			rd.include(request,response);
		}  
		else
		{ 
			request.setAttribute("flag", false);
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
			rd.include(request,response);  
		} 
          
    }  
}  
