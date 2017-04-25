<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.ArrayList" %>

<!doctype html>
<%ArrayList<String> movie = (ArrayList<String>) request.getAttribute("movie");%>
<%String id = movie.get(0);%>
<%String title = movie.get(1);%>
<%String year = movie.get(2);%>
<%String director = movie.get(3);%>
<%String banner_url = movie.get(4);%>
<%String trailer_url = movie.get(5);%>

<%if(movie != null) {%>
    <table>
    <%
        for(int i = 0; i < movie.size(); ++i){
    %>
    <tr>
    <td><%=movie.get(i)%></td>
    </tr>
    <% } %>
    <% } %>

    </table>

   	<div class="form">	
		<form class="addCart" action="AddCart" method="post" id="cart">  
			<input type="hidden" name="movieName" value="<%=title%>"/>
			<input type="submit" value="Add to cart"/> 
		</form>
	</div>
