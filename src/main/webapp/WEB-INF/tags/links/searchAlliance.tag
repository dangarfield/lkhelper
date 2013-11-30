<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="serverId" required="true" %>
<%@ attribute name="searchTerm" required="false" %>
<%@ attribute name="searchPage" required="false" %>
${links['URL_Search_SearchAllianceBase']}/${serverId}<c:if test="${not empty searchTerm}">?${links['URL_Search_SearchString']}=${searchTerm}</c:if><c:if test="${not empty searchPage}">&${links['URL_Search_SearchPage']}=${searchPage}</c:if>