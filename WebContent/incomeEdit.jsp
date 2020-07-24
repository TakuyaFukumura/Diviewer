<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import= "dto.DividendIncomeDto" %>

<% DividendIncomeDto incomeDto = (DividendIncomeDto)request.getAttribute("配当データ"); %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>配当編集</title>
		<link rel="stylesheet" href="css\\design.css">
	</head>
	<body>
		<div class="show_cam">
		配当情報を編集できます<br>
		<form action="incomeOperation" method="POST">
		<div class="table_design">
			<table > <!-- border=1 style="border-collapse: collapse" -->
				<tr>
					<th>受領額</th>
					<td>
						<input type="number" required name="aftertax_income"
						placeholder="半角数字小数2桁まで" min="0"
						value="<%= incomeDto.getAftertax_income() %>" step=".01" required >
						ドル<br>
					</td>
				</tr>
				<tr>
					<th>受領日</th>
					<td>
						<input type="date" name="receipt_date"
						value="<%= incomeDto.getReceipt_date() %>" required >
					</td>
				</tr>
			</table>
			<input type="hidden" name="dividend_income_id()" value="<%= incomeDto.getDividend_income_id() %>">
			<input type="submit" name="incomeUpdate" value="更新" class="submit_btn">　　
			<input type="submit" name="incomeDelete" value="削除"
			class="submit_btn" onclick="return confirm('データを削除します。よろしいですか？')">
		</div>
		</form>
		</div>
	</body>
</html>