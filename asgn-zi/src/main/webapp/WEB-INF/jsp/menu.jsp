<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">

	<a href="${pageContext.request.contextPath}/welcome">Home</a> | &nbsp;
	<a href="${pageContext.request.contextPath}/addNewEmployee">Add Employee</a> | &nbsp;
	<a href="${pageContext.request.contextPath}/getEmployees">Show Employees</a> | &nbsp;
	<a href="#" onclick="document.forms['logoutForm'].submit()" style="color: red;font-size: large;">Logout</a>
    <form id="logoutForm" method="POST" action="${contextPath}/logout"></form>

</div>