<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>${searchTitle}</h1>
		
		<%-- TODO: Search boxes --%>
		
		<layout:container cssClass="">
			<layout:row>
				<layout:column cssClass="col-md-3">
					<search:searchComposite serverId="${serverData.id}" />
				</layout:column>
				
				<layout:column cssClass="col-md-9">
					
					<search:searchPagination />
					
					<c:choose>
						<c:when test="${not empty searchResults.results}">
							<div class="list-group results">
							
								<c:forEach var="result" items="${searchResults.results}" varStatus="status">
								
									<c:choose>
										<c:when test="${searchType eq 'castle' }">
											<a href="<links:scorecardLinkCastle serverId="${serverId}" castleId="${result.id}" />" class="list-group-item clearfix">
												<b>${result.value}</b><br/>
												${result.player}<br/>
												${result.alliance}
												<span class="badge">${result.id}</span>
											</a>
										</c:when>
										<c:when test="${searchType eq 'player' }">
											<a href="<links:scorecardLinkPlayer serverId="${serverId}" playerId="${result.id}" />" class="list-group-item clearfix">
												<b>${result.value}</b><br/>
												${result.alliance}
												<span class="badge">${result.id}</span>
											</a>
										</c:when>
										<c:when test="${searchType eq 'alliance' }">
											<a href="<links:scorecardLinkAlliance serverId="${serverId}" allianceId="${result.id}" />" class="list-group-item clearfix">
												<b>${result.value}</b>
												<span class="badge">${result.id}</span>
											</a>
										</c:when>
										<c:otherwise>
											<p>${result.value} (Unknown type)</p>
										</c:otherwise>
									</c:choose>
						
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							There were no found for your search, please try searching again
						</c:otherwise>
					</c:choose>
		
					<search:searchPagination />
					
				</layout:column>
			</layout:row>
		</layout:container>
		
	</layout:container>
</template:page>