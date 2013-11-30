<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<h1>Message : ${message}</h1>
		<br/>
	</layout:container>
</template:page>