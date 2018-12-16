<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Top 100 data</title>
</head>
<body>
	<table name="booktable" border="1px solid">
		<tr>
			<th>Product Id</th>
			<th>Total Votes</th>
		</tr>
		<c:forEach  items="${requestScope.topHundred}" var="i">
			<tr>
				<td>${i.prodId}</td>
				<td>${i.topVotes}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>