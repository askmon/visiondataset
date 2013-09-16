<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<jsp:useBean id="Constants"
	class="br.usp.ime.vision.dataset.util.Constants" scope="session" />

<c:choose>

	<c:when test="${!userHasViewPermission}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<s:set var="deleteImageText">
			<s:text name="deleteImage" />
		</s:set>
		<s:set var="deleteAlbumText">
			<s:text name="deleteAlbum" />
		</s:set>
		<s:set var="deleteTagText">
			<s:text name="deleteAlbumTag" />
		</s:set>
		<s:set var="renameTagText">
			<s:text name="renameTag" />
		</s:set>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:url action="ListAlbums" var="listAlbums" />
					<a href="${listAlbums}"><s:text name="listAlbums" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<c:forEach items="${album.parents}" var="parentAlbum">

						<s:url var="linkAction" action="AlbumDetail">
							<s:param name="albumId">${parentAlbum.id}</s:param>
						</s:url>
						<a href="${linkAction}"> ${parentAlbum.name} </a>

						<img src="img/icons/breadcrumb.png" alt=">" />

					</c:forEach>

					${album.name}
				</h1>

				<div class="section">

					<s:set var="menu">
						<s:text name="menu" />
					</s:set>
					<v:optionsMenu name="${menu}" icon="img/icons/menu.png">

						<ul>

							<c:if test="${!empty album.tags || userHasCreatePermission}">

								<li class="tagOption show"><a href="#"
									onclick="showTags();"> <img src="img/icons/tag.png"
										alt="<s:text name="showAlbumTags"/>" /> <s:text
											name="showAlbumTags" /> </a>
								</li>

								<li class="tagOption hide" style="display: none"><a
									href="#" onclick="hideTags();"> <img
										src="img/icons/tag.png" alt="<s:text name="hideAlbumTags"/>" />
										<s:text name="hideAlbumTags" /> </a>
								</li>

							</c:if>

							<c:if test="${sessionScope.user.admin}">
								<li><s:url action="ChangeAlbumOwnerData"
										var="changeAlbumOwner">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <s:set var="changeAlbumOwnerText">
										<s:text name="changeAlbumOwner" />
									</s:set> <a href="${changeAlbumOwner}"><img
										alt="${changeAlbumOwner}" src="img/icons/owner.png" />${changeAlbumOwnerText}</a>
								</li>
							</c:if>

							<c:if
								test="${sessionScope.user.id == album.ownerId || sessionScope.user.admin}">
								<c:if test="${album.rootAlbum}">
									<li><s:url action="EditAlbumPermissionsData"
											var="editAlbumPermissions">
											<s:param name="albumId">${album.id}</s:param>
										</s:url> <s:set var="editAlbumPermissionsText">
											<s:text name="editAlbumPermissions" />
										</s:set> <a href="${editAlbumPermissions}"><img
											alt="${editAlbumPermissionsAlbum}"
											src="img/icons/password.png" />${editAlbumPermissionsText}</a>
									</li>
								</c:if>
							</c:if>

							<c:if test="${userHasCreatePermission}">

								<li><s:url action="RenameAlbumData" var="renameAlbum">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <s:set var="renameAlbumText">
										<s:text name="renameAlbum" />
									</s:set> <a href="${renameAlbum}"><img alt="${renameAlbumText}"
										src="img/icons/edit.png" />${renameAlbumText}</a>
								</li>

								<li><s:url action="UploadImageData" var="uploadImage">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <s:set var="uploadImageText">
										<s:text name="uploadImage" />
									</s:set> <a href="${uploadImage}"><img alt="${uploadImageText}"
										src="img/icons/new-image.png" />${uploadImageText}</a>
								</li>

								<li><s:url action="CreateSubAlbumData" var="createSubAlbum">
										<s:param name="parentId">${album.id}</s:param>
									</s:url> <s:set var="createSubAlbumText">
										<s:text name="createSubAlbum" />
									</s:set> <a href="${createSubAlbum}"><img
										alt="${createSubAlbumText}" src="img/icons/new-album.png" />${createSubAlbumText}</a>
								</li>

								<li><s:url action="DeleteAlbum" var="deleteAlbum">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <a href="#"
									onclick="confirmDelete('${deleteAlbumText}', ' ${album.name}', '${deleteAlbum}'); return false;">
										<img alt="${deleteAlbumText}" src="img/icons/delete.png" />${deleteAlbumText}
								</a>
								</li>

								<li><s:url action="MultiTagData" var="multiTag">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <s:set var="multiTagText">
										<s:text name="multiTag" />
									</s:set> <a href="${multiTag}"><img alt="${multiTagText}"
										src="img/icons/multitag.png" />${multiTagText}</a>
								</li>

								<c:if test="${!album.emptyAlbum}">
									<li><s:url action="MoveAlbumContentData"
											var="moveAlbumContent">
											<s:param name="albumId">${album.id}</s:param>
										</s:url> <s:set var="moveAlbumContentText">
											<s:text name="moveAlbumContent" />
										</s:set> <a href="${moveAlbumContent}"><img
											alt="${moveAlbumContentText}" src="img/icons/move.png" />${moveAlbumContentText}</a>
									</li>
								</c:if>

							</c:if>

						</ul>

					</v:optionsMenu>

					<c:if test="${!empty album.tags || userHasCreatePermission}">

						<table class="data-view tags">
							<tr>
								<th><img src="img/icons/tag.png"
									alt="<s:text name="albumTags"/>" /> <s:text name="albumTags" />
								</th>
							</tr>
							<tr>
								<td><c:if test="${userHasCreatePermission}">
										<s:url action="AddAlbumTagData" var="addAlbumTag">
											<s:param name="albumId">${album.id}</s:param>
										</s:url>
										<s:set var="addAlbumTagText">
											<s:text name="addAlbumTag" />
										</s:set>
										<a href="${addAlbumTag}" title="${addAlbumTagText}"><img
											alt="${addAlbumTagText}" src="img/icons/add-tag.png" /> </a>
									</c:if> <c:forEach var="tag" items="${album.tags}">
										<span class="tag"> ${tag.tagName} <c:if
												test="${userHasCreatePermission}">
												<s:url action="DeleteAlbumTag" var="deleteTag">
													<s:param name="tagId">${tag.id}</s:param>
													<s:param name="albumId">${album.id}</s:param>
												</s:url>
												<s:url action="RenameAlbumTagData" var="renameTag">
													<s:param name="tagId">${tag.id}</s:param>
													<s:param name="albumId">${album.id}</s:param>
												</s:url>
												<span class="tagOptions"> <a href="#"
													onclick="confirmDelete('${deleteTagText}', '${tag.tagName}','${deleteTag}'); return false;"
													title="${deleteTagText}"> <img alt="${deleteTagText}"
														src="img/icons/remove-tag.png" /> </a> <a href="${renameTag}"
													title="${renameTagText}"> <img alt="${renameTagText}"
														src="img/icons/edit-tag.png" /> </a> </span>
											</c:if> </span>
									</c:forEach>
								</td>
							</tr>
						</table>

					</c:if>

					<c:if test="${!empty album.subAlbums}">

						<table class="data-view">
							<tr>
								<th><s:text name="album.name" /></th>
								<th><s:text name="album.dateCreation" /></th>
								<th><s:text name="album.dateUpdate" /></th>
							</tr>
							<c:forEach var="subAlbum" items="${album.subAlbums}">
								<tr>
									<td><s:url var="linkAction" action="AlbumDetail">
											<s:param name="albumId">${subAlbum.id}</s:param>
										</s:url> <a href="${linkAction}" title="<s:text name="viewAlbum" />">

											<img src="img/icons/album.png" alt="<s:text name="album"/>" />
											<c:out value="${subAlbum.name}" /> </a>
									</td>
									<td><fmt:formatDate value="${subAlbum.dateCreation}"
											pattern="${Constants.datePattern}" /></td>
									<td><fmt:formatDate value="${subAlbum.dateUpdate}"
											pattern="${Constants.datePattern}" /></td>
								</tr>
							</c:forEach>
						</table>

					</c:if>

					<c:if test="${empty album.images}">
						<s:text name="noImages" />
					</c:if>

					<c:forEach items="${album.images}" var="image">

						<c:choose>

							<c:when test="${userHasCreatePermission}">
								<s:url action="DeleteImage" var="delete">
									<s:param name="imageId">${image.id}</s:param>
									<s:param name="albumId">${album.id}</s:param>
								</s:url>
								<v:thumbnail image="${image}" delete="${delete}"
									deleteText="${deleteImageText}" />
							</c:when>

							<c:otherwise>
								<v:thumbnail image="${image}" />
							</c:otherwise>

						</c:choose>

					</c:forEach>

				</div>

				<div class="confirmDialog" id="confirmDeleteDialog">
					<p class="warning">
						<s:text name="operationCannotBeUndone" />
					</p>
				</div>

			</tiles:putAttribute>

			<tiles:putAttribute name="pageScript">
				animateMenu();
				$(document).ready(function() {
					
					if($.cookie("showTags")){
						showTags(true);
					}
					
				});
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>