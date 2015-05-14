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

<title>Admin-Trainings</title>
</head>
<body class="container">
	<%@ include file="header.jsp"%>

	<div class="jumbotron content ">
		<h3>
			<strong class="row"> Trainings <%@ include file="adminLinks.jsp"%></strong>
		</h3>
		<div style="width: 80%; padding-left: 10px;">
			<div class="row" style="clear: both; padding-bottom: 10px;">
				<div class="col-lg-3 addTrainings">
					<input id="category" type="text"
						class="form-control custom-form-control" placeholder="Category">
				</div>
				<div class="col-lg-3 addTrainings">
					<input id="training" type="text"
						class="form-control custom-form-control" placeholder="Training">
				</div>
				<div class="col-lg-3 addTrainings">
					<input id="coordinator" type="text"
						class="form-control custom-form-control" placeholder="Coordinator">
				</div>
				<div class="col-lg-3" style="padding: 0px;">
					<input id="createNewBtn" type="submit" value="Create New"
						class="btn btn-primary"
						style="padding: 6px 32%; border-radius: 0px;">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-3 addTrainings"></div>
				<div class="col-lg-6 addTrainings">
					<div class="addTrainings">
						<input id="customSearchFilter" type="text"
							class="form-control custom-form-control "
							placeholder="search training">
					</div>
					<span style="font-size: 0.8em; color: red">*double click each cell to edit</span>
				</div>
			</div>
			<table id="createdTrainings" class="display cell-border"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="col-lg-3 addTrainings">Category</th>
						<th class="col-lg-3 addTrainings">Training</th>
						<th class="col-lg-3 addTrainings">Coordinator</th>
						<th class="col-lg-3" style="text-align: center;">Action</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="toast" class='error' style='display:none'>I did something!</div>
	<%@ include file="jsFiles.jsp"%>
</body>
</html>