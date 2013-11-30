<%@ tag body-content="scriptless"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/footer"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<un:useConstants className="com.dangarfield.lkhelper.constants.ControllerConstants" scope="request" var="links" />
<un:useConstants className="com.dangarfield.lkhelper.service.impl.ImageProcessingServiceImpl" scope="request" var="mapColour" />
<un:useConstants className="com.dangarfield.lkhelper.controller.SearchController" scope="request" var="searchController" />

<c:choose>
	<c:when test="${not empty pageTitle}">
		<c:set var="pageTitle" scope="request">LK Helper | ${pageTitle}</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" scope="request">LK Helper</c:set>
	</c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>${pageTitle}</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta name="title" content="${pageTitle}" />
<%-- 		<meta name="description" content="${metaDescription}"/> --%>
<%-- 		<meta name="keywords" content="${metaKeywords}"/> --%>
<meta name="robots" content="index, follow" />

<meta charset="utf-8" />
<!--         <meta name="viewport" content="width=device-width" /> -->

<link rel="icon" href="/assets/images/favicon/favicon.ico" type="image/x-icon" />

<template:stylesheets />

<script type="text/javascript">
function imgLoaded(img){
    var imgWrapper = img.parentNode;

    imgWrapper.className += imgWrapper.className ? ' loaded' : 'loaded';
};
</script>
</head>


<body data-offset="50" data-target=".bs-sidebar-scroll-spy" data-spy="scroll">
	
	<header:header />

	<common:globalMessage />
	
	<jsp:doBody />

	<footer:footer />
	
	<template:javascript />
</body>
<%-- 	<template:debugFooter /> --%>
</html>
