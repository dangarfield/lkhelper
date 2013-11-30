<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<layout:row>
			<layout:column cssClass="col-sm-6 col-sm-offset-3">
				<h1>Edit User Details</h1>
				<h3>User: ${userData.email}</h3>
	
				<p>The non-editable fields are fetched from an authenticated account.</p>
				<p>Please add a correct username, password and server and you will be re-authenticated</p>
	
				<form action="${links['URL_User_UserEditDetails']}" method="POST" class="form-horizontal">
					<div class="form-group">
						<label for="email" class="col-sm-3 control-label">Email address</label>
						<div class="col-sm-9">
							<input type="text" id="email" name="email" class="form-control" placeholder="Email address" value="${userData.email}">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-3 control-label">Password</label>
						<div class="col-sm-9 input-group">
							<input type="password" id="password" name="password" class="form-control" placeholder="Password" value="${userData.password}">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label for="serverId" class="col-sm-3 control-label">Server</label>
						<div class="col-sm-9">
							<select class="form-control" id="serverId" name="serverId">
								<c:forEach var="server" items="${allServers}">
									<option value="${server.id}"<c:if test="${server.id eq userData.serverId}"> selected="selected"</c:if>>${server.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Player ID</label>
						<div class="col-sm-9">
							<p class="form-control-static">
								<c:choose>
									<c:when test="${userData.playerId gt 0}">
										${userData.playerId} - ${playerName}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Alliance</label>
						<div class="col-sm-9">
							<p class="form-control-static">
								<c:choose>
									<c:when test="${userData.allianceId gt 0}">
										${userData.allianceId} - ${allianceName}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Authenticated</label>
						<div class="col-sm-9">
							<p class="form-control-static">
								<c:choose>
									<c:when test="${userData.isUnauthenticatedUser eq true}">
										No
									</c:when>
									<c:otherwise>
										Yes
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
	
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-9">
							<button type="submit" class="btn btn-default">Save and authenticate</button>
						</div>
					</div>
					
				</form>
			</layout:column>
		</layout:row>
	</layout:container>
</template:page>