<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<template:page pageTitle="${pageTitle}">
	<layout:container>
		<div class="login-page">
	
			<h1>Login</h1>
	
			<c:if test="${not empty error}">
				<div class="panel panel-danger">
					Your login attempt was not successful, try again.<br /> <b>Caused :</b>
					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
					${errorMessage}
				</div>
			</c:if>
	
			<layout:row>
				<layout:column cssClass="col-sm-4 col-sm-offset-2">
					<div class="login-form">
	
						<form class='form-horizontal login-form'
							action="${links['URL_Login_LoginPostURL']}"
							data-action-login="${links['URL_Login_LoginPostURL']}"
							data-action-update-password="${links['URL_Login_LoginUpdatePassword']}"
							method='POST' role="form">
							
							<h2 class="form-heading">Please login</h2>
							
							<p class="form-details">
								If you have already registered, you can login with your Lords and Knights details.
								If you've changed your password recently, tick the box to re-authenticate
							</p>
	
							<div class="form-group">
								<label for="j_username_l" class="col-sm-3 control-label">Email</label>
								<div class="col-sm-9">
									<input type="email" class="form-control" id="j_username_l"
										placeholder="Email" name='j_username'>
								</div>
							</div>
	
							<div class="form-group">
								<label for="j_password_l" class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="j_password_l"
										placeholder="Password" name='j_password'>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<div class="checkbox">
										<label>
											<input type="checkbox" name="j_reauthenticate"> I have changed my password in the game, please re-authenticate me
										</label>
									</div>
								</div>
							</div>
	
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-default">Sign in</button>
								</div>
							</div>
	
						</form>
					</div>
				</layout:column>
				
				<layout:column cssClass="col-sm-4">
					<div class="register-form">
	
						<form class='form-horizontal'
							action="${links['URL_Login_Register']}"
							method='POST' role="form">
							
							<h2 class="form-heading">or register</h2>
							
							<p class="form-details">
								Please be aware that at the minute, we are only taking players on the <b>UK 3</b> server.<br/>
								If you would like LK Helper to come to your server, please post a comment on the <a href="${links['URL_Suggestions_Suggestions']}">suggestions page</a>
							</p>
							
							<p class="form-details">
								<b>Why do I need to add my game login?</b><br/>
								LK Helper can only gather your data for battles calculations and reports if you provide access.
								However, this is read only, stored and encrypted securely and no other user has access to your reports.
							</p>
	
							<div class="form-group">
								<label for="email_r" class="col-sm-3 control-label">Email</label>
								<div class="col-sm-9">
									<input type="email" class="form-control" id="email_r"
										placeholder="Email" name='email'>
								</div>
							</div>
	
							<div class="form-group">
								<label for="password_r" class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="password_r"
										placeholder="Password" name='password'>
								</div>
							</div>
							
							<div class="form-group">
								<label for="serverid_r" class="col-sm-3 control-label">Server</label>
								<div class="col-sm-9">
									<select class="form-control" id="serverid_r" name="server">
										<c:forEach var="server" items="${allServers}">
											<option value="${server.id}">${server.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
	
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-default">Register</button>
								</div>
							</div>
	
						</form>
					</div>
				</layout:column>
			</layout:row>
	
	
	<%-- 
			<div class="login">
				<h2>Login</h2>
				<form name='f'
					action="<c:url value='/login/j_spring_security_check' />"
					method='POST'>
					<table>
						<tr>
							<td>User:</td>
							<td><input type='text' name='j_username' value=''></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type='password' name='j_password' /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="submit" type="submit"
								value="submit" /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="reset" type="reset" /></td>
						</tr>
					</table>
	
				</form>
			</div>
	
			<div class="register">
				<h2>Register</h2>
				<form name='fr' action="<c:url value='${registerUrl}' />"
					method='POST'>
					<table>
						<tr>
							<td>User:</td>
							<td><input type='text' name='email' value=''></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type='password' name='password' /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="submit" type="submit"
								value="submit" /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="reset" type="reset" /></td>
						</tr>
					</table>
	
				</form>
			</div> --%>
		</div>
	</layout:container>
</template:page>