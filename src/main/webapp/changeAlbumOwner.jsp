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

		<tiles:insertDefinition name="templateVisionDataset">
			<tiles:putAttribute name="content">

				<h1>
					<s:url action="ListAlbums" var="listAlbums" />
					<a href="${listAlbums}"><s:text name="listAlbums" /> </a> <img
						src="img/icons/breadcrumb.png" alt=">" />

					<c:forEach items="${album.albumsChain}" var="parentAlbum">

						<s:url var="linkAction" action="AlbumDetail">
							<s:param name="albumId">${parentAlbum.id}</s:param>
						</s:url>
						<a href="${linkAction}"> ${parentAlbum.name} </a>

						<img src="img/icons/breadcrumb.png" alt=">" />

					</c:forEach>

					<s:text name="changeAlbumOwner" />
				</h1>

				<div class="section">

					<s:url action="ChangeAlbumOwner" var="changeAlbumOwner" />
					<s:set var="changeAlbumOwnerText">
						<s:text name="changeAlbumOwner" />
					</s:set>

					<s:form action="ChangeAlbumOwner">
						<s:hidden name="albumId" />
						<s:select key="user" name="userId" list="users" listKey="id"
							listValue="description" value="album.ownerId" />
						<s:submit key="ok" />
						<s:submit type="button" key="cancel" method="cancel" />
					</s:form>

				</div>

			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>