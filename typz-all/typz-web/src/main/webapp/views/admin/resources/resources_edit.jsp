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
				<div class="row inbox">
				 <div class="col-md-12">
				<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title">
					<c:if test="${tXtResources.resourceId eq null||tXtResources.resourceId eq ''}">
						添加资源信息
					</c:if>
					<c:if test="${tXtResources.resourceId ne null && tXtResources.resourceId ne ''}">
						修改资源信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="${ctx}/resources/resources_addORupdate.izhbg" class="form-horizontal" id="userinfoForm" method="post">
						<input type="hidden" name="resourceId" id="resourceId" value="${tXtResources.resourceId }"/>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">资源名称：</label>
								<div class="col-md-4">
									<input name="resourceName" id="resourceName" class="form-control" maxlength=10 value="${tXtResources.resourceName}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">资源类型：</label>
								<div class="col-md-4">
									<select name="resourceType" id="resourceType" class="form-control">
										<option value="url"   <c:if test="${tXtResources.resourceType=='url'}">selected="selected"</c:if> >url</option>
										<option value="action" <c:if test="${tXtResources.resourceType=='action'}">selected="selected"</c:if>>action</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">有效标记：</label>
								<div class="col-md-4">
									<select name="enabled" id="enabled" class="form-control">
										<option value="1"   <c:if test="${tXtResources.enabled==1}">selected="selected"</c:if> >有效</option>
										<option value="2" <c:if test="${tXtResources.enabled==2}">selected="selected"</c:if>>无效</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">资源表达式：</label>
								<div class="col-md-4">
									<input name="resourceString" id="resourceString" class="form-control" maxlength=100 value="${tXtResources.resourceString}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">备注：</label>
								<div class="col-md-4">
									<textarea name="resourceDesc" id="resourceDesc" class="form-control">${tXtResources.resourceDesc }</textarea>				
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="role_addorupd('${tXtResources.resourceId }')" class="btn green">提交</button>
								<button type="button" onclick="returnPage()" class="btn default">返回</button>                              
							</div> 
						</div>
					</form>
					<!-- END FORM-->                
					</div>
				</div>
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/jquery.validate.min.js"></script>
	<script>
	
	$(document).ready(function(){
		 var formvalidate = $('#userinfoForm');
		 formvalidate.validate({
             errorElement: 'span', //default input error message container
             errorClass: 'help-block', // default input error message class
             focusInvalid: false, // do not focus the last invalid input
             ignore: "",
             rules: {
            	 resourceName: {
                	 minlength: 2,
                     required: true
                 },
                 resourceString: {
                	 minlength: 2,
                     required: true
                 }
             }, messages: {
            	 resourceName:{
            		required: "资源名称不能为空"
                	 },
                 resourceString:{
            		required: "资源表达式不能为空"
                	 }
             
                 },
             errorPlacement: function (error, element) { // render error placement for each input type
                 if (element.parent(".input-group").size() > 0) {
                     error.insertAfter(element.parent(".input-group"));
                 } else if (element.attr("data-error-container")) { 
                     error.appendTo(element.attr("data-error-container"));
                 } else if (element.parents('.radio-list').size() > 0) { 
                     error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                 } else if (element.parents('.radio-inline').size() > 0) { 
                     error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                 } else if (element.parents('.checkbox-list').size() > 0) {
                     error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                 } else if (element.parents('.checkbox-inline').size() > 0) { 
                     error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                 } else {
                     error.insertAfter(element); // for other inputs, just perform default behavior
                 }
             },
             highlight: function (element) { // hightlight error inputs
                $(element)
                     .closest('.form-group').addClass('has-error'); // set error class to the control group
             },

             unhighlight: function (element) { // revert the change done by hightlight
                 $(element)
                     .closest('.form-group').removeClass('has-error'); // set error class to the control group
             },

             success: function (label) {
                 label
                     .closest('.form-group').removeClass('has-error'); // set success class to the control group
             }

         });
	});
	
	function returnPage(){
			window.location.href="${ctx}/resources/resources_list.izhbg?appId=${parameterMap.currentAppId}";
	}
	
	function role_addorupd(id){
		$("#userinfoForm").submit();
	}



</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>