package fablix;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GenreDropDown extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccessDB access = new AccessDB();
        ResultSet rs;
        response.setContentType("text/html");
        rs = access.getGenres();
        StringBuilder sb = new StringBuilder();
        boolean i = false;
        try
		{
			while(rs.next()) {
				i = true;
				sb.append(rs.getString(1));  
				sb.append("\t");
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(i){
            PrintWriter out = response.getWriter();
            out.println(sb.toString());
        	System.out.println(sb.toString());
        }
    }    
}
