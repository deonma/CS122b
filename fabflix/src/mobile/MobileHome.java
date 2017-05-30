package mobile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fablix.AccessDB;
import fablix.ProcessCookies;

public class MobileHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");    
		PrintWriter out = response.getWriter();
		ResultSet rs;
        String searchString = request.getParameter("searchString");
        AccessDB access = new AccessDB();
		try {
	        if (searchString == null) { // if this is not a search
	        	rs = access.getMobileMovies();
	        	if (!rs.next())
	        		out.print("No Results found");
	        	else
	        		out.print(rs.getString(1)+";");
	        	while (rs.next())
	        		out.print(rs.getString(1)+";");

	        } else { // if it is a search
	        	rs = access.getMobileSearch(searchString);
	        	if (!rs.next())
	        		out.print("No Results found");
	        	else
	        		out.print(rs.getString(1)+";");
	        	while (rs.next())
	        		out.print(rs.getString(1)+";");
	        }
		} catch (Exception e) {
			out.println(e.toString());
			out.println("LOL");

		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {  
			doGet(request, response);
	}
	
}
