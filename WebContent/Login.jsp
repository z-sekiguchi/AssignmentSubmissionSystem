<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String sysName = "課題提出システム"; %>
<% String title = "ログイン画面"; %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/Style.css">
<link rel="stylesheet" type="text/css" href="css/validationEngine.jquery.css">

<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js" type="text/javascript" charset="UTF-8"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= title %> - <%= sysName %></title>
</head>
<body>
	<header>
		<img src="https://zeruqacademy.jp/wp/wp-content/themes/zeruqacademy/Templates/Images/logo.svg" alt="ゼルクアカデミー" >
		<h1>課題提出システム</h1>
	</header>
	<div class="main-container">
		<form action="Login" method="post" class="login">
			<h2>ログイン画面</h2>
			<div class="loginId">
				<label for="loginId">ID</label>
				<input type="text" name="loginId" required>
			</div>
			<div class="loginPassword">
				<label for="loginPassword">パスワード</label>
				<input type="password" name="loginPassword" required>
			</div>
			<input type="submit" value="ログイン" class="btn loginButton">
		</form>
	</div>
</body>
</html>