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
			<th>Marketplace</th>
			<th>Customer Id</th>
			<th>Review Id</th>
			<th>Product Id</th>
			<th>Product Parent</th>
			<th>Product Title</th>
			<th>Product Category</th>
			<th>Total Votes</th>
			<th>Review Headline</th>
		</tr>
		<c:forEach  items="${requestScope.topHundred}" var="i">
			<tr>
				<td>${i.marketplace}</td>
				<td>${i.customer_id}</td>
				<td>${i.review_id}</td>
				<td>${i.product_id}</td>
				<td>${i.product_parent}</td>
				<td>${i.product_title}</td>
				<td>${i.product_category}</td>
				<td>${i.total_votes}</td>
				<td>${i.review_headline}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>