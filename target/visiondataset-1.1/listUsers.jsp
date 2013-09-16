<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<c:choose>

	<c:when test="${!sessionScope.user.admin}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<jsp:useBean id="Constants"
			class="br.usp.ime.vision.dataset.util.Constants" scope="session" />

		<s:set var="removeUserText">
			<s:text name="removeUser" />
		</s:set>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:text name="listUsers" />
				</h1>

				<div class="section">

					<s:url action="CreateNewUser" var="createNewUser" />
					<s:set var="createNewUserText">
						<s:text name="createNewUser" />
					</s:set>
					<div class="action">
						<a href="${createNewUser}"><img alt="${createNewUserText}"
							src="img/icons/new-user.png" />${createNewUserText}</a>
					</div>

					<table class="data-view">
						<tr>
							<th><s:text name="user.username" /></th>
							<th><s:text name="user.name" /></th>
							<th><s:text name="user.email" /></th>
							<th><s:text name="user.emailConfirmed" /></th>
							<th><s:text name="user.accountAuthorized" /></th>
							<th><s:text name="user.accountActive" /></th>
							<th><s:text name="user.permission" /></th>
							<th><s:text name="user.dateCreation" /></th>
							<th><s:text name="user.dateUpdate" /></th>
							<th><s:text name="user.actions" /></th>
						</tr>
						<c:forEach var="user" items="${users}">
							<tr>
								<td><c:out value="${user.username}" /></td>
								<td><c:out value="${user.name}" /></td>
								<td><c:out value="${user.email}" /></td>
								<td class="center"><img
									src="img/icons/${user.emailConfirmed}.png"
									alt="${user.emailConfirmed}" /></td>
								<td class="center"><img
									src="img/icons/${user.accountAuthorized}.png"
									alt="${user.accountAuthorized}" /></td>
								<td class="center"><img src="img/icons/${user.active}.png"
									alt="${user.active}" /></td>
								<td><img alt="${user.permissionString}"
									src="img/icons/permission/${user.permissionStringLowerCase}.png" />
									<c:out value="${user.permissionString}" />
								</td>
								<td><fmt:formatDate value="${user.dateCreation}"
										pattern="${Constants.datePattern}" /></td>
								<td><fmt:formatDate value="${user.dateUpdate}"
										pattern="${Constants.datePattern}" /></td>
								<td><c:if test="${user.id != sessionScope.user.id}">

										<c:choose>

											<c:when test="${!user.accountAuthorized}">
												<s:url action="AuthorizeAccount" var="authorizeAccount">
													<s:param name="username">${user.username}</s:param>
												</s:url>
												<s:set var="authorizeAccountText">
													<s:text name="authorizeAccount" />
												</s:set>
												<a href="${authorizeAccount}"
													title="${authorizeAccountText}"><img
													alt="${authorizeAccountText}"
													src="img/icons/authorize-account.png" /> </a>
											</c:when>

											<c:otherwise>

												<c:choose>

													<c:when test="${user.active}">
														<c:set var="changeUserActiveStatusText">
															<s:text name="deactivateAccount" />
														</c:set>
														<c:set var="changeUserActiveStatusIcon">img/icons/deactivate-account.png</c:set>
													</c:when>

													<c:otherwise>
														<c:set var="changeUserActiveStatusText">
															<s:text name="activateAccount" />
														</c:set>
														<c:set var="changeUserActiveStatusIcon">img/icons/activate-account.png</c:set>
													</c:otherwise>

												</c:choose>

												<s:url action="ChangeUserActiveStatus"
													var="changeUserActiveStatus">
													<s:param name="userId">${user.id}</s:param>
													<s:param name="active">${!user.active}</s:param>
												</s:url>
												<a href="${changeUserActiveStatus}"
													title="${changeUserActiveStatusText}"><img
													alt="${changeUserActiveStatusText}"
													src="${changeUserActiveStatusIcon}" /> </a>

											</c:otherwise>

										</c:choose>

										<c:if test="${user.permission != Constants.adminPermission}">
											<s:url action="SetUserPermission" var="permAdmin">
												<s:param name="username">${user.username}</s:param>
												<s:param name="permission">${Constants.adminPermission}</s:param>
											</s:url>
											<s:set var="admin">
												<s:text name="setUserPermission">
													<s:param>
														<s:text name="permission.admin" />
													</s:param>
												</s:text>
											</s:set>
											<a href="${permAdmin}" title="${admin}"><img
												alt="${admin}" src="img/icons/permission/admin.png" /> </a>
										</c:if>

										<c:if test="${user.permission != Constants.createPermission}">
											<s:url action="SetUserPermission" var="permCreate">
												<s:param name="username">${user.username}</s:param>
												<s:param name="permission">${Constants.createPermission}</s:param>
											</s:url>
											<s:set var="create">
												<s:text name="setUserPermission">
													<s:param>
														<s:text name="permission.create" />
													</s:param>
												</s:text>
											</s:set>
											<a href="${permCreate}" title="${create}"><img
												alt="${create}" src="img/icons/permission/create.png" /> </a>
										</c:if>

										<c:if test="${user.permission != Constants.viewPermission}">
											<s:url action="SetUserPermission" var="permView">
												<s:param name="username">${user.username}</s:param>
												<s:param name="permission">${Constants.viewPermission}</s:param>
											</s:url>
											<s:set var="view">
												<s:text name="setUserPermission">
													<s:param>
														<s:text name="permission.view" />
													</s:param>
												</s:text>
											</s:set>
											<a href="${permView}" title="${view}"><img alt="${view}"
												src="img/icons/permission/view.png" /> </a>
										</c:if>

										<s:url action="RemoveUser" var="removeUser">
											<s:param name="username">${user.username}</s:param>
										</s:url>
										<a href="#"
											onclick="confirmRemoveUser('${user.username}','${removeUser}');"
											title="${removeUserText}"><img alt="${removeUserText}"
											src="img/icons/delete.png" /> </a>

									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>

				<div class="confirmDialog" id="confirmRemoveUserDialog">
					<p class="warning">
						<s:text name="allUserDataWillBeLost" />
						<br />
						<s:text name="operationCannotBeUndone" />
					</p>
				</div>

			</tiles:putAttribute>

			<tiles:putAttribute name="pageScript">
		
				function confirmRemoveUser(username, actionLink) {
					$("#confirmRemoveUserDialog").dialog({
						title: "${removeUserText} " + username + "?",
						modal: true,
						buttons: {
							OK : function() {
								window.location = actionLink;
							},
							Cancel: function() {
								$(this).dialog('close');
							}
						}
					});
				}
			
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>