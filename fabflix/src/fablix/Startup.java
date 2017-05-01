package fablix;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;


public class Startup extends HttpServlet {
public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    AccessDB access = new AccessDB();
    response.setContentType("text/html");
    String n,p;
    Cookie user = ProcessCookies.get(request, "user");
    if(user != null){
        String[] toSplit = user.getValue().split("-");
        if(access.validate(toSplit[0], toSplit[1])){
                    RequestDispatcher rd=request.getRequestDispatcher("Search");
                    rd.forward(request,response);
        }
        else{         
                    RequestDispatcher rd=request.getRequestDispatcher("login.html");
                    rd.include(request,response);
        }
    } 

    else {
                    RequestDispatcher rd=request.getRequestDispatcher("login.html");
                    rd.include(request,response);



    }   
     


    }
}
