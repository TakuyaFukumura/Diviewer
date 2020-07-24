<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import= "dto.DividendIncomeDto" %>

<% DividendIncomeDto dividendIncomeDto = (DividendIncomeDto)request.getAttribute("新規配当データ"); %>





<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>配当登録</title>
		<link rel="stylesheet" href="css\\design.css">
	</head>
	<body>
		<div class="show_cam">
		インカムゲインを追加登録できます<br>
		<form action="incomeCreate" method="POST">
			<div class="table_design">
			<table> <!--  border=1 style="border-collapse: collapse"-->
				<tr>
					<th>受領額</th>
					<td>
						<input type="number" required name="aftertax_income"
						placeholder="半角数字小数2桁まで" min="0"
						value="<%= dividendIncomeDto.getAftertax_income() %>" step=".01" required >
						ドル<br>
					</td>
				</tr>
				<tr>
					<th>受領日</th>
					<td>
						<input type="date" name="receipt_date"
						value="<%= dividendIncomeDto.getReceipt_date() %>" required >
					</td>
				</tr>
			</table>
			</div>
			<br>
			<input type="hidden" name="ticker_id" value="<%= dividendIncomeDto.getTicker_id() %>">
			<A HREF="javascript:history.back()">戻る</A>　　
			<input type="submit" name="create" value="登録" class="submit_btn">
		</form>
		</div>
	</body>
</html>