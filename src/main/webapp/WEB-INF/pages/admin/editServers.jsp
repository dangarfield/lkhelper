<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>Edit Servers</h1>
		
		<layout:row>
			<layout:column cssClass="col-sm-3">
				<ul class="list-group">
					<li class="list-group-item active">All servers (links)<span class="badge">${serverDataCount} servers</span></li>
					<c:forEach var="serverData" items="${serverDatas}" varStatus="status">
						<a href="#server${status.index}" class="list-group-item">
							<c:choose>
								<c:when test="${not empty serverData.name}">
									${serverData.name}
								</c:when>
								<c:otherwise>
									Create new server
								</c:otherwise>
							</c:choose>
						</a>
					</c:forEach>
				</ul>
			</layout:column>
			
			<c:forEach var="serverData" items="${serverDatas}" varStatus="status">
			
				<layout:column cssClass="col-sm-3">
					<div class="serverData">
						<h4 id="server${status.index}">
							<c:choose>
								<c:when test="${not empty serverData.name}">
									${serverData.name}
								</c:when>
								<c:otherwise>
									Create new server
								</c:otherwise>
							</c:choose>
						</h4>
						
						<form action="${links['URL_Admin_AdminEditServers']}" method="POST" class="form-horizontal" id="serverDataForm${status.index}">
						
							<div class="form-group">
								<label for="id${status.index}" class="col-sm-3 control-label">ID</label>
								<div class="col-sm-9">
									<input type="text" id="id${status.index}" name="id" class="form-control" placeholder="Server ID" value="<c:if test="${not empty serverData.id}">${serverData.id}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="code${status.index}" class="col-sm-3 control-label">Code</label>
								<div class="col-sm-9">
									<input type="text" id="code${status.index}" name="code" class="form-control" placeholder="Server Code" value="<c:if test="${not empty serverData.code}">${serverData.code}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="name${status.index}" class="col-sm-3 control-label">Name</label>
								<div class="col-sm-9">
									<input type="text" id="name${status.index}" name="name" class="form-control" placeholder="Server Name" value="<c:if test="${not empty serverData.name}">${serverData.name}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="url${status.index}" class="col-sm-3 control-label">URL</label>
								<div class="col-sm-9">
									<input type="text" id="url${status.index}" name="url" class="form-control" placeholder="URL" value="<c:if test="${not empty serverData.url}">${serverData.url}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="centralX${status.index}" class="col-sm-3 control-label">Central X coords</label>
								<div class="col-sm-9">
									<input type="text" id="centralX${status.index}" name="centralX" class="form-control" placeholder="Central X coords" value="<c:if test="${serverData.centralX gt 0}">${serverData.centralX}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="centralY${status.index}" class="col-sm-3 control-label">Central Y coords</label>
								<div class="col-sm-9">
									<input type="text" id="centralY${status.index}" name="centralY" class="form-control" placeholder="Central Y coords" value="<c:if test="${serverData.centralY gt 0}">${serverData.centralY}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="country${status.index}" class="col-sm-3 control-label">Country</label>
								<div class="col-sm-9">
									<input type="text" id="country${status.index}" name="country" class="form-control" placeholder="Country" value="<c:if test="${not empty serverData.country}">${serverData.country}</c:if>">
								</div>
							</div>
							<div class="form-group">
								<label for="timezone${status.index}" class="col-sm-3 control-label">Timezone</label>
								<div class="col-sm-9">
									<input type="text" id="timezone${status.index}" name="timezone" class="form-control" placeholder="Timezone" value="<c:if test="${not empty serverData.timezone}">${serverData.timezone}</c:if>">
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3">
									<button type="submit" class="btn btn-default btn-remove" data-modal-heading="Remove Server?" data-modal-body="Are you sure you want to remove the server: ${serverData.name} ?">Remove Server</button>
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