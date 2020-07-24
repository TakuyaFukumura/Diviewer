<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="dto.DividendIncomeDto"%>
<%@ page import="java.util.List"%>

<% List<DividendIncomeDto> incomeList = (List<DividendIncomeDto>)request.getAttribute("新着配当データ"); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP</title>
<link rel="stylesheet" href="css\\header.css">
<link rel="stylesheet" href="css\\design.css">
</head>
<body>
	<nav>
		<ul>
			<li><a href="top"><img src="image\logo.png" alt="ロゴ"
					title="ロゴマーク"></a></li>
			<li><a href="top" class="current">TOP</a></li>
			<li><a href="new">銘柄登録</a></li>
			<li>
				<form action="search" method="POST">
					<input type="text" name="ticker_symbol" value=""
						placeholder="銘柄検索 (大文字)英字" pattern="^[A-Z]+$"> <input
						type="submit" name="search" value=""
						class="btn-flat-dashed-border">
					<!-- input type="submit" name="search" value="検索" class="btn-flat-dashed-border" -->
				</form>
			</li>
			<li><a href="index" class="btn-square-little-rich">ログアウト</a></li>
		</ul>
	</nav>
	<div class="example">
		<br>
		<br>
		<div align="center">月別受領額グラフ（縦軸単位：ドル）
		　　　　　　　　　　　　　　　
		　　　
		新着情報
		</div>
		<table class="not_test" align="center">
			<tr>
				<th><%@ include file="partsHTML\\chart.jsp"%>
					<br> 赤色：2019年　　　 青色：2020年</th>
				<td>
					<div class="table_design">
						<% if(incomeList.size() == 0){ %>
						※情報はありません
						<% }else{ %>
						<table>
							<!--  border=1 style="border-collapse: collapse"-->
							<tr>
								<th>ティッカーシンボル</th>
								<th>受領額　</th>
								<th>　受領日　</th>
							</tr>
							<% for (DividendIncomeDto s: incomeList) {  //一覧表示 %>
							<tr>
								<td><a
									href="show?ticker_symbol=<%= s.getTicker_symbol() %>"><%= s.getTicker_symbol() %></a></td>
								<td><%= s.getAftertax_income() %>ドル</td>
								<td><%= s.getReceipt_date() %></td>
							</tr>
							<% } %>
						</table>
						<% } %>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>