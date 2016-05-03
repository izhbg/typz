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
					<c:if test="${app.yyId eq null||app.yyId eq ''}">
						添加应用信息
					</c:if>
					<c:if test="${app.yyId ne null && app.yyId ne ''}">
						修改应用信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="appinfoForm" method="post">
						<input type="hidden" name="yyId" id="yyId"  value="${app.yyId}" />
						<div class="form-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">系统编码：</label>
										<div class="col-md-9">
											<input name="code" id="code" class="form-control"  value="${app.code }" <c:if test="${app.yyId ne null && app.yyId ne ''}">readonly="readonly"	</c:if>  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">系统名称：</label>
										<div class="col-md-9">
											<input name="appName" id="appName" class="form-control"  value="${app.appName }" />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">系统中文名称：</label>
										<div class="col-md-9">
											<input name="chineseName" id="chineseName" class="form-control"  value="${app.chineseName }" />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">部门：</label>
										<div class="col-md-9">
											<input name="respectiveDivisions" id="respectiveDivisions"  class="form-control" value="${app.respectiveDivisions }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								
								<!--/span-->
							</div>
							<!--/row-->
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">徽标：</label>
										<div class="col-md-9">
											<input name="logoMark" id="logoMark"  class="form-control" value="${app.logoMark }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">联系方式：</label>
										<div class="col-md-9">
											<input name="contact" id="contact"  class="form-control" value="${app.contact }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<!--/row-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">责任人：</label>
										<div class="col-md-9">
											<div class="input-group">
												<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
												<input name="charger" id="charger" class="form-control"  value="${app.charger }"/>
											</div>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">用户类型：</label>
										<div class="col-md-9">
											<select name="yhLx" id="yhLx" class="form-control">
			        						<!--<ww:iterator value="#dictNew.getDictItems('d_apptype')">
			        						<option value="<ww:property value="itemCode"/>" 
			        							<ww:if test="itemCode.toString() == app.yhLx.toString()">selected</ww:if>><ww:property value="itemName"/></option>
			        						</ww:iterator>
										--></select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<!--/row-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">密码：</label>
										<div class="col-md-9">
											<c:if test="${app.yyId eq null||app.yyId eq''}">
												<input name="password" class="form-control" id="password" value="123456" maxlength=6 minlength=1 />
											</c:if>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">有效标记：</label>
										<div class="col-md-9">
											<select name="yxBj" id="yxBj" class="form-control">
												<option value="1"   <c:if test="${app.yxBj==1}">selected="selected"</c:if> >有效</option>
												<option value="2" <c:if test="${app.yxBj==2}">selected="selected"</c:if>>无效</option>
										   </select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<!--/row-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">登录展示：</label>
										<div class="col-md-9">
											<select name="loginDisplay" id="loginDisplay" class="form-control">
												<option value="1"   <c:if test="${app.loginDisplay==1}">selected="selected"</c:if> >是</option>
												<option value="2" <c:if test="${app.loginDisplay==2}">selected="selected"</c:if>>否</option>
										   </select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">显示：</label>
										<div class="col-md-9">
											<select name="showFlag" id="showFlag" class="form-control">
												<option value="1"   <c:if test="${app.showFlag==1}">selected="selected"</c:if> >是</option>
												<option value="2" <c:if test="${app.showFlag==2}">selected="selected"</c:if>>否</option>
										   </select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<!--/row-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">登录：</label>
										<div class="col-md-9">
											<select name="loginFlag" id="loginFlag" class="form-control">
												<option value="1"   <c:if test="${app.loginFlag==1}">selected="selected"</c:if> >是</option>
												<option value="2" <c:if test="${app.loginFlag==2}">selected="selected"</c:if>>否</option>
										   </select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">分类：</label>
										<div class="col-md-9">
											<input name="classification" id="classification" class="form-control" value="${app.classification }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<!--/row-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">排序：</label>
										<div class="col-md-9">
											<input name="sortNo" id="sortNo" class="form-control" value="${app.sortNo }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">发布地址：</label>
										<div class="col-md-9">
											<input name="deployUrl" id="deployUrl" class="form-control" value="${app.deployUrl }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
								<!--/span-->
							<div class="row">
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">主页：</label>
										<div class="col-md-9">
											<input name="homePage" id="homePage" class="form-control"  value="${app.homePage }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">描述：</label>
										<div class="col-md-9">
											<textarea name="description" id="description" class="form-control" >${app.description}</textarea>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								
							</div>
							<div class="row">
								<!--/span-->
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">图片：</label>
										<div class="col-md-9">
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								
							</div>
							<!--/row-->
							<!--/row-->
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="subForm('${app.yyId}')" class="btn green">提交</button>
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
	<jsp:include page="/common/footer.jsp"></jsp:include>
	 <script type="text/javascript">
	 $(document).ready(function(){
			//validate 

			 var formvalidate = $('#appinfoForm');
			 formvalidate.validate({
	             errorElement: 'span', //default input error message container
	             errorClass: 'help-block', // default input error message class
	             focusInvalid: false, // do not focus the last invalid input
	             ignore: "",
	             rules: {
				 code: {
	                     minlength: 2,
	                     required: true,
	                     remote:{                         //自带远程验证存在的方法  
			                 url:"${ctx}/sys/validateCode.izhbg",  
			                 type:"post",  
			                 dataType:"html",  
			                 data:{  
			                	 code:function(){return $("#code").val();}  
			                 },  
			                 dataFilter: function(data, type) {  
			                      if (data == "yes")  
			                          return true;  
			                      else
			                      {
				                      if($("#yyId").val()){
										return true;
					                   }else{
			                    	  	return false;
					                      }
				                   }  
			                            
			                 }  
			              }  
	                 },
	                 appName: {
	                	 minlength: 2,
	                     required: true
	                 }/**,
	                 deployUrl: {
	                     required: true
	                 },
	                 homePage: {
	                     required: true
	                 }**/
	             }, messages: {
	            	 code:{
	            	 	required: "系统编码不能为空",
	            	 	remote:"系统编码已存在请重新填写"
	            	 },
	            	 appName:{
	            		required: "系统名称不能为空"
	                	 }/**,
	                 deployUrl:{
	                	required: "发布地址不能为空"    
	                    },
	                  homePage:{
	                    required: "主页不能为空"       
	                        }**/
	             
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
		
		function getPortalImg(){
			
		}
		function returnPage(){
			window.location.href="${ctx}/sys/sys-list.izhbg";
			}
		function subForm(id){
			if(id)
				$("#appinfoForm").attr("action","${ctx}/sys/updateSys.izhbg");
			else
				$("#appinfoForm").attr("action","${ctx}/sys/addSystem.izhbg");
			
			$("#appinfoForm").submit();
		}
	</script>
</body>
</html>