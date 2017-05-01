<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>

<!doctype html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/StarsPage.css">
    <script src="https://www.w3schools.com/lib/w3data.js"></script>
</head>

<%ArrayList<String> star = (ArrayList<String>) request.getAttribute("star");%>
<%HashMap<String, String> movies = (HashMap<String, String>) request.getAttribute("movies");%>
<%String id = star.get(0);%>
<%String fname = star.get(1);%>
<%String lname = star.get(2);%>
<%String dob = star.get(3);%>
<%String photo_url = star.get(4);%>

<body>
<div w3-include-html="Search.html"></div>
<script>w3IncludeHTML();</script>
<%if(star != null) {%>
    <img src="<%=photo_url%>" style="width:80px;height:100px;">
    <table class="star-table">
        <tr><th>Name:</th><td><%=fname%> <%=lname%></td></tr>
        <tr><th>Date of Birth:</th><td><%=dob%></td></tr>
        <tr><th>Star id:</th><td><%=id%></td></tr>
        <tr>
            <th>Movies:</th>
            <td>
                <%Iterator iter = movies.entrySet().iterator();%>
                <%while(iter.hasNext()){%>
                    <%Map.Entry pair = (Map.Entry)iter.next();%>
                        <a href="MoviesPage?id=<%=pair.getKey()%>&title=<%=pair.getValue()%>"><%=pair.getValue()%></a>
                    <%if(iter.hasNext()){%>
                        ,
                    <% } } %>
            </td>
        </tr>
    </table>
    <% } %>
</body>
