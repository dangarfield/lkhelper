<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>Message : ${message}</h1>
		<br/>
		<a href="${links['URL_Admin_AdminEditServers']}">Edit Servers</a><br/>
		<a href="${links['URL_Admin_AdminEditUsers']}">Edit Users</a><br/>
		<a href="${links['URL_Admin_AdminTriggerEnsureIndexes']}">Ensure Indexes</a><br/>
		<a href="${links['URL_Admin_AdminCreateCSVData']}">Create CSV Data</a><br/>
		<br/>
	</layout:container>
</template:page>