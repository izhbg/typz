<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/userpicker/userpicker.css">
 <script type="text/javascript" src="${ctx}/widgets/userpicker/userpicker.js"></script>
</head>
<body class="page-header-fixed">
<jsp:include page="/common/header.jsp"></jsp:include> 
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
			<form action="${ctx}/user/profile-saveConfig.izhbg" method="post">
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-danger">
						<h4 class="alert-heading">日志安全配置</h4>
						<p>允许以下人员查看我的日志:</p>
							<div class="input-group userPicker" style="margin-top: 10px;margin-bottom: 10px;">
								 <input id="rzUserid" type="hidden" name="rzUserid" class="input-medium" value="<c:forEach items="${userlog}" var="item" varStatus="status">${item.shareToUserId}<c:if test="${(status.index+1)!=fn:length(userlog)}">,</c:if></c:forEach>">
								 <textarea id="rzUsername" rows="1" cols="10" name="rzUsername" readonly="readonly"  class="form-control" placeholder="选择用户" ><c:forEach items="${userlog}" var="item" varStatus="status"><tags:user userId="${item.shareToUserId}"></tags:user><c:if test="${(status.index+1)!=fn:length(userlog)}">,</c:if></c:forEach></textarea>
								<span class="input-group-addon add-on"><i class="fa fa-user icon-user"></i></span>
							</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="alert alert-success">
						<h4 class="alert-heading">日程安全配置</h4> 
						<p>允许以下人员查看我的日程</p>
						<div class="input-group userPicker" style="margin-top: 10px;margin-bottom: 10px;">
							 <input id="rcUserid" type="hidden" name="rcUserid" class="input-medium" value="<c:forEach items="${schedulelog}" var="item" varStatus="status">${item.shareToUserId}<c:if test="${(status.index+1)!=fn:length(schedulelog)}">,</c:if></c:forEach>">
							 <textarea id="rcUsername" rows="1" cols="10" name="rcUsername" readonly="readonly"  class="form-control" placeholder="选择用户" ><c:forEach items="${schedulelog}" var="item" varStatus="status"><tags:user userId="${item.shareToUserId}"></tags:user><c:if test="${(status.index+1)!=fn:length(schedulelog)}">,</c:if></c:forEach></textarea>
							<span class="input-group-addon add-on"><i class="fa fa-user icon-user"></i></span>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<button type="submit" class="btn red pull-right">确认保存  <i class="m-icon-swapright m-icon-white"></i></button>
				</div>
			</div>	
			</form>
		</div>
	</div>
	<script>
	function returnPage(){
		window.location.href="${ctx}/dashboard/dashboard.izhbg";
		}
	$(function(){
		 createUserPicker({
				modalId: 'userPicker',
				url: '${ctx}/org-user/getOrgUser.izhbg'
			});
	});
	</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>