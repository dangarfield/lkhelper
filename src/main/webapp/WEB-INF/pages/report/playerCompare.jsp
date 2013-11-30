<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image" %>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>

<template:page pageTitle="${pageTitle}">

	<div class="bs-header player-report" id="content">
		<div class="container">
			<h1>Compare Players</h1>
			<p>Comparing the following players:</p>
			<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
				<c:choose>
					<c:when test="${reportModelPlayerData.playerData.allianceId gt 0}"> <%-- Player part of an alliance --%>
						<p>${reportModelPlayerData.playerData.name} is part of the ${reportModelPlayerData.allianceData.name} alliance. Overall rank: ${reportModelPlayerData.playerData.rank} and has ${reportModelPlayerData.playerData.castleCount} castles.</p>
					</c:when>
					<c:otherwise> <%-- Player not part of an alliance --%>
						<p>${reportModelPlayerData.playerData.name} is not part of an alliance. Overall rank: ${reportModelPlayerData.playerData.rank} and has ${reportModelPlayerData.playerData.castleCount} castles.</p>
					</c:otherwise> <%-- TODO: Something about active status --%>
				</c:choose>
			</c:forEach>
			
			
			
			<div id="carbonads-container">
				<div class="carbonad">
					<div id="azcarbon">
						<span class="carbonad-imaged">
							<a href="">
								<img id="adzerk_ad" class="carbonad-image carbonad-img" src="/assets/images/hero/player.png" width="130" height="100" border="0" alt="Compare Players"/>
							</a>
						</span>
						<span class="carbonad-text">
							Compare Players
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<layout:container cssClass="bs-docs-container player-report">
		<layout:row>
			<layout:column cssClass="col-md-3">
				<div class="bs-sidebar sidebar-affix" data-spy="affix" data-offset-top="200">
					<div class="bs-sidebar-scroll-spy hidden-print" role="complementary">
						<ul class="nav bs-sidenav">
							<li>
								<a href="#player-report">Player Report</a>
								<ul class="nav">
									<li><a href="#player-report-details">Player Details</a></li>
									<li><a href="#player-report-historic-points">Historic Points</a></li>
									<li><a href="#player-report-historic-rank">Historic Rank</a></li>
									<li><a href="#player-report-historic-castle-count">Historic Castle Count</a></li>
								</ul>
							</li>
							<li>
								<a href="#castle-report">Castle Report</a>
								<ul class="nav">
									<li><a href="#castle-report-castle-map">Castle Map</a></li>
									<li><a href="#castle-report-size-frequency">Castle Size Frequency</a></li>
								</ul>
							</li>
							<li>
								<a href="#alliance-report">Alliance Report</a>
								<ul class="nav">
									<li><a href="#alliance-report-historic-alliance-rank">Historic Alliance Rank</a></li>
									<li><a href="#alliance-report-alliance-castle-map">Alliance Castle Map</a></li>
								</ul>
							</li>
						</ul>
					</div>
					
					<div class="data-compare">
						<search:searchComparePlayer serverId="${serverData.id}" />
					</div>
				</div>				
				
			</layout:column>
		
			<layout:column cssClass="col-md-9" role="main">
				
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="player-report">Player Report</h1>
					</div>
					
					
					
					
					<h3 id="player-report-details">Player Details</h3>
					
					<table class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>Player Name</th>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<th><a href="<links:scorecardLinkPlayer playerId="${reportModelPlayerData.playerData.id}" serverId="${serverData.id}" />">${reportModelPlayerData.playerData.name}</a></th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Player Name (normalised)</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.playerData.normalisedName}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Player ID</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkPlayer playerId="${reportModelPlayerData.playerData.id}" serverId="${serverData.id}" />">${reportModelPlayerData.playerData.id}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Points</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.playerData.points}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Rank</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.playerData.rank}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Alliance Name</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${reportModelPlayerData.allianceData.id}" />">${reportModelPlayerData.allianceData.name}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Alliance ID</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${reportModelPlayerData.allianceData.id}" />">${reportModelPlayerData.allianceData.id}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Castle Count</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.playerData.castleCount}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Game Status</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.gameStatusForPlayer}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>On Vacation</td>
								<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
									<td>${reportModelPlayerData.playerData.onVacation}</td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
					
					
					
					
					
					<h3 id="player-report-historic-points">Historic Points</h3>
					<p>All historic points held by the player for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Points"
						data-subtitle="Players <c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status"> - ${reportModelCastleData.playerData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Points Total"
						data-series-1-type="spline"
						data-series-2-title="Points Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelPlayerData.playerData.name} points"
								data-series-2-name="${reportModelPlayerData.playerData.name} growth"
							>
							<c:set var="previousPoints">${reportModelPlayerData.playerData.historicPoints[0].points}</c:set>
							
								<c:forEach var="historicPoints" items="${reportModelPlayerData.playerData.historicPoints}" varStatus="status">
									
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
					
					
					
					
					
					<h3 id="player-report-historic-rank">Historic Rank</h3>
					<p>The ranking of the player against other players on the server for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Rank"
						data-subtitle="Players <c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status"> - ${reportModelCastleData.playerData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Rank Total"
						data-series-1-type="spline"
						data-series-1-yaxis-reversed="true"
						data-series-2-title="Rank Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelPlayerData.playerData.name} rank"
								data-series-2-name="${reportModelPlayerData.playerData.name} growth"
							>
							<c:set var="previousRank">${reportModelPlayerData.playerData.historicRank[0].rank}</c:set>
							
								<c:forEach var="historicRank" items="${reportModelPlayerData.playerData.historicRank}" varStatus="status">
									<c:set var="year"><fmt:formatDate pattern="yyyy" value="${historicRank.lastUpdate}" /></c:set>
									<c:set var="month"><fmt:formatDate pattern="M" value="${historicRank.lastUpdate}" /></c:set>
									<c:set var="day"><fmt:formatDate pattern="d" value="${historicRank.lastUpdate}" /></c:set>
									
									<c:set var="rank">${historicRank.rank}</c:set>
									<c:set var="growth"><c:out value="${previousRank-rank}"/></c:set>
									<c:set var="previousRank">${historicRank.rank}</c:set>
									
									<data-value
										data-date="Date.UTC(<c:out value="${year}"/>,<c:out value="${month-1}"/>,<c:out value="${day}"/>)"
										data-series-1-data="<c:out value="${rank}"/>"
										data-series-2-data="<c:out value="${growth}"/>"
									/>
									
								</c:forEach>
							</data-series>
						</c:forEach>
						
					</div>
					
					
					
						
					
					
					<h3 id="player-report-historic-castle-count">Historic Castle Count</h3>
					<p>The castle count of the player for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Castle Count"
						data-subtitle="Players <c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status"> - ${reportModelCastleData.playerData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Castle Count"
						data-series-1-type="spline"
						data-series-2-title="Castle Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelPlayerData.playerData.name} points"
								data-series-2-name="${reportModelPlayerData.playerData.name} growth"
							>
							<c:set var="previousCount">${reportModelPlayerData.playerData.historicCastleCount[0].castleCount}</c:set>
							
								<c:forEach var="historicCastleCount" items="${reportModelPlayerData.playerData.historicCastleCount}" varStatus="status">
									
									<c:set var="year"><fmt:formatDate pattern="yyyy" value="${historicCastleCount.lastUpdate}" /></c:set>
									<c:set var="month"><fmt:formatDate pattern="M" value="${historicCastleCount.lastUpdate}" /></c:set>
									<c:set var="day"><fmt:formatDate pattern="d" value="${historicCastleCount.lastUpdate}" /></c:set>
									
									<c:set var="castleCount">${historicCastleCount.castleCount}</c:set>
									<c:set var="growth"><c:out value="${castleCount-previousCount}"/></c:set>
									<c:set var="previousCount">${historicCastleCount.castleCount}</c:set>
									
									<data-value
										data-date="Date.UTC(<c:out value="${year}"/>,<c:out value="${month-1}"/>,<c:out value="${day}"/>)"
										data-series-1-data="<c:out value="${castleCount}"/>"
										data-series-2-data="<c:out value="${growth}"/>"
									/>
									
								</c:forEach>
							</data-series>
						</c:forEach>
					</div>
					
					
					<%--
					<h3 id="player-report-historic-player-name">Historic Player Name</h3>
					<p>Every recorded name change of the player</p>
					
					<ul class="list-group">
						<c:forEach var="historicPlayerName" items="${reportModelPlayerData.historicPlayerNameDatas}" varStatus="status">
							<c:choose>
								<c:when test="${status.count eq 1}">
									<li class="list-group-item clearfix active">
										${historicPlayerName.name}
										<span class="badge">Current</span>
									</li>
								</c:when>
								<c:otherwise>
									<li class="list-group-item clearfix">
										${historicPlayerName.name}
										<span class="badge">Last date: <fmt:formatDate type="date" value="${historicPlayerName.lastUpdate}" /></span>
									</li>	
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</ul>
					--%>
					
				</div>
				
				
				
				
				
				
				
				
				<div class="bs-docs-section">
					<div class="page-header">
						<h1 id="castle-report">Castle Report</h1>
					</div>
					
					
					
					<%-- 
					<h3 id="castle-report-castle-list">Castle List <i>(${reportModelPlayerData.castleDatasCount} castles)</i></h3>
					<p>Every castle the player current owns</p>
					<div class="list-group">
						<c:choose>
							<c:when test="${not empty reportModelPlayerData.castleDatas}">
								<c:forEach var="castleData" items="${reportModelPlayerData.castleDatas}" varStatus="status">
									<c:choose>
										<c:when test="${status.count lt 20}">
											<a href="<links:scorecardLinkCastle serverId="${serverData.id}" castleId="${castleData.id}" />" class="list-group-item clearfix">
												${castleData.name}
												<span class="badge">${castleData.points} points</span>
											</a>
										</c:when>
										<c:when test="${status.count eq 20}">
											<a href="<links:scorecardLinkCastle serverId="${serverData.id}" castleId="${castleData.id}" />" class="list-group-item clearfix">
												${castleData.name}
												<span class="badge">${castleData.points} points</span>
											</a>
											<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
												Click to reveal all castles
												<span class="badge">Showing 20 of ${reportModelPlayerData.playerData.castleCount} castles</span>
											</a>
										</c:when>
										<c:otherwise>
											<a href="<links:scorecardLinkCastle serverId="${serverData.id}" castleId="${castleData.id}" />" class="list-group-item clearfix hide">
												${castleData.name}
												<span class="badge">${castleData.points} points</span>
											</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelPlayerData.playerData.name} does not have any castles
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					--%>
					
					
					
					<%-- 
					<h3 id="castle-report-new-castle-list">New Castle List <i>(${reportModelPlayerData.newCastlesForPlayerCount} castles)</i></h3>
					<p>Castles that the player has recently taken with the last 7 days</p>
					<div class="list-group join-group new-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelPlayerData.newCastlesForPlayer}">
								<c:forEach var="castleChangeData" items="${reportModelPlayerData.newCastlesForPlayer}" varStatus="status">
									<div class="list-group-item clearfix<c:if test="${status.count gt 10}"> hide</c:if>">
										<div class="main col-md-10">
											<h4>
												<span class="label label-default">
													<a class="new-castle-name" href="<links:scorecardLinkCastle serverId="${serverData.id}" castleId="${castleChangeData.id}" />">
														${castleChangeData.castleNameEnd}
													</a>
												</span>
											</h4>
											
											<p>
												Old castle name:
												${castleChangeData.castleNameStart}
											</p>
											
											<p>
												Old player:
												<a class="old-player" href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${castleChangeData.playerIdStart}" />">
													${castleChangeData.playerNameStart}
												</a>
											</p>
												
											<p>
												Old alliance:
												<a class="old-alliance" href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${castleChangeData.allianceIdStart}" />">	
													${castleChangeData.allianceNameStart}
												</a>
											</p>
											
											<p class="date">
												Date captured:
												<fmt:formatDate type="date" value="${castleChangeData.lastUpdate}" />
											</p>
											
										</div>
										<div class="points col-md-2">
											<span class="badge">
												${castleChangeData.points} points
											</span>
										</div>
									</div>
									<c:if test="${status.count eq 10}">
										<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
											Click to reveal all members
											<span class="badge">Showing 20 of ${reportModelPlayerData.newCastlesForPlayerCount} castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelPlayerData.playerData.name} has not captured any new castles recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					--%>
					
					<%-- 
					<h3 id="castle-report-lost-castle-list">Lost Castle List <i>(${lostCastlesForPlayerCount} castles)</i></h3>
					<p>Castles that the player has recently lost with the last 7 days</p>
					<div class="list-group join-group lost-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelPlayerData.lostCastlesForPlayer}">
								<c:forEach var="castleChangeData" items="${reportModelPlayerData.lostCastlesForPlayer}" varStatus="status">
									<div class="list-group-item clearfix<c:if test="${status.count gt 10}"> hide</c:if>">
										<div class="main col-md-10">
											<h4>
												<span class="label label-default">
													<a class="old-castle-name" href="<links:scorecardLinkCastle serverId="${serverData.id}" castleId="${castleChangeData.id}" />">
														${castleChangeData.castleNameEnd}
													</a>
												</span>
											</h4>
											
											<div class="old-castle-name">
												<p>
													Old castle name:
													${castleChangeData.castleNameStart}
												</p>
											</div>
											
											<p>
												Old player:
												<a class="old-player" href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${castleChangeData.playerIdStart}" />">
													${castleChangeData.playerNameStart}
												</a>
											</p>
											<p>
												New player:
												<a class="old-player" href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${castleChangeData.playerIdEnd}" />">
													${castleChangeData.playerNameEnd}
												</a>
											</p>

											<p>
												New alliance: 
												<a class="old-alliance" href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${castleChangeData.allianceIdEnd}" />">
													${castleChangeData.allianceNameEnd}
												</a>
											</p>
											
											<p class="date">
												Date captured:
												<fmt:formatDate type="date" value="${castleChangeData.lastUpdate}" />
											</p>
										</div>
										<div class="points col-md-2">
											<span class="badge">
												${castleChangeData.points} points
											</span>
										</div>
									</div>
									<c:if test="${status.count eq 10}">
										<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
											Click to reveal all members
											<span class="badge">Showing 10 of ${reportModelPlayerData.lostCastlesForPlayerCount} castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelPlayerData.playerData.name} has not lost any castles recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					--%>
					
					<h3 id="castle-report-castle-map">Castle Map</h3>
					<p>A map of the player's castle with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" players="${mapMap['players']}" />
					
					
					
					<h3 id="castle-report-size-frequency">Castle Size Frequency</h3>
					<p>A chart of the player's castle size</p>
					
					<div class="highchart historicPoints"
						data-chart-type="column"
						data-title="Castle Size Frequency"
						data-subtitle="Players <c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status"> - ${reportModelCastleData.playerData.name}</c:forEach>"
						data-series-count="1"
						data-series-1-title="Frequency"
						data-series-1-type="column"
					>
					
						<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
							<data-categories>
								<c:forEach var="castlePointsFrequencyReport" items="${reportModelPlayerData.castlePointsFrequencyReports}" varStatus="status">
									<data-category data-category="${castlePointsFrequencyReport.pointsBand}"/>
								</c:forEach>
							</data-categories>
							
							<data-series data-series-1-name="${reportModelPlayerData.playerData.name}">
								<c:forEach var="castlePointsFrequencyReport" items="${reportModelPlayerData.castlePointsFrequencyReports}" varStatus="status">
									<data-value data-series-1-data="${castlePointsFrequencyReport.frequency}" />
								</c:forEach>
							</data-series>
						</c:forEach>
					</div>
					
				</div>
				
				
				
				
				
				
				
				
				<div class="bs-docs-section">
					<div class="page-header">
						<h1 id="alliance-report">Alliance Reports</h1>
					</div>
					
					<h3 id="alliance-report-historic-alliance-rank">Historic Alliance Rank</h3>
					<p>The ranking of the player against other players in the alliance for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Alliance Rank"
						data-subtitle="Players <c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status"> - ${reportModelCastleData.playerData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Alliance Rank Total"
						data-series-1-type="spline"
						data-series-1-yaxis-reversed="true"
						data-series-2-title="Alliance Rank Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelPlayerData" items="${reportModelPlayerDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelPlayerData.playerData.name} rank"
								data-series-2-name="${reportModelPlayerData.playerData.name} growth"
							>
							<c:set var="previousRank">${reportModelPlayerData.playerData.historicAllianceRank[0].rank}</c:set>
							
								<c:forEach var="historicAllianceRank" items="${reportModelPlayerData.playerData.historicAllianceRank}" varStatus="status">
									<c:set var="year"><fmt:formatDate pattern="yyyy" value="${historicAllianceRank.lastUpdate}" /></c:set>
									<c:set var="month"><fmt:formatDate pattern="M" value="${historicAllianceRank.lastUpdate}" /></c:set>
									<c:set var="day"><fmt:formatDate pattern="d" value="${historicAllianceRank.lastUpdate}" /></c:set>
									
									<c:set var="rank">${historicAllianceRank.rank}</c:set>
									<c:set var="growth"><c:out value="${previousRank-rank}"/></c:set>
									<c:set var="previousRank">${historicAllianceRank.rank}</c:set>
									
									<data-value
										data-date="Date.UTC(<c:out value="${year}"/>,<c:out value="${month-1}"/>,<c:out value="${day}"/>)"
										data-series-1-data="<c:out value="${rank}"/>"
										data-series-2-data="<c:out value="${growth}"/>"
									/>
									
								</c:forEach>
							</data-series>
						</c:forEach>
					</div>
					
					
					
					
				
					<%--
					<h3 id="alliance-report-historic-alliance-membership">Historic Alliance Membership</h3>
					<p>A list of all alliances this player has been a member of</p>
					
					<div class="list-group">
					<c:forEach var="historicAllianceId" items="${reportModelPlayerData.historicPlayerAllianceDatas}" varStatus="status">
						<c:choose>
							<c:when test="${status.count eq 1}">
								<a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${historicAllianceId.allianceId}" />" class="list-group-item clearfix active">
									${historicAllianceId.allianceName} - <i>(${historicAllianceId.allianceId})</i>
									<span class="badge">Current</span>
								</a>
							</c:when>
							<c:otherwise>
								<a href="<links:scorecardLinkAlliance serverId="${serverData.id}" allianceId="${historicAllianceId.allianceId}" />" class="list-group-item clearfix">
									${historicAllianceId.allianceName} - <i>(${historicAllianceId.allianceId})</i>
									<span class="badge">Last date: <fmt:formatDate type="date" value="${historicAllianceId.lastUpdate}" /></span>
								</a>	
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
					</div>
					--%>
					
					
					
					<h3 id="alliance-report-alliance-castle-map">Alliance Castle Map</h3>
					<p>A map of the player's castles and the players' alliance castles with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" players="${mapMap['players']}" alliances="${mapMap['alliances']}" />
					
					
					
					<%--
					<h3 id="alliance-report-stronghold-position">Castle Alliance Stronghold Position</h3>
					<p>For each grouping of the alliances' castles,
					this graph and map indicates where the player's castles are located in relation to other castles in the alliance</p>
					<p><i>(Coming soon)</i></p>
					--%>
				</div>
			</layout:column>
		</layout:row>
	</layout:container>
</template:page>