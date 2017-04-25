<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.ArrayList" %>

<!doctype html>
<body>
<% String[] movies = (String[]) request.getAttribute("shop_cart"); %>
<%if(movies != null) {%>
    <table>
    <%
        for(int i = 0; i < movies.length; ++i){
    %>
    <tr>
    <td><%=movies[i].replaceAll("-"," ") %></td>
    </tr>
    <% } %>
    <% } %>

    </table>
   
</body>
