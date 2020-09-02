<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,innitial-scale=1">  
		<title>BOS管理系统 登陆页面</title>
		<script src="js/easyui/jquery.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="css/login.css">
	</head>
	
	<body>
		<div class="loginbox">
		<div class="loginnav">
			<nav class="navbar navbar-default">
				<div class="container">
					<div class="navbar-header">
						<a class="navbar-brand" href="#"><img src="images/logo.png"></a>
						<span class="logintitle">员工登录</span>
					</div>
				</div>
			</nav>
		</div>
		
		<section class="mainlogin">
			<div class="container">
				<div class="col-md-4 col-md-offset-7 logincontent">
					<h4>员工登录</h4>
					<form class="form-horizontal" id="loginform" name="loginform" method="post" action="">
						<div class="form-group" id="idInputLine">
							<label for="inputPassword3" class="col-sm-3 control-label">账号</label>
							<div class="col-sm-8">
								<input id="loginform:idInput" type="text" name="username" class="form-control" placeholder="请输入手机号/邮箱/用户名">
							</div>
						</div>
						<div class="form-group" id="pwdInputLine">
							<label id="loginform:pwdInput" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-8">
								<input for="pwdInput" type="password" name="password" class="form-control" id="inputaccount" placeholder="请输入您的密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputvalidate" class="col-sm-3 control-label">验证码</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="validCode" id="inputaccount" placeholder="请输入验证码">
							</div>
							<div class="col-sm-4">
								<img id="loginform:vCode" src="validatecode.jsp"  onclick="document.getElementById('loginform:vCode'). src='validatecode.jsp?'+Math.random();" />
							</div>
						</div>
						<div class="form-group">
							
							<div class="col-sm-offset-3 col-sm-4">
								<input type="checkbox"><span class="size12">　记住用户名</span>
							</div>
							<div class="col-sm-4">
								<a href="#"><span class="size12 forget">忘记密码</span></a>
							</div>
						</div>
						<div class="col-md-offset-3 col-md-8">
							<a  id="loginBtn" name="loginform:j_id19"
								 class="btn btn-danger" target="_blank">立即登录</a>
							
						</div>
					</form>
				</div>
			</div>
		</section>

		<footer class="clearfix">
			<div class="container">
			<p class="text-center">地址：xxxxxxxxxxxxxxx 邮编：xxxxxx 电话：xxx-xxx-xxxx 传真：xxx-xxxxxxx </p>
<p  class="text-center">京ICP备xxxxxxxxx号京公网安备xxxxxxxxxxxxx</p>
</div>
		</footer>
		</div>
		<script type="text/javascript">
			$(function(){
				//用户登录
				$("#loginBtn").click(function(){
					$.post("user/login",$("#loginform").serialize(),function(data){
						if(data.code==200){

							window.location.href="index.jsp";

						}else{
							alert("登录失败："+data.msg);
						}
					},"json");
				});
			});
		</script>
		
	</body>
</html>