<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<% String sysName = "課題提出システム"; %>
<% String title = "パスワード変更"; %>
<% int privilege = Integer.parseInt((String)session.getAttribute("loginUserPrivilege")); %>
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
		<div class="loginUserInfo">
			<p><%= (String)session.getAttribute("loginUserName") %>(<%= privilege %>)</p>
			<p>ID: <%= (String)session.getAttribute("loginUserId") %></p>
		</div>
	</header>
	<div class="main-container">
		<div class="columns">
			<aside class="columns-side">
				<input type="checkbox" id="menuStatus" hidden checked>
				<h3 id="toggleBtn"><span class="usuMenu">メニュー</span><span class="menuSize usuMenu">◀</span><span class="menuSize redMenu" style="display:none">▶</span></h3>
				<ul class="upper">
					<!-- ユーザーの権限によってメニューの表示を変更する -->
					<% if(privilege == 1 || privilege == 2) { %>
						<li>
							<a href="/AssignmentSubmissionSystem/StudentsList"><img src="icon/studentsListButton.png"><span class="usuMenu">受講生一覧</span></a>
						</li>
						<li>
							<a href="/AssignmentSubmissionSystem/AssignmentsList"><img src="icon/assignmentListButton.png"><span class="usuMenu">課題一覧</span></a>
						</li>
						<li>
							<a href="/AssignmentSubmissionSystem/UserManagement"><img src="icon/userManagementButton.png"><span class="usuMenu">ユーザー管理</span></a>
						</li>
					<% } else if(privilege == 3) { %>
						<li>
							<a href="/AssignmentSubmissionSystem/AssignmentsList"><img src="icon/assignmentListButton.png"><span class="usuMenu">課題一覧</span></a>
						</li>
					<% } %>
				</ul>
				<ul class="lower">
					<li style="background-color: #B62536;">
						<a href="/AssignmentSubmissionSystem/ChangePassword"><img src="icon/changePasswordButton.png"><span class="usuMenu">パスワード変更</span></a>
					</li>
					<li>
						<a href="/AssignmentSubmissionSystem/Logout"><img src="icon/logoutButton.png"><span class="usuMenu">ログアウト</span></a>
					</li>
				</ul>
			</aside>
			<main>
				<h1>パスワード変更ページ</h1>
				<form action="ChangePassword" method="post">
					<input type="password" name="defaultPassword">
					<br>
					<input type="password" name="password1">
					<br>
					<input type="password" name="password2">
					<br>
					<input type="submit" value="変更">
				</form>
				<% if(request.getAttribute("result") != null) {
						int res = Integer.parseInt((String)request.getAttribute("result"));
						@SuppressWarnings("unchecked")
						ArrayList<String> err = (ArrayList<String>)request.getAttribute("err");
						if(res == 1) {
							String msg = (String)request.getAttribute("msg"); %>
								<p><%= msg %></p>
						<% } else if(res == -1) {
							for(String e : err) {
								%> <p><%= e %></p>
							<% } %>
						<% } %>
				<% } %>
			</main>
		</div>
	</div>
	<script src="js/menu.js" type="text/javascript" charset="UTF-8"></script>
</body>
</html>