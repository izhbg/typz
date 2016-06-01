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
		<div class="page-content">
			<h3>
			    Illegal action.
			</h3>
			
			<div class="alert alert-danger">
			    <c:out value="${error.summary}"/>
			</div>
			<button class="btn green" onclick="javascript:history.go(-1);">返回</button>
		</div>
</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>