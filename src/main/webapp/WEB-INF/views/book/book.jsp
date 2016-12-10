<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<spring:url value="../../resources/css/home.css"/>" type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>

	<jsp:include page="../../views/fragments/header_yo.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<h1>Kitap Ekle - Düzenle - Sil</h1>
		</div>
		<div class="row">

			<spring:url value="/admin/book/add" var="formUrl" />

			<form:form action="${formUrl}" method="POST"
				modelAttribute="bookDTO">

				<div class="form-group">
					<label for="book-name">Kitap İsim</label>
					<form:input path="name" cssClass="form-control" id="book-name" />
					
					<label for="book-page-number">Sayfa Sayısı</label>
					<form:input path="pageNumber" cssClass="form-control" id="book-page-number" />
					
					<label for="book-author">Yazar (Birden fazla yazarı arada virgül bırakarak yazınız)</label>
					<form:input path="author" cssClass="form-control" id="book-author" />
					
					<label for="book-publisher">Yayınevi</label>
					<form:input path="publisher" cssClass="form-control" id="book-publisher" />
					
					<label for="book-edition">Baskı</label>
					<form:input path="edition" cssClass="form-control" id="book-edition" />
				</div>

				<button type="submit" class="btn btn-default">Ekle</button>

			</form:form>
		</div>
	</div>
</body>
</html>