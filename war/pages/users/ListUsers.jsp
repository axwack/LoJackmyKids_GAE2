<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<c:url value="/user/list" var="transportReadUrl" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
<link href="styles/kendo.common.min.css" rel="stylesheet"type="text/css" />
<link href="styles/kendo.material.min.css" rel="stylesheet"type="text/css" />
<script src="js/jquery.min.js"></script>
<script src="js/kendo.web.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Users</title>
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
				<li><a href="#">Get List of Users</a></li>
				<li><a href="/SendAll">Send Message</a></li>
				<li><a href="#">&nbsp;</a></li>
			</ul>
		</div>
	</div>
	<div class="content">
		<div class="container">
			<div class="main">
				<div id="clientsDb" class="content">
					<kendo:grid name="grid" groupable="true" sortable="true" selectable="multiple" style="height:380px;">
						<kendo:grid-pageable refresh="true" pageSizes="true"
							buttonCount="5">
						</kendo:grid-pageable>
						<kendo:grid-columns>
							<kendo:grid-column title="ID" field="id" width="140" />
							<kendo:grid-column title="Email" field="email" width="190" />
							<kendo:grid-column title="Last Update" field="updatedAt"
								format="{0:MM/dd/yyyy}" width="190" />
							<kendo:grid-column title="Create Date" field="createdAt"
								format="{0:MM/dd/yyyy}" width="190" />
						</kendo:grid-columns>
						<kendo:dataSource pageSize="10" data="${ users }">
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model>
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="user" type="string" />
										<kendo:dataSource-schema-model-field name="id" type="string" />
										<kendo:dataSource-schema-model-field name="updatedAt"
											type="datetime" />
										<kendo:dataSource-schema-model-field name="createdAt"
											type="datetime" />
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-read url="${transportReadUrl}" />
							</kendo:dataSource-transport>
						</kendo:dataSource>
					</kendo:grid>
				</div>
			</div>
		</div>
		<div class="aside">
			<h3>Information</h3>
			<%@include file="/pages/login/AppEngineLogin.jsp"%>
		</div>
	</div>

	<div class="footer">(c) Vincent Lee</div>
	<style>
#clientsDb {
	width: 952px;
	height: 396px;
	margin: 20px auto 0;
	padding: 51px 4px 0 4px;
	background: url('${backgroundUrl}') no-repeat 0 0;
}
</
div
>
</style>
</body>
</html>