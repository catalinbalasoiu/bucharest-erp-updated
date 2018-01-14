<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>My template - starter</title>
<c:import url="/WEB-INF/views/head.jsp" />
</head>
<body>
	<!-- HEADER -->
	<c:import url="/WEB-INF/views/header.jsp" />

	<div class="content-wrapper toggled" id="wrapper">

		<!-- Sidebar menu -->
		<c:import url="/WEB-INF/views/sidebarMenu.jsp" />

		<div class="container-fluid main-container">
			<h1>HOME PAGE</h1>
			<h2>Content here</h2>
			${contextPath}
		</div>
	</div>

	<script src="<c:url value="/resources/scripts/scripts.js" />"></script>

</body>
</html>
