<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when test="${sessionScope.user == null}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:url action="Profile" var="profile" />
					<a href="${profile}"><s:text name="profile" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<s:text name="changePassword" />
				</h1>

				<div class="section">

					<v:info>
						<s:text name="validation.allFieldsRequired" />
					</v:info>

					<s:form action="ChangePassword"
						focusElement="ChangePassword_password">
						<s:password key="password" />
						<s:password key="passwordConfirmation" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

				</div>

			</tiles:putAttribute>
		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>