<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="projectName" /></title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/template.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/general.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/content.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/smoothness/jquery-ui-1.8.2.custom.css" />
<s:head />
<script type="text/javascript" src="javascript/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="javascript/jquery-ui-1.8.2.custom.min.js"></script>
<script type="text/javascript" src="javascript/jquery.cookie.js"></script>
<script type="text/javascript" src="javascript/visionDataset.js"></script>

<script type="text/javascript">
			<tiles:insertAttribute name="pageScript" />
		</script>

</head>

<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
	<div id="content">
		<s:actionmessage escape="false" />
		<s:actionerror escape="false" />
		<tiles:insertAttribute name="content" />
	</div>
	<tiles:insertAttribute name="footer" />
</body>

</html>