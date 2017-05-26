package fablix;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Popup extends HttpServlet {

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
        
        System.out.println(movie.size());
        System.out.println(stars.size());
        System.out.println(genre.size());
        out.println(movie);
    }
}
