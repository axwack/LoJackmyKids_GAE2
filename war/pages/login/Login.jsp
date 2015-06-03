
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.principalmvl.lojackmykids.server.DatastoreServerHelper"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link rel="stylesheet" href="/styles/styles.css" type="text/css" />
<%
	UserService userService = UserServiceFactory.getUserService();

	String thisURL = request.getRequestURI(); 
%>
<title>Log In to Lo Jack My Kids</title>
</head>
<body>
<div class="header">
		<div class="container">
			<h1 class="header-heading">Lo Jack My Kids App Engine Messaging</h1>
		</div>
	</div>
	<div class ="container">
	<div class = "content">
		Please <a href="<%=userService.createLoginURL(thisURL)%>">log in.</a>
	</div>
	</div>
</body>
<div class="footer">(c) Vincent Lee</div>
</html>