<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="serverId" required="true" %>
<%@ attribute name="searchTerm" required="true" %>
${links['URL_Search_TypeaheadPlayerBase']}/${serverId}/${searchTerm}