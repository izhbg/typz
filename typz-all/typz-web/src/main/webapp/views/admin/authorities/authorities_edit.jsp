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
					<c:if test="${tXtAuthorities.authorityId eq null||tXtAuthorities.authorityId eq ''}">
						添加权限信息
					</c:if>
					<c:if test="${tXtAuthorities.authorityId ne null && tXtAuthorities.authorityId ne ''}">
						修改权限信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="${ctx}/authorities/authorities_addORupdate.izhbg" class="form-horizontal" id="userinfoForm" method="post">
						<input type="hidden" name="authorityId" id="authorityId" value="${tXtAuthorities.authorityId }"/>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">权限名称：</label>
								<div class="col-md-4">
									<input name="authorityName" id="authorityName" class="form-control" maxlength=10 value="${tXtAuthorities.authorityName}"/>
									<span class="help-block"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">有效标记：</label>
								<div class="col-md-4">
									<select name="enabled" id="enabled" class="form-control">
										<option value="1"   <c:if test="${tXtAuthorities.enabled==1}">selected="selected"</c:if> >有效</option>
										<option value="2" <c:if test="${tXtAuthorities.enabled==2}">selected="selected"</c:if>>无效</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">备注：</label>
								<div class="col-md-4">
									<textarea name="authorityDesc" id="authorityDesc" class="form-control">${tXtAuthorities.authorityDesc }</textarea>				
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="role_addorupd('${tXtAuthorities.authorityId }')" class="btn green">提交</button>
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
            		required: "权限名称不能为空"
                	 },
                 resourceString:{
            		required: "权限表达式不能为空"
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
			window.location.href="${ctx}/authorities/authorities_list.izhbg?appId=${parameterMap.currentAppId}";
	}
	
	function role_addorupd(id){
		$("#userinfoForm").submit();
	}



</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>