<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.*" %>


<!doctype html>
<head>
	<meta charset="UTF-8">
	<title>Login Form</title>
	<link rel="stylesheet" href="css/login.css">
	<script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<%
    boolean flag = true;
	boolean captchaFlag = true;
    try{
        captchaFlag = (Boolean) request.getAttribute("captchaFlag");
    }catch(Exception e){
    }
    try{
        flag = (Boolean) request.getAttribute("flag");
    }catch(Exception e){
    }
%>

<body>
	<div class="login-page">
	<div class="form">	
		<form class="login-form" action="EmployeeLogin" method="post" id="loginform">  
			<img src="fabflix.png">
            <%if(!flag){%>
  			    <p id="error"><font color="red">Incorrect Employee Information</font></p>	
			<% } %>
			<%if(!captchaFlag){%>
  			    <p id="error"><font color="red">Invalid Captcha </font></p>
			<% } %>
			<p> <font color="black"> Employee Login Page </font> </p>
            <input type="email" name="username" placeholder="email"/> 
			<input type="password" name="userpass" placeholder="password"/>
			<input type="submit" value="Login"/> 
			<div align="center" class="g-recaptcha" data-sitekey="6LeTtSAUAAAAAE3-uC0G1xgfKV8z7jJpMc8BJw7c"></div>
		</form>
	</div>
	</div>
</body>

