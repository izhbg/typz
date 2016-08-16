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
					<c:if test="${func.gnDm eq null||func.gnDm eq ''}">
						添加功能信息
					</c:if>
					<c:if test="${func.gnDm ne null && func.gnDm ne ''}">
						修改功能信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<input type="hidden" name="flag" id="flag" value="${func.gnDm}">
					<form action="#" class="form-horizontal" id="userinfoForm" method="post">
						<input type="hidden" name="currentAppId" id="currentAppId" value="${currentAppId }">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">功能代码：</label>
								<div class="col-md-4">
									<input name="gnDm" id="gnDm" class="form-control"  maxlength=30 <c:if test="${func.gnDm ne null&&func.gnDm ne ''}">readonly="readonly"</c:if> value="${func.gnDm}" />
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">功能名称：</label>
								<div class="col-md-4">
									<input name="gnMc" id="gnMc" class="form-control" maxlength=100 value="${func.gnMc}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">上级功能：</label>
								<div class="col-md-4">
									<div class="input-group">
										<input type="hidden" name="sjgnDm" id="uodg_citySel1" value="${func.sjgnDm }" />
										<input name="citySel" id="uodg_citySel" class="form-control"  value="${sjgnzyname}" onclick="uodg_showMenu()" readonly  />
										<span class="input-group-addon" onclick="uodg_showMenu(); return false;"><i class="fa fa-link" style="cursor: pointer;"></i></span>
									</div>
									<div id="uodg_menuContent" class="menuContent col-md-10" style="display:none; position: absolute;z-index: 999;">
										<ul id="uodg_treeDemo" class="ztree" style="margin-top:0; height: 260px;background:white;border:#e5e7ee solid 1px;overflow-y:scroll;overflow-x:hidden;"></ul>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">功能序号：</label>
								<div class="col-md-4">
									<input name="gnXh" id="gnXh" class="form-control" maxlength=10 value="${func.gnXh}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">功能类型：</label>
								<div class="col-md-4">
									<select name="gnLx" id="gnLx" class="form-control">
										<option value="1" <c:if test="${func.gnLx eq 1 }">selected="selected"</c:if> >私有功能</option>
										<c:if test="${currentYh.isadmin eq 2}">
											<option value="2" <c:if test="${func.gnLx eq 2 }">selected="selected"</c:if> >公有功能</option>
										</c:if>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">有效标记：</label>
								<div class="col-md-4">
									<select name="yxBj" id="yxBj" class="form-control">
										<option value="1"   <c:if test="${func.yxBj==1}">selected="selected"</c:if> >有效</option>
										<option value="2" <c:if test="${func.yxBj==2}">selected="selected"</c:if>>无效</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">是否生成快捷方式：</label>
								<div class="col-md-4">
									<select name="sqBj" id="sqBj" class="form-control">
										<option value="1"   <c:if test="${func.sqBj==1}">selected="selected"</c:if> >否</option>
										<option value="2" <c:if test="${func.sqBj==2}">selected="selected"</c:if>>是</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">所属系统：</label>
								<div class="col-md-4">
									<select name="appId" id="appId" class="form-control">
										<c:forEach items="${txtYy}" var="item">
											<option value="${item.yyId }"  <c:if test="${func.appId eq item.yyId}">selected="selected"</c:if>>${item.chineseName }</option>
										</c:forEach>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">url：</label>
								<div class="col-md-4">
									<input type="text" name="newurl" id="newurl" class="form-control" value="${func.newurl}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">图标（class）：</label>
								<div class="col-md-4">
									<input type="text" name="gnIcon" id="gnIcon" class="form-control" value="${func.gnIcon}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">备注（快捷方式的颜色）：</label>
								<div class="col-md-4">
									<textarea name="bz" id="bz" class="form-control">${func.bz }</textarea>				
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="func_addorupd('${func.gnDm }')" class="btn green">提交</button>
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
				url:"${ctx}/fun/getFuncCheckTree.izhbg",
				autoParam:["id=dm"],
				otherParam: ["gnDm", '${func.sjgnDm}'],
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
			 gnDm: {
                     minlength: 2,
                     required: true,
                     remote:{                         //自带远程验证存在的方法  
		                 url:"${ctx}/fun/validateGnDm.izhbg",  
		                 type:"post",  
		                 dataType:"html",  
		                 data:{  
		                	 gnDm:function(){return $("#gnDm").val();}  
		                 },  
		                 dataFilter: function(data, type) {  
		                      if (data == "yes")  
		                          return true;  
		                      else
		                      {
			                      if($("#flag").val()){
									return true;
				                   }else{
		                    	  	return false;
				                      }
			                   }  
		                            
		                 }  
		              }  
                 },
                 gnMc: {
                	 minlength: 2,
                     required: true
                 },
                 sjgnDm: {
                	 minlength: 2,
                     required: true
                 },
                 appId: {
                     required: true
                 }
             }, messages: {
            	 gnDm:{
            	 	required: "功能代码不能为空",
            	 	remote:"功能代码已存在请重新填写"
            	 },
            	 gnMc:{
            		required: "功能名称不能为空"
                	 },
                 sjgnDm:{
                	required: "上级功能不能为空"    
                         },
                  appId:{
                	required: "所属系统不能为空"    
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
		window.location.href="${ctx}/fun/fun_list.izhbg?appId=${func.appId}&sjgnDm=${func.sjgnDm }&currentAppId=${currentAppId}";
		}
	function func_addorupd(id){
		if(id)
			$("#userinfoForm").attr("action","${ctx}/fun/updateFun.izhbg");
		else
			$("#userinfoForm").attr("action","${ctx}/fun/addFun.izhbg");

		$("#userinfoForm").submit();
		}



</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>