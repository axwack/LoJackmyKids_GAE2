<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="styles/styles.css" type="text/css" />
	<link href="styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="styles/kendo.material.min.css" rel="stylesheet"type="text/css" />
<script src="js/jquery.min.js"></script>
<script src="js/kendo.web.min.js"></script>
<!-- Default Kendo UI theme CSS -->
<link href="styles/kendo.default.min.css" rel="stylesheet" />
<link href="styles/kendo.dataviz.min.css" rel="stylesheet" type="text/css" />
<link href="styles/kendo.dataviz.default.min.css" rel="stylesheet" type="text/css" />
</head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send Message to GCM</title>
</head>
<body>

	<div class="header">
		<div class="container">
			<h1 class="header-heading">Lo Jack My Kids App Engine Messaging</h1>

		</div>
	</div>
	<div class="nav-bar">
		<div class="container">
			<ul class="nav">
				<li><a href="/">Home</a></li>
				<li><a href="/ListUsers">Get List of Users</a></li>
				<li><a href="#">Send Message</a></li>
				<li><a href="#">&nbsp;</a></li>
			</ul>
		</div>
	</div>

	<div class="content">
		<div class="container">
			<div class="main">
				<form name="form" method='POST' action='/SendMessage'>
				<label for="User">Select Users:</label>
					<kendo:multiSelect name="RegUsers" placeholder="Select User(s)">
						<kendo:dataSource data="${ users }"></kendo:dataSource>
					</kendo:multiSelect>
					<br>				
					<textarea id="yui-t5" Name='GCMTextBox' cols='50' rows='20"'></textarea>
					<input type="input" value="AIzaSyAjYdpOzQcDqIWtHn1zzRvJipSi5L2c5eY"></input>
					<br> <input type="submit" value="Send Message"></input>
				</form>
			</div>
		</div>
		<div class="aside">
			<h3>Information</h3>
			<%@include file="/pages/login/AppEngineLogin.jsp"%>
		</div>
	</div>

	<div class="footer">
		<div class="container">(c) Vincent Lee</div>
	</div>
</body>
</html>