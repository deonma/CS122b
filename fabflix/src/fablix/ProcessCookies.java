package fablix;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
public class ProcessCookies {
    public static Cookie get(HttpServletRequest request, String name) {
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }

        }
        return null;
    }
}
