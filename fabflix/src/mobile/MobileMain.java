package mobile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fablix.AccessDB;
import fablix.ProcessCookies;

public class MobileMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");    
		PrintWriter out = response.getWriter();
		String email = "";
		String pass  = "";
		
		try {
		   email = request.getParameter("email");
		   pass = request.getParameter("pass");	
		   AccessDB access = new AccessDB();
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
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AccessDB access = new AccessDB();
        ResultSet rs;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String searchString = request.getParameter("searchstring");
        String sortString = request.getParameter("sortString"); 
        request.setAttribute("sortString", sortString);
        if (searchString.isEmpty()) { // if this is not a search
        	rs = access.getMobileMovies();

        } else { // if it is a search
        	
        }
       
    

    }

}  

