<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lk" uri="/WEB-INF/tags/customTag.tld" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image"%>

<%@ attribute name="serverId" required="true" %>
<%@ attribute name="allianceId" required="true" %>

<%@ attribute name="clear" required="false" %>
<%@ attribute name="thickness" required="false" %>
<%@ attribute name="base" required="false" %>
<%@ attribute name="colour" required="false" %>

<c:set var="src" scope="request">
	${links['URL_Maps_AllianceMapRoot']}${serverId}/${allianceId}.${links['URL_Maps_ImageFormat']}<lk:createQueryStringForImage clear="${clear}" thickness="${thickness}" base="${base}" colour="${colour}"/>
</c:set>

<image:spinnerLoadImage src="${src}" />