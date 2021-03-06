<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>

<!doctype html>
<head>
    <meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/StarsPage.css">
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
    <div class="sOuter">
    <div class="row">
        <div class="col-sm-4 col-md-4 col-lg-4"><img class="img-responsive" src="<%=photo_url%>" style="width:160px;height:190px;float:right;"></div>
    <div class="col-sm-8 col-md-8 col-lg-8">
    <table style="float:left" class="star-table">
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
    </div>
    </div>
</body>
