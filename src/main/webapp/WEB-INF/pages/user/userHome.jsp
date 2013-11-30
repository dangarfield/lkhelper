<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		
		<layout:column cssClass="col-md-12">
			
			<layout:row cssClass="user-home lk-thumbnail">
				<div class="col-sm-12 col-md-12">
					<h1>LK Helper User Home</h1>
					<p>Hi ${userData.email}</p>
				</div>
			</layout:row>
			
			<layout:row cssClass="user-home lk-thumbnail">
				
				<div class="col-sm-12 col-md-12">
					<a class="thumbnail report" href="${links['URL_Report_ReportHome']}">
						<img src="/assets/images/hero/report.png" alt="View Reports"/>
						<span class="text">View Reports</span>
					</a>
				</div>
				
			</layout:row>
			
			<layout:row cssClass="user-home lk-thumbnail">
			
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail player" href="${editDetailsLink}">
						<img src="/assets/images/hero/player.png" alt="Your Player Scorecard"/>
						<span class="text">Edit Your User Data</span>
					</a>
				</div>
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail castle" href="${gatherSpecificPlayerLink}">
						<img src="/assets/images/hero/castle.png" alt="Your Alliance Scorecard"/>
						<span class="text">Gather Your Castle Data</span>
					</a>
				</div>
				
			</layout:row>
			
			<layout:row cssClass="user-home lk-thumbnail">
			
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail map" href="${links['URL_Tools_MapCreatorBase']}">
						<img src="/assets/images/hero/map.png" alt="Map Creator"/>
						<span class="text">Map Creator</span>
					</a>
				</div>
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail battle" href="${links['URL_Tools_BattleCalculatorBase']}">
						<img src="/assets/images/hero/battle.png" alt="Battle Calculator"/>
						<span class="text">Battle Calculator</span>
					</a>
				</div>
				
			</layout:row>
			
		</layout:column>
	</layout:container>
</template:page>