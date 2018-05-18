<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<!--
	<url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>


	<%--@elvariable id="userInfo" type="com.clabuyakchai"--%>
	<spring:form method="POST" action="/signin" modelAttribute="userInfo" class="form-signin">
		<h2 class="form-heading">Log in</h2>

		<div class="form-group ${error != null ? 'has-error' : ''}">
			<span>${message}</span>
			<spring:input name="username" type="text" class="form-control" placeholder="Username" path="username"
				   autofocus="true"/>
			<spring:input name="password" type="password" class="form-control" placeholder="Password" path="password"/>
			<span>${error}</span>
			<input type="hidden" name="Authorization" value="${Authorization}"/>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
			<h4 class="text-center"><a href="/signup">Create an account</a></h4>
		</div>

	</spring:form>
	<!-- /.container -->

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
