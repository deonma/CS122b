import java.io.IOException;  
import java.io.PrintWriter;  
  
import javax.servlet.*;
import javax.servlet.http.*;
  
public class WelcomePlaceHolder extends HttpServlet {  
public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {  
		  
	response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        Cookie cookie = null;
	Cookie[] cookies= null;
	cookies = request.getCookies();  
        String n=request.getParameter("username");
	if(cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			out.print("Name: " + cookie.getName()+ " ");
			out.print("Value: " + cookie.getValue());				
		}
	}
	else {
		out.print("No cookies found!");
	}   
        out.close();  
    }  
  
}
