<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<tiles:insertDefinition name="templateVisionDataset">
	<tiles:putAttribute name="content">

		<h1>HOME...</h1>

		<c:if test="${sessionScope.user == null}">

			<div id="infoHome">

				<div class="action">
					<p>
						<s:text name="createAccount.help" />
					</p>
					<s:url action="SignUp" var="signUp" />
					<c:set var="signUpText">
						<s:text name="createAccount" />
					</c:set>
					<a href="${signUp}"><img alt="${signUpText}"
						src="img/icons/new-user.png" />${signUpText}</a>
				</div>

				<div class="action">
					<p>
						<s:text name="passwordLost.help" />
					</p>
					<s:url action="PasswordRecovery" var="passwordLost" />
					<c:set var="passwordLostText">
						<s:text name="passwordLost" />
					</c:set>
					<a href="${passwordLost}"><img alt="${passwordLostText}"
						src="img/icons/password.png" />${passwordLostText}</a>
				</div>

			</div>

		</c:if>

	</tiles:putAttribute>
</tiles:insertDefinition>
