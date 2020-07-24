<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import= "dto.PossessionDto" %>
<%@ page import= "java.util.List" %>

<% List<PossessionDto> possessionList = (List<PossessionDto>)request.getAttribute("検索結果"); %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>検索結果</title>
		<link rel="stylesheet" href="css\\design.css">
	</head>
	<body>
		<jsp:include page="partsHTML\\header.html" />
		<div class="show_cam">
		<div class="table_design">
		<br>
		検索結果を表示します。<br>
		<% if(possessionList.size() != 0){ %>
        <table > <!-- border=1 style="border-collapse: collapse" -->
			<tr>
				<th>ティッカーシンボル</th>
				<th>保有数量</th>
				<th>平均取得単価</th>
				<th>更新日</th>
			</tr>
		<% //一覧表示
		for (PossessionDto s: possessionList) {
        %>
			<tr>
				<td><a href="show?ticker_symbol=<%= s.getTicker_symbol() %>"><%= s.getTicker_symbol() %></a></td>
				<td><%= s.getUnit() %></td>
				<td><%= s.getAverage_unit_cost() %>ドル</td>
				<td><%= s.getUpdate_at() %></td>
			</tr>
		<% } %>
		</table>
		<% }else{ %>
		※該当データがありません
		<% }%>
		</div>
		</div>
	</body>
</html>