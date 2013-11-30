<%@ tag body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>

<header:mainNav>

	<ul class="nav navbar-nav navbar-right">
		<li><a href="${links['URL_Login_Login']}">Register</a></li>
	</ul>
	
	<form class="navbar-form navbar-right header-login" action="${links['URL_Login_LoginPostURL']}" method="post">
		<div class="form-group">
			<input name="j_username" type="text" placeholder="Email" class="form-control">
		</div>
		<div class="form-group">
			<input name="j_password" type="password" placeholder="Password" class="form-control">
		</div>
		<button type="submit" class="btn btn-success">Log in</button>
	</form>
	
</header:mainNav>