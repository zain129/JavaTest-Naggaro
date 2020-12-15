<%@page session="true"%>
<html>
<head>
<title>Welcome </title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	      
	<h3 style="color: red;">Hello (${userType})</h3>
</body>
</html>