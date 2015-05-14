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

<title>Admin-Request For Trainings</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>

	<div class="jumbotron content ">
		<h3>
			<strong class="row"> Request For Trainings <%@ include
					file="adminLinks.jsp"%></strong>
		</h3>
		<div style="width: 80%; padding-left: 10px;">
			<div class="row" style="clear: both; padding-bottom: 10px;">
				<div class="col-lg-6 addTrainings">
					<input id="Training" type="text"
						class="form-control custom-form-control" placeholder="Training">
					<input id="trainingId" type="text" style="display: none"
						class="form-control custom-form-control" placeholder="TrainId">
				</div>
				<div class="col-lg-3" style="padding: 0px;">
					<input id="getRequests" type="submit" value="Get Requests"
						class="btn btn-primary"
						style="padding: 6px 32%; border-radius: 0px;">
				</div>
			</div>
			<table id="reqForTrain" class="display cell-border" cellspacing="0"
				width="100%">
				<thead>
					<tr>
						<th class="col-lg-3 addTrainings">Category</th>
						<th class="col-lg-3 addTrainings">Training</th>
						<th class="col-lg-3 addTrainings">Coordinator</th>
						<th class="col-lg-3 addTrainings">Requested By  <span
							class="label label-success" id="reqCount">0</span></th>
						<th class="col-lg-3 addTrainings">Requested On</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="toast" class='error' style='display: none'>I did
		something!</div>
	<%@ include file="jsFiles.jsp"%>
</body>
</html>