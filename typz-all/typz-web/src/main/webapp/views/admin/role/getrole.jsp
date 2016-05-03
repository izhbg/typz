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
					<c:if test="${role.gnjsDm eq null||role.gnjsDm eq ''}">
						添加角色信息
					</c:if>
					<c:if test="${role.gnjsDm ne null && role.gnjsDm ne ''}">
						修改角色信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="userinfoForm" method="post">
						<input type="hidden" name="gnjsDm" id="gnjsDm" value="${role.gnjsDm }"/>
						<input type="hidden" name="appId2" id="appId2" value="${parameterMap.currentAppId}"/>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">角色代码：</label>
								<div class="col-md-4">
									<input name="code" id="code" class="form-control" maxlength=10 value="${role.code}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">角色名称：</label>
								<div class="col-md-4">
									<input name="gnjsMc" id="gnjsMc" class="form-control" maxlength=10 value="${role.gnjsMc}"/>
									<span class="help-block"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-3 control-label">角色类型：</label>
								<div class="col-md-4">
									<select name="jsLx" id="jsLx" class="form-control">
										<option value="1" <c:if test="${func.gnLx eq 1 }">selected="selected"</c:if> >私有角色</option>
										<c:if test="${currentYh.isadmin eq 2}">
											<option value="2" <c:if test="${func.gnLx eq 2 }">selected="selected"</c:if> >公有角色</option>
										</c:if>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">有效标记：</label>
								<div class="col-md-4">
									<select name="yxBj" id="yxBj" class="form-control">
										<option value="1"   <c:if test="${role.yxBj==1}">selected="selected"</c:if> >有效</option>
										<option value="2" <c:if test="${role.yxBj==2}">selected="selected"</c:if>>无效</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">所属系统：</label>
								<div class="col-md-4">
									<select name="appId" id="appId" class="form-control">
										<c:forEach items="${txtYy}" var="item">
											<option value="${item.yyId }"  <c:if test="${role.appId eq item.yyId}">selected="selected"</c:if>>${item.chineseName }</option>
										</c:forEach>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">是否是管理员：</label>
								<div class="col-md-4">
									<select name="jgId" id="jgId" class="form-control">
										<option value="1" <c:if test="${func.jgId eq 1 }">selected="selected"</c:if> >否</option>
										<c:if test="${currentYh.isadmin eq 2}">
											<option value="2" <c:if test="${func.jgId eq 2 }">selected="selected"</c:if> >是</option>
										</c:if>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">备注：</label>
								<div class="col-md-4">
									<textarea name="bz" id="bz" class="form-control">${role.bz }</textarea>				
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="role_addorupd('${role.gnjsDm }')" class="btn green">提交</button>
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
		//$.fn.zTree.init($("#uodg_treeDemo"), uodg_setting, uodg_zNodes);
		//validate 

		 var formvalidate = $('#userinfoForm');
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
		                 url:"${ctx}/role/validateJsDm.izhbg",  
		                 type:"post",  
		                 dataType:"html",  
		                 data:{  
		                      jsDm:function(){return $("#code").val();}  
		                 },  
		                 dataFilter: function(data, type) {  
		                      if (data == "yes")  
		                          return true;  
		                      else
		                      {
			                      if($("#gnjsDm").val()){
									return true;
				                   }else{
		                    	  	return false;
				                      }
			                   }  
		                            
		                 }  
		              }  
                 },
                 gnjsMc: {
                	 minlength: 2,
                     required: true
                 }
             }, messages: {
            	 code:{
            	 	required: "角色代码不能为空",
            	 	remote:"角色代码已存在请重新填写"
            	 },
            	 gnjsMc:{
            		required: "角色名称不能为空"
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
	
	function getPortalImg(){
		
	}
	function returnPage(){
		window.location.href="${ctx}/role/role-list.izhbg?appId=${parameterMap.currentAppId}";
		}
	function role_addorupd(id){
		if(id)
			$("#userinfoForm").attr("action","${ctx}/role/updRole.izhbg");
		else
			$("#userinfoForm").attr("action","${ctx}/role/addRole.izhbg");

		$("#userinfoForm").submit();
		}



</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>