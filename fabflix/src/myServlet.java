import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

public class myServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                        HttpServletResponse response)
        throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        out.println("<B><BR>User:" + request.getParameter("user"));
        out.println
            (", Employee number:" + request.getAttribute("empid") + "</B>");
        this.getServletContext().getRequestDispatcher("/jsp/welcome.jsp").
            include(request, response);

        }
}
