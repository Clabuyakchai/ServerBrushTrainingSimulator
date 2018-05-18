<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

    <!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
    <c:url value="/css/main.css" var="jstlCss" />
    <link href="${jstlCss}" rel="stylesheet" />

</head>
<body>


Name: ${userInfo.username};
<br/>


<form id="logoutForm" method="POST" action="/userpage">
    <input  name="Authorization" value="${Authorization}"/>
</form>

<!-- /.container -->

<script type="text/javascript"
        src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
