<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ログイン画面</title>
		<!-- jsp:include page="partsHTML\\css.html" /-->
		<link rel="stylesheet" href="css\\login.css">
	</head>
	<body>
		<div class="login-page">
			<div class="form">
				<img src="image\logo.png" alt="ロゴ" title="ロゴマーク">
				<br>
				<br>
				<form action="top" method="POST" class="login-form">
					<input type="text" name="loginUserId" value="fukumura"
					 placeholder="User_ID" pattern="^[a-zA-Z0-9]+$" required />
					<input type="password" name="loginPassword" value="takuya045A"
					placeholder="password" pattern="^[a-zA-Z0-9]+$" required  />
					<input type="submit" name="login" value="Login" class="login_button">
				</form>
			</div>
		</div>
	</body>
</html>


