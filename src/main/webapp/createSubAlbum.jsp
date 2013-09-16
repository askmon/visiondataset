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

					<c:forEach items="${parents}" var="album">

						<s:url var="linkAction" action="AlbumDetail">
							<s:param name="albumId">${album.id}</s:param>
						</s:url>
						<a href="${linkAction}"> ${album.name} </a>

						<img src="img/icons/breadcrumb.png" alt=">" />

					</c:forEach>

					<s:text name="createSubAlbum" />
				</h1>

				<div class="section">

					<v:info>
						<s:text name="validation.allFieldsRequired" />
					</v:info>

					<s:form action="CreateSubAlbum"
						focusElement="CreateSubAlbum_albumName">
						<s:hidden name="parentId" />
						<s:textfield key="albumName" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

				</div>

			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>