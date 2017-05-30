<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.*" %>

<!doctype html>
<head>
    <meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/MoviesPage.css">
    <script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
    <script src="https://www.w3schools.com/lib/w3data.js"></script> 
    <script>
	$(document).on('keyup', "#searchMovies", function () {
		var that = this,
        value = $(this).val();
		$.ajax({
        	type: "GET",
            url: "searchAutoComplete",
            data: {toSearch : value},
            dataType: "text",
            success: function(data){
            	var list = data.split("\t");
           		var options = "";
				for (i = 0;i < list.length; i++) {
					options+="<option value='" + list[i] + "'></option>";
				}		
				$('#movieList').html(options);
           	}
		});

	});
$(document).ready(function(){
	$.ajax({ url: "GenreDropDown",
	        context: document.body,
	        success: function(data){
 				var list = data.split("\t");
	        	$("#genredp").append("<option id='default' value='' style='display:none' disabled selected>Browse by Genre</option>");
				for (i = 0;i < list.length; i++) {
					$("#genredp").append("<option id='" + list[i] + "' value='" + list[i] + "'>"+list[i]+"</option>");
				} 
	        }});
	});
	</script>
</head>

<%ArrayList<String> movie = (ArrayList<String>) request.getAttribute("movie");%>
<%HashMap<String, String> stars = (HashMap<String, String>) request.getAttribute("stars");%>
<%ArrayList<String> genre = (ArrayList<String>) request.getAttribute("genre");%>
<%String id = movie.get(0);%>
<%String title = movie.get(1);%>
<%String year = movie.get(2);%>
<%String director = movie.get(3);%>
<%String banner_url = movie.get(4);%>
<%String trailer_url = movie.get(5);%>

<body>
<div w3-include-html="Search.html"></div>
<script>w3IncludeHTML();</script>
<%if(movie != null) {%>
    <div class="mOuter">
    <div class="row">
        <div class="col-sm-4 col-md-4 col-lg-4"><img class="img-responsive" src="<%=banner_url%>" style="width:150px;height:190px;float:right;"></div>
    <div class="col-sm-8 col-md-8 col-lg-8">
    <table style="float:left" class="movie-table">
    <tr><th>Title:</th><td><%=title%></td></tr>
    <tr><th>Year:</th><td><%=year%></td></tr>
    <tr><th>Director:</th><td><%=director%></td></tr>
    <tr><th>Movie Id:</th><td><%=id%></td></tr>
    <tr>
        <th>Stars:</th>
        <td>
            <%Iterator iter = stars.entrySet().iterator();%>
            <%while(iter.hasNext()){%>
                <% Map.Entry pair = (Map.Entry)iter.next(); %>
                    <a href="StarsPage?id=<%=pair.getKey()%>&name=<%=pair.getValue()%>"><%=pair.getValue()%></a> 
                <% if (iter.hasNext()){%>
                    ,
                <% } } %>
        </td>
    </tr>
    <tr>
        <th>Genre</th>
        <td>
            <%for(int j = 0; j < genre.size(); ++j){%>
                <%if(j + 1 != genre.size()){%>
                    <a href="Search?genre=<%=genre.get(j)%>"><%=genre.get(j)%></a>
                    , 
                <% } else{%>
                    <a href="Search?genre=<%=genre.get(j)%>"><%=genre.get(j)%></a>
                <% } } %>
        </td>
    </tr>
    <tr><th>Trailer:</th><td><a href="<%=trailer_url%>">Click here</a> to watch the trailer</td></tr>
    <tr><th>Price:</th><td>$5.00</td></tr>
    </table>
    </div>
    </div>
    <div class="sButton">
        <form class="addCart" action="CartFunction"  id="cart" method="post">  
            <input type="hidden" name="cartMethod" value="addCart"/>
            <input type="hidden" name="movieName" value="<%=title%>"/>
            <input type="hidden" name="movieId" value="<%=id%>"/>
            <button class="glyphicon glyphicon-shopping-cart" type="submit" name="cart" value="Add to cart"/> 
        </form>
    </div>
    <% } %>
    </div>
</body>
