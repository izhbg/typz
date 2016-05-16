<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
</head>
<body class="page-header-fixed"> 
<jsp:include page="/common/header.jsp"></jsp:include> 
<div class="clearfix"></div>
	<div class="page-container">
		<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
				<div class="row inbox">
				<div class="col-md-3">
					<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">表单类型</h3>
					</div>
					<div class="panel-body">
			            <ul id="user_treeDemo" class="ztree"></ul>
					</div>
					</div>
				 </div>
				 <div class="col-md-9" style="padding-left: 0px;"> 
					 <jsp:include page="maintablemin.jsp"></jsp:include>
				 </div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="formtemplatetypeDialog" tabindex="-1" user="basic" aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">新建类型</h4>
				</div>
				<div class="modal-body">
					<div class="scroller" style="height: 170px;" data-always-visible="1" data-rail-visible1="1">  
						<form class="form-horizontal" action="${ctx}/maintable/maintabletype_save.izhbg" id="formtemplatetypeform"  method="post">
							
							<input type="hidden" id="formtemplatetypeform_pid" name="pid"/>
							<input type="hidden" id="formtemplatetypeform_id" name="id"/>
							
							<div class="form-group">
								<label class="col-md-4 control-label">类型名称：</label>
								<div class="col-md-6">
									<input name="name" id="formtemplatetypeform_name" class="form-control"  maxlength=30 value="" />
									<span class="help-block"></span>
								</div>
							</div>
					  	</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">取消</button>
					<button type="button" onclick="formtemplatetype_add()" class="btn blue">确定</button>
				</div>
			</div>
			<!-- /.modal-content -->
			</div>
		<!-- /.modal-dialog -->
	</div>
	<div class="clearfix"></div>
	<div class="modal fade" id="formtemplatetyperemoveDialog" tabindex="-1" user="basic" aria-hidden="true" style="display: none;">
		<form class="form-horizontal" action="${ctx}/maintable/maintabletype_remove.izhbg" id="formtemplatetyperemoveform"  method="post">
			<input name="selectedItem" id="selectedItem" type="hidden">
		</form>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
<script>
//$(window).resize(div_user);
var user_setting = {
		view: {
			addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        check: {
			enable: true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : user_onClick
		}
	};
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    
    var addStr = "<span class='button remove' id='removeBtn_" + treeNode.tId
            + "' title='删除该节点' onfocus='this.blur();'></span>";

    addStr += "<span title='添加节点' class='button add' id='addBtn_" + treeNode.tId + "'></span>";
    addStr += "<span class='button edit' title='编辑该节点' id='editBtn_" + treeNode.tId + "'></span>";
    sObj.after(addStr);
    
    var btn_add = $("#addBtn_"+treeNode.tId);
    if (btn_add) btn_add.bind("click", function(){
    	//添加
    	$("#formtemplatetypeform_pid").val(treeNode.id);
    	$("#formtemplatetypeDialog").modal({
            backdrop: 'static',
            keyboard: false  
        }).modal('show');
    	
        return false;
    });
    
    var btn_del = $("#removeBtn_"+treeNode.tId);
	if (btn_del) btn_del.bind("click", function(){
    	$("#formtemplatetyperemoveDialog").find("#selectedItem").val(treeNode.id);
    	$("#formtemplatetyperemoveform").submit();
        return false;
    });
	
	var btn_edit = $("#editBtn_"+treeNode.tId);
	if (btn_edit) btn_edit.bind("click", function(){
		$("#formtemplatetypeform_id").val(treeNode.id);
		$("#formtemplatetypeform_name").val(treeNode.text);
		
		$("#formtemplatetypeDialog").modal({
            backdrop: 'static',
            keyboard: false  
        }).modal('show');
		
        return false;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
    $("#removeBtn_"+treeNode.tId).unbind().remove();
    $("#editBtn_"+treeNode.tId).unbind().remove();
};
function user_onClick(e, treeId, treeNode) {
	$("#type").val(treeNode ? treeNode.id : "");
	$("#searchForm").submit();
}
var user_zNodes =${result};
var treeObj = $.fn.zTree.init($("#user_treeDemo"), user_setting, user_zNodes);
treeObj.expandAll(true);
var node = treeObj.getNodeByParam("id", $("#type").val());
treeObj.selectNode(node,false);

function formtemplatetype_add(){
	var val = $("#formtemplatetypeform_name").val();
	if(val){
		$("#formtemplatetypeform").submit();
	}else{
		alert("名称不能为空");
	}
}
</script>
