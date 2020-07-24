<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "dto.PossessionDto" %>
<%@ page import= "dto.DividendIncomeDto" %>
<%@ page import= "java.util.List" %>

<% PossessionDto possessionDto = (PossessionDto)request.getAttribute("詳細データ"); %>
<% List<DividendIncomeDto> incomeList = (List<DividendIncomeDto>)request.getAttribute("配当情報"); %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>銘柄詳細</title>
		<link rel="stylesheet" href="css\\design.css">
	</head>
	<body>
		<jsp:include page="partsHTML\\header.html" />
		<br>
		<div class="show_cam">
		銘柄の詳細を閲覧できます。<br>
		<div class="table_design">
		<table border=1 style="border-collapse: collapse" > <!-- -->
			<tr>
				<th>ティッカーシンボル</th>
				<th>保有数量</th>
				<th>平均取得単価</th>

			</tr>
			<tr>
				<td><%= possessionDto.getTicker_symbol() %></td>
				<td><%= possessionDto.getUnit() %></td>
				<td><%= possessionDto.getAverage_unit_cost() %>ドル</td>
			</tr>
		</table>
		</div>

        <form action="edit" method="POST">
        	<input type="hidden" name="ticker_symbol" value="<%= possessionDto.getTicker_symbol() %>">
        	<input type="submit" name="edit" value="編集" class="submit_btn">
		</form>


		<% if(incomeList.size() != 0){ %>
		<br>
		配当情報<br>
		<div class="table_design">
		<table > <!-- border=1 style="border-collapse: collapse" -->
			<tr>
				<th>受取額</th>
				<th>受領日</th>
			</tr>
		<% for (DividendIncomeDto s: incomeList) { %>
			<tr>
				<td><a href="incomeEdit?dividend_income_id=<%= s.getDividend_income_id() %>"><%= s.getAftertax_income() %>ドル</a></td>
				<td><%= s.getReceipt_date() %></td>
			</tr>
        <% } %>
		</table>
		</div>
		<% }else{ %>
		※配当情報はありません
		<% } %>
		<form action="incomeNew" method="GET">
        	<input type="hidden" name="ticker_id" value="<%= possessionDto.getTicker_id() %>">
        	<input type="submit" name="incomeNew" value="配当登録" class="submit_btn">
		</form>
		</div>
	</body>
</html>