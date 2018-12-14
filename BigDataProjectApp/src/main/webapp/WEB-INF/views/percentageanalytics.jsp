<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import="com.edu.pojo.Percentage"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
Percentage p = (Percentage) request.getAttribute("percentageData"); 
map = new HashMap<Object,Object>(); map.put("label", "Verified Purchase"); map.put("y", p.getVerifiedPerc()); map.put("exploded", true); list.add(map);
map = new HashMap<Object,Object>(); map.put("label", "Non Verified Purchase"); map.put("y", p.getNonVerifiedPerc()); list.add(map);
String dataPoints = gsonObj.toJson(list);
%>
 
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.onload = function() { 
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2",
	animationEnabled: true,
	exportFileName: "Percentage of Verified and Non Verified products",
	exportEnabled: true,
	title:{
		text: "Percentage of Verified and Non Verified products"
	},
	data: [{
		type: "pie",
		showInLegend: true,
		legendText: "{label}",
		toolTipContent: "{label}: <strong>{y}%</strong>",
		indexLabel: "{label} {y}%",
		dataPoints : <%out.print(dataPoints);%>
	}]
});
 
chart.render();
 
}
</script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="http://localhost:5050/javascript/canvasjs.min.js"></script>
</body>
</html>                   