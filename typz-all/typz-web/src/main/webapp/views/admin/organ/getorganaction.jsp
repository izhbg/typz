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
						添加机构信息
					</c:if>
					<c:if test="${role.gnjsDm ne null && role.gnjsDm ne ''}">
						修改机构信息
					</c:if>
				</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="userinfoForm" method="post">
						<input type="hidden" name="jgId" id="jgId" value="${organ.jgId }"/>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">机构代码：</label>
								<div class="col-md-4">
									<input name="jgDm" id="jgDm" class="form-control" maxlength=30 value="${organ.jgDm}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">机构名称：</label>
								<div class="col-md-4">
									<input name="jgMc" id="jgMc" class="form-control" maxlength=10 value="${organ.jgMc}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">上级机构：</label>
								<div class="col-md-4">
									<div class="input-group">
										<input type="hidden" name="sjjgId" id="uodg_citySel1" value="${organ.sjjgId }" />
										<input name="citySel" id="uodg_citySel" class="form-control" maxlength=10 value="${jgMc2}" onclick="uodg_showMenu()" readonly  />
										<span class="input-group-addon" onclick="uodg_showMenu(); return false;"><i class="fa fa-link" style="cursor: pointer;"></i></span>
									</div>
									<div id="uodg_menuContent" class="menuContent col-md-10" style="display:none; position: absolute;z-index: 999;padding: 0px;">
										<ul id="uodg_treeDemo" class="ztree" style="margin-top:0; height: 260px;background:white;border:#e5e7ee solid 1px;overflow-y:scroll;overflow-x:hidden;"></ul>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">所属应用：</label>
								<div class="col-md-4">
									<select name="appId" id="appId" class="form-control">
										<c:forEach items="${txtYy}" var="item">
											<option value="${item.yyId }"  <c:if test="${organ.appId eq item.yyId}">selected="selected"</c:if>>${item.chineseName }</option>
										</c:forEach>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">有效标记：</label>
								<div class="col-md-4">
									<select name="yxBj" id="yxBj" class="form-control">
										<option value="1"   <c:if test="${organ.yxBj==1}">selected="selected"</c:if> >有效</option>
										<option value="2" <c:if test="${organ.yxBj==2}">selected="selected"</c:if>>无效</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">序号：</label>
								<div class="col-md-4">
									<input name="xh" id="xh" class="form-control" maxlength=10 value="${organ.xh}"/>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">机构类型：</label>
								<div class="col-md-4">
									<select name="jgLx" id="jgLx" class="form-control">
										<option value="1"   <c:if test="${organ.jgLx==1}">selected="selected"</c:if> >集团公司</option>
										<option value="2" <c:if test="${organ.jgLx==2}">selected="selected"</c:if>>成员酒店</option>
									</select>
									<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">备注：</label>
								<div class="col-md-4">
									<textarea name="bz" id="bz" class="form-control">${organ.bz }</textarea>				
									<span class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="organ_addorupd('${organ.jgId }')" class="btn green">提交</button>
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
	<script type="text/javascript">
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
				url:"${ctx}/org/getGUserOrganCheckTree.izhbg",
				autoParam:["id=id"],
				otherParam: ["jgId", '${organ.jgId}'],
				type:"post"
			},
			callback: {
				beforeClick : uodg_beforeClick,
				onCheck: uodg_onCheck
			}
		};

	var uodg_zNodes=${result};
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
			 jgDm: {
                     minlength: 2,
                     required: true,
                     remote:{                         //自带远程验证存在的方法  
		                 url:"${ctx}/org/validateJgDm.izhbg",  
		                 type:"post",  
		                 dataType:"html",  
		                 data:{  
		                	 jgDm:function(){return $("#jgDm").val();}  
		                 },  
		                 dataFilter: function(data, type) {  
		                      if (data == "yes")  
		                          return true;  
		                      else
		                      {
			                      if($("#jgId").val()){
									return true;
				                   }else{
		                    	  	return false;
				                      }
			                   }  
		                            
		                 }  
		              }  
                 },
                 jgMc: {
                	 minlength: 2,
                     required: true
                 }
             }, messages: {
            	 jgDm:{
            	 	required: "机构代码不能为空",
            	 	remote:"机构代码已存在请重新填写"
            	 },
            	 jgMc:{
            		required: "机构名称不能为空"
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
		window.location.href="${ctx}/org/org-list.izhbg?sjjgId=${sjjgId}&currentAppId=${currentAppId}";
		}
	function organ_addorupd(id){
		if(id)
			$("#userinfoForm").attr("action","${ctx}/org/updateOrg.izhbg?currentAppId=${currentAppId}");
		else
			$("#userinfoForm").attr("action","${ctx}/org/addOrgan.izhbg?currentAppId=${currentAppId}");

		$("#userinfoForm").submit();
		}
	</script>
</body>
</html>