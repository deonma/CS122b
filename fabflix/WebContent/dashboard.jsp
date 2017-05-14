<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>

<!doctype html>
<head>
    <meta charset="UTF-8">
    <title>dashboard</title>
    <link rel="stylesheet" href="css/ccInfo.css">
</head>

<%
    boolean flag = true;
    try{
        flag = (boolean) request.getAttribute("flag");
    }catch(Exception e){
    }
%>

<body>
    <div class="dashboard-page">
    <div class="form">
        <form class="dashboard-form" action="dashboard" method="post" id="dashboardForm">
        <h3>Dashboard</h3>
        <span style="display:inline-block">
            <label for="fname">First name</label>
            <input id="fname" type="text" value="" name="fname"/>
        </span>
        <span style="display:inline-block">
            <label for="lname">Last name</label>
            <input id="lname" type="text" value="" name="lname"/>
        </span>
        <span style="display:inline-block">
            <label for="dob">Date of Birth</label>
            <input id="dob" type="date" value="" name="dob"/>
        </span>
        <span style="display:inline-block">
            <label for="photoURL">Last name</label>
            <input id="photoURL" type="text" value="" name="photoURL"/>
        </span>
        <div style="text-align:center">
            <input type="submit" value="Submit"/>
        </div>
    </div>
    </div>
</body>
