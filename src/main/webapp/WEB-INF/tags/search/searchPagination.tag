<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>

<div class="pagination center-block">
	<p>Showing ${searchResults.displayFrom} to ${searchResults.displayTo} of ${searchResults.totalResults} results</p>
	<%-- <p>Page ${searchResults.page} of ${searchResults.totalPages} results</p> --%>
	
	<ul class="pagination">
	
		<c:choose>
			<c:when test="${searchType eq 'castle' }">
			
				<li<c:if test="${searchResults.page=='1'}"> class="disabled"</c:if>><a href="<links:searchCastle serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="1" />">&laquo;</a></li>
				
				<c:forEach var="i" begin="1" end="${searchResults.totalPages}" varStatus="loopCounter">
					<li<c:if test="${loopCounter.count==searchResults.page}"> class="active"</c:if>><a href="<links:searchCastle serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${loopCounter.count}" />">${i}</a></li>
				</c:forEach>
				
				<li<c:if test="${searchResults.page==searchResults.totalPages}"> class="disabled"</c:if>><a href="<links:searchCastle serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${searchResults.totalPages}" />">&raquo;</a></li>
				
			</c:when>
			<c:when test="${searchType eq 'player' }">
			
				<li<c:if test="${searchResults.page=='1'}"> class="disabled"</c:if>><a href="<links:searchPlayer serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="1" />">&laquo;</a></li>
				
				<c:forEach var="i" begin="1" end="${searchResults.totalPages}" varStatus="loopCounter">
					<li<c:if test="${loopCounter.count==searchResults.page}"> class="active"</c:if>><a href="<links:searchPlayer serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${loopCounter.count}" />">${i}</a></li>
				</c:forEach>
				
				<li<c:if test="${searchResults.page==searchResults.totalPages}"> class="disabled"</c:if>><a href="<links:searchPlayer serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${searchResults.totalPages}" />">&raquo;</a></li>
				
			</c:when>
			<c:when test="${searchType eq 'alliance' }">
				
				<li<c:if test="${searchResults.page=='1'}"> class="disabled"</c:if>><a href="<links:searchAlliance serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="1" />">&laquo;</a></li>
				
				<c:forEach var="i" begin="1" end="${searchResults.totalPages}" varStatus="loopCounter">
					<li<c:if test="${loopCounter.count==searchResults.page}"> class="active"</c:if>><a href="<links:searchAlliance serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${loopCounter.count}" />">${i}</a></li>
				</c:forEach>
				
				<li<c:if test="${searchResults.page==searchResults.totalPages}"> class="disabled"</c:if>><a href="<links:searchAlliance serverId="${serverData.id}" searchTerm="${searchResults.query}" searchPage="${searchResults.totalPages}" />">&raquo;</a></li>
				
			</c:when>
			<c:otherwise>
				Unable to render pagination, unknown result type
			</c:otherwise>
		</c:choose>
		
	</ul>
</div>
