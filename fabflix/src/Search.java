import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
public class Search extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccessDB access = new AccessDB();
        ResultSet rs;
        response.setContentType("text/html");
        String searchString = request.getParameter("searchstring");
        String changePage = request.getParameter("changePage");  
        Cookie offset = ProcessCookies.get(request, "offset");
        Cookie searchCookie = ProcessCookies.get(request, "searchString"); 
        
        if(searchString != null) {
            searchCookie = new Cookie("searchString", searchString);
            response.addCookie(searchCookie); 
        }
        if(changePage != null) { 
            if(changePage.equals("<") && !offset.getValue().equals("0")) {
                offset.setValue(String.valueOf(Integer.parseInt(offset.getValue())-1));
                
            }
            if(changePage.equals(">")) {
                offset.setValue(String.valueOf(Integer.parseInt(offset.getValue())+1));
            }   
        }
        ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
        try{
            if (searchCookie != null) {
                rs = access.searchForMovie(searchCookie.getValue(), Integer.parseInt(offset.getValue()));
            }
            else {
                rs = access.getPageMovies(Integer.parseInt(offset.getValue()));
            }
            while(rs.next()) {
                ArrayList<String> temp = new ArrayList<String>();
                for(int i = 1;i <= 6; ++i){
                    temp.add(rs.getString(i));
                }
                toReturn.add(temp);
            }
            request.setAttribute("Movies", toReturn);
        }
        catch(Exception e){
        }
        response.addCookie(offset);
        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request,response);
      
    }
}


