<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%> 
<!-- BEGIN HEADER -->   
<div class="header navbar navbar-inverse navbar-fixed-top" >
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="header-inner">
			<!-- BEGIN LOGO -->  
			<a class="navbar-brand" href="${ctx}/main/common.izhbg" style="padding-top: 5px;width: 800px;">
				<img src="" height="35">
				<span style="color: #FFF;font-weight: 800;padding-left: 20px;">技术平台&nbsp;&nbsp;&nbsp;&nbsp;——<span> 
				<span style="color: #FFF;font-weight: 600;padding-left: 20px;font-size: 14px;">人永远是要学习的。死的时候，才是毕业的时候。 —— 萧楚女<span> 
			</a>
			<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
			<img src="${ctx}/s/assets/img/menu-toggler.png" alt="">
			</a> 
			<ul class="nav navbar-nav pull-right">
				<li class="divider-vertical hidden-xs"></li>
				<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true" style="line-height:29px;">
					<span style="display: block;padding: 7px 4px 6px 9px;">
						<span class="username"> <sec:authentication property="principal.displayName" /><!--【<sec:authentication property="principal.username" />】--></span>
						<i class="fa fa-angle-down"></i>
					</span>
					</a>
					<ul class="dropdown-menu">
		         	 	<li><a href="${ctx}/user/profile-list.izhbg"><i class="fa fa-user"></i>个人信息</a></li>
		          	 	<li class="divider"></li>
		          	 	<li><a href="${ctx}/auth/logout.izhbg"><i class="fa fa-key"></i> 退出</a></li>
						<li class="divider"></li>
						<!--<li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i>全屏</a></li>
						--><li><a href="${ctx}/user/profile-sysconfig.izhbg"><i class="fa fa-lock"></i>系统配置</a></li>
					</ul>
				</li>
				<li class="divider-vertical hidden-xs"></li>
			</ul>
		</div>
		<div class="theme-panel hidden-xs hidden-sm pull-right" style="margin-top: -3px; position: absolute; right: 0px">
			<div class="toggler"></div>
			<div class="toggler-close"></div>
			<div class="theme-options">
				<div class="theme-option theme-colors clearfix">
					<span>颜色样式</span>
					<ul>
						<li class="color-black current color-default" data-style="default"></li>
						<li class="color-blue" data-style="blue"></li>
						<li class="color-brown" data-style="brown"></li>
						<li class="color-purple" data-style="purple"></li>
						<li class="color-grey" data-style="grey"></li>
						<li class="color-white color-light" data-style="light"></li>
					</ul>
				</div>
				<div class="theme-option">
					<span>页面布局</span> <select class="layout-option form-control input-small">
						<option value="fluid" selected="selected">扩展</option>
						<option value="boxed">收缩</option>
					</select>
				</div>
				<div class="theme-option">
					<span>页面头部</span> <select class="header-option form-control input-small">
						<option value="fixed" selected="selected">固定</option>
						<option value="default">自动</option>
					</select>
				</div>
				<!-- <div class="theme-option">
					<span>侧边菜单</span> <select class="sidebar-option form-control input-small">
						<option value="fixed">浮动</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div> -->
				<div class="theme-option">
					<span>页面底部</span> <select class="footer-option form-control input-small">
						<option value="fixed">固定</option>
						<option value="default" selected="selected">自动</option>
					</select>
				</div>
				<!-- <div class="theme-option">
					<span>右键菜单</span> <select class="context-menu-option form-control input-small">
						<option value="enable" selected="selected">启用</option>
						<option value="disable">禁用</option>
					</select>
				</div> -->
				<!-- <div class="theme-option">
					<span>表格布局</span> <select class="grid-shrink-option form-control input-small">
						<option value="auto" selected="selected">自动</option>
						<option value="true">收缩</option>
					</select>
				</div> -->
			</div>
		</div>
	</div>
