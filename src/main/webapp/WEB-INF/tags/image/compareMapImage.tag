<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="lk" uri="/WEB-INF/tags/customTag.tld" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image"%>

<%@ attribute name="serverId" required="true" %>
<%@ attribute name="castles" required="false" %>
<%@ attribute name="players" required="false" %>
<%@ attribute name="alliances" required="false" %>

<c:set var="src" scope="request">
	${links['URL_Maps_CompareMapRoot']}${serverId}/<lk:compareMapConstructor alliances="${alliances}" players="${players}" castles="${castles}"/>.${links['URL_Maps_ImageFormat']}
</c:set>

<image:spinnerLoadImage src="${src}" />
