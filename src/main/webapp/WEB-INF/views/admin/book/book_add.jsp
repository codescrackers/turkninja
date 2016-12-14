<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="<spring:url value="/resources/css/home.css"/>" type="text/css"/>
	<link rel="stylesheet" href="<spring:url value="/resources/css/simple-sidebar.css"/>"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
<div id="wrapper">
	<jsp:include page="../fragments/admin_sidebar.jsp"></jsp:include>	
        <!-- Page Content -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
            
				<div class="container">
					<div class="row">
						<h1>Kitap Ekle</h1>
					</div>
					<div class="row">
			
						<spring:url value="/admin/book/add" var="formUrl" />
			
						<form:form action="${formUrl}" method="POST"
							modelAttribute="bookDTO">
			
							<div class="form-group">
								<label for="book-name">Kitap İsmi</label>
								<label><form:errors path="name" cssClass="error" /></label>
								<form:input path="name" cssClass="form-control" id="book-name" />
								
								<label for="book-page-number">Sayfa Sayısı</label>
								<label><form:errors path="pageNumber" cssClass="error" /></label>
								<form:input path="pageNumber" cssClass="form-control" id="book-page-number" type="number"/>
								
								<label for="book-author">Yazar (Birden fazla yazarı arada virgül bırakarak yazınız)</label>
								<label><form:errors path="author" cssClass="error" /></label>
								<form:input path="author" cssClass="form-control" id="book-author" />
								
								<label for="book-publisher">Yayınevi</label>
								<label><form:errors path="publisher" cssClass="error" /></label>
								<form:input path="publisher" cssClass="form-control" id="book-publisher" />
								
								<label for="book-edition">Baskı</label>
								<label><form:errors path="edition" cssClass="error" /></label>
								<form:input path="edition" cssClass="form-control" id="book-edition" />
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