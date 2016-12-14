<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		
		<h2>Resources</h2>
		<table class="table table-hover">
			<tbody>
				<tr>
					<th>Name</th>
				</tr>
				<c:forEach items="${bookChapters}" var="chapter">
					<tr>
						<td><a href="<spring:url 
							value="/book/${book.urlUniqueName }/${chapter.urlUniqueName }?chapterId=${chapter.id }"/>">${chapter.name}</a></td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</body>
</html>