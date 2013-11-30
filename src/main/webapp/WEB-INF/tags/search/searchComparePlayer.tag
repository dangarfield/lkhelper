<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<h3>Compare Players</h3>
<div class="existing-compare">
	<div class="form-group">
		<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
			<c:if test="${not empty reportModelPlayerData.removeCompareUrl}">
				<a class="list-group-item clearfix" href="${links['URL_Report_PlayerReportCompareBase']}/${serverData.id}/${reportModelPlayerData.removeCompareUrl}">
					${reportModelPlayerData.playerData.name} <span class="badge"><span class="glyphicon glyphicon-remove"></span></span>
				</a>
			</c:if>
		</c:forEach>
	</div>
</div>

<div class="search-form">
	<form class="player-search compare" role="form" action="<links:searchComparePlayer serverId="${serverId}" />" method="GET" data-compare-root="${links['URL_Report_PlayerReportCompareBase']}/${serverData.id}/${currentCompare}" data-type="player">
		<div class="form-group">
			<label class="sr-only" for="player-search">Player name or id</label>
			<input type="text" class="form-control typeahead" id="player-search" name="${links['URL_Search_SearchString']}" placeholder="Player name or id" data-type="player" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadPlayer serverId="${serverId}" searchTerm="%QUERY" />"/>
			<input type="hidden" name="${links['URL_Search_SearchPage']}" value="1"/>
		</div>
		<button type="submit" class="btn btn-default">Compare</button>
	</form>
</div>

<div class="search-results">
</div>