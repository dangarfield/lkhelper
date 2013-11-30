<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="lk" uri="/WEB-INF/tags/customTag.tld" %>

<template:page pageTitle="${pageTitle}">
	
	

	<div id="home-page-carousel" class="carousel slide" data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#home-page-carousel" data-slide-to="0" class="active"></li>
			<!-- <li data-target="#home-page-carousel" data-slide-to="1"></li>
			<li data-target="#home-page-carousel" data-slide-to="2"></li> -->
		</ol>
		
		<div class="carousel-inner">
			<div class="item active">
				<img src="http://lorempixel.com/g/1600/500/abstract/1/" alt="...">
				<div class="container">
					<div class="carousel-caption">
						<div class="carousel-caption">
							<h1>Welcome to Lords and Knights Helper</h1>
							<h3>Wouldn't it be nice to:</h3>
							<ul>
								<li>Have easy access to player, castle and alliance data in one place?</li>
								<li>View trends and historic information to help your alliance plan attacks?</li>
								<li>Create maps and schedule battles with your real castle data to help your alliance plan attacks or defence?</li>
							</ul>
							<p>Well, you can! Just register with your lords and knights account details and you can!</p>
							
							<a href="${links['URL_Login_Login']}" class="btn btn-primary btn-lg" role="button">Register or Login</a>

						</div>
					</div>
				</div>
			</div>
			<!--
			<div class="item">
				<img src="http://lorempixel.com/g/1600/500/abstract/2/" alt="...">
				<div class="container">
					<div class="carousel-caption">
						<div class="carousel-caption">
							<h1>Example headline 2</h1>
							<p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
							<p><a role="button" href="#" class="btn btn-lg btn-primary">Sign up today</a></p>
						</div>
					</div>
				</div>
			</div>
			<div class="item">
				<img src="http://lorempixel.com/g/1600/500/abstract/3/" alt="...">
				<div class="container">
					<div class="carousel-caption">
						<div class="carousel-caption">
							<h1>Example headline 3</h1>
							<p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
							<p><a role="button" href="#" class="btn btn-lg btn-primary">Sign up today</a></p>
						</div>
					</div>
				</div>
			</div>
			-->
		</div>
		
		<!-- Controls -->
		<a class="left carousel-control" href="#home-page-carousel" data-slide="prev">
			<span class="glyphicon glyphicon-chevron-left"></span>
		</a>
		<a class="right carousel-control" href="#home-page-carousel" data-slide="next">
			<span class="glyphicon glyphicon-chevron-right"></span>
		</a>
	</div><!-- /.carousel -->
	
		
	<layout:container>

		<layout:row cssClass="lk-thumbnail">
			<layout:column cssClass="col-md-4">
				<div class="thumbnail report">
					<img src="/assets/images/hero/report.png" alt="View Reports"/>
					<h2>View Reports</h2>
					<p>View all widely available alliances, players and castle castle data as well as being able to make comparisons between each.
						Leaderboards, growth, target identification and meetings are also reported</p>
					<p>
						<a class="btn btn-primary" href="${links['URL_Report_ReportHome']}"
							role="button">View Reports &raquo;</a>
					</p>
				</div>
			</layout:column>
	
			<layout:column cssClass="col-md-4">
				<div class="thumbnail map">
					<img src="/assets/images/hero/map.png" alt="Map Creator"/>
					<h2>Map Creator</h2>
					<p>Create a custom map or your server, highlighting any combination of specific castles, players, alliances in any colour.
					These can be be shared with others in your alliance</p>
					<p>
						<a class="btn btn-primary" href="${links['URL_Tools_MapCreatorBase']}"
							role="button">Map Creator &raquo;</a>
					</p>
				</div>
			</layout:column>
	
			<layout:column cssClass="col-md-4">
				<div class="thumbnail battle">
					<img src="/assets/images/hero/battle.png" alt="Battle Calculator"/>
					<h2>Battle Calculator</h2>
					<p>Plan and schedule attacks and defense using your own castle data.
					Troop travel and synchronisation times can be planned for multiple home castles to multiple target castles</p>
					<p>
						<a class="btn btn-primary" href="${links['URL_Tools_BattleCalculatorBase']}"
							role="button">Battle Calculator &raquo; <i>(Coming soon)</i></a>
					</p>
				</div>
			</layout:column>
	
		</layout:row>
	</layout:container>

</template:page>