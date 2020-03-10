<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>
<style type="text/css">
a {
	font-size: 40px;
	font-weight: bold;
}

span {
	font-size: 40px;
	font-weight: bold;
}
</style>
</head>
<body>

	<%-- 	<c:if test="${fn:length(sessionScope.userid) == 0}">
		<a href="<c:url value='/member/signupForm'/>">회원가입 화면</a><br>
		<a href="<c:url value='/member/loginForm'/>">로그인</a>
	</c:if>
	<c:if test="${fn:length(sessionScope.userid) != 0}">
		<span>${sessionScope.userid} 님 반갑습니다!</span><br>
		<a href="<c:url value='/member/logout'/>">로그아웃</a>
	</c:if> --%>

	<c:choose>
		<c:when test="${sessionScope.userid == null}">
			<a href="<c:url value='/member/signupForm'/>">회원가입 화면</a>
			<br>
			<a href="<c:url value='/member/loginForm'/>">로그인</a>
		</c:when>
		<c:otherwise>
			<span>${sessionScope.userid} 님 반갑습니다!</span>
			<br>
			<a href="<c:url value='/member/logout'/>">로그아웃</a>
		</c:otherwise>
	</c:choose>



</body>
</html>