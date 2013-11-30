<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="cssClass" required="false"%>
<div class="container<c:if test="${not empty cssClass}"> ${cssClass}</c:if>">
	<jsp:doBody/>
</div>