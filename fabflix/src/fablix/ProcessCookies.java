package fablix;

import javax.servlet.http.*;
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
