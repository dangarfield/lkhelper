<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="image" tagdir="/WEB-INF/tags/image"%>

<template:page pageTitle="${pageTitle}">

	<div class="bs-header map-creator" id="content">
		<div class="container">
			<h1>Map Creator</h1>
			<p>Create maps of the server's castles with a customised selection of alliances, players and castles</p>
			<div id="carbonads-container">
				<div class="carbonad">
					<div id="azcarbon">
						<span class="carbonad-imaged">
							<a href="">
								<img id="adzerk_ad" class="carbonad-image carbonad-img" src="/assets/images/hero/map.png" width="130" height="100" border="0" alt="Map Creator"/>
							</a>
						</span>
						<span class="carbonad-text">
							Map Creator
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
					<search:searchMapCreatorComposite serverId="${serverData.id}" />
				</layout:column>
				
				<layout:column cssClass="col-md-9">
					<div class="map-main">
						<div class="controls">
						</div>
						<div class="image">
							<image:spinnerLoadImage src="${mapUrl}" />
						</div>
					</div>
				</layout:column>
				
			</layout:row>
		</layout:container>
	</layout:container>
	
</template:page>