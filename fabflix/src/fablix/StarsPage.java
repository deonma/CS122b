package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;

public class StarsPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccessDB access = new AccessDB();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        ArrayList<String> star = access.getStarInfo(id);
        HashMap<String, String> movies = access.getMovies(id);

        request.setAttribute("star", star);
        request.setAttribute("movies", movies);
        RequestDispatcher rd = request.getRequestDispatcher("StarsPage.jsp");
        rd.forward(request, response);
    }
}
