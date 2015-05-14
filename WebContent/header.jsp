<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="x-ua-compatible" content="IE=9"/> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body >
	<div class="username pull-right">
	<% if(request.getSession().getAttribute("userLogin")!=null)
	{%>
		<%=request.getSession().getAttribute("userLogin")%>
	<%} %>
	</div>
	<div class="jumbotron backImg">
		<div>
			<img src="images/iTeam.png">
		</div>
	</div>
	<%if(request.getSession()!=null){%>
		<script type="text/javascript">
	
		var empCode = <%=request.getSession().getAttribute("empCode")%>
	</script>
	<%} %>
	
	<%if(session.getAttribute("isAdmin") != null)
			  {
				  if((Boolean)session.getAttribute("isAdmin")){
			  }}else{
				response.sendRedirect("first.jsp");
			}
	%>
</body>
</html>