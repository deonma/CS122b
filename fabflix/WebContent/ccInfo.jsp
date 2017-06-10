<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.*" %>


<!doctype html>
<head>
    <meta charset="UTF-8">
    <title>ccInfo Form</title>
    <link rel="stylesheet" href="css/ccInfo.css">
    <script src="https://www.w3schools.com/lib/w3data.js"></script>
    <script src="js/jquery.min.js"></script>
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

<%
    boolean flag = true;
    try{
        flag = (Boolean) request.getAttribute("flag");
    }catch(Exception e){
    }

%>

<body>
    <div w3-include-html="Search.html"></div>
    <script>w3IncludeHTML();</script>
    <div class="ccInfo-page">
    <div class="form">
        <form class="ccInfo-form" action="ccInfo" method ="post" id="ccInfoForm">
            <h3>Payment Information</h3>
            <%if(!flag){%>
                <p id="error"><font color="red">Please enter the the correct payment information</font></p>
            <% } %>
            <span style="display:inline-block">
                <label for="ccid">Card number</label>
                <input id="ccid" type="text" value="" name="ccid">
            </span>
            <span style="display:inline-block">
                <label for="expiration">Expiration date</label>
                <input id="expiration" type="date" name="expiration">
            </span>

            <h3>Billing Information</h3>
            <span style="display:inline-block">
                <label for="fname">First name</label>
                <input id="fname" type="text" value="" name="fname"/>
            </span>
            <span style="display:inline-block">
                <label for="lname">Last name</label>
                <input id="lname" type="text" value="" name="lname"/>
            </span> 
            <span style="display:inline-block">
                <label for="addr">Billing address</label>
                <input id="addr" type="text" value="" name="addr"/>
            </span>
            <span style="display:inline-block">
                <label for="city">Country</label>
                <input id="city" type="text" value="" name="city"/>
            </span>
            <span style="display:inline-block">
                <label for="state">City</label>
                <input id="state" type="text" value="" name="state"/>
            </span>
            <span style="display:inline-block">
                <label for="country">State</label>
                <input id="country" type="text" value="" name="country"/>
            </span>
            <span style="display:inline-block">
                <label for="zip">Zip or postal code</label>
                <input id="zip" type="text" value="" name="zip"/>
            </span>
            <span style="display:inline-block">
                <label for="phone">Phone number</label>
                <input id="phone" type="text" value="" name="phone"/>
            </span>
            <div style="text-align:center">        
               <input type="submit"  value="Continue"/>
            </div>
    </div>
    </div>
</body>

