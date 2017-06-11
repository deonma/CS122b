package mobile;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fablix.AccessDB;

public class MobileLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");    
		PrintWriter out = response.getWriter();
		String email = "";
		String pass  = "";
		
		try {
		   email = request.getParameter("email");
		   pass = request.getParameter("pass");	
		   AccessDB access = new AccessDB(0);
		   if (access.validate(email, pass)) {
			   out.println("true");
		   } else {
			   out.println("false");
		   }
			   
		} catch (Exception e) {
			out.println("false");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {  
			doGet(request, response);
		}
	}  

