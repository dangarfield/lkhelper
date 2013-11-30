<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search" %>
<%@ attribute name="serverId" required="true" %>

<div class="seach-composite">

	<div class="existing-compare">
		<div class="list-group map-existing"
			data-root-url="${links['URL_Tools_MapCreatorBase']}"
			data-divider-attribute="${links['URL_Maps_CompareDividerAttribute']}"
			data-divider-item="${links['URL_Maps_CompareDividerItem']}"
			data-default-thickness-castle="${defaultThicknessCastle}"
			data-default-thickness-player="${defaultThicknessPlayer}"
			data-default-thickness-alliance="${defaultThicknessAlliance}"
			data-server-id="${serverId}"
			data-colors="${allColours}"
		>
			<c:forEach var="mapCompareRequest" items="${mapCompareRequests}" varStatus="status">
				<div class="list-group-item clearfix ${mapCompareRequest.type}"
					data-type="${mapCompareRequest.type}"
					data-id="${mapCompareRequest.id}"
					data-thickness="${mapCompareRequest.thickness}"
					data-color="${mapCompareRequest.color}"
					data-path="${mapCompareRequest.path}"
					data-remove-url="${mapCompareRequest.removeURL}"
				>
					<span class="label label-default type">${mapCompareRequest.type}</span>
					
					${mapCompareRequest.textValue}
					
					<span class="badge">
						<a class="remove-from-map" href="${mapCompareRequest.removeURL}">
							<span class="glyphicon glyphicon-remove"></span>
						</a>
					</span>
					
					<span class="badge">
						<a data-toggle="dropdown" class="edit-color" href="#">
							<span class="glyphicon glyphicon-pencil" style="color:#${mapCompareRequest.color};"></span>
						</a>
						<ul class="dropdown-menu">
							<li><div class="colorpalette"></div></li>
	       				</ul>
	       			</span>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<search:searchMapCreatorAlliance serverId="${serverId}" />
	<search:searchMapCreatorPlayer serverId="${serverId}" />
	<search:searchMapCreatorCastle serverId="${serverId}" />
	
	<div class="search-results">
	</div>
</div>