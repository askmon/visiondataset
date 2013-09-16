<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when test="${sessionScope.user != null}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:text name="passwordLost" />
				</h1>

				<div class="section">

					<v:info>
						<s:text name="passwordLost.info" />
					</v:info>

					<s:form action="PasswordLost" focusElement="PasswordLost_username">
						<s:textfield key="username" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

				</div>

			</tiles:putAttribute>
		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>