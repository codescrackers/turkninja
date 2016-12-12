<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>

        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="${pageContext.servletContext.contextPath}/admin">
                        ADMIN PANEL
                    </a>
                </li>
                <li>
                    <a href="${pageContext.servletContext.contextPath}/admin/book">Kitaplar</a>
                    <ul>
                    	<li><a href="${pageContext.servletContext.contextPath}/admin/book">Kitapları Listele</a></li>
                    	<li><a href="${pageContext.servletContext.contextPath}/admin/book/add">Kitap Ekle</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.servletContext.contextPath}/admin/question-chapter/add">Kitap Bölümleri</a>
                </li>
                <li>
                    <a href="#">Overview</a>
                </li>
                <li>
                    <a href="#">Events</a>
                </li>
                <li>
                    <a href="#">About</a>
                </li>
                <li>
                    <a href="#">Services</a>
                </li>
                <li>
                    <a href="#">Contact</a>
                </li>
            </ul>
        </div>
        
    <script>
	    $("#menu-toggle").click(function(e) {
	        e.preventDefault();
	        $("#wrapper").toggleClass("toggled");
	    });
    </script>
        
        <!-- /#sidebar-wrapper -->