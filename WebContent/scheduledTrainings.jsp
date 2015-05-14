<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=9">

<%@ include file="css.jsp"%>

<title>Admin-Scheduled Trainings</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>

	<div class="jumbotron content ">
		<h3>
			<strong class="row">Scheduled Trainings <%@ include file="adminLinks.jsp"%></strong>
		</h3>
		
			<div style="width: 100%; padding:0px 10px;">
				<div style="padding:0px 10px">
					<table id="manageAllActiveTrainings" class="display cell-border"cellspacing="0" width="100%">
						<thead>
							<tr>
								<th class="col-lg-3 addTrainings">Trainings</th>
								<th class="col-lg-3 addTrainings">Coordinator</th>
								<th class="col-lg-3 addTrainings">Start Dt</th>
								<th class="col-lg-3 addTrainings">End Dt</th>
								<th class="col-lg-3 addTrainings">Trainer</th>
								<th class="col-lg-3 addTrainings">Enrolled/pending Request</th>
								<th class="col-lg-3 addTrainings">Actions</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div id="toast" class='error' style='display: none'>I did
			something!</div>
		<%@ include file="jsFiles.jsp"%>
	
</body>
</html>