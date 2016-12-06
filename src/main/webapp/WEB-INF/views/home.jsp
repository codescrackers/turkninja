<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="<spring:url value="/resources/css/home.css"/>" type="text/css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	
	<jsp:include page="../views/fragments/header_yo.jsp"></jsp:include>			

	<div class="container">
		<div class="logo">
			<img alt="Yazılım Okulu" src="<spring:url value="/resources/images/yo_logo.png"/>">
		</div>
		<div  class="container">
		
		<h1>Yazılım Okulu</h1>
		<h3>Editörler</h3>
		<h4>Kadir Yaka</h4>
		<h4>Yusuf Önder</h4>
		<h4>Rıdvan Uyan</h4>
		<h4>Ali Turgut Bozkurt</h4>
		
		</div>
		
	</div>
</body>
</html>