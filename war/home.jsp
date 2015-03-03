<!DOCTYPE html>

<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>

<%-- use the 'taglib' directive to make the JSTL 1.0 core tags available; use the uri "http://java.sun.com/jsp/jstl/core" for JSTL 1.1 --%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.principalmvl.lojackmykids.server.Datastore"%>


<html>
<link href="./styles/kendo.common.min.css" rel="stylesheet"
	type="text/css" />
<!-- Default Kendo UI theme CSS -->
<link href="./styles/kendo.default.min.css" rel="stylesheet" />
<link href="./styles/kendo.dataviz.min.css" rel="stylesheet"
	type="text/css" />
<link href="./styles/kendo.dataviz.default.min.css" rel="stylesheet"
	type="text/css" />

<script src="./js/jquery.min.js"></script>
<script src="./js/kendo.dataviz.min.js"></script>
<head>
<meta charset="UTF-8">

<title>Lo Jack My Kids</title>

</head>
<body>


	<% int total = Datastore.getTotalDevices(); 
	if (total == 0) { 
	  %>
	No devices registered
	<%} else {%>
	<h2>
		<% out.print(total); }%>
		device(s) registered!
	</h2>
	<form name='form' method='POST' action='sendAll'>
		<input type='submit' value='Send Message' />
	</form>

</body>
</html>