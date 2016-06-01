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
					注册Client
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="${ctx}/client/register_client.izhbg" class="form-horizontal" id="userinfoForm" method="post">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">client_id<em class="text-danger">*</em></label>
								<div class="col-md-9">
									<input name="clientId" id="clientId" class="form-control" maxlength=50 value=""/>
									<p class="help-block">client_id必须输入,且必须唯一,长度至少5位; 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">client_secret<em class="text-danger">*</em></label>
								<div class="col-md-9">
									<input name="clientSecret" id="clientSecret" class="form-control" maxlength=50 value=""/>
									<p class="help-block">client_secret必须输入,且长度至少8位; 在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念.</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">resource_ids<em class="text-danger">*</em></label>
								<div class="col-md-9">
									<select name="resourceIds" id="resourceIds" class="form-control">
										<option value="mobile-resource"   selected="selected">mobile-resource</option>
									</select>
									<p class="help-block">resourceIds必须选择; 可选值必须来源于与<code>security.xml</code>中标签<code>&lsaquo;oauth2:resource-server</code>的属性<code>resource-id</code>值
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">scope<em class="text-danger">*</em></label>
								<div class="col-md-9">
									<select name="scope" id="scope" class="form-control">
										<option value="write">write</option>
										<option value="read">read</option>
										<option value="read,write">read,write</option>
									</select>
									<p class="help-block">scope必须选择</p>
								</div>
							</div>
							<div class="form-group">
				                <label class="col-sm-3 control-label">grant_type(s)<em class="text-danger">*</em></label>
				                <div class="col-sm-9">
				                    <label class="checkbox-inline">
				                        <input type="checkbox" name="authorizedGrantTypes"
				                               value="authorization_code" />
				                        authorization_code
				                    </label>
				                    <label class="checkbox-inline">
				                        <input type="checkbox" name="authorizedGrantTypes"
				                               value="password" />
				                        password
				                    </label>
				                    <label class="checkbox-inline">
				                        <input type="checkbox" name="authorizedGrantTypes"
				                               value="implicit" />
				                        implicit
				                    </label>
				                    <label class="checkbox-inline">
				                        <input type="checkbox" name="authorizedGrantTypes"
				                               value="client_credentials"  />
				                        client_credentials
				                    </label>
				                    <label class="checkbox-inline">
				                        <input type="checkbox" name="authorizedGrantTypes"
				                               value="refresh_token"  />
				                        refresh_token
				                    </label>
				
				                    <p class="help-block">至少勾选一项grant_type(s), 且不能只单独勾选<code>refresh_token</code></p>
				                </div>
				            </div>
				            <div class="form-group">
				                <label for="webServerRedirectUri" class="col-sm-3 control-label">redirect_uri</label>
				                <div class="col-sm-9">
									<input name="webServerRedirectUri" id="webServerRedirectUri" class="form-control" maxlength=50 value=""/>
				                    <p class="help-block">若<code>grant_type</code>包括<em>authorization_code</em>或<em>implicit</em>,
				                        则必须输入redirect_uri</p>
				                </div>
				            </div>
				
				            <div class="form-group">
				                <label for="authorities" class="col-sm-3 control-label">authorities</label>
				
				                <div class="col-sm-9">
				                	<select name="authorities" id="authorities" class="form-control">
										<option value="ROLE_MOBILE"   selected="selected">ROLE_MOBILE</option>
									</select>
				                    <p class="help-block">指定客户端所拥有的Spring Security的权限值,可选;
								                        若<code>grant_type</code>为<em>implicit</em>或<em>client_credentials</em>则必须选择authorities,
								                        因为服务端将根据该字段值的权限来判断是否有权限访问对应的API</p>
				                </div>
				            </div>
				
				            <div ng-show="visible">
				                <div class="form-group">
				                    <label for="accessTokenValidity" class="col-sm-3 control-label">access_token_validity</label>
				
				                    <div class="col-sm-9">
				                        <input type="number" class="form-control" name="accessTokenValidity" id="accessTokenValidity"
				                               placeholder="access_token_validity"/>
				
				                        <p class="help-block">设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时);
				                            若设定则必须是大于0的整数值</p>
				                    </div>
				                </div>
				
				                <div class="form-group">
				                    <label for="refreshTokenValidity" class="col-sm-3 control-label">refresh_token_validity</label>
				
				                    <div class="col-sm-9">
				                        <input type="number" class="form-control" name="refreshTokenValidity" id="refreshTokenValidity"
				                               placeholder="refresh_token_validity"/>
				
				                        <p class="help-block">设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30,
				                            30天);
				                            若设定则必须是大于0的整数值</p>
				                    </div>
				                </div>
				
				                <div class="form-group">
				                    <label for="additionalInformation" class="col-sm-3 control-label">additional_information</label>
				
				                    <div class="col-sm-9">
				                        <input type="text" class="form-control" name="additionalInformation" id="additionalInformation"
				                               placeholder="additional_information"/>
				
				                        <p class="help-block">
				                            这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:
				                            <code>{"country":"CN","country_code":"086"}</code>
				                        </p>
				                    </div>
				                </div>
				
				
				                <div class="form-group">
				                    <label class="col-sm-3 control-label">trusted</label>
				
				                    <div class="col-sm-9">
				                        <label class="radio-inline">
				                            <input type="radio" name="trusted" value="true"/> Yes
				                        </label>
				                        <label class="radio-inline">
				                            <input type="radio" name="trusted" value="false" checked="checked"/> No
				                        </label>
				
				                        <p class="help-block">该属性是扩展的,
				                            只适用于grant_type(s)包括<code>authorization_code</code>的情况,当用户登录成功后,若选No,则会跳转到让用户Approve的页面让用户同意授权,
				                            若选Yes,则在登录后不需要再让用户Approve同意授权(因为是受信任的)</p>
				                    </div>
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
            	 clientId: {
                	 minlength: 2,
                     required: true
                 },
                 clientSecret: {
                	 minlength: 2,
                     required: true
                 },
                 resourceIds: {
                     required: true
                 },
                 scope: {
                     required: true
                 },
                 authorizedGrantTypes: {
                     required: true
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