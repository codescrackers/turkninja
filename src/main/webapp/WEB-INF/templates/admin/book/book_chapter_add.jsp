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
<link rel="stylesheet"
	href="<spring:url value="/resources/css/simple-sidebar.css"/>" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="../fragments/admin_sidebar.jsp"></jsp:include>
		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">

				<div class="container">
					<div class="row">
						<h1>Kitap Bölümü Ekle</h1>
					</div>
					<div class="row">

						<spring:url value="/admin/book-chapter/add" var="formUrl" />

						<form:form action="${formUrl}" method="POST"
							modelAttribute="chapter">

							<div class="form-group">
								<form:select path="book.id">
									<form:option value="NONE" label="--- Kitap Seç ---" />
									<form:options items="${books}" />
								</form:select>
							</div>

							<div class="form-group">
								<label for="chapter-name">Bölüm İsim</label>
								<form:input path="name" cssClass="form-control"
									id="chapter-name" />
							</div>

							<button type="submit" class="btn btn-default">Ekle</button>

						</form:form>
					</div>
				</div>

			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->
</body>
</html>