<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/home.css"/>" type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>

	<jsp:include page="../../views/fragments/header_yo.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<h1>Kitap Bölümü Ekle - Düzenle - Sil</h1>
		</div>
		<div class="row">

			<spring:url value="/admin/question-chapter/add" var="formUrl" />

			<form:form action="${formUrl}" method="POST"
				modelAttribute="chapter">

				<div class="form-group">
					<label for="chapter-name">Bölüm İsim</label>
					<form:input path="name" cssClass="form-control" id="chapter-name" />
				</div>

				<button type="submit" class="btn btn-default">Ekle</button>

			</form:form>
		</div>
	</div>
</body>
</html>