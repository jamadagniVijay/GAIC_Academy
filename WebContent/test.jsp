<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="icon" type="image/png" href="images/factslogo.ico">
<%@ include file="css.jsp"%>
<title>HCL-GAI Academy</title>

</head>
<body class="container">

	<%@ include file="jsFiles.jsp"%>
	<%@ include file="header.jsp"%>

	<script>
		/* swal({
			title: "Schedule Training!",  
			text: "Write something interesting:",
			html:"<p><input type='text' id='startDt'/><input type='text'id='endDt'/></p>",
			showCancelButton: true,
			closeOnConfirm: false,
			animation: "slide-from-top",
			inputPlaceholder: "Write something" 
		}, 
		function(){
			if (
					($('#startDt').val() === false)||($('#endDt').val() === false)
				) return false;
			if (
					($('#startDt').val() === "")||($('#endDt').val() === "")
				) {
				swal.showInputError("You need to write something!");    
				return false   
				}
			swal("Nice!", "You wrote: " + $('#startDt').val()+" "+$('#endDt').val(), "success"); 
			}); */
		
	</script>
</body>
</html>