<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="alliance-search">
	<form class="alliance-search" role="form" action="<links:searchAlliance serverId="${serverId}" />" method="GET">
		<div class="form-group">
			<label class="sr-only" for="alliance-search">Alliance name or id</label>
			<input type="text" class="form-control typeahead" id="alliance-search" name="${links['URL_Search_SearchString']}" placeholder="Alliance name or id" data-type="alliance" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadAlliance serverId="${serverId}" searchTerm="%QUERY" />"/>
		</div>
		<button type="submit" class="btn btn-default">Find Alliance</button>
	</form>
</div>