<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>i综合办公</title>  
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">

<!-- style -->
<link href="${ctx}/s/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/custom.css" rel="stylesheet" type="text/css"/>

</head>  
<body class="login">

	<div class="logo">
		<img src="${ctx}/s/assets/img/logo.jpg" >
	</div>
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form class="login-form" action="../j_spring_security_check" method="post"> 
			<h3 class="form-title" style="text-align: center;">登录</h3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span>请输入用户名、密码.</span>
			</div>
			<div class="alert m-alert-error" ${error!='' ? '' : 'style="display:none"'}>
		        <strong><spring:message code="core.login.failure" text="登陆失败"/></strong>
				&nbsp;
		         ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
	       </div>
	        <div class="form-group" style="display:none">
		    	<label class="col-md-2 control-label" for="tenant">租户</label>
				<div class="col-md-10">
			      	<input type='text' id="tenant" name='tenant' class="form-control" value="default">
			    </div>
		    </div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9"><spring:message code="core.login.username" text="账号2"/></label>
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<spring:message code="core.login.username" text="账号"/>" id="username" name='j_username' value="yuewuxing" <%-- value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" --%>/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9"><spring:message code="core.login.password" text="密码"/></label>
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<spring:message code="core.login.password" text="密码"/>" id="password"  name="j_password" value="123456"/>
				</div>
			</div>
			<div class="form-actions">
				
				<label class="checkbox">
					&nbsp;&nbsp;<input id="_spring_security_remember_me" name="remember-me" type="checkbox" style="margin-left: 0px;"/> 记住<br><br>
					默认账户：yuewuxing/123456  test/123456
				</label>
				<button type="submit" class="btn blue pull-right">
				<spring:message code='core.login.submit' text='提交'/>  <i class="m-icon-swapright m-icon-white"></i>
				</button>            
			</div>
		</form>
	</div>
	<!-- script -->
	<script src="${ctx}/s/assets/plugins/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/jquery.validate.js"></script>
	<script src="${ctx}/s/assets/scripts/login-soft.js" type="text/javascript"></script> 
	
	<script src="${ctx}/s/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {  
		  Login.init();
		});
	</script>
</body>  
</html>  