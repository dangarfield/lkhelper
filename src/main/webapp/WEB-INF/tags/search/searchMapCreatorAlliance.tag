<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="map-creator-search alliance">
	<h3>Alliances</h3>
	
	<div class="search-form">
		<form class="alliance-search map-creator" role="form" action="<links:searchCompareAlliance serverId="${serverId}" />" method="GET" data-compare-root="${links['URL_Report_AllianceReportCompareBase']}/${serverData.id}/${currentCompare}" data-type="alliance">
			<div class="form-group">
				<label class="sr-only" for="alliance-search">Alliance name or id</label>
				<input type="text" class="form-control typeahead" id="alliance-search" name="${links['URL_Search_SearchString']}" placeholder="Alliance name or id" data-type="alliance" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadAlliance serverId="${serverId}" searchTerm="%QUERY" />"/>
				<input type="hidden" name="${links['URL_Search_SearchPage']}" value="1"/>
			</div>
			<button type="submit" class="btn btn-default">Add alliance</button>
		</form>
	</div>
	
</div>