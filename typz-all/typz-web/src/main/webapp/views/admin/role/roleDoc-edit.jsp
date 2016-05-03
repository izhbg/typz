<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/s/cloudFile/css/cloud.css">
<script type="text/javascript" src="${ctx}/s/cloudFile/scripts/cloud.js"></script>
<script type="text/javascript" src="${ctx}/s/cloudFile/scripts/cloud.core.js"></script>
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
				<div class="panel-heading">
					<h3 class="panel-title">
					授权文档仓库
					</h3></div>
				<div class="panel-body form">
					<!-- BEGIN FORM-->
					<form action="${ctx}/rolFun/roleDoc-add.izhbg" class="form-horizontal" id="userinfoForm" onsubmit="return initForm();" method="post">
						<div class="form-body">
							<div class="portlet-title">
								<div class="caption"><i class="fa fa-reorder"></i>文档查看权限</div>
							</div>
							<div class="alert alert-div alert-info">默认只能查看本部门及下级部门的文档。</div>
							<div class="chx-container">
								<span class='chx-item' style='width: 400px;'><input id='viewSelfDept' type='checkbox' />只能查看本部门，不能查看下级部门</span>	
							</div>
							<div id="DOCVIEW" style="margin-bottom: 10px;">
								<ul id="tree" class="ztree tree-container" style="overflow: auto;"></ul>
							</div>
							
							<div class="portlet-title">
								<div class="caption"><i class="fa fa-reorder"></i>文档操作权限</div>
							</div>
							<div id="DOCOP" class="chx-container"></div>
							
							<input type="hidden" name="gnjsDm" value="${js.gnjsDm}" />
							<input type="hidden" id="_resourceIds" name="resourceIds"  value="${js.resourceIds}"/>
							<input type="hidden" id="_viewDirIds" name="viewDirIds" value="${js.viewDirIds}"/>
							<input type="hidden" id="_viewSelfDept" name="viewSelfDept" value="${js.viewSelfDept}"/>
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="submit"  class="btn green">提交</button>
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
<jsp:include page="/common/footer.jsp"></jsp:include>
<script>
		initResource();
		
		function initResource() {
			_remoteCall("role/getResources.izhbg", null, function(data) {
				$("#ORG").html(combineResHtml(data.ORG));
				$("#SYS").html(combineResHtml(data.SYS));
				$("#DOCOP").html(combineResHtml(data.izhbgCOP));
				
				initPage();
				//if($("input:checkbox"))
				$("input:checkbox").iCheck({checkboxClass: "icheckbox_square-blue"});
				//autoFrameHeight();
			}, true);
		}
		
		function initPage() {
			var residss = $("#_resourceIds").val();
			var resIds = residss ? residss.split(",") : [];
			
			for(var j in resIds) {
				$("#" + resIds[j]).attr("checked", true);						
			}
			
			initDovViewRoleTree($("#_viewDirIds").val());	
			var views = $("#_viewSelfDept").val();				
			if(views&&views == "Y")  $("#viewSelfDept").iCheck("check");
		}
		
		function combineResHtml(d) {
			var html = "";
			for(var i in d) {
				html += "<span class='chx-item'><input id='" + d[i].id + "' type='checkbox' />" + d[i].name + "</span>";
			}
			return html;
		}
		
		function initForm() {
			// get resource ids
			var resIds = [];
			$("input:checkbox:checked:not(#viewSelfDept)").each(function() {
				resIds.push($(this).attr("id"));
			});
			
			$("#_resourceIds").val(resIds.join(","));
			
			// get view dir ids
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var nodes = treeObj.getCheckedNodes(true);
			var viewDirIds = [];
			
			for(var i in nodes) {
				viewDirIds.push("Y" == nodes[i].isDepartDir ? nodes[i].departId : nodes[i].id);
			}
			
			// get view self dept
			$("#_viewSelfDept").val(isChecked("#viewSelfDept") ? "Y" : "N"); 
			
			$("#_viewDirIds").val(viewDirIds.join(","));
			
			return checkForm();
		}
		
		// init doc view role tree
		function initDovViewRoleTree(dirIds) {
			var setting = {
				view: {
					showLine: true,
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: ""
					}
				},
				check: {
					enable: true,
					chkboxType: {"Y": "", "N": ""}
				}
			};
			
			_remoteCall("docstore/getAllDirTree.izhbg", null, function(datas) {
				if(!datas || datas.length == 0) {
					$("#DOCVIEW").html("无文件夹");	
				}
				
				for(var i in datas) {
					if(dirIds && dirIds != "null" && dirIds.indexOf(datas[i].id) != -1) {
						datas[i].checked = true;
					}
					datas[i].isParent = true;
					if(datas[i].isDepartDir == "Y")  datas[i].name = "[部门] " + datas[i].name; 
				}
					
				$.fn.zTree.init($("#tree"), setting, datas);	
			}, true);
		}
	</script>
</body>
</html>