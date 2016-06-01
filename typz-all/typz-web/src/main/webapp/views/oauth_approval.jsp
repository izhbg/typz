<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
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
			<p>Do you authorize '${authorizationRequest.clientId}' to access your protected resources?</p>
			<form id='confirmationForm' name='confirmationForm' action='${pageContext.request.contextPath}/oauth/authorize.izhbg'
			      method='post'>
			    <input name='user_oauth_approval' value='true' type='hidden'/>
			    <label> <input name='authorize' value='Authorize' type='submit' class="btn btn-success"/></label>
			</form>
			<form id='denialForm' name='denialForm' action='${pageContext.request.contextPath}/oauth/authorize.izhbg' method='post'>
			    <input name='user_oauth_approval' value='false' type='hidden'/>
			    <label><input name='deny' value='Deny' type='submit' class="btn btn-warning"/></label>
			</form>
		</div>
</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>