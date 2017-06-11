package fablix;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class searchAutoComplete extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1402314384283700459L;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccessDB access = new AccessDB(0);
        ResultSet rs;
        response.setContentType("text/html");
        String name = request.getParameter("toSearch");
        rs = access.getDropList(name);
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