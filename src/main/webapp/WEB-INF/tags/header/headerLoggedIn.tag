<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header" %>

<header:mainNav>

	<ul class="nav navbar-nav navbar-right">
		<p class="navbar-text">Hi ${playerName}</p>
		<li><a href="${links['URL_User_UserEditDetails']}">Edit User Details</a></li>
		<li><a href="${links['URL_Login_Logout']}">Logout</a></li>
	</ul>
	
</header:mainNav>