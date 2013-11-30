<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>Edit User Details</h1>
		
		<layout:row>
			<layout:column cssClass="col-sm-3">
				<ul class="list-group">
					<li class="list-group-item active">All users (links)<span class="badge">${userDataCount} users</span></li>
					<c:forEach var="userData" items="${userDatas}" varStatus="status">
						<a href="#user${status.index}" class="list-group-item">
							<c:choose>
								<c:when test="${not empty userData.email}">
									${userData.email}
								</c:when>
								<c:otherwise>
									Create new user
								</c:otherwise>
							</c:choose>
						</a>
					</c:forEach>
				</ul>
			</layout:column>
			
			
			<c:forEach var="userData" items="${userDatas}" varStatus="status">
				<layout:column cssClass="col-sm-3">
					<div class="userData">
						<h4 id="user${status.index}">
							<c:choose>
								<c:when test="${not empty userData.email}">
									${userData.email}
								</c:when>
								<c:otherwise>
									Create new user
								</c:otherwise>
							</c:choose>
						</h4>
						
						<form action="${links['URL_Admin_AdminEditUsers']}" method="POST" class="form-horizontal" id="userDataForm${status.index}">
							<div class="form-group">
								<label for="email${status.index}" class="col-sm-3 control-label">Email</label>
								<div class="col-sm-9">
									<input type="text" id="email${status.index}" name="email" class="form-control" placeholder="Email address" value="<c:if test="${not empty userData.email}">${userData.email}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="password${status.index}" class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9 input-group">
									<input type="password" id="password${status.index}" name="password" class="form-control" placeholder="Password" value="<c:if test="${not empty userData.password}">${userData.password}</c:if>">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button">
											<span class="glyphicon glyphicon-eye-open"></span>
										</button>
									</span>
								</div>
							</div>
							<div class="form-group">
								<label for="name${status.index}" class="col-sm-3 control-label">Name</label>
								<div class="col-sm-9">
									<input type="text" id="name${status.index}" name="name" class="form-control" placeholder="Name" value="<c:if test="${not empty userData.name}">${userData.name}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="serverId${status.index}" class="col-sm-3 control-label">Server</label>
								<div class="col-sm-9">
									<input type="text" id="serverId${status.index}" name="serverId" class="form-control" placeholder="Server" value='<c:if test="${not empty userData.serverId}">${userData.serverId}</c:if>'>
								</div>
							</div>
							<div class="form-group">
								<label for="playerId${status.index}" class="col-sm-3 control-label">Player</label>
								<div class="col-sm-9">
									<input type="text" id="playerId${status.index}" name="playerId" class="form-control" placeholder="Player ID" value="<c:if test="${userData.playerId gt 0}">${userData.playerId}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="allianceId${status.index}" class="col-sm-3 control-label">Alliance</label>
								<div class="col-sm-9">
									<input type="text" id="allianceId${status.index}" name="allianceId" class="form-control" placeholder="Alliance ID" value="<c:if test="${userData.allianceId gt 0}">${userData.allianceId}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="lastLogin${status.index}" class="col-sm-3 control-label">Last Login</label>
								<div class="col-sm-9">
									<input type="text" id="lastLogin${status.index}" name="lastLogin" class="form-control" placeholder="Last Login" value="<c:if test="${not empty userData.lastLogin}">${userData.lastLogin}</c:if>">
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3">
									
									<c:forEach var="possibleUserRole" items="${allUserRoles}" >
										<div class="checkbox">
											<label>
												<input type="checkbox" name="${possibleUserRole}" class=""
														<c:forEach var="existingUserRole" items="${userData.userRoles}">
															<c:if test="${possibleUserRole eq existingUserRole}">checked="checked"</c:if>
														</c:forEach>
													/>
												${possibleUserRole}
											</label>
										</div>
									</c:forEach>
										
								</div>
							</div>
							
	
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3">
									<button type="submit" class="btn btn-default btn-remove" data-modal-heading="Remove User?" data-modal-body="Are you sure you want to remove the user: ${userData.email} ?">Remove User</button>
									<button type="submit" class="btn btn-primary">Save</button>
								</div>
							</div>
							
							
						</form>
					</div>
				</layout:column>
			</c:forEach>
			
		</layout:row>
	</layout:container>
</template:page>