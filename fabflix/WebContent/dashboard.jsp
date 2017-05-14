<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>

<!doctype html>
<head>
    <meta charset="UTF-8">
    <title>dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/shopping_cart.css">
    <script src="https://www.w3schools.com/lib/w3data.js"></script> 


</head>

<%
    boolean flag = true;
    try{
        flag = (boolean) request.getAttribute("flag");
    }catch(Exception e){
    }
%>

<body>
	<h3>Dashboard</h3>
	<div class="container row">
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
				  <h4 class="panel-title"> <a data-toggle="collapse" href="#actorform">Add new Actor</a> </h4>
				</div>
				<div id="actorform" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="dashboard" method="post" id="dashboardForm">
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">First Name</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="First Name" name="fname" id="lname">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Last Name</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="Last Name" name="lname" id="lname">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Date of Birth</label>
							  <div class="col-10">
								<input class="form-control" type="date" placeholder="Date of Birth" name="dob" id="dob">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Photo Url</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="Photo URL" name="photoURL" id="photoURL">
							  </div>
							</div>
							<button type="submit" style="margin-left:50%" class="btn btn-warning" >Submit</button>
						</form>
					</div>
				</div>
			</div>
		</div>
    </div>
    <div class="container row">
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
				  <h4 class="panel-title"> <a data-toggle="collapse" href="#movieform">Add new Movie</a> </h4>
				</div>
				<div id="movieform" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="dashboard" method="post" id="dashboardForm">
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Title</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="Title" name="title" id="title">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Year</label>
							  <div class="col-10">
								<input class="form-control" type="number" pattern='[0-9]{4}' placeholder="yyyy" name="year" id="year">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="exampMle-text-input" class="col-2 col-form-label">Director</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="Director" name="director" id="director">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">First Name</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="First Name" name="fname" id="lname">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Last Name</label>
							  <div class="col-10">
								<input class="form-control" type="text" placeholder="Last Name" name="lname" id="lname">
							  </div>
							</div>
							<div class="form-group row">
							  <label for="example-text-input" class="col-2 col-form-label">Genre</label>
							  <div class="col-10">
								<input class="form-control" type="date" placeholder="Genre" name="genre" id="genre">
							  </div>
							</div>
							<button type="submit" style="margin-left:50%" class="btn btn-warning" >Submit</button>
						</form>
					</div>
				</div>
			</div>
		</div>
    </div>
    <div class="container row">
		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
				  <h4 class="panel-title"> <a data-toggle="collapse" href="#metadata">Show MetaData</a> </h4>
				</div>
				<div id="metadata" class="panel-collapse collapse"> 
				<% ResultSetMetaData[] metadata = ((ResultSetMetaData[]) request.getAttribute("metadata"));	
					if (metadata != null) {
						for (int i = 0 ; i < metadata.length; ++i ){
							int columnsNumber = metadata[i].getColumnCount();
							%> 
							<h3><%=metadata[i].getTableName(1)%></h3>
							<table class="table">
							<thead> <tr><th>Column</th><th>Datatype</th></tr></thead>
							<tbody>
							<%
							for(int j = 1 ; j <= columnsNumber; j++){ %>
								<tr><td><%=metadata[i].getColumnName(j)%></td> <td><%=metadata[i].getColumnTypeName(j)%></td></tr><%
							}
							%> 
							</tbody> 
							</table>
							<%
						}
					}
					else {%>
						 <h1>Nothing</h1> 
					<% } %>

				</div>
			</div>
		</div>
	</div>
</body>
