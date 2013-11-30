<%@ tag body-content="scriptless" %>
<%@ attribute name="cssClass" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row<c:if test="${not empty cssClass}"> ${cssClass}</c:if>">
<!-- 	<div class="container"> -->
		<jsp:doBody/>
<!-- 	</div> -->
</div>