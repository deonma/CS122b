<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.*"%>
<!doctype html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/index.css">
    <script src="https://www.w3schools.com/lib/w3data.js"></script> 
</head>
  <% ArrayList<String> movies = (ArrayList<String>) request.getAttribute("Movies"); %>
  <% ArrayList<String> ids = (ArrayList<String>) request.getAttribute("Ids"); %>
  <% ArrayList<String> directors = (ArrayList<String>) request.getAttribute("Directors"); %>
  <% ArrayList<String> years = (ArrayList<String>) request.getAttribute("Years"); %>
  <% ArrayList<String> banners = (ArrayList<String>) request.getAttribute("Banners"); %>
  <% ArrayList<HashMap<String,String>> stars = (ArrayList<HashMap<String,String>>) request.getAttribute("Stars"); %>
  <% ArrayList<ArrayList<String>> genres = (ArrayList<ArrayList<String>>) request.getAttribute("Genres"); %>
  <% String searchString = (String) request.getAttribute("searchString"); %>
  <% String sortString = (String) request.getAttribute("sortString"); %>
  <% String none = (String) request.getAttribute("none"); %>
  <% String pageSize = (String) request.getAttribute("pageSize"); %> 
  <% String genreString = (String) request.getAttribute("genreString"); %>  
<body> 
    <div w3-include-html="Search.html"></div>
    <script>w3IncludeHTML();</script>
    <form class="page-form" action="Search" method="get" id="pageform">
        <select name="paginate" onchange="this.form.submit()">
            <option value=${pageSize} selected>Page Size: ${pageSize}</option>
            <option value="5">Page Size: 5</option>
            <option value="10">Page Size: 10</option>
            <option value="15">Page Size: 15</option>
            <option value="20">Page Size: 20</option>
            <option value="25">Page Size: 25</option>
            <option value="30">Page Size: 30</option>
        </select>
        <input type="hidden" name="scrollString" value="${searchString}" />
        <input type="hidden" name="sortString" value="${sortString}" />
    <% if (genreString != null) { %>
        <input type="hidden" name="genreString" value="${genreString}" />
    <% } %>
    </form>
  <table class="everything-table">
  <% if(none != null) { %>
    <tr> <td>No Results Found...</td></tr>
  <% }else{if( movies != null && movies.size() != 0) {for(int i = 0; i < movies.size(); ++i){ %>
    <tr>

        <%String title = movies.get(i);%>
        <%String id = ids.get(i);%>
    <table class="result-table">
        <tr>
        <p><a href="MoviesPage?id=<%=id%>&title=<%=title%>"><%=movies.get(i)%> (<%=years.get(i)%>)</a></p>
        </tr>
        <tr>
        <td>ID:<%=ids.get(i)%></td>
        <td><a href="MoviesPage?id=<%=id%>&title=<%=title%>"><img border="0" alt="<%=movies.get(i)%>" src="<%=banners.get(i)%>" width="100" height="100"></a></td>
        </tr>
        <tr>
        <td>Director:</td><td><%=directors.get(i)%></td>
        </tr>
        <tr>
        <td>Stars:</td>
        <td>
            <%int size = stars.get(i).size();%>
            <%for(Map.Entry<String, String> entry : stars.get(i).entrySet()){%>
                <a href="StarsPage?id=<%=entry.getKey()%>&name=<%=entry.getValue()%>"><%=entry.getValue()%></a>
                <%if(size != 1){%>
                    ,
                <% } %>
                <%size--;%>
            <% } %>
        </td>
        </tr>
        <tr>
        <td>Genres:</td>
        <td>
            <%for(int a = 0; a < genres.get(i).size(); ++a){%>
                <a href="Search?genre=<%=genres.get(i).get(a)%>"><%=genres.get(i).get(a)%></a>
                <%if(a != genres.get(i).size() - 1){%>
                    ,
                <% } %>
            <% } %>
        </td>
        </tr>
    </table>
    </tr>
    <% } %>
    <tr>
    <td>
    <form class="scrollform" action="Search" method="get" id="scrollform">
        <input type="submit" name="changePage" value="<<"/>
        <input type="submit" name="changePage" value="<"/>
        <input type="submit" name="changePage" value=">"/>
        <input type="submit" name="changePage" value=">>"/>
        <input type="hidden" name="scrollString" value="${searchString}" />
        <input type="hidden" name="sortString" value="${sortString}" />
    <% if (genreString != null) { %>
        <input type="hidden" name="genreString" value="${genreString}" />
    <% } %>
    </form>
    <form class="sortform" action="Search" method="get" id="sortform">
        <select name="sortBy" onchange="this.form.submit()">
    <% if(sortString.equals("m.title")) {%>
        <option value="m.title" selected>Sort:A-Z</option>
        <option value="m.title desc">Sort:Z-A</option>
        <option value="m.myear desc">Sort:New</option>
        <option value="m.myear">Sort:Old</option>
    <% } else if (sortString.equals("m.myear")){ %>
        <option value="m.title" >Sort:A-Z</option>
        <option value="m.title desc">Sort:Z-A</option>
        <option value="m.myear desc">Sort:New</option>
        <option value="m.myear" selected>Sort:Old</option>
    <% } else if (sortString.equals("m.myear desc")){ %>
        <option value="m.title" >Sort:A-Z</option>
        <option value="m.title desc">Sort:Z-A</option>
        <option value="m.myear desc" selected>Sort:New</option>
        <option value="m.myear">Sort:Old</option>
    <% } else if (sortString.equals("m.title desc")){ %>
        <option value="m.title" >Sort:A-Z</option>
        <option value="m.title desc" selected>Sort:Z-A</option>
        <option value="m.myear desc">Sort:New</option>
        <option value="m.myear">Sort:Old</option>
    <% } %>
        </select>
        <input type="hidden" name="scrollString" value="${searchString}" />
    <% if (genreString != null) { %>
        <input type="hidden" name="genreString" value="${genreString}" />
    <% } %>
    </form>
    </td>
    </tr>

    <% } else{ %> 
    <tr> <td>No More Results</td></tr>
    <tr><td>
    <form class="scrollform" action="Search" method="get" id="scrollform">
        <input type="submit" name="changePage" value="<<"/>
        <input type="submit" name="changePage" value="<"/>
        <input type="hidden" name="scrollString" value="${searchString}" />
        <input type="hidden" name="sortString" value="${sortString}" />
    <% if (genreString != null) { %>
        <input type="hidden" name="genreString" value="${genreString}" />
    <% } %>   
    </form>
    </td></tr>
    <% }} %>
    </table>
</body>





