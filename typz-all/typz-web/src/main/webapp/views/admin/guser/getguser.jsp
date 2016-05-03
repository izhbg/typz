<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/s/assets/plugins/ajaxupload/ajaxupload.3.6.js"></script>
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
					<c:if test="${user.yhId eq null||user.yhId eq ''}">
						添加用户信息
					</c:if>
					<c:if test="${user.yhId ne null && user.yhId ne ''}">
						修改用户信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="userinfoForm" method="post">
						<div class="form-body">
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">登录名：</label>
										<div class="col-md-9">
											<input name="yhId" id="yhId" type="hidden"  value="${user.yhId }" />
											<input name="yhDm" id="yhDm" class="form-control"  value="${user.yhDm }" <c:if test="${user.yhId ne null && user.yhId ne ''}">readonly="readonly"	</c:if>  />
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">用户姓名：</label>
										<div class="col-md-9">
											<input name="yhMc" id="yhMc" class="form-control"  value="${user.yhMc }" />
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
										<label class="control-label col-md-3">机构名称：</label>
										<div class="col-md-9">
											<div class="input-group">
												<input type="hidden" name="checkdel" id="uodg_citySel1" value="${user.jgId}" />
												<input name="citySel" class="form-control"  id="uodg_citySel" value="${jgMc}" onclick="uodg_showMenu();" readonly class="readonly"/>
												<span class="input-group-addon" onclick="uodg_showMenu(); return false;"><i class="fa fa-link" style="cursor: pointer;"></i></span>
											</div>
											<div id="uodg_menuContent" class="menuContent col-md-10" style="display:none; position: absolute;z-index: 999;padding: 0px;">
												<ul id="uodg_treeDemo" class="ztree" style="margin-top:0; height: 260px;background:white;border:#e5e7ee solid 1px;overflow-y:scroll;overflow-x:hidden;"></ul>
											</div>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">密码：</label>
										<div class="col-md-9">
											<c:if test="${user.yhId eq null||user.yhId eq ''}">
												<input name="mm"  class="form-control" id="mm" value="123456" maxlength=6 minlength=1/>
											</c:if>
											<c:if test="${user.yhId ne null && user.yhId ne ''}">
												
											</c:if>
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
										<label class="control-label col-md-3">性别：</label>
										<div class="col-md-9">
											<select name="xb" id="xb" class="form-control" >
												<option value="1"   <c:if test="${user.xb==1}">selected="selected"</c:if> >男</option>
												<option value="2" <c:if test="${user.xb==2}">selected="selected"</c:if>>女</option>
										   </select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">邮箱：</label>
										<div class="col-md-9">
											<div class="input-group">
												<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
												<input name="email" id="email" class="form-control"  value="${user.email }"/>
											</div>
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
										<label class="control-label col-md-3">所属应用：</label>
										<div class="col-md-9">
											<select name="appId" id="appId" class="form-control">
												<c:forEach items="${txtYy}" var="item">
													<option value="${item.yyId }"  <c:if test="${user.appId eq item.yyId}">selected="selected"</c:if>>${item.chineseName }</option>
												</c:forEach>
											</select>
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
												<option value="1"   <c:if test="${user.yxBj==1}">selected="selected"</c:if> >有效</option>
												<option value="2" <c:if test="${user.yxBj==2}">selected="selected"</c:if>>无效</option>
										   </select>
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
										<label class="control-label col-md-3">序号：</label>
										<div class="col-md-9">
											<input name="xh" id="xh"  value="${user.xh }" class="form-control"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">工号：</label>
										<div class="col-md-9">
											<input name="photoPath" id="photoPath"  class="form-control" value="${user.photoPath }" onfocus="getPortalImg()"/>
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
										<label class="control-label col-md-3">办公室电话：</label>
										<div class="col-md-9">
											<input name="userOfficePhone" id="userOfficePhone" class="form-control"  value="${user.userOfficePhone }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">手机：</label>
										<div class="col-md-9">
											<input name="userMobile" id="userMobile" class="form-control"  value="${user.userMobile }"/>
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
										<label class="control-label col-md-3">ICQ(QQ)：</label>
										<div class="col-md-9">
											<input name="userIcq" id="userIcq" class="form-control" value="${user.userIcq }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">部门负责人：</label>
										<div class="col-md-9">
											<select name="userSystemAdmin" id="userSystemAdmin" class="form-control">
											<option value="0">否</option>
											<option value="1">是</option>
			        						<!--<ww:iterator value="#dictNew.getDictItems('d_truefalse')">
			        						<option value="<ww:property value="itemCode"/>" 
			        							<ww:if test="itemCode.toString() == user.userSystemAdmin.toString()">selected</ww:if>><ww:property value="itemName"/></option>
			        						</ww:iterator>
										--></select>
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
										<label class="control-label col-md-3">优先级：</label>
										<div class="col-md-9">
											<input name="userPriority" id="userPriority" class="form-control" value="${user.userPriority }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">等级：</label>
										<div class="col-md-9">
											<input name="userLevel" id="userLevel" class="form-control"  value="${user.userLevel }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
							</div>
								<!--/span-->
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">只读：</label>
										<div class="col-md-9">
											<select name="readOnly" id="readOnly" class="form-control">
											<option value="">--请选择--</option>
			        						<!--<ww:iterator value="#dictNew.getDictItems('d_truefalse')">
			        						<option value="<ww:property value="itemCode"/>" 
			        							<ww:if test="itemCode.toString() == user.readOnly.toString()">selected</ww:if>><ww:property value="itemName"/></option>
			        						</ww:iterator>
										--></select>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">密码有效时长：</label>
										<div class="col-md-9">
											<input name="userPwdDuration" id="userPwdDuration" class="form-control"  value="${user.userPwdDuration }"/>
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
										<label class="control-label col-md-3">照片路径：</label>
										<div class="col-md-9">
											<input name="staffId" id="staffId" class="form-control" value="${user.staffId }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">职称：</label>
										<div class="col-md-9">
											<input name="duty" id="duty" class="form-control" value="${user.duty }"/>
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
										<label class="control-label col-md-3">办公室门牌：</label>
										<div class="col-md-9">
											<input name="officeSpace" id="officeSpace" class="form-control"  value="${user.officeSpace }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">外部邮箱：</label>
										<div class="col-md-9">
											<div class="input-group">
												<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
												<input name="outEmail" id="outEmail"  class="form-control" value="${user.outEmail }"/>
											</div>
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
										<label class="control-label col-md-3">分管职责：</label>
										<div class="col-md-9">
											<input name="branchedpassageJob" class="form-control" id="branchedpassageJob" value="${user.branchedpassageJob }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">秘书：</label>
										<div class="col-md-9">
											<input name="secretary" id="secretary"  class="form-control" value="${user.secretary }"/>
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">备注：</label>
										<div class="col-md-9">
											<textarea name="bz" id="bz"  class="form-control" style="height:90px;">${user.bz }</textarea>			
											<span class="help-block"></span>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-3">电子签名</label>
										<div class="col-md-9">
											<input type="hidden" name="ddqmPath" id="ddqmPath" value="${user.ddqmPath}">
											<c:if test="${user.ddqmPath ne null && user.ddqmPath ne ''}">
												<img alt="签名" id="userqmImg" src="${ctx}/management/security/maintable/downloadFileImg.izhbg?attachId=${user.ddqmPath}">
											</c:if>
											<c:if test="${user.ddqmPath eq null || user.ddqmPath eq ''}">
												<img alt="签名" id="userqmImg" src="${ctx }/s/assets/img/qm_default.png">
											</c:if>
											<div style="margin-top:10px;">
												<input type="button" class="btn btn-danger" id="uploadqm" value="上传签名">
											</div>
										</div>
									</div>
								</div>
								<!--/span-->
								<!--/span-->
							</div>
							<!--/row-->
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="user_addorupd('${user.yhId }')" class="btn green">提交</button>
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
var uodg_setting = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""},
			chkStyle: "radio",
			radioType: "all"
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:"${ctx}/user/getGUserOrganCheckTree.izhbg",
			autoParam:["id=id"],
			otherParam: ["jgId", '${user.jgId}'],
			otherParam: ["appId", '${parameterMap.currentAppId}'],
			type:"post"
		},
		callback: {
			beforeClick : uodg_beforeClick,
			onCheck: uodg_onCheck
		}
	};

	var uodg_zNodes =${result};
	function uodg_beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("uodg_treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	function uodg_onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("uodg_treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		v = "";
		v1 = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			v1 += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (v1.length > 0 ) v1 = v1.substring(0, v1.length-1);
		var cityObj = $("#uodg_citySel");
		var cityObj1 = $("#uodg_citySel1");
		cityObj.attr("value", v);
		cityObj1.attr("value", v1);
	}
	function uodg_showMenu() {
		$("#uodg_menuContent").slideDown("fast");
		$("#dgformdiv").bind("mousedown", uodg_onBodyDown);	
	}
	function uodg_hideMenu() {
		$("#uodg_menuContent").fadeOut("fast");
		$("#dgformdiv").unbind("mousedown", uodg_onBodyDown);
	}
	function uodg_onBodyDown(event) {
		if (!(event.target.id == "uodg_menuBtn" || event.target.id == "uodg_citySel" 
				|| event.target.id == "uodg_menuContent" || $(event.target).parents("#uodg_menuContent").length>0)) {
			uodg_hideMenu();
		}
	}

	$(document).ready(function(){
		$.fn.zTree.init($("#uodg_treeDemo"), uodg_setting, uodg_zNodes);
		//validate 

		 var formvalidate = $('#userinfoForm');
		 formvalidate.validate({
             errorElement: 'span', //default input error message container
             errorClass: 'help-block', // default input error message class
             focusInvalid: false, // do not focus the last invalid input
             ignore: "",
             rules: {
			 	yhDm: {
                     minlength: 2,
                     required: true,
                     remote:{                         //自带远程验证存在的方法  
		                 url:"${ctx}/user/validateYhDm.izhbg",  
		                 type:"post",  
		                 dataType:"html",  
		                 data:{  
		                      username:function(){return $("#yhDm").val();}  
		                 },  
		                 dataFilter: function(data, type) {  
		                      if (data == "yes")  
		                          return true;  
		                      else
		                      {
			                      if($("#yhId").val()){
									return true;
				                   }else{
		                    	  	return false;
				                      }
			                   }  
		                            
		                 }  
		              }  
                 },
                 yhMc: {
                	 minlength: 2,
                     required: true
                 },
                 checkdel: {
                     required: true
                 },
                 mm: {
                     required: true
                 }
             }, messages: {
            	 yhDm:{
            	 	required: "登录名称不能为空",
            	 	remote:"登陆名称已存在请重新填写"
            	 },
            	 yhMc:{
            		required: "用户姓名不能为空"
                	 },
                	 checkdel:{
                	required: "所属单位不能为空"    
                    },
                   mm:{
                    required: "初始密码不能为空"       
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

		var ajaxupload = new AjaxUpload('#uploadqm', {
			action: '../management/security/maintable/uploadFileAttach.izhbg',
			name: 'uploadFile',
			autoSubmit: true,
			responseType: 'json',
			onSubmit: function(filename, ext) {
				ajaxupload.setData({ temppath:$("#temppath").val(),fileconfid:$("#fileconfid").val()});
				 if(!/\.(gif|jpg|jpeg|png|JPG|PNG)$/.test(filename)){
					showInfoWin("","未允许上传的文件格式!");
					return false;
				}
			},
			onComplete: function(filename, data) { 
				switch(data.result){
				case 0:
					//management/security/maintable/getAttachFilePathAndpdfToSwf.izhbg?attachId="+attachId;
					var fileId = data.mainTableAttachFile.id;
					$("#ddqmPath").val(fileId);
		        	if(fileId){
		        		//updateUserPicId(fileId);
		        	var url = getWebRootPath() + "/management/security/maintable/downloadFileImg.izhbg?attachId="+fileId;
		        	$("#userqmImg").attr("src",url);
			        }
					break;
				case 1:
					break;
				}
			}
		});

         
	});
	
	function getPortalImg(){
		
	}
	function returnPage(){
		window.location.href="${ctx}/user/user-list.izhbg?sjjgId=${user.jgId}&currentAppId=${currentAppId}";
		}
	function user_addorupd(id){
		if(id)
			$("#userinfoForm").attr("action","${ctx}/user/updateGUser.izhbg?currentAppId=${currentAppId}");
		else
			$("#userinfoForm").attr("action","${ctx}/user/addGUser.izhbg?currentAppId=${currentAppId}");

		$("#userinfoForm").submit();
		}



</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>