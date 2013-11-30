<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search" %>
<%@ attribute name="serverId" required="true" %>

<div class="seach-composite">
	<h3>Search</h3>
	<search:searchAlliance serverId="${serverId}" />
	<search:searchPlayer serverId="${serverId}" />
	<search:searchCastle serverId="${serverId}" />
</div>