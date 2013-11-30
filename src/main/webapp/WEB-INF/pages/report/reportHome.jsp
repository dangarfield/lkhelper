<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>

<template:page pageTitle="${pageTitle}">

	<div class="bs-header report-home" id="content">
		<div class="container">
			<h1>Reports Home</h1>
			<p>This page is the hub for reports. All widely available alliances, players and castle castle data as well as being able to make comparisons between each. Leaderboards, growth, target identification and meetings are also reported</p>
			<div id="carbonads-container">
				<div class="carbonad">
					<div id="azcarbon">
						<span class="carbonad-imaged">
							<a href="">
								<img id="adzerk_ad" class="carbonad-image carbonad-img" src="/assets/images/hero/report.png" width="130" height="100" border="0" alt="Reports"/>
							</a>
						</span>
						<span class="carbonad-text">
							Reports Home
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<layout:container>

		<layout:container cssClass="">
			<layout:row>
				<layout:column cssClass="col-md-3">
					<search:searchComposite serverId="${serverData.id}" />
				</layout:column>
				
				<layout:column cssClass="col-md-9">
				
					<layout:row cssClass="lk-thumbnail">
					
						<div class="col-sm-12 col-md-12">
							<h3>Player Reports</h3>
						</div>
						
					</layout:row>
					
					<layout:row cssClass="lk-thumbnail">
					
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail player" href="${links['URL_Report_OwnPlayerReportScorecard']}">
								<img src="/assets/images/hero/player.png" alt="Your Player Scorecard"/>
								<span class="text">Your Player Scorecard</span>
							</a>
						</div>
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail alliance" href="${links['URL_Report_OwnAllianceReportScorecard']}">
								<img src="/assets/images/hero/alliance.png" alt="Your Alliance Scorecard"/>
								<span class="text">Your Alliance Scorecard</span>
							</a>
						</div>
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail castle" href="${links['URL_Report_OwnCastleReportScorecard']}">
								<img src="/assets/images/hero/castle.png" alt="Your Castle Scorecard"/>
								<span class="text">Your Castle Scorecard</span>
							</a>
						</div>
					
					</layout:row>
					
					<layout:row cssClass="lk-thumbnail">
					
						<div class="col-sm-12 col-md-12">
							<h3>Compare Reports</h3>
						</div>
						
					</layout:row>
					
				
					
					<layout:row cssClass="lk-thumbnail">
					
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail player" href="${links['URL_Report_PlayerReportCompareBase']}/${serverData.id}/${rivalPlayersComparePath}">
								<img src="/assets/images/hero/player.png" alt="Your Player Scorecard"/>
								<span class="text">Comparing you to rival members of your alliance</span>
							</a>
						</div>
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail alliance" href="${links['URL_Report_AllianceReportCompareBase']}/${serverData.id}/${rivalAlliancesComparePath}">
								<img src="/assets/images/hero/alliance.png" alt="Your Alliance Scorecard"/>
								<span class="text">Comparing your alliance to rival rank alliances</span>
							</a>
						</div>
						<div class="col-sm-4 col-md-4">
							<a class="thumbnail castle" href="${links['URL_Report_CastleReportCompareBase']}/${serverData.id}/${rivalCastlesComparePath}">
								<img src="/assets/images/hero/castle.png" alt="Your Castle Scorecard"/>
								<span class="text">Comparing castles close to one of your castles</span>
							</a>
						</div>
						
					</layout:row>

					
					<h3>Date limits</h3>
					<p>Own growth - between two dates <i>(Coming soon)</i></p>
					<p>Specific player growth - between two dates <i>(Coming soon)</i></p>
					<br/>
				
					<h3>Internal Alliance Reports <i>(Coming soon)</i></h3>
					<p>Own alliance leaderboard and prizes - between two dates <i>(Coming soon)</i></p>
					<p>Specific alliance leaderboard and prizes - between two dates <i>(Coming soon)</i></p>
					<p>Crusaders Stronghold breakdown <i>(Coming soon)</i></p>
					<br />
			
					<h3>Intra-alliance Reports <i>(Coming soon)</i></h3>
					<p>Winners and losers all alliances - between two dates <i>(Coming soon)</i></p>
					<p>Winners and losers all players - between two dates <i>(Coming soon)</i></p>
					<br />
			
					<h3>Leaderboard Reports <i>(Coming soon)</i></h3>
					<p>All alliances sorted by id / name / rank <i>(Coming soon)</i></p>
					<p>All players sorted by id / name / rank <i>(Coming soon)</i></p>
					<p>All castles sorted by id / name / rank / proximity to point <i>(Coming soon)</i></p>
					<br />
			
					<h3>Prospective Target Reports <i>(Coming soon)</i></h3>
					<p>Inactive identification - between co-ordinates / distance from point <i>(Coming soon)</i></p>
			
					<h3>Server Happenings Reports <i>(Coming soon)</i></h3>
					<p>Identify meeting alliances and visits <i>(Coming soon)</i></p>
					<br />
					
				</layout:column>
			</layout:row>
			
		</layout:container>
	</layout:container>
</template:page>