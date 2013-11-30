<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="cssClass" required="true"%>
<%@ attribute name="role" required="false"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<div class="${cssClass}"<c:if test="${not empty role}"> role="${role}"</c:if>>
	<jsp:doBody/>
</div>