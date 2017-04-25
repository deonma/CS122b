import java.io.IOException;  
import java.io.PrintWriter;  

import javax.servlet.*;
import javax.servlet.http.*;  
  
  
public class Login extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
    AccessDB access = new AccessDB();
    response.setContentType("text/html");  
    PrintWriter out = response.getWriter();  
    String n,p;      
    n=request.getParameter("username");  
    p=request.getParameter("userpass");
    Cookie user = new Cookie("user", n+"-"+p);
    
    if(access.validate(n,p)){
        Cookie offset = new Cookie("offset", String.valueOf(0));
	    user.setMaxAge(60*60*24); 
        offset.setMaxAge(60*60*24);
	    response.addCookie(user);
        response.addCookie(offset);
        RequestDispatcher rd=request.getRequestDispatcher("Search");  
        rd.forward(request,response);  
    }  
    else{ 
        RequestDispatcher rd=request.getRequestDispatcher("loginError.html");  
        rd.include(request,response);  
    }  
          
    out.close();  
    }  
}  
