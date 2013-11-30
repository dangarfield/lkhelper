<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image" %>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search" %>

<template:page pageTitle="${pageTitle}">

	<div class="bs-header castle-report" id="content">
		<div class="container">
			<h1>Compare Castles</h1>
			<p>Comparing the following castles:</p>
			<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
				<c:choose>
					<c:when test="${reportModelCastleData.allianceData.id gt 0}"> <%-- Player part of an alliance --%>
						<p>${reportModelCastleData.castleData.name} belongs to ${reportModelCastleData.playerData.name} who is part of the ${reportModelCastleData.allianceData.name} alliance</p>
					</c:when>
					<c:otherwise> <%-- Player not part of an alliance --%>
						<p>${reportModelCastleData.castleData.name} belongs to ${reportModelCastleData.playerData.name} who is not part of an alliance</p>
					</c:otherwise> <%-- TODO: Something about active status --%>
				</c:choose>
			</c:forEach>
			
			<div id="carbonads-container">
				<div class="carbonad">
					<div id="azcarbon">
						<span class="carbonad-imaged">
							<a href="">
								<img id="adzerk_ad" class="carbonad-image carbonad-img" src="/assets/images/hero/castle.png" width="130" height="100" border="0" alt="Compare Castles"/>
							</a>
						</span>
						<span class="carbonad-text">
							Compare Castles
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<layout:container cssClass="bs-docs-container castle-report">
		<layout:row>
			<layout:column cssClass="col-md-3">
				<div class="bs-sidebar sidebar-affix" data-spy="affix" data-offset-top="200">
					<div class="bs-sidebar-scroll-spy hidden-print" role="complementary">
						<ul class="nav bs-sidenav">
							<li>
								<a href="#castle-report">Castle Report</a>
								<ul class="nav">
									<li><a href="#castle-report-details">Castle Details</a></li>
									<li><a href="#castle-report-historic-points">Historic Points</a></li>
									<li><a href="#castle-report-castle-map">Castle Map</a></li>
									<li><a href="#castle-report-player-map">Castle and Player Map</a></li>
									<li><a href="#castle-report-alliance-map">Castle, Player and Alliance Map</a></li>
								</ul>
							</li>
						</ul>
					</div>
					
					<div class="data-compare">
						<search:searchCompareCastle serverId="${serverData.id}" />
					</div>
				</div>
			</layout:column>
		
			<layout:column cssClass="col-md-9" role="main">
				 
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="castle-report">Castle Report</h1>
					</div>
					
					
					
					
					<h3 id="castle-report-details">Castle Details</h3>
					
					<table class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>Castle Name</th>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<th><a href="<links:scorecardLinkCastle castleId="${reportModelCastleData.castleData.id}" serverId="${serverData.id}" />">${reportModelCastleData.castleData.name}</a></th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Castle Name (normalised)</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td>${reportModelCastleData.castleData.normalisedName}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Castle ID</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkCastle castleId="${reportModelCastleData.castleData.id}" serverId="${serverData.id}" />">${reportModelCastleData.castleData.id}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Coordinates</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td>x: ${reportModelCastleData.castleData.mapX}, y: ${reportModelCastleData.castleData.mapY}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Player</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkPlayer playerId="${reportModelCastleData.playerData.id}" serverId="${serverData.id}" />">${reportModelCastleData.playerData.name}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Player ID</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkPlayer playerId="${reportModelCastleData.playerData.id}" serverId="${serverData.id}" />">${reportModelCastleData.playerData.id}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Points</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td>${reportModelCastleData.castleData.points}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Alliance Name</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${reportModelCastleData.castleData.allianceId}" />">${reportModelCastleData.castleData.allianceName}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Alliance ID</td>
								<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${reportModelCastleData.castleData.allianceId}" />">${reportModelCastleData.castleData.allianceId}</a></td>
								</c:forEach>
							</tr>
						</tbody>
						
					</table>
					
					
					
					<h3 id="castle-report-historic-points">Historic Points</h3>
					<p>The historic points of the castle for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Points"
						data-subtitle="Castles <c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status"> - ${reportModelCastleData.castleData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Points Total"
						data-series-1-type="spline"
						data-series-2-title="Points Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelCastleData" items="${reportModelCastleDatas}" varStatus="status">
						
							<data-series
								data-series-1-name="${reportModelCastleData.castleData.name} points"
								data-series-2-name="${reportModelCastleData.castleData.name} growth"
							>
							<c:set var="previousPoints">${reportModelCastleData.historicPointsDatas[0].points}</c:set>
							
								<c:forEach var="historicPoints" items="${reportModelCastleData.historicPointsDatas}" varStatus="status">
									
									<c:set var="year"><fmt:formatDate pattern="yyyy" value="${historicPoints.lastUpdate}" /></c:set>
									<c:set var="month"><fmt:formatDate pattern="M" value="${historicPoints.lastUpdate}" /></c:set>
									<c:set var="day"><fmt:formatDate pattern="d" value="${historicPoints.lastUpdate}" /></c:set>
									
									<c:set var="points">${historicPoints.points}</c:set>
									<c:set var="growth"><c:out value="${points-previousPoints}"/></c:set>
									<c:set var="previousPoints">${historicPoints.points}</c:set>
									
									<data-value
										data-date="Date.UTC(<c:out value="${year}"/>,<c:out value="${month-1}"/>,<c:out value="${day}"/>)"
										data-series-1-data="<c:out value="${points}"/>"
										data-series-2-data="<c:out value="${growth}"/>"
									/>
									
								</c:forEach>
							</data-series>
						
						</c:forEach>
						
					</div>
					 
					
					
					
				<%--	
					
					<h3 id="castle-report-historic-castle-name">Historic Castle Name</h3>
					<p>Every recorded name change of the castle</p>
					
					<ul class="list-group">
						<c:forEach var="historicCastleName" items="${reportModelCastleData.historicCastleNameDatas}" varStatus="status">
							<c:choose>
								<c:when test="${status.count eq 1}">
									<li class="list-group-item clearfix active">
										${historicCastleName.name}
										<span class="badge">Current</span>
									</li>
								</c:when>
								<c:otherwise>
									<li class="list-group-item clearfix">
										${historicCastleName.name}
										<span class="badge">Last date: <fmt:formatDate type="date" value="${historicCastleName.lastUpdate}" /></span>
									</li>	
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</ul>
					
					
					
					
					
					
					<h3 id="castle-report-historic-player-name">Historic Players</h3>
					<p>All recorded player owners of this castle</p>
					
					<c:forEach var="historicPlayerName" items="${reportModelCastleData.historicPlayerNameDatas}" varStatus="status">
						<c:choose>
							<c:when test="${status.count eq 1}">
								<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${historicPlayerName.playerId}" />" class="list-group-item clearfix active">
									${historicPlayerName.playerName} - <i>(${historicPlayerName.playerId})</i>
									<span class="badge">Current</span>
								</a>
							</c:when>
							<c:otherwise>
								<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${historicPlayerName.playerId}" />" class="list-group-item clearfix">
									${historicPlayerName.playerName} - <i>(${historicPlayerName.playerId})</i>
									<span class="badge">Last date: <fmt:formatDate type="date" value="${historicPlayerName.lastUpdate}" /></span>
								</a>	
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
					
					
					
					
					
					
					
					<h3 id="castle-report-historic-alliance-name">Historic Alliances</h3>
					<p>All recorded alliance owners of this castle</p>
					
					<c:forEach var="historicAllianceName" items="${reportModelCastleData.historicAllianceNameDatas}" varStatus="status">
						<c:choose>
							<c:when test="${status.count eq 1}">
								<a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${historicAllianceName.allianceId}" />" class="list-group-item clearfix active">
									${historicAllianceName.allianceName} - <i>(${historicAllianceName.allianceId})</i>
									<span class="badge">Current</span>
								</a>
							</c:when>
							<c:otherwise>
								<a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${historicAllianceName.allianceId}" />" class="list-group-item clearfix">
									${historicAllianceName.allianceName} - <i>(${historicAllianceName.allianceId})</i>
									<span class="badge">Last date: <fmt:formatDate type="date" value="${historicAllianceName.lastUpdate}" /></span>
								</a>	
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
					
					
					
					
					--%>	
					
					<h3 id="castle-report-castle-map">Castle Map</h3>
					<p>A map identifying the location of the castle with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" castles="${mapMap['castles']}" />
					
					
					
					
					
					<h3 id="castle-report-player-map">Castle and Player Map</h3>
					<p>A map identifying the location of the castle in relation to the players' castles with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" castles="${mapMap['castles']}" players="${mapMap['players']}" />
					
					
					
					
					<h3 id="castle-report-alliance-map">Castle, Player and Alliance Map</h3>
					<p>A map identifying the location of the castle in relation to the players' and alliances' castles with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" castles="${mapMap['castles']}" players="${mapMap['players']}" alliances="${mapMap['alliances']}" />
					
				
				</div>
				
			</layout:column>
		</layout:row>
	</layout:container>
</template:page>