<me%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.ArrayList" %>

<!doctype html>
    <form class="search-form" action="Search" method="post" id="searchform">
        <input type="text" name="searchstring" placeholder="Search for movies..."/> 
        <input type="submit" value="Go"/> 
    </form>
    
  <% ArrayList<ArrayList<String>> movies = (ArrayList<ArrayList<String>>) request.getAttribute("Movies"); %>
  <% if( movies != null) { %>
    <table>
    <%
        
        for(int i = 0; i < movies.size(); ++i){
            //out.print(movies.get(i).get(1));
   
    %>
    <tr>
        <%String title = movies.get(i).get(1);%>
        <td><a href="MoviesPage?title=<%=title%>"><%=movies.get(i).get(1)%></a></td>
    </tr>
    <% } %>
    <form class="scroll-form" action="Search" method="post" id="searchform">
        <input type="submit" name="changePage" value="<"/>
        <input type="submit" name="changePage" value=">"/>
    </form>
    <% } %>
    </table>






