<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dto.PossessionDto"%>

<% PossessionDto possessionDto = (PossessionDto)request.getAttribute("バックデータ"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>銘柄登録</title>
<link rel="stylesheet" href="css\\header.css">
<link rel="stylesheet" href="css\\design.css">
</head>
<body>
	<nav>
		<ul>
			<li><a href="top"><img src="image\logo.png" alt="ロゴ"
					title="ロゴマーク"></a></li>
			<li><a href="top">TOP</a></li>
			<li><a href="new" class="current">銘柄登録</a></li>
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
	<div class="show_cam">
		<br> 新しい銘柄を登録できます<br>
		<% if(possessionDto == null){ %>
		<form action="create" method="POST">
			<div class="table_design">
				<table>
					<!-- class="table_design" border=1 style="border-collapse: collapse" -->
					<tr>
						<th>ティッカーシンボル</th>
						<td><input type="text" name="ticker_symbol" value=""
							placeholder="半角英字のみ" pattern="^[A-Z]+$" required></td>
					</tr>
					<tr>
						<th>保有数量</th>
						<td><input type="text" name="unit" value=""
							placeholder="半角数字のみ" pattern="^0$|^[1-9][0-9]*$" required>
						</td>
					</tr>
					<tr>
						<th>平均取得単価</th>
						<td><input type="number" required name="average_unit_cost"
							placeholder="半角数字小数点以下2桁" min="0" value="" step=".01" required>
							<!-- input type="text" name="average_unit_cost" value="" placeholder="半角の数字と小数点のみ" pattern="(0|[1-9][0-9]*)\\.?(0|[1-9][0-9]*)" required -->
							ドル<br></td>
					</tr>
				</table>
			</div>
			<!-- input type="reset" name="reset" value="リセット" class="" -->
			<br> <input type="submit" name="create" value="登録"
				class="submit_btn">
		</form>
		<% }else{ %>
		<br> ※この銘柄は既に登録されています<br>
		<form action="create" method="POST">
			<div class="table_design">
				<table>
					<!-- border=1 style="border-collapse: collapse" -->
					<tr>
						<th>ティッカーシンボル</th>
						<td><input type="text" name="ticker_symbol"
							value="<%= possessionDto.getTicker_symbol() %>"
							placeholder="半角英字のみ" pattern="^[A-Z]+$" required></td>
					</tr>
					<tr>
						<th>保有数量</th>
						<td><input type="text" name="unit"
							value="<%= possessionDto.getUnit() %>" placeholder="半角数字のみ"
							pattern="^0$|^[1-9][0-9]*$" required></td>
					</tr>
					<tr>
						<th>平均取得単価</th>
						<td><input type="number" required name="average_unit_cost"
							placeholder="半角数字小数点以下2位まで" min="0"
							value="<%= possessionDto.getAverage_unit_cost() %>" step=".01"
							required> <!-- input type="text" name="average_unit_cost" value="" placeholder="半角の数字と小数点のみ" pattern="(0|[1-9][0-9]*)\\.?(0|[1-9][0-9]*)" required -->
							ドル<br></td>
					</tr>
				</table>
			</div>
			<!-- input type="reset" name="reset" value="リセット" class="" -->
			<br> <input type="submit" name="create" value="登録"
				class="submit_btn">
		</form>
		<% } %>
	</div>
</body>
</html>