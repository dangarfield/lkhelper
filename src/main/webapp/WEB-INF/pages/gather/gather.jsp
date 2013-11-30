<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="links" tagdir="/WEB-INF/tags/links"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>Gather Data</h1>
		
		<layout:column cssClass="col-md-12">
			
			<layout:row cssClass="user-home lk-thumbnail">
			
				<h3>Gather All Data</h3>
				
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail castle" href="${links['URL_Gather_GatherAllCastleData']}">
						<img src="/assets/images/hero/castle.png" alt="Gather All Castle Data For All Servers"/>
						<span class="text">Gather All Castle Data For All Servers</span>
					</a>
				</div>
				<div class="col-sm-6 col-md-6">
					<a class="thumbnail player" href="${links['URL_Gather_GatherAllPlayerCastleData']}">
						<img src="/assets/images/hero/player.png" alt="Gather All Player Castle Data For All Players"/>
						<span class="text">Gather All Player Castle Data For All Players</span>
					</a>
				</div>
				
			</layout:row>
			
			
			<layout:row cssClass="user-home lk-thumbnail">
			
				<h3>Gather Castle Data For Each Server</h3>
				
				<c:forEach var="serverData" items="${allServerDatas}" varStatus="status">
				
					<div class="col-sm-6 col-md-3">
						<a class="thumbnail castle" href="<links:gatherAllCastleDataForServer serverId="${serverData.id}" />">
							<img src="/assets/images/hero/castle.png" alt="Gather Castle Data For Server - ${serverData.name}"/>
							<span class="text">Gather Castle Data For Server<br/>${serverData.name}</span>
						</a>
					</div>
				
				</c:forEach>
			</layout:row>
			
			
			<layout:row cssClass="user-home lk-thumbnail">
			
				<h3>Gather Player Castle Data For Each Player</h3>
				
				<c:forEach var="userData" items="${allUserDatas}" varStatus="status">
				
					<div class="col-sm-6 col-md-3">
						<a class="thumbnail player" href="<links:gatherAllPlayerCastleDataForUser userEmail="${userData.email}" />">
							<img src="/assets/images/hero/player.png" alt="Gather All Player Castle Data - ${userData.email}"/>
							<span class="text">Gather All Player Castle Data<br/>${userData.email}<br/>on ${userData.serverId}</span>
						</a>
					</div>
					
				</c:forEach>
			</layout:row>
			
		</layout:column>
		
	</layout:container>
</template:page>