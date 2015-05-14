<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=9">

<%@ include file="css.jsp"%>

<title>All details</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>
	<div class="jumbotron content ">
		<header>
			<div class="pull-left">
				<h3>
					<strong>Active Trainings</strong>
				</h3>
			</div>
			<div class="row" style="clear:both;">
				  <div class="col-lg-6">
				    <input id="customSearchFilter" type="text" class="form-control custom-form-control" placeholder="Search">
				  </div>
				  <div class="back-link pull-right">
					<a href="index.jsp">Back</a>
				  </div>
			</div>
		</header>
		<div class="dataTablesWidth">
			<table id="getAllActiveTrainings" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>Trainings</th>
						<th>Coordinator</th>
						<th>Start Dt</th>
						<th>End Dt</th>
						<th>Trainer</th>
						<th>Enrolled/pending Request</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<%@ include file="jsFiles.jsp"%>
</body>
</html>