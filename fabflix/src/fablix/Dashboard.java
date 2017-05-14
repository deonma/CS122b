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
	        HttpSession session = request.getSession();
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
	        //Actions : movie, star
	        
	        String action, message, title, year, director, fname, lname, genre, dob, photoURL;
	        action = message = title = year = director = fname = lname = genre = dob = photoURL = "";

	        try{
	        	action = request.getParameter("action");
	        	switch(action){
	        	case "movie":
	        		title = request.getParameter("title");
	        		year = request.getParameter("year");
	        		director = request.getParameter("director");
	        		fname = request.getParameter("fname");
	        		lname = request.getParameter("lname");
	        		genre = request.getParameter("genre");
	        		message = access.addMovie(title, Integer.parseInt(year), director, fname, lname, genre);
	        		break;
	        	case "star":
	        		fname = request.getParameter("fname");
	        		lname = request.getParameter("lname");
	        		dob =  request.getParameter("dob");
	        		photoURL = request.getParameter("photoURL");
	        		message = access.insertStar(fname, lname, dob, photoURL);
	        		break;
	        	default:
	        }
	        }catch(Exception e){

	        }
	        
	        
	        request.setAttribute("message", message);
	        request.setAttribute("metadata", access.provideMetadata());
	        RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
            rd.include(request, response);

	    }
	
}
