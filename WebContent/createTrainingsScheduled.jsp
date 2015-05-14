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

<title>Admin-Schedule Trainings</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>

	<div class="jumbotron content ">
		<h3>
			<strong class="row"> Schedule Training <%@ include file="adminLinks.jsp"%></strong>
		</h3>
		<div style=" padding-left: 10px;">
			<div class="row" style="clear: both; padding-bottom: 10px; width:96%">
				<div class="col-lg-3 addTrainings">
					<input id="Training" type="text"
						class="form-control custom-form-control" placeholder="Trainings">
				</div>
				<div class="col-lg-2 addTrainings">
					<input id="startDate" type="text"
						class="form-control custom-form-control " placeholder="Start Date" >
				</div>
				<div class="col-lg-2 addTrainings">
					<input id="endDate" type="text"
						class="form-control custom-form-control " placeholder="End Date" >
				</div>
				<div class="col-lg-3 col-sm-3 addTrainings">
					<input id="trainer" type="text"
						class="form-control custom-form-control" placeholder="Trainer">
				</div>
				<div class="col-lg-2 col-sm-3 addTrainings " style="padding: 0px;">
					<input id="getRequest" type="submit" value="Schedule It"
						class="btn btn-primary"
						style="padding: 6px 32%; border-radius: 0px;">
				</div>
			</div>
		</div>
	</div>
	<div id="toast" class='error' style='display:none'>I did something!</div>
	<%@ include file="jsFiles.jsp"%>
</body>
</html>