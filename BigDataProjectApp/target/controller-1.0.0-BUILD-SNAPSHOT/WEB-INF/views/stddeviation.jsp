<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import="com.edu.pojo.StandardDeviation"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> mapMed = null;
List<Map<Object,Object>> listMed = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> listDev = new ArrayList<Map<Object,Object>>();
 
ArrayList<StandardDeviation> listStd = (ArrayList<StandardDeviation>) request.getAttribute("stdOutput");
for (StandardDeviation s : listStd) {
	mapMed = new HashMap<Object, Object>();
	mapMed.put("label", s.getProdName());
	mapMed.put("y", s.getMedian());
	listMed.add(mapMed);
}

Map<Object,Object> mapStd = null;
for (StandardDeviation s : listStd) {
	mapStd = new HashMap<Object, Object>();
	mapStd.put("label", s.getProdName());
	mapStd.put("y", s.getStdDev());
	listDev.add(mapStd);
}
 
String dataPointsMed = gsonObj.toJson(listMed);
String dataPointsStd = gsonObj.toJson(listDev);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2",
	title: {
		text: "Median and Standard Deviation for product Ratings"
	},
	axisX: {
		title: "Product Category"
	},
	axisY: {
		title: "Median and Standard deviation"
	},
	data: [{
		type: "line",
		indexLabel : "Median: {y}",
		name: "Median",
		dataPoints : <%out.print(dataPointsMed);%>
	},{
		type: "line",
		indexLabel : "Std Dev: {y}",
		name: "Standard Deviation",
		dataPoints : <%out.print(dataPointsStd);%>
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