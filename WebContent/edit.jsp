<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import= "dto.PossessionDto" %>

<% PossessionDto possessionDto = (PossessionDto)request.getAttribute("詳細データ"); %>



<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>銘柄編集</title>
		<link rel="stylesheet" href="css\\design.css">
	</head>
	<body>
		<div class="show_cam">
		<form action="operation" method="POST">
		<div class="table_design">
			<table> <!-- border=1 style="border-collapse: collapse" -->
				<tr>
					<th>ティッカーシンボル</th>
					<td>
						<input type="hidden" name="new_ticker_symbol"
						value="<%= possessionDto.getTicker_symbol() %>">
						<%= possessionDto.getTicker_symbol() %>
					</td>
				</tr>
				<tr>
					<th>口数</th>
					<td>
						<input type="text" name="unit" value="<%= possessionDto.getUnit() %>"
						placeholder="半角数字のみ"  pattern="^0$|^[1-9][0-9]*$" required >
					</td>
				</tr>
				<tr>
					<th>平均取得単価</th>
					<td>
						<input type="number" required name="average_unit_cost"
						placeholder="半角数字小数点以下2位まで" min="0"
						value="<%= possessionDto.getAverage_unit_cost() %>"
						step=".01" required >
						<!-- input type="text" name="average_unit_cost" value="" placeholder="半角の数字と小数点のみ" pattern="(0|[1-9][0-9]*)\\.?(0|[1-9][0-9]*)" required -->
						ドル<br>
					</td>
				</tr>
			</table>
			</div>
			<input type="hidden" name="old_ticker_id" value="<%= possessionDto.getTicker_id() %>">
			<input type="hidden" name="old_ticker_symbol" value="<%= possessionDto.getTicker_symbol() %>">
			<input type="hidden" name="created_at" value="<%= possessionDto.getCreated_at() %>">

			<input type="submit" name="update" value="更新" class="submit_btn">　　　
        	<input type="submit" name="delete" value="削除" class="submit_btn" onclick="return confirm('データを削除します。よろしいですか？')">

		</form>
		</div>
	</body>
</html>