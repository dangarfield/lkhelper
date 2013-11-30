<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<%-- 
request - ${request}<br>
request.userPrincipal - ${request.userPrincipal}<br>
userData - ${userData}<br>
userData.isUser - ${userData.isUser}<br>
userData.isCrusader - ${userData.isCrusader}<br>
userData.isReporter - ${userData.isReporter}<br>
userData.isAdmin - ${userData.isAdmin}<br>
--%>

<header class="navbar navbar-default navbar-fixed-top" role="banner">
	<layout:container>
		<c:choose>
			<c:when test="${not empty userData}">
				<header:headerLoggedIn />
			</c:when>
			<c:otherwise>
				<header:headerNotLoggedIn />
			</c:otherwise>
		</c:choose>
	</layout:container>
</header>