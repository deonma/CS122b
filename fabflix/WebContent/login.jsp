<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ page import="java.util.*" %>


<!doctype html>
<head>
	<meta charset="UTF-8">
	<title>Login Form</title>
	 <link rel="stylesheet" href="css/login.css">
</head>

<%
    boolean flag = true;
    try{
        flag = (boolean) request.getAttribute("flag");
    }catch(Exception e){
    }
%>

<body>
	<div class="login-page">
	<div class="form">	
		<form class="login-form" action="Login" method="post" id="loginform">  
			<img src="fabflix.png">
            <%if(!flag){%>
  			    <p id="error"><font color="red">Please use correct email or password</font></p>	
			<% } %>
            <input type="email" name="username" placeholder="email"/> 
			<input type="password" name="userpass" placeholder="password"/>
			<input type="submit" value="Login"/> 
		</form>
	</div>
	</div>
</body>

