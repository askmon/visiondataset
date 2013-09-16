<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<c:choose>

	<c:when test="${sessionScope.user == null}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<jsp:useBean id="Constants"
			class="br.usp.ime.vision.dataset.util.Constants" scope="session" />

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:text name="profile" />
				</h1>

				<div class="section">

					<c:set value="${sessionScope.user}" var="user" />

					<div class="action">
						<s:url action="EditProfileData" var="editProfile" />
						<s:set var="editProfileText">
							<s:text name="editProfile" />
						</s:set>
						<a href="${editProfile}"><img alt="${editProfileText}"
							src="img/icons/edit.png" />${editProfileText}</a>

						<s:url action="ChangePasswordData" var="changePassword" />
						<s:set var="changePasswordText">
							<s:text name="changePassword" />
						</s:set>
						<a href="${changePassword}"><img alt="${changePasswordText}"
							src="img/icons/password.png" />${changePasswordText}</a>
					</div>

					<table class="data-view">
						<tr>
							<th><s:text name="user.username" /></th>
							<th><s:text name="user.name" /></th>
							<th><s:text name="user.email" /></th>
							<th><s:text name="user.permission" /></th>
							<th><s:text name="user.dateCreation" /></th>
							<th><s:text name="user.dateUpdate" /></th>
						</tr>
						<tr>
							<td><c:out value="${user.username}" /></td>
							<td><c:out value="${user.name}" /></td>
							<td><c:out value="${user.email}" /></td>
							<td><img alt="${user.permissionString}"
								src="img/icons/permission/${user.permissionStringLowerCase}.png" />
								<c:out value="${user.permissionString}" />
							</td>
							<td><fmt:formatDate value="${user.dateCreation}"
									pattern="${Constants.datePattern}" /></td>
							<td><fmt:formatDate value="${user.dateUpdate}"
									pattern="${Constants.datePattern}" /></td>
						</tr>
					</table>

				</div>

			</tiles:putAttribute>
		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>