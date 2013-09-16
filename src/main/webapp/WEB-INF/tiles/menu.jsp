<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="menu">

	<c:if test="${sessionScope.user.admin}">
		<s:url action="ListUsers" var="listUsers" />
		<a href="${listUsers}"><s:text name="listUsers" /> </a>
	</c:if>

	<c:if test="${sessionScope.user != null}">
		<s:url action="Profile" var="profile" />
		<a href="${profile}"><s:text name="profile" /> </a>
	</c:if>

	<s:url action="ListAlbums" var="listAlbums" />
	<a href="${listAlbums}"><s:text name="listAlbums" /> </a>

</div>