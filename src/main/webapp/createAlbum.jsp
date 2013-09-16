<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when test="${!sessionScope.user.createPermission}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:url action="ListAlbums" var="listAlbums" />
					<a href="${listAlbums}"><s:text name="listAlbums" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<s:text name="createAlbum" />
				</h1>

				<div class="section">

					<v:info>
						<s:text name="validation.allFieldsRequired" />
					</v:info>

					<s:form action="CreateAlbum" focusElement="CreateAlbum_album_name"
						onsubmit="getIdsUsersPermission('CreateAlbum');">
						<s:textfield key="album.name" />
						<s:radio list="albumViewPermissions" listKey="key"
							listValue="value" key="album.viewPermission" value="0" />
						<s:radio list="albumCreatePermissions" listKey="key"
							listValue="value" key="album.createPermission" value="0" />
						<tr id="users">
							<td></td>
							<td>
								<table>
									<tr>
										<td colspan="2"><v:info>
												<s:text name="dragNDropUsers" />
											</v:info></td>
									</tr>
									<tr>
										<td><s:text name="usersWithoutPermission" />
											<ul id="usersWithoutPermission" class="dndContainer">
												<c:forEach items="${users}" var="user">
													<li id="${user.id}">${user.name} <span
														class="detailUser">(${user.username}) ${user.email}</span>
													</li>
												</c:forEach>
											</ul>
										</td>
										<td><s:text name="usersWithPermission" />
											<ul id="usersWithPermission" class="dndContainer">
											</ul>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<s:hidden name="idsUsersPermission" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

				</div>

			</tiles:putAttribute>

			<tiles:putAttribute name="pageScript">
				permissionsForm("CreateAlbum");
				restrictedPermissions("CreateAlbum",  "users");
				dragNDropUsers("usersWithoutPermission", "usersWithPermission");
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>