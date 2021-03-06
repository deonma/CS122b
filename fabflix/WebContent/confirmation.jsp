<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.*"%>

<!doctype html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/confirmation.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
    <script src="https://www.w3schools.com/lib/w3data.js"></script> </head>
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
<%
    HashMap<String, Integer> purchase = (HashMap<String, Integer>) request.getAttribute("purchased");
    String date = (String) request.getAttribute("date");
    Integer totalPrice = (Integer) request.getAttribute("totalPrice");
%>

<body>
    <div w3-include-html="Search.html"></div>
    <script>w3IncludeHTML();</script>
    <div class="cOuter">
    <div id="box">
    <div class="jumbotron row text-center">
    <h2>Thank you for purchasing!</h2>
    <table class="table">
        <thread>
        <tr>
            <th>Date</th>
            <th>Movie</th>
            <th>Qty</th>
            <th>Subtotal</th>
        </tr>
        </thread>
        <%for(Map.Entry<String, Integer> entry : purchase.entrySet()){%>
            <tbody>
            <tr>
                <td><%=date%></td>
                <td><%=entry.getKey()%></td>
                <td><%=entry.getValue()%></td>
                <th>$<%=entry.getValue() * 5%></td>
            </tr>
        <% } %>
    </tbody>
    </table>
    	<p> Grand Total: $<%=totalPrice%> </p>
    </div>
    </div>
    </div>
</body>
