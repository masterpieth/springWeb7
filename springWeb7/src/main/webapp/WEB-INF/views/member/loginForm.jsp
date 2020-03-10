<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>

	<form action="<c:url value='/member/login'/>" method="post">
		<table>
			<tr>
				<th colspan="2">로그인</th>
			</tr>
			<tr>
				<th>ID</th>
				<td><input type="text" name="userid"></td>
			</tr>
			<tr>
				<th>암호</th>
				<td><input type="password" name="userpwd"></td>
			</tr>
			<tr>
				<th colspan="2"><input type="submit" value="로그인"></th>
			</tr>
		</table>
	</form>

</body>
</html>