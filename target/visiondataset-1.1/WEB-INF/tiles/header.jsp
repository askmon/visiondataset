<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="header">

	<div id="logo">

		<s:url action="Home" var="home" />
		<a href="${home}"><img alt="Vision Dataset" width="343"
			height="54" src="img/logo.png" /> </a>

	</div>


	<div id="entrance">

		<c:choose>
			<c:when test="${sessionScope.user != null}">

				<div class="action">
					<s:url action="Logout" var="logout" />
					<c:set var="logoutText">
						<s:text name="logout" />
						<c:out value="${sessionScope.user.username}" />
					</c:set>
					<a href="${logout}"><img alt="${logoutText}"
						src="img/icons/logout.png" />${logoutText}</a>
				</div>

			</c:when>
			<c:otherwise>

				<s:form action="Login" theme="simple">
					<label><s:text name="usernameLogin" /> </label>
					<s:textfield key="usernameLogin" />
					<label><s:text name="passwordLogin" /> </label>
					<s:password key="passwordLogin" />
					<s:submit key="login" />
				</s:form>

			</c:otherwise>
		</c:choose>

	</div>

</div>