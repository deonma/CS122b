package fablix;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.javafx.collections.MappingChange.Map;

public class Popup extends HttpServlet {
	
	private static StringBuilder sb;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccessDB access = new AccessDB();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    
        String id = request.getParameter("toSearch");
        ArrayList<String> movie = access.getMovie(id);
        HashMap<String, String> stars = access.getStars(id);
        ArrayList<String> genre = access.getGenre(id);
        sb = new StringBuilder();
        
        buildBody(id, movie, stars, genre);
        //sb.append("<h1>Title"+ id +"</h>");
        out.println(sb.toString());
        System.out.println(sb.toString());

    }
    
    public static void buildHeader(){
    	sb.append("<%@ page contentType='text/html;charset=UTF-8'  %>"
    	+ "<%@ page import='java.util.*' %>"
    	+ "<!doctype html>"
    	+ "<head>"
    	+    "<meta charset='UTF-8'>"
    	+	 "<link rel='stylesheet' href='css/bootstrap.min.css'>"
    	+	 "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>"
    	+    "<link rel='stylesheet' href='css/MoviesPage.css'>"
    	+    "<script src='js/jquery.min.js'></script>"
    	+	 "<script src='js/bootstrap.min.js'></script>"
    	+ "</head>");
    }
    
    public static void buildBody(String id, ArrayList<String> movie, HashMap<String, String> stars, ArrayList<String> genre){
    	String mid = movie.get(0);
        String title = movie.get(1);
        String year = movie.get(2);
        String director = movie.get(3);
        String banner_url = movie.get(4);
        String trailer_url = movie.get(5);
        
        sb.append("<div class='mOuter'>"
        + "<div class='row'>"
        + "<div class='col-sm-4 col-md-4 col-lg-4'><img class='img-responsive' src='<%="+ banner_url +"%>' style='width:150px;height:190px;float:right;'></div>"
        + "<div class='col-sm-8 col-md-8 col-lg-8'>"
        + "<table style='float:left' class='movie-table'>"
        + "<tr><th>Title:</th><td>"+ title +"</td></tr>"
        + "<tr><th>Year:</th><td>"+ year +"</td></tr>"
        + "<tr><th>Director:</th><td>"+ director +"</td></tr>"
        + "<tr><th>Movie Id:</th><td>"+ id +"</td></tr>"
        + "<tr>"
        + "<th>Stars:</th>"
        + "<td>");
        
        java.util.Iterator<String> iter = stars.keySet().iterator();
        while(iter.hasNext()){
        	String key = iter.next();
        	sb.append("<a href='StarsPage?id="+ key +"&name="+ stars.get(key) +"'>"+ stars.get(key) +"</a>");
        	if(iter.hasNext()){
        		sb.append(", ");
        	}
        }
        
        sb.append("</td>"
        + "</tr>"
        + "<tr>"
        + "<th>Genre</th>"
        + "<td>");
        
        for(int j = 0; j < genre.size(); ++j){
        	if(j + 1 != genre.size()){
        		sb.append("<a href='Search?genre="+ genre.get(j) +">"+ genre.get(j) +"</a>");
        		sb.append(", ");
        	}else{
        		sb.append("<a href='Search?genre="+ genre.get(j) +">"+ genre.get(j) +"</a>");
        	}
        }
        
        sb.append("</td>"
        + "</tr>"
        + "<tr><th>Trailer:</th><td><a href='"+ trailer_url +"'>Click here</a> to watch the trailer</td></tr>"
        + "<tr><th>Price:</th><td>$5.00</td></tr>"
        + "</table>"
        + "</div>"
        + "</div>"
        + "<div class='sButton'>"
        + "<form class='addCart' action='CartFunction'  id='cart' method='post'>"
        +    "<input type='hidden' name='cartMethod' value='addCart'/>"
        +    "<input type='hidden' name='movieName' value='"+ title +"'/>"
        +    "<input type='hidden' name='movieId' value='"+ id +"'/>"
        +    "<button class='glyphicon glyphicon-shopping-cart' type='submit' name='cart' value='Add to cart'/>"
        + "</form>"
        + "</div>");
    }
}
