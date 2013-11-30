<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/header" %>


<div class="navbar-header">
	<button data-target=".navbar-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</button>
	<a href="${links['URL_Home_Home']}" class="navbar-brand">LK Helper</a>
</div>
<div class="navbar-collapse collapse bs-navbar-collapse" role="navigation">
	<ul class="nav navbar-nav">
		<!-- DO SOME IF URL CONTAINS x then class="active" for each of these -->
		<%-- ${fn:contains('tempStr',"test")} --%>
		
		<li<c:if test="${moduleRootURL eq links['URL_User_UserHome']}"> class="active"</c:if>>
			<a href="${links['URL_User_UserHome']}">User Home</a>
		</li>
		
		<li<c:if test="${moduleRootURL eq links['URL_Report_ReportHome']}"> class="active"</c:if>>
			<a href="${links['URL_Report_ReportHome']}">Reports</a>
		</li>
		
		<li class="dropdown<c:if test="${moduleRootURL eq links['URL_Tools_ToolsHome']}"> active</c:if>">
			<a data-toggle="dropdown" class="dropdown-toggle" href="#">Tools <b class="caret"></b></a>
			<ul class="dropdown-menu">
				<li><a href="${links['URL_Tools_MapCreatorBase']}">Map Creator</a></li>
				<li><a href="${links['URL_Tools_BattleCalculatorBase']}">Battle Calculator</a></li>
			</ul>
		</li>
		
		
		<c:if test="${userData.isAdmin}">
			<li class="dropdown<c:if test="${moduleRootURL eq links['URL_Admin_AdminHome']}"> active</c:if>">
				<a data-toggle="dropdown" class="dropdown-toggle" href="#">Admin <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="${links['URL_Admin_AdminHome']}">Admin Home</a></li>
					<li><a href="${links['URL_Gather_GatherHome']}">Gather Home</a></li>
					<li><a href="${links['URL_Admin_AdminEditServers']}">Edit Servers</a></li>
					<li><a href="${links['URL_Admin_AdminEditUsers']}">Edit Users</a></li>
					<li><a href="${links['URL_Admin_AdminTriggerEnsureIndexes']}">Ensure Indexes</a></li>
					<li><a href="${links['URL_Admin_AdminCreateCSVData']}">Create CSV Data</a></li>
				</ul>
			</li>
		</c:if>
		
	</ul>
	
	<jsp:doBody />
	
</div>
<!--/.nav-collapse -->

