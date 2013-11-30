<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<c:if test="${not empty error}">
			<div class="errorblock">
				<h4>ERROR</h4>
				<p>${errorMessage}</p>
			</div>
		</c:if>
	
		<h3>Gathering Job: ${jmsJob.id}<br/>
			User: ${jmsJob.userId}<br/>
			Server: ${jmsJob.serverId}<br/>
			Start Time: ${jmsJob.startTime}</h3>
	
		<p>Progress: <i>(updates automatically)</i></p>
		
		<ul class="list-group gatherLogs" 
			data-max-steps="7" 
			data-job="${jmsJob.id}"
			data-complete-modal="false"
			data-error-modal="false"
			>
			<li data-step="1" class="list-group-item step clearfix active">
				<span class="glyphicon glyphicon-refresh"></span>
				<span class="key">Step 1 - Waiting in queue</span>
				<span class="badge">Pending</span>
			</li>
			<li data-step="2" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-road"></span>
				<span class="key">Step 2 - Started processing from queue</span>
				<span class="badge"></span>
			</li>
			<li data-step="3" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-log-in"></span>
				<span class="key">Step 3 - Logging In</span>
				<span class="badge"></span>
			</li>
			<li data-step="4" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-map-marker"></span>
				<span class="key">Step 4 - Locating centre point of map</span>
				<span class="badge"></span>
			</li>
			<li data-step="5" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-cloud-download"></span>
				<span class="key">Step 5 - Calculating all map tiles</span>
				<span class="badge"></span>
			</li>
			<li data-step="6" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-download-alt"></span>
				<span class="key">Step 6 - Capturing remaining tiles</span>
				<span class="badge"></span>
			</li>
			<li data-step="7" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-hdd"></span>
				<span class="key">Step 7 - Updating alliance and player data based on new castle data</span>
				<span class="badge"></span>
			</li>
			<li data-step="complete" class="list-group-item clearfix active hide">
				<span class="glyphicon glyphicon-ok"></span>
				<span class="key">Step 8 - Complete</span>
				<span class="badge"></span>
			</li>
			<li data-step="error" class="list-group-item clearfix active hide">
				<span class="glyphicon glyphicon-ban-circle"></span>
				<span class="key">Error</span>
				<span class="badge"></span>
			</li>
		</ul>
	</layout:container>

</template:page>