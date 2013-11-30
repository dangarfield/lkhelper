<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ attribute name="serverId" required="true" %>

<div class="castle-search">
	<form class="castle-search" role="form" action="<links:searchCastle serverId="${serverId}" />" method="GET">
		<div class="form-group">
			<label class="sr-only" for="castle-search">Castle name or id</label>
			<input type="text" class="form-control typeahead" id="castle-search" name="${links['URL_Search_SearchString']}" placeholder="Castle name or id" data-type="castle" data-limit="${searchController['RESULTS_FOR_TYPEAHEAD']}" data-remote="<links:typeaheadCastle serverId="${serverId}" searchTerm="%QUERY" />"/>
		</div>
		<button type="submit" class="btn btn-default">Find Castle</button>
	</form>
</div>