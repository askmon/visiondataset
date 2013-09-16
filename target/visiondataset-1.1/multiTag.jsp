<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

					<s:text name="multiTag" />

				</h1>

				<div class="section">

					<v:info>
						<s:text name="multiTag.info" />
					</v:info>

					<table id="moveContent">

						<tr>
							<td id="sources"><textarea id="tags" cols="50" rows="3"></textarea>
								<ul id="selectedTags"></ul> <s:form action="MultiTag"
									onsubmit="getTagsContent();">
									<s:hidden name="albumId" />
									<s:hidden name="albunsTags" />
									<s:hidden name="imagesTags" />
									<s:submit key="confirmMultiTag" />
									<s:submit type="button" key="cancel" method="cancel" />
								</s:form>
							</td>
							<td id="destinations" class="tagContainer"><v:info>
									<s:text name="auxiliaryOptions" />:
								<button onclick="selectAll();">
										<s:text name="selectAll" />
									</button>
									<button onclick="selectNone();">
										<s:text name="selectNone" />
									</button>
								</v:info>

								<div class="albumHandler" id="${album.id}">
									<input type="checkbox" />${album.name}
									<ul class="multiTag"></ul>
								</div> <c:forEach items="${album.subAlbums}" var="subAlbum">
									<div class="subAlbumHandler" id="${subAlbum.id}">
										<input type="checkbox" />${subAlbum.name}
										<ul class="multiTag"></ul>
									</div>
								</c:forEach> <c:forEach items="${album.images}" var="image">
									<div class="miniThumbnailHandler" id="${image.id}">
										<input type="checkbox" />
										<v:miniThumbnail image="${image}" />
										<ul class="multiTag"></ul>
									</div>
								</c:forEach>
							</td>
						</tr>
					</table>

				</div>

			</tiles:putAttribute>

			<tiles:putAttribute name="pageScript">
				$(document).ready(
					function() {
									
						var tags = [];
						<c:forEach items="${tags}" var="tag">
							tags.push("${tag}");
						</c:forEach>
						autocomplete("#tags", tags, true, true);
						
						multiTags();
						
					});
			</tiles:putAttribute>

		</tiles:insertDefinition>

	</c:otherwise>

</c:choose>