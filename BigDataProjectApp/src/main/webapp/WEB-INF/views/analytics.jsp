<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%@ page import="com.edu.pojo.Ratings"%>
<%
	Gson gsonObj = new Gson();
	Map<Object, Object> map = null;
	List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
	ArrayList<Ratings> list = (ArrayList<Ratings>) request.getAttribute("ratings");
	for (Ratings r : list) {
		map = new HashMap<Object, Object>();
		map.put("label", r.getProductName());
		map.put("y", r.getProductRating());
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
			theme : "light2",
			title : {
				text : "Amazon product - App Rating"
			},
			subtitles : [ {
				text : ""
			} ],
			axisY : {
				title : "Rating",
				labelFormatter : addSymbols
			},
			data : [ {
				type : "bar",
				indexLabel : "{y}",
				indexLabelFontColor : "#444",
				indexLabelPlacement : "inside",
				dataPoints :
<%out.print(dataPoints);%>
	} ]
		});
		chart.render();

		function addSymbols(e) {
			var suffixes = [ "", "K", "M", "B" ];

			var order = Math.max(
					Math.floor(Math.log(e.value) / Math.log(1000)), 0);
			if (order > suffixes.length - 1)
				order = suffixes.length - 1;

			var suffix = suffixes[order];
			return CanvasJS.formatNumber(e.value / Math.pow(1000, order))
					+ suffix;
		}
	}
</script>
</head>
<body>
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="http://localhost:5050/javascript/canvasjs.min.js"></script>
</body>
</html>
