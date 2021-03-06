<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="fablix.Cart" %>
<%@ page import="fablix.CartItem" %>

<!doctype html>
<head>
	<meta charset="UTF-8">
	<title>Cart</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/shopping_cart.css">
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
<body>
<div w3-include-html="Search.html" class="row"></div>
<script>w3IncludeHTML();</script> 
<div class="row">
	<div class="col col-md-12">
			<h1> Shopping Cart </h1> 
	</div>
</div>
<div class="row">
	<div class="container col-md-7 col-md-offset-1">
		<% LinkedHashMap<String, CartItem> movies = ((Cart) request.getAttribute("shop_cart")).items(); %>
		<%if(movies != null) {%>
			<table class="table table-bordered">
			<thead><tr><th>Movie Name</th> <th>Price</th> <th>Quantity</th> <th>Change Quantity</th> <th>Modify</th> <th>Delete</th></tr></thead>
			<tbody>
			<%
				int totalPrice = 0;
				for (String movie : movies.keySet()) {
					int price = movies.get(movie).getPrice();
					int quantity = movies.get(movie).getCount();
					totalPrice+=price*quantity;
			%>
				<tr>
				<td><%=movie %></td>
				<td>$<%=price%></td>
				<td><%=quantity%>
				<td>
					<form action="CartFunction" method="post" id="modify <%=movie%>">
						<input type="hidden" name="movieName" value="<%=movie%>" />
						<input type="hidden" name="cartMethod" value="modifyItem"/>
						<input type="number" class="quantity" name="quantity" min="0" maxlength="2" placeholder="<%=quantity%>"/> 
					</form>
				</td>
				<td>
					<button form="modify <%=movie%>" type="submit">Modify</button>
				</td>	
				<td>
					<form action="CartFunction" method="post">
						<input type="hidden" name="cartMethod" value="deleteItem"/>
						 <input type="hidden" name="movieName" value="<%=movie%>" />
						<div class="row"> <input type="submit" value="Delete"> </div>
					</form>
				</td>
			<% } %>
				</tr>
			</tbody>
			</table>
		</div>
	<div class="container col-md-4">
        <div id="box"> 
		<div class="jumbotron" align="right">
		<form action="ccInfo.jsp" id="ccInfo">
			<div class="row"> <h3> Subtotal (<%=movies.size()%> item):</h3> $<%=totalPrice%></div>
			<div class="row"><input type="submit" value="Proceed to Checkout"></div>
		</form>
		</div>
        </div>
	</div>
	<% } %>
</div>
   
</body>
