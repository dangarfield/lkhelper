<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="map-creator-search player">
	<h3>Players</h3>
	
	<div class="search-form">
		<form class="player-search map-creator" role="form" action="<links:searchComparePlayer serverId="${serverId}" />" method="GET" data-compare-root="${links['URL_Report_PlayerReportCompareBase']}/${serverData.id}/${currentCompare}" data-type="player">
			<div class="form-group">
				<label class="sr-only" for="player-search">Player name or id</label>
				<input type="text" class="form-control typeahead" id="player-search" name="${links['URL_Search_SearchString']}" placeholder="Player name or id" data-type="player" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadPlayer serverId="${serverId}" searchTerm="%QUERY" />"/>
				<input type="hidden" name="${links['URL_Search_SearchPage']}" value="1"/>
			</div>
			<button type="submit" class="btn btn-default">Add players</button>
		</form>
	</div>
	
</div>