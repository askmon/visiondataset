<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when test="${sessionScope.user != null && !sessionScope.user.admin}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<c:if test="${sessionScope.user.admin}">
						<s:url action="ListUsers" var="listUsers" />
						<a href="${listUsers}"><s:text name="listUsers" /> </a>

						<img src="img/icons/breadcrumb.png" alt=">" />

					</c:if>
					<s:text name="createAccount" />
				</h1>

				<div class="section">

					<v:info>
						<s:text name="validation.allFieldsRequired" />
					</v:info>

					<c:choose>
						<c:when test="${sessionScope.user.admin}">
							<s:form action="AdminCreateAccount"
								focusElement="AdminCreateAccount_user_username">
								<s:textfield key="user.username" />
								<s:password key="password" />
								<s:password key="passwordConfirmation" />
								<s:textfield key="user.name" />
								<s:textfield key="user.email" />
								<s:submit key="ok" />
								<s:submit type="button" key="cancel" method="cancel" />
							</s:form>
						</c:when>
						<c:otherwise>
							<s:form action="CreateAccount"
								focusElement="CreateAccount_user_username">
								<s:textfield key="user.username" />
								<s:password key="password" />
								<s:password key="passwordConfirmation" />
								<s:textfield key="user.name" />
								<s:textfield key="user.email" />
								<s:submit key="ok" />
								<s:submit type="button" key="cancel" method="cancel" />
							</s:form>
						</c:otherwise>
					</c:choose>

				</div>

			</tiles:putAttribute>
		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>