<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<link rel="icon" 
      type="image/png" 
      href="images/factslogo.ico">
<%@ include file="css.jsp"%>

<title>HCL-GAI Academy</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>
	<div class="jumbotron content">
		<div class="left pull-left">
			<div class="row">
			  <div class="col-lg-6">
			    <input id="customSearchFilter" type="text" class="form-control custom-form-control" placeholder="Search">
			  </div>
			  <%if(session.getAttribute("isAdmin")!=null){
			  if((Boolean)session.getAttribute("isAdmin"))
			  {%>
				  <div class="pull-right switchAdmin">
				  	<a href="trainings.jsp">Switch to Admin</a>
				  </div>	
			<%}
			 } %>
			</div>
			<table id="indexPage" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Category</th>
						<th>Training</th>
						<th>Coordinator</th>
						<th>Action</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="right pull-right">
			<!-- <span style="display:none;">right</span> -->
			<div class="right-top ">
				<div class="list-header">My Requests</div>
					<div class="list-contents">
						<table id="myRequests" class="row-border" cellspacing="0" width="100%" style="border-collapse: collapse">
						</table>
				</div>
			</div>
			<div class="margin">&nbsp;</div>
			<div class="right-bottom ">
				<div class="list-header ">
					<div class="row">
						My Active Trainings
						<div class="pull-right">
							<a href="viewAllTrainings.jsp">View All</a>
						</div>
					</div>
				</div>
				<div class="list-contents">
					<table id="myActiveTrain" class="row-border" cellspacing="0" width="100%" style="border-collapse: collapse">
					</table>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		var empCode = <%=request.getSession().getAttribute("empCode")%>
	</script>
	</div>
	<div id="toast" class='error' style='display:none'>I did something!</div>
	<%@ include file="jsFiles.jsp"%>
</body>
</html>