<%@ tag body-content="scriptless" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<template:master pageTitle="${pageTitle}">

	<jsp:body>
		<jsp:doBody />
	</jsp:body>
	
</template:master>