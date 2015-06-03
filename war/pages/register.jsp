
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/styles/styles.css" type="text/css" />s" />
<title>Registration</title>
</head>
<body>
<div id="content">
<p>
Welcome to the Spring Security GAE sample application, <sec:authentication property="principal.nickname" />.
Please enter your registration details in order to use the application.
</p>
<p>
The data you enter here will be registered in the application's GAE data store, keyed under your unique
Google Accounts identifier. It doesn't have to be accurate. When you log in again, the information will be automatically
retrieved.
</p>

<form:form id="register" method="post" modelAttribute="registrationForm">
  	<fieldset>
  		<form:label path="forename">
  		First Name:
 		</form:label> <form:errors path="forename" cssClass="fieldError" /><br />
  		<form:input path="forename" /> <br />

  		<form:label path="surname">
  		Last Name:
 		</form:label><form:errors path="surname" cssClass="fieldError" /> <br />
  		<form:input path="surname" /><br />
  		
  		<form:label path="email">
  		Email:
 		</form:label><form:errors path="email" cssClass="fieldError" /> <br />
  		<form:input path="email" /><br />
  		
  		<form:label path="password">
  		Password:
 		</form:label><form:errors path="password" cssClass="fieldError" /> <br />
  		<form:input path="password" /><br />
	</fieldset>
	<input type="submit" value="Register">
</form:form>
</body>
</div>
</html>
