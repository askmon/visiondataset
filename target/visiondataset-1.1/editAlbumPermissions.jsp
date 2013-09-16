<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when
		test="${!(sessionScope.user.id == album.ownerId || sessionScope.user.admin)}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:url action="ListAlbums" var="listAlbums" />
					<a href="${listAlbums}"><s:text name="listAlbums" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<s:url var="linkAction" action="AlbumDetail">
						<s:param name="albumId">${album.id}</s:param>
					</s:url>
					<a href="${linkAction}"> ${album.name} </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<s:text name="editAlbumPermissions" />
				</h1>

				<div class="section">

					<s:form action="EditAlbumPermissions"
						onsubmit="getIdsUsersPermission('EditAlbumPermissions');">
						<s:hidden name="album.id" />
						<s:radio list="albumViewPermissions" listKey="key"
							listValue="value" key="album.viewPermission" />
						<s:radio list="albumCreatePermissions" listKey="key"
							listValue="value" key="album.createPermission" />
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
												<c:forEach items="${usersWithoutPermission}" var="user">
													<li id="${user.id}">${user.name} <span
														class="detailUser">(${user.username}) ${user.email}</span>
													</li>
												</c:forEach>
											</ul>
										</td>
										<td><s:text name="usersWithPermission" />
											<ul id="usersWithPermission" class="dndContainer">
												<c:forEach items="${usersWithPermission}" var="user">
													<li id="${user.id}">${user.name} <span
														class="detailUser">(${user.username}) ${user.email}</span>
													</li>
												</c:forEach>
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
				permissionsForm("EditAlbumPermissions");
				restrictedPermissions("EditAlbumPermissions",  "users");
				dragNDropUsers("usersWithoutPermission", "usersWithPermission");
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>