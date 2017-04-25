import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;

public class MoviesPage extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccessDB access = new AccessDB();
        ResultSet rs;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ArrayList<String> movie = new ArrayList<String>();
        String title = request.getParameter("title");
        try{
            rs = access.findMovie(title);
            while(rs.next()){
                for(int i = 1; i <= 6; ++i){
                    movie.add(rs.getString(i));
                }
            } 
        }catch(Exception e){
        }

        request.setAttribute("movie", movie);
        RequestDispatcher rd = request.getRequestDispatcher("MoviesPage.jsp");
        rd.forward(request, response);

    }
}
