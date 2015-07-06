<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%-- use the 'taglib' directive to make the JSTL 1.0 core tags available; use the uri "http://java.sun.com/jsp/jstl/core" for JSTL 1.1 --%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page
	import="com.principalmvl.lojackmykids.server.DatastoreServerHelper"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="/styles/styles.css" type="text/css" />
<link href="./styles/kendo.common.min.css" rel="stylesheet"
	type="text/css" />
<!-- Default Kendo UI theme CSS -->
<link href="./styles/kendo.default.min.css" rel="stylesheet" />
<link href="./styles/kendo.dataviz.min.css" rel="stylesheet"
	type="text/css" />
<link href="./styles/kendo.dataviz.default.min.css" rel="stylesheet"
	type="text/css" />
</head>
<script src="./js/jquery.min.js"></script>
<script src="./js/kendo.dataviz.min.js"></script>
<head>
<meta charset="UTF-8">

<title>Lo Jack My Kids</title>

</head>
<body>
	<div class="header">
		<div class="container">
			<h1 class="header-heading">Lo Jack My Kids App Engine Messaging</h1>
		</div>
	</div>

	<%
		int total = DatastoreServerHelper.getTotalDevices();
	%>

	<div class="nav-bar">
		<div class="container">
			<ul class="nav">
				<li><a href="#">Home</a></li>
				<li><a href="<c:url value="/ListUsers"/>">Get List of Users</a></li>
				<li><a href="<c:url value="/sendUser"/>">Send Message</a></li>
				<li><a href="#">&nbsp;</a></li>
			</ul>
		</div>
	</div>
	<div class="content">
		<div class="container">
			<div class="main">
				<%
					if (total == 0) {
				%>
				<h1>No devices registered</h1>
				<%
					} else {
				%>
				<h1>
					<%
						out.print(total);
						}
					%>
					device(s) registered!
				</h1>
			</div>
			<div class="aside">
				<h1>Information</h1>
				<h3>
					<p>
						Welcome back
						<sec:authentication property="principal.nickname" />
						.
					</p>
				</h3>
				<h4>
					Logout <a href="/logout.jsp">here.</a>
				</h4>
			</div>
		</div>
	</div>
	<div class="footer">
		(c) Vincent Lee
		<p>
			User Registered as :
			<sec:authentication property="principal.forename" />
			&nbsp;
			<sec:authentication property="principal.surname" />
			<sec:authentication property="principal.nickname" />
		<p>
	</div>
</body>


</html>