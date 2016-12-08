<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="navbar navbar-default">
		<div class="container-fluid">
		
			<div class="navbar-header">
      			<a class="navbar-brand" href="#">Yazılım OKULU</a>
    		</div>
    		
    		<ul class="nav navbar-nav">
    		
    			<li><a href="#">Ana Sayfa</a></li>
    		
    			<li class="dropdown">
          			
          			<a href="#" class="dropdown-toggle" 
          				data-toggle="dropdown" role="button" 
          				aria-expanded="false">Kategoriler <span class="caret"></span></a>
          	
          			<ul class="dropdown-menu" role="menu">
            			<li><a href="">Java</a></li>
            			<li><a href="">JavaEE</a></li>
            			<li><a href="">Spring-core</a></li>
            			<li><a href="">Spring-MVC</a></li>
            			<li><a href="">Spring-Data</a></li>
            			<li><a href="">Spring-Security</a></li>
            			<li><a href="">MongoDB</a></li>
          			</ul>
          			
        		</li>
        		
    			<li class="dropdown">
          		
          			<a href="#" class="dropdown-toggle" 
          				data-toggle="dropdown" role="button" 
          				aria-expanded="false">Cracking Interview Questions Çözümleri <span class="caret"></span></a>
          			<ul class="dropdown-menu" role="menu">
	          		<c:forEach items="${questionChapter}" var="chapter">
						<li><a href="<spring:url 
								value="/${chapter.id}/${chapter.urlUniqueName}"/>">${chapter.name}</a></li>
					</c:forEach>
          			</ul>
        		
        		</li>
				       		
    		</ul>
    		
		</div>
</nav>