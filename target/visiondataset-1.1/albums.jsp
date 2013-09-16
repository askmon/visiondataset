<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<jsp:useBean id="Constants"
	class="br.usp.ime.vision.dataset.util.Constants" scope="session" />

<tiles:insertDefinition name="templateVisionDataset">
	<tiles:putAttribute name="content">

		<h1>
			<s:text name="listAlbums" />
		</h1>

		<div class="section">

			<c:if test="${sessionScope.user.createPermission}">
				<s:url action="CreateAlbumData" var="createAlbum" />
				<s:set var="createAlbumText">
					<s:text name="createAlbum" />
				</s:set>
				<div class="action">
					<a href="${createAlbum}"><img alt="${createAlbumText}"
						src="img/icons/new-album.png" />${createAlbumText}</a>
				</div>
			</c:if>

			<c:choose>

				<c:when test="${empty albums}">

					<p>
						<s:text name="noAlbums" />
					</p>

				</c:when>

				<c:otherwise>

					<table class="data-view">
						<tr>
							<th><s:text name="album.name" /></th>
							<th><s:text name="album.dateCreation" /></th>
							<th><s:text name="album.dateUpdate" /></th>
						</tr>
						<c:forEach var="album" items="${albums}">
							<tr>
								<td><s:url var="linkAction" action="AlbumDetail">
										<s:param name="albumId">${album.id}</s:param>
									</s:url> <a href="${linkAction}" title="<s:text name="viewAlbum" />">

										<img src="img/icons/album.png" alt="Album" /> <c:out
											value="${album.name}" /> </a>
								</td>
								<td><fmt:formatDate value="${album.dateCreation}"
										pattern="${Constants.datePattern}" /></td>
								<td><fmt:formatDate value="${album.dateUpdate}"
										pattern="${Constants.datePattern}" /></td>
							</tr>
						</c:forEach>
					</table>

				</c:otherwise>

			</c:choose>

		</div>

	</tiles:putAttribute>

</tiles:insertDefinition>