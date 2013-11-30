<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<h3>Compare Alliance</h3>
<div class="existing-compare">
	<div class="form-group">
		<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
			<c:if test="${not empty reportModelAllianceData.removeCompareUrl}">
				<a class="list-group-item clearfix" href="${links['URL_Report_AllianceReportCompareBase']}/${serverData.id}/${reportModelAllianceData.removeCompareUrl}">
					${reportModelAllianceData.allianceData.name} <span class="badge"><span class="glyphicon glyphicon-remove"></span></span>
				</a>
			</c:if>
		</c:forEach>
	</div>
</div>

<div class="search-form">
	<form class="alliance-search compare" role="form" action="<links:searchCompareAlliance serverId="${serverId}" />" method="GET" data-compare-root="${links['URL_Report_AllianceReportCompareBase']}/${serverData.id}/${currentCompare}" data-type="alliance">
		<div class="form-group">
			<label class="sr-only" for="alliance-search">Alliance name or id</label>
			<input type="text" class="form-control typeahead" id="alliance-search" name="${links['URL_Search_SearchString']}" placeholder="Alliance name or id" data-type="alliance" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadAlliance serverId="${serverId}" searchTerm="%QUERY" />"/>
			<input type="hidden" name="${links['URL_Search_SearchPage']}" value="1"/>
		</div>
		<button type="submit" class="btn btn-default">Compare</button>
	</form>
</div>

<div class="search-results">
</div>