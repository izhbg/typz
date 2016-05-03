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
					修改密码
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="userinfoForm" method="post">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">原密码：</label>
								<div class="col-md-4">
									<input name="oldPassword" id="oldPassword" class="form-control" maxlength=20  type="password"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">新密码：</label>
								<div class="col-md-4">
									<input name="newPassword" id="newPassword" class="form-control" type="password" maxlength=20 />
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">确认密码：</label>
								<div class="col-md-4">
									<input name="confirmPassword" id="confirmPassword" class="form-control" type="password" maxlength=20 />
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="m-spacer"></div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="role_addorupd()" class="btn green">提交</button>
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
	<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation//jquery.validate.min.js"></script>
	<script>
	$(document).ready(function(){
		//validate 

	jQuery.validator.addMethod("notEqual",
                function (value, element, params) {
                    var oldPwd = $(params).val();
                    return oldPwd == value ? false : true;
                },"新旧密码不能相同"); 
	jQuery.validator.addMethod("equalTo",
                function (value, element, params) {
                    var oldPwd = $(params).val();
                    return oldPwd == value ? true : false;
                },"两次输入的密码不一致"); 
	var formvalidate = $('#userinfoForm');
			formvalidate.validate( {
				errorElement :'span', //default input error message container
				errorClass :'help-block', // default input error message class
				focusInvalid :false, // do not focus the last invalid input
				ignore :"",
				rules : {
					oldPassword : {
						minlength :6,
						required :true
					},
					newPassword : {
						minlength :6,
						required :true,
						notEqual :"#oldPassword"
					},
					confirmPassword : {
						minlength :6,
						required :true,
						notEqual :"#oldPassword",
						equalTo :"#newPassword"

					}
				},
				messages : {
					oldPassword : {
						required :"旧密码不能为空",
						minlength :"密码至少为6位"
					},
					newPassword : {
						required :"新密码不能为空",
						minlength :"密码至少为6位"
					},
					confirmPassword : {
						required :"确认密码不能为空",
						minlength :"密码至少为6位",
						equalTo :"两次输入的密码不相同"
					}

				},
				errorPlacement : function(error, element) { // render error placement for each input type
					if (element.parent(".input-group").size() > 0) {
						error.insertAfter(element.parent(".input-group"));
					} else if (element.attr("data-error-container")) {
						error.appendTo(element.attr("data-error-container"));
					} else if (element.parents('.radio-list').size() > 0) {
						error.appendTo(element.parents('.radio-list').attr(
								"data-error-container"));
					} else if (element.parents('.radio-inline').size() > 0) {
						error.appendTo(element.parents('.radio-inline').attr(
								"data-error-container"));
					} else if (element.parents('.checkbox-list').size() > 0) {
						error.appendTo(element.parents('.checkbox-list').attr(
								"data-error-container"));
					} else if (element.parents('.checkbox-inline').size() > 0) {
						error.appendTo(element.parents('.checkbox-inline')
								.attr("data-error-container"));
					} else {
						error.insertAfter(element); // for other inputs, just perform default behavior
					}
				},
				highlight : function(element) { // hightlight error inputs
					$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
				},

				unhighlight : function(element) { // revert the change done by hightlight
					$(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
				},

				success : function(label) {
					label.closest('.form-group').removeClass('has-error'); // set success class to the control group
			}

			});
		});

	function getPortalImg() {

	}
	function returnPage() {
		window.location.href = "${ctx}/dashboard/dashboard.izhbg";
	}
	function role_addorupd() {
		$("#userinfoForm").attr("action",
				"${ctx}/user/change-password-save.izhbg");

		$("#userinfoForm").submit();
	}
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>