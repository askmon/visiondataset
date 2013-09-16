<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/vision.tld" prefix="v"%>

<c:choose>

	<c:when test="${!userHasCreatePermission}">
		<s:action name="UnauthorizedAction" executeResult="true" />
	</c:when>

	<c:otherwise>

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>

					<s:url action="ListAlbums" var="listAlbums" />
					<a href="${listAlbums}"><s:text name="listAlbums" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<c:forEach items="${album.albumsChain}" var="album">

						<s:url var="linkAction" action="AlbumDetail">
							<s:param name="albumId">${album.id}</s:param>
						</s:url>
						<a href="${linkAction}"> ${album.name} </a>

						<img src="img/icons/breadcrumb.png" alt=">" />

					</c:forEach>

					<s:url var="linkAction" action="ImageDetail">
						<s:param name="imageId">${image.id}</s:param>
					</s:url>
					<a href="${linkAction}"> <s:text name="image" /> ${image.id} </a>

					<img src="img/icons/breadcrumb.png" alt=">" />

					<s:text name="editAnnotation" />

				</h1>

				<div class="section">

					<v:info>
						<s:text name="validation.allFieldsRequired" />
					</v:info>

					<s:form action="EditAnnotation"
						onsubmit="getAnnotationCoords('EditAnnotation');">
						<s:hidden name="imageId" />
						<s:hidden name="tagId" />
						<s:textfield key="tagName" />
						<s:hidden name="x" />
						<s:hidden name="y" />
						<s:hidden name="width" />
						<s:hidden name="height" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

					<div id="annotationContainer">
						<img src="${image.imageURL}" id="image" alt="${image.id}" />
						<div class="annotation"
							style="top: ${annotation.y}px; left: ${annotation.x}px; width: ${annotation.width}px; height: ${annotation.height}px;"></div>
					</div>

				</div>

			</tiles:putAttribute>

			<tiles:putAttribute name="pageScript">
				$(document).ready(
					function() {
						var tags = [];
						<c:forEach items="${tags}" var="tag">
							tags.push("${tag}");
						</c:forEach>
						autocomplete("#EditAnnotation_tagName", tags);
						
						$("#annotationContainer .annotation").resizable( {
							containment : "#annotationContainer",
							handles : "all"
						});
						$("#annotationContainer .annotation").draggable( {
							containment : 'parent'
						});
						
					}
				);
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>
