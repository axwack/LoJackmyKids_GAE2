<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib  prefix="form"uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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

<title>Registration</title>
</head>
<body>
	<div class="header">
		<div class="container">
			<h1 class="header-heading">Lo Jack My Kids App Engine Messaging</h1>
		</div>
	</div>
	<p>
		Welcome to Lo Jack My Kids
		<sec:authentication property="principal.nickname" />
		. Please enter your registration details in order to use the
		application.
	</p>
	<p>The data you enter here will be registered in the application's
		GAE data store, keyed under your unique Google Accounts identifier. It
		doesn't have to be accurate. When you log in again, the information
		will be automatically retrieved.</p>
	<div class="content">
		<div class="container">
			<div class="main">
				<div class="aside">
					<form:form id="register" method="post"
						modelAttribute="registrationForm">
						<fieldset>
							<form:label path="forename">First Name:</form:label>
							<form:errors path="forename" cssClass="fieldError" />
							<br />
							<form:input path="forename" />
							<br />

							<form:label path="surname">Last Name:</form:label>
							<form:errors path="surname" cssClass="fieldError" />
							<br />
							<form:input path="surname" />
							<br />

							<form:label path="email">Email:</form:label>
							<form:errors path="email" cssClass="fieldError" />
							<br />
							<form:input path="email" />
							<br />

							<form:label path="password">Password:</form:label>
							<form:errors path="password" cssClass="fieldError" />
							<br />
							<form:input path="password" />
							<br />
						</fieldset>
						<input type="submit" value="Register">
					</form:form>
				</div>
			</div>
		</div>
		</div>
</body>
&nbsp;
<div class="footer">(c) Vincent Lee  <p>User Registered at: <sec:authentication property="principal.nickname"/>&nbsp; <sec:authentication property="principal.forename"/> <sec:authentication property="principal.surname"/><p>
<sec:authorize access="hasRole('NEW_USER')"><b>New User Role</b></sec:authorize>
</div>

</html>
