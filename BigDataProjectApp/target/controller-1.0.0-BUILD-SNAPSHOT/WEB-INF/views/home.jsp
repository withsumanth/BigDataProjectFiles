<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Big Data Project</title>
</head>
<body>
	<h1>Choose which analytics you want to see</h1>

	<form action="analytics" method="post">
		<select name="analytics">
			<option value="avg">Average review of product category</option>
			<option value="per">Percentage of Verified purchases</option>
			<option value="std">Standard Deviation and Median</option>
			<option value="top">Top 100 products</option>
			<option value="total">Total Votes based on year</option>
			<option value="pigip">Data with Review headline Five Star</option>
		</select> <input type="hidden" name="path" value="part-r-00000.txt"> <input
			type="submit" value="Submit">
	</form>
</body>
</html>
