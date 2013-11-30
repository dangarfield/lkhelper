<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image" %>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search" %>


<template:page pageTitle="${pageTitle}">

	<div class="bs-header alliance-report" id="content">
		<div class="container">
			<h1>Compare Alliances</h1>
			<p>Comparing the following players:</p>
			<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
				<p>${reportModelAllianceData.allianceData.name} have ${reportModelAllianceData.allianceData.memberCount} members who between them have ${reportModelAllianceData.allianceData.castleCount} castles.</p>
			</c:forEach>
				<%-- TODO: Something about active status --%>
			<div id="carbonads-container">
				<div class="carbonad">
					<div id="azcarbon">
						<span class="carbonad-imaged">
							<a href="">
								<img id="adzerk_ad" class="carbonad-image carbonad-img" src="/assets/images/hero/alliance.png" width="130" height="100" border="0" alt="Compare Alliances"/>
							</a>
						</span>
						<span class="carbonad-text">
							Compare Alliances
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<layout:container cssClass="bs-docs-container alliance-report">
		<layout:row>
			<layout:column cssClass="col-md-3">
				<div class="bs-sidebar sidebar-affix" data-spy="affix" data-offset-top="200">
					<div class="bs-sidebar-scroll-spy hidden-print" role="complementary">
						<ul class="nav bs-sidenav">
							<li>
								<a href="#alliance-report">Alliance Report</a>
								<ul class="nav">
									<li><a href="#alliance-report-details">Alliance Details</a></li>
									<li><a href="#alliance-report-historic-points">Historic Points</a></li>
									<li><a href="#alliance-report-historic-rank">Historic Rank</a></li>
									<li><a href="#alliance-report-historic-castle-count">Historic Castle Count</a></li>
								</ul>
							</li>
							<li>
								<a href="#members-report">Alliance Members Report</a>
								<ul class="nav">
									<li><a href="#members-report-inactive-members">Inactive members</a></li>
								</ul>
							</li>
							<li>
								<a href="#castles-report">Alliance Castles Report</a>
								<ul class="nav">
									<li><a href="#castles-report-castle-map">Castle Map</a></li>
									<li><a href="#castles-report-stronghold-position">Castle Alliance Stronghold Positions</a></li>
								</ul>
							</li>
						</ul>
					</div>
					
					<div class="data-compare">
						<search:searchCompareAlliance serverId="${serverData.id}" />
					</div>
				</div>
			</layout:column>
		
			<layout:column cssClass="col-md-9" role="main">
				
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="alliance-report">Alliance Report</h1>
					</div>
					
					<h3 id="alliance-report-details">Alliance Details</h3>
					
					<table class="table table-striped table-hover table-responsive">
						<thead>
							<tr>
								<th>Alliance Name</th>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<th><a href="<links:scorecardLinkAlliance allianceId="${reportModelAllianceData.allianceData.id}" serverId="${serverData.id}" />">${reportModelAllianceData.allianceData.name}</a></th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Alliance Name (normalised)</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.normalisedName}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Alliance ID</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td><a href="<links:scorecardLinkAlliance allianceId="${reportModelAllianceData.allianceData.id}" serverId="${serverData.id}" />">${reportModelAllianceData.allianceData.id}</a></td>
								</c:forEach>
							</tr>
							<tr>
								<td>Points</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.points}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Points Average</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.pointsAverage}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Member Count</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.memberCount}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Castle Count</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.castleCount}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Rank</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.allianceData.rank}</td>
								</c:forEach>
							</tr>
							<tr>
								<td>Game Status</td>
								<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
									<td>${reportModelAllianceData.gameStatusForAlliance}</td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
					
				
				
				
					<h3 id="alliance-report-historic-points">Historic Points</h3>
					<p>All historic points held by the alliance for the last week including growth</p>
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Points"
						data-subtitle="Alliances <c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status"> - ${reportModelAllianceData.allianceData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Points Total"
						data-series-1-type="spline"
						data-series-2-title="Points Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelAllianceData.allianceData.name} points"
								data-series-2-name="${reportModelAllianceData.allianceData.name} growth"
							>
							<c:set var="previousPoints">${reportModelAllianceData.allianceData.historicPoints[0].points}</c:set>
							
								<c:forEach var="historicPoints" items="${reportModelAllianceData.allianceData.historicPoints}" varStatus="status">
									
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
				
				
				
				
					<h3 id="alliance-report-historic-rank">Historic Rank</h3>
					<p>The ranking of the alliance against other alliances on the server for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Rank"
						data-subtitle="Alliances <c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status"> - ${reportModelAllianceData.allianceData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Rank Total"
						data-series-1-type="spline"
						data-series-1-yaxis-reversed="true"
						data-series-2-title="Rank Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelAllianceData.allianceData.name} rank"
								data-series-2-name="${reportModelAllianceData.allianceData.name} growth"
							>
							<c:set var="previousRank">${reportModelAllianceData.allianceData.historicRank[0].rank}</c:set>
							
								<c:forEach var="historicRank" items="${reportModelAllianceData.allianceData.historicRank}" varStatus="status">
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
				
				
				
				
				
					<h3 id="alliance-report-historic-castle-count">Historic Castle Count</h3>
					<p>The castle count of the alliance for the last week including growth</p>
					
					<div class="highchart historicPoints"
						data-chart-type="spline"
						data-title="Historic Castle Count"
						data-subtitle="Alliances <c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status"> - ${reportModelAllianceData.allianceData.name}</c:forEach>"
						data-series-count="2"
						data-series-track-growth="true"
						data-series-1-title="Castle Count"
						data-series-1-type="spline"
						data-series-2-title="Castle Growth"
						data-series-2-type="column"
					>
						<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelAllianceData.allianceData.name} points"
								data-series-2-name="${reportModelAllianceData.allianceData.name} growth"
							>
							<c:set var="previousCount">${reportModelAllianceData.allianceData.historicCastleCount[0].castleCount}</c:set>
							
								<c:forEach var="historicCastleCount" items="${reportModelAllianceData.allianceData.historicCastleCount}" varStatus="status">
									
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
					<h3 id="alliance-report-historic-alliance-name">Historic Alliance Name</h3>
					<p>Every recorded name change of the alliance</p>
					
					<ul class="list-group">
						<c:forEach var="historicAllianceName" items="${reportModelAllianceData.historicAllianceNameDatas}" varStatus="status">
							<c:choose>
								<c:when test="${status.count eq 1}">
									<li class="list-group-item clearfix active">
										${historicAllianceName.name}
										<span class="badge">Current</span>
									</li>
								</c:when>
								<c:otherwise>
									<li class="list-group-item clearfix">
										${historicAllianceName.name}
										<span class="badge">Last date: <fmt:formatDate type="date" value="${historicAllianceName.lastUpdate}" /></span>
									</li>	
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</ul>
					--%>
				
				
				
				
					
				
				</div>
				
				
				
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="members-report">Alliance Members Report</h1>
					</div>
				
				
				
				
					<%-- 
					<h3 id="members-report-all-members">All members <i>(${reportModelAllianceData.allianceData.memberCount} members)</i></h3>
					<p>All current members of the alliance</p>
					<div class="list-group">
						<c:forEach var="memberList" items="${reportModelAllianceData.allianceData.memberList}" varStatus="status">
							<c:choose>
								<c:when test="${status.count eq 0}">
									This alliance has no members
								</c:when>
								<c:when test="${status.count lt 20}">
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${memberList.playerId}" />" class="list-group-item clearfix">
										${memberList.playerName} - <i>(PlayerID: ${memberList.playerId})</i>
										<span class="badge">${memberList.points} points</span>
									</a>
								</c:when>
								<c:when test="${status.count eq 20}">
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${memberList.playerId}" />" class="list-group-item clearfix">
										${memberList.playerName} - <i>(PlayerID: ${memberList.playerId})</i>
										<span class="badge">${memberList.points} points</span>
									</a>
									<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
										Click to reveal all members
										<span class="badge">Showing 20 of ${reportModelAllianceData.allianceData.memberCount} members</span>
									</a>
								</c:when>
								<c:otherwise>
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${memberList.playerId}" />" class="list-group-item clearfix hide">
										${memberList.playerName} - <i>(PlayerID: ${memberList.playerId})</i>
										<span class="badge">${memberList.points} points</span>
									</a>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</div>
					--%>
					
					
					
					
					
					<h3 id="members-report-inactive-members">Inactive members</h3>
					<p>A breakdown of current members based on their points growth.
					A player is <b>active</b> if their points have grown by more than 10 in the last week.
					A player is <b>possibly inactive</b> if growth has been less than 10.
					A player is <b>no growth</b> if growth has been zero.
					A player is <b>losing points</b> if growth is negative.</p>
					
					<div class="highchart historicPoints"
						data-chart-type="pie"
						data-title="Inactivity Chart"
						data-subtitle="Alliances <c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status"> - ${reportModelAllianceData.allianceData.name}</c:forEach>"
						data-series-count="1"
						data-series-1-title="Activity Type"
						data-series-1-type="pie"
					>
						<c:forEach var="reportModelAllianceData" items="${reportModelAllianceDatas}" varStatus="status">
							<data-series
								data-series-1-name="${reportModelAllianceData.allianceData.name} activity type"
							>
							
								<c:forEach var="inactivePieSlice" items="${reportModelAllianceData.inactivePieChart}" varStatus="status">
									
									<data-value
										data-series-1-data="<c:out value="${inactivePieSlice.fieldName}"/>"
										data-series-1-type="String"
										data-series-2-data="<c:out value="${inactivePieSlice.quantity}"/>"
									/>
									
								</c:forEach>
							</data-series>
						</c:forEach>
					</div>
					
					
					
					<%-- 
					<div class="list-group">
						<c:forEach var="inactiveMember" items="${reportModelAllianceData.inactiveList}" varStatus="status">
							<c:choose>
								<c:when test="${status.count eq 0}">
									This alliance has no inactive members
								</c:when>
								<c:otherwise>
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${inactiveMember.playerId}" />" class="list-group-item clearfix<c:if test="${status.count gt 20}"> hide</c:if>">
										${inactiveMember.playerName} - <i>(Points: ${inactiveMember.points})</i>
										<span class="badge">${inactiveMember.gameStatus} - Points: ${inactiveMember.points}</span>
									</a>
									<c:if test="${status.count eq 20}">
										<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
											Click to reveal all inactive members
											<span class="badge">Showing 20 of ${reportModelAllianceData.inactiveListCount} inactive members</span>
										</a>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
					--%>
					
					
					
					
					<%-- 
					<h3 id="members-report-new-members">New members <i>(${reportModelAllianceData.newMembersCount} members)</i></h3>
					<p>All member that have joined the alliance with the past month</p>
					<div class="list-group">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.newMembers}">
								<c:forEach var="newMember" items="${reportModelAllianceData.newMembers}" varStatus="status">
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${newMember.playerId}" />" class="list-group-item clearfix<c:if test="${status.count gt 20}"> hide</c:if>">
										${newMember.playerName} - <i>(from: ${newMember.previousAlliance.allianceName})</i>
										<span class="badge">Left after: <fmt:formatDate type="date" value="${newMember.previousAlliance.lastUpdate}" /></span>
									</a>
									<c:if test="${status.count eq 20}">
										<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
											Click to reveal all new members
											<span class="badge">Showing 20 of ${reportModelAllianceData.newMembersCount} new members</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} does not have members that have recently joined
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					--%>
					
					
					
					<%-- 
					<h3 id="members-report-ex-members">Ex members <i>(${reportModelAllianceData.exMembersCount} members)</i></h3>
					<p>All member that have left the alliance with the past month</p>
					<div class="list-group">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.exMembers}">
								<c:forEach var="exMember" items="${reportModelAllianceData.exMembers}" varStatus="status">
									<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${exMember.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 20}"> hide</c:if>">
										${exMember.name} - <i>(to: ${exMember.historicAllianceId[0].allianceName})</i>
										<span class="badge">Left after: <fmt:formatDate type="date" value="${exMember.lastUpdate}" /></span>
									</a>
									<c:if test="${status.count eq 20}">
										<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
											Click to reveal all ex members
											<span class="badge">Showing 20 of ${reportModelAllianceData.exMembersCount} ex members</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} does not have members that have recently left
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					
					
				</div>
				
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="members-leaderboard">Alliance Members Leaderboard</h1>
					</div>
					
					 
					<h3 id="members-leaderboard-top-points">Members Top Points</h3>
					<p>Leaderboard and graph of the top points holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.points}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name}
								<span class="badge">${leaderboardData.pointsEnd} points</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					<h3 id="members-leaderboard-top-points-growth">Members Top Points Growth</h3>
					<p>Leaderboard and graph of the top points growth holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.pointsGrowth}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name} <i>(now ${leaderboardData.pointsEnd} points)</i>
								<span class="badge">${leaderboardData.pointsGrowth} points growth</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					
					<h3 id="members-leaderboard-top-castle-count">Members Top Castle Count</h3>
					<p>Leaderboard and graph of the top castle count holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.castleCount}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name}
								<span class="badge">${leaderboardData.castleCountEnd} castles</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					
					<h3 id="members-leaderboard-top-castle-count-growth">Members Top Castle Count Growth</h3>
					<p>Leaderboard and graph of the top castle count growth holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.castleCountGrowth}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name} <i>(now ${leaderboardData.castleCountEnd} castles)</i>
								<span class="badge">${leaderboardData.castleCountGrowth} new castles</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					
					<h3 id="members-leaderboard-top-rank">Members Top Ranking</h3>
					<p>Leaderboard and graph of the top rank holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.rank}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name}
								<span class="badge">ranked ${leaderboardData.rankEnd}</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					
					<h3 id="members-leaderboard-top-rank-growth">Members Top Rank Growth</h3>
					<p>Leaderboard and graph of the top rank growth holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.rankGrowth}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name} <i>(now ranked ${leaderboardData.rankEnd})</i>
								<span class="badge">jumped ${leaderboardData.rankGrowth} ranks</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					<h3 id="members-leaderboard-top-alliance-rank">Members Alliance Ranking</h3>
					<p>Leaderboard and graph of the top alliance rank holders in the alliance over the last week</p>
					<div class="list-group">
						<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.allianceRank}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name}
								<span class="badge">ranked ${leaderboardData.allianceRankEnd}</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					</div>
					
					
					<h3 id="members-leaderboard-top-alliance-rank-growth">Members Top Alliance Rank Growth</h3>
					<p>Leaderboard and graph of the top alliance rank growth holders in the alliance over the last week</p>
					<c:forEach var="leaderboardData" items="${reportModelAllianceData.allianceLeaderboardReport.allianceRankGrowth}" varStatus="status">
							<a href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${leaderboardData.id}" />" class="list-group-item clearfix<c:if test="${status.count gt 5}"> hide</c:if>">
								<c:choose>
									<c:when test="${status.count eq 1}">
										<span class="label label-warning">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 2}">
										<span class="label label-success">${status.count}</span>
									</c:when>
									<c:when test="${status.count eq 3}">
										<span class="label label-primary">${status.count}</span>
									</c:when>
									<c:otherwise>
										<span class="label label-default">${status.count}</span>
									</c:otherwise>
								</c:choose>
								${leaderboardData.name} <i>(now ranked ${leaderboardData.allianceRankEnd})</i>
								<span class="badge">jumped ${leaderboardData.allianceRankGrowth} ranks</span>
							</a>
							<c:if test="${status.count eq 5}">
								<a href="#expand-cta" class="list-group-item clearfix active expand-cta">
									Click to reveal all other members
									<span class="badge">Showing 5 of ${reportModelAllianceData.allianceLeaderboardReport.playerCount} leaderboard members</span>
								</a>
							</c:if>
						</c:forEach>
					 --%>
					
				</div>
				
				
				
				<div class="bs-docs-section">
					
					<div class="page-header">
						<h1 id="castles-report">Alliance Castles Report</h1>
					</div>
					
					
					<h3 id="castles-report-castle-map">Castle Map</h3>
					<p>A map of the alliance's castles with all castles in the background</p>
					<image:compareMapImage serverId="${serverData.id}" alliances="${mapMap['alliances']}" />
					
					
					
					<%-- 
					<h3 id="castles-report-new-castle-list-main-players">New Castle List - Through Battles <i>(${reportModelAllianceData.newCastlesForAllianceMainPlayersCount} castles)</i></h3>
					<p>All castles that have been added to the alliance with the past week</p>
					<div class="list-group join-group new-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.newCastlesForAllianceMainPlayers}">
								<c:forEach var="castleChangeData" items="${reportModelAllianceData.newCastlesForAllianceMainPlayers}" varStatus="status">
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
												New owner:
												<a class="new-player" href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${castleChangeData.playerIdEnd}" />">
													${castleChangeData.playerNameEnd}
												</a>
											</p>
											
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
											Click to reveal all new castles
											<span class="badge">Showing 10 of ${reportModelAllianceData.newCastlesForAllianceMainPlayersCount} new castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} have not captured any new castles recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					
					
					<h3 id="castles-report-new-castle-list-new-players">New Castle List - Through New Joins / Visits <i>(${reportModelAllianceData.newCastlesForAllianceNewPlayersCount} castles)</h3>
					<p>All castles that have been added to the alliance with the past week</p>
					<div class="list-group join-group new-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.newCastlesForAllianceNewPlayers}">
								<c:forEach var="castleChangeData" items="${reportModelAllianceData.newCastlesForAllianceNewPlayers}" varStatus="status">
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
												New owner:
												<a class="new-player" href="<links:scorecardLinkPlayer serverId="${serverData.id}" playerId="${castleChangeData.playerIdEnd}" />">
													${castleChangeData.playerNameEnd}
												</a>
											</p>
											
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
											Click to reveal all new castles
											<span class="badge">Showing 10 of ${reportModelAllianceData.newCastlesForAllianceNewPlayersCount} new castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} have had any new joins or visits recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					
					



					<h3 id="castles-report-lost-castle-list-main-players">Lost Castle List - Through Battles <i>(${reportModelAllianceData.lostCastlesForAllianceMainPlayersCount} castles)</i></h3>
					<p>All castles that have been lost by the alliance with the past week</p>
					<div class="list-group join-group lost-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.lostCastlesForAllianceMainPlayers}">
								<c:forEach var="castleChangeData" items="${reportModelAllianceData.lostCastlesForAllianceMainPlayers}" varStatus="status">
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
											Click to reveal all lost castles
											<span class="badge">Showing 10 of ${reportModelAllianceData.lostCastlesForAllianceMainPlayersCount} lost castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} have not lost any castles recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					
					
					
					<h3 id="castles-report-lost-castle-list-ex-players">Lost Castle List - Through Ex Players / Visits <i>(${reportModelAllianceData.lostCastlesForAllianceExPlayersCount} castles)</i></h3>
					<p>All castles that have been lost by the alliance with the past week</p>
					<div class="list-group join-group lost-castle-list">
						<c:choose>
							<c:when test="${not empty reportModelAllianceData.lostCastlesForAllianceExPlayers}">
								<c:forEach var="castleChangeData" items="${reportModelAllianceData.lostCastlesForAllianceExPlayers}" varStatus="status">
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
											Click to reveal all lost castles
											<span class="badge">Showing 10 of ${reportModelAllianceData.lostCastlesForAllianceExPlayersCount} lost castles</span>
										</a>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="list-group-item clearfix">
									${reportModelAllianceData.allianceData.name} have not had any players leave with their castles recently
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					 --%>
					
					
					
					
					<h3 id="castles-report-stronghold-position">Castle Alliance Stronghold Positions</h3>
					<p>For each grouping of the alliances' castles, this graph and map indicates where the alliances's castles are located in relation to other castles in the alliance</p>
					<p><i>(Coming soon)</i></p>
					
				</div>
			</layout:column>
		</layout:row>
	</layout:container>
</template:page>