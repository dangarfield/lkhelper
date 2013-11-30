<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="player-search">
	<form class="player-search" role="form" action="<links:searchPlayer serverId="${serverId}" />" method="GET">
		<div class="form-group">
			<label class="sr-only" for="player-search">Player name or id</label>
			<input type="text" class="form-control typeahead" id="player-search" name="${links['URL_Search_SearchString']}" placeholder="Player name or id" data-type="player" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadPlayer serverId="${serverId}" searchTerm="%QUERY" />"/>
		</div>
		<button type="submit" class="btn btn-default">Find Player</button>
	</form>
</div>