<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lk" uri="/WEB-INF/tags/customTag.tld" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image"%>

<%@ attribute name="serverId" required="true" %>
<%@ attribute name="playerId" required="true" %>
<%@ attribute name="allianceId" required="false" %>

<%@ attribute name="clear" required="false" %>
<%@ attribute name="thickness" required="false" %>
<%@ attribute name="base" required="false" %>
<%@ attribute name="colour" required="false" %>

<c:choose>
	<c:when test="${not empty allianceId}">
		<c:set var="src" scope="request">
			${links['URL_Maps_CompareMapRoot']}${serverId}/a-${allianceId}-${mapColour['DEFAULT_THICKNESS_ALLIANCE']}-${mapColour['DEFAULT_COLOUR_ALLIANCE']}_p-${playerId}-${mapColour['DEFAULT_THICKNESS_PLAYER']}-${mapColour['DEFAULT_COLOUR_PLAYER']}.${links['URL_Maps_ImageFormat']}
		</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="src" scope="request">
			${links['URL_Maps_PlayerMapRoot']}${serverId}/${playerId}.${links['URL_Maps_ImageFormat']}<lk:createQueryStringForImage clear="${clear}" thickness="${thickness}" base="${base}" colour="${colour}"/>
		</c:set>
	</c:otherwise>
</c:choose>

<image:spinnerLoadImage src="${src}" />