<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="map-creator-search castle">
	<h3>Castles</h3>
	
	<div class="search-form">
		<form class="castle-search map-creator" role="form" action="<links:searchCompareCastle serverId="${serverId}" />" method="GET" data-compare-root="${links['URL_Report_CastleReportCompareBase']}/${serverData.id}/${currentCompare}" data-type="castle">
			<div class="form-group">
				<label class="sr-only" for="castle-search">Castle name or id</label>
				<input type="text" class="form-control typeahead" id="castle-search" name="${links['URL_Search_SearchString']}" placeholder="Castle name or id" data-type="castle" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadCastle serverId="${serverId}" searchTerm="%QUERY" />"/>
				<input type="hidden" name="${links['URL_Search_SearchPage']}" value="1"/>
			</div>
			<button type="submit" class="btn btn-default">Add castles</button>
		</form>
	</div>
	
</div>