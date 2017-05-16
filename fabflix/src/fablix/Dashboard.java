package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

public class Dashboard extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        AccessDB access = new AccessDB();
	        response.setContentType("text/html");
	        
	        String button = null, button1 = null;

	        try{
	        	button = request.getParameter("movie");
	        	button1 = request.getParameter("star");
	        }catch(Exception e){
	        	request.setAttribute("message", e.getMessage());
	        }
	        
	        if(button != null){
        		String title = request.getParameter("title");
        		String year = request.getParameter("year");
        		String director = request.getParameter("director");
        		String fname = request.getParameter("firstName");
        		String lname = request.getParameter("lastName");
        		String genre = request.getParameter("genre");
        		request.setAttribute("message", access.addMovie(title, Integer.parseInt(year), director, fname, lname, genre));
        	}
        	else if(button1 != null){
        		String fname1 = request.getParameter("fname");
        		String lname1 = request.getParameter("lname");
        		String dob =  request.getParameter("dob");
        		String photoURL = request.getParameter("photoURL");
        		request.setAttribute("message", access.insertStar(fname1, lname1, dob, photoURL));
        	}
	        
	        request.setAttribute("metadata", access.provideMetadata());
	        RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
            rd.include(request, response);

	    }
	
}
