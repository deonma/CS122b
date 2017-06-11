package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;

public class MoviesPage extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccessDB access = new AccessDB(0);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String id = request.getParameter("id");
        ArrayList<String> movie = access.getMovie(id);
        HashMap<String, String> stars = access.getStars(id);
        ArrayList<String> genre = access.getGenre(id);

        request.setAttribute("movie", movie);
        request.setAttribute("stars", stars);
        request.setAttribute("genre", genre);
        RequestDispatcher rd = request.getRequestDispatcher("MoviesPage.jsp");
        rd.forward(request, response);
    }
}
