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
	
		<h3>Authentication Job: ${jmsJob.id}<br/>
			User: ${jmsJob.userId}<br/>
			Server: ${jmsJob.serverId}<br/>
			Start Time: ${jmsJob.startTime}</h3>
	
		<p>Progress: <i>(updates automatically)</i></p>
		
		<ul class="list-group gatherLogs" 
			data-max-steps="4" 
			data-job="${jmsJob.id}"
			
			data-complete-modal="true"
			data-complete-modal-id="authenticateModelComplete"
			data-complete-modal-heading="Authentication Passed"
			data-complete-modal-body="You have succesfully been authenticated. Click below to be redirected to the user home area"
			data-complete-modal-primarylinktext="User Home"
			data-complete-modal-primarylinkaction="LK.common.openPage('${links['URL_User_UserHome']}');"
			data-complete-modal-secondarylinktext="Close"
			
			data-error-modal="true"
			data-error-modal-id="authenticateModelError"
			data-error-modal-heading="Authentication Failed"
			data-error-modal-body="You have not been authenticated. Click below to be redirected to the edit user details area where you can make changes to your login credentials"
			data-error-modal-primarylinktext="Edit credentials"
			data-error-modal-primarylinkaction="LK.common.openPage('${links['URL_User_UserEditDetails']}');"
			data-error-modal-secondarylinktext="Close"
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
				<span class="key">Step 3 - Authenticating User Credentials</span>
				<span class="badge"></span>
			</li>
			<li data-step="4" class="list-group-item step clearfix">
				<span class="glyphicon glyphicon-cloud-download"></span>
				<span class="key">Step 4 - Fetching Player Name</span>
				<span class="badge"></span>
			</li>
			<li data-step="complete" class="list-group-item clearfix active hide">
				<span class="glyphicon glyphicon-ok"></span>
				<span class="key">Authentication Complete</span>
				<span class="badge"></span>
			</li>
			<li data-step="error" class="list-group-item clearfix active hide">
				<span class="glyphicon glyphicon-ban-circle"></span>
				<span class="key">Authentication Error</span>
				<span class="badge"></span>
			</li>
		</ul>
	</layout:container>

</template:page>