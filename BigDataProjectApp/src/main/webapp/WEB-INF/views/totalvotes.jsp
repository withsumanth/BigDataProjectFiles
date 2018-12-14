<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import="com.edu.pojo.TotalVotes"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
ArrayList<TotalVotes> list = (ArrayList<TotalVotes>) request.getAttribute("totalvotes");
for (TotalVotes r : list) {
	map = new HashMap<Object, Object>();
	map.put("label", r.getYear());
	map.put("y", r.getVotes());
	listMap.add(map);
}

String dataPoints = gsonObj.toJson(listMap);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	theme: "light2",
	title: {
		text: "Total number of votes by customer in all the years"
	},
	axisY: {
		title: "Total number of votes"
	},
	data: [{
		type: "area",
		markerSize: 0,
		indexLabel : "{y}",
		dataPoints: <%out.print(dataPoints);%>
	}]
});
chart.render();
 
}
</script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>                          