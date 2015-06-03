<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="styles/styles.css" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>App Engine</title>
</head>
<body>
	<% 
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
        String thisURL = request.getRequestURI();

        response.setContentType("text/html");
        if (request.getUserPrincipal() != null) {
    %>
         <p>Hello, <%out.print(user); %>!  You can <a href="<%=userService.createLogoutURL(thisURL) %>">sign out</a>.</p>
    <% } else { %> 
           <p>Please <a href="<%=userService.createLoginURL(thisURL) %>">sign in</a>.</p>
    <% } %>
</body>
</html>