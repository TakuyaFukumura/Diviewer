<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String[] incomeData = (String[])request.getAttribute("年別月毎配当金額"); %>

		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js" type="text/javascript"></script>
		<canvas id="graph-area" height="405" width="540"></canvas>
		<script type="text/javascript">
		   // ▼グラフの中身
		   var barChartData = {
		      labels : ["1月","2月","3月","4月","5月","6月","7月",
		    	  "8月", "9月", "10月", "11月", "12月"],
		      datasets : [
		         {
		            fillColor : "rgba(240,128,128,0.6)",    // 塗りつぶし色
		            strokeColor : "rgba(240,128,128,0.9)",  // 枠線の色
		            highlightFill: "rgba(255,64,64,0.75)",  // マウスが載った際の塗りつぶし色
		            highlightStroke: "rgba(255,64,64,1)",   // マウスが載った際の枠線の色
		            data : [ <%= incomeData[0] %> ]     // 各棒の値(カンマ区切りで指定)
		         },
		         {
		            fillColor : "rgba(151,187,205,0.6)",
		            strokeColor : "rgba(151,187,205,0.9)",
		            highlightFill : "rgba(64,96,255,0.75)",
		            highlightStroke : "rgba(64,96,255,1)",
		            data : [ <%= incomeData[1] %> ]
		         }
		      ]

		   }
		   // ▼上記のグラフを描画するための記述
		   window.onload = function(){
		      var ctx = document.getElementById("graph-area").getContext("2d");
		      window.myBar = new Chart(ctx).Bar(barChartData);
		   }
		</script>