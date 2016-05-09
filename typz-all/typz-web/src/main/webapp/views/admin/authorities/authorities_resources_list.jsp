<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/bootstrap-switch/static/stylesheets/bootstrap-switch-metro.css"/>
<style type="text/css">
	ul.ztree li a{width: 470px;}
</style>
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

		<div class="content content-inner">
			<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
			<!-- 操作按钮如下 -->
			<div class="content content-inner">
			<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">权限授权资源【权限：${tXtAuthorities.authorityId }权限名称：${tXtAuthorities.authorityName }】</h3>
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="#treeDialog" data-toggle="modal" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
				<a href="javascript:rf_del()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
				<a href="${ctx }/authorities/authorities_list.izhbg" class="btn btn-sm default"  ><i class="fa fa-mail-reply-all"></i>返回</a>
				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
				<form action="${ctx}/authorities_resources/authorities_resources_list.izhbg" id="form1">
					<input type="hidden" name="authorityId" value="${tXtAuthorities.authorityId}">
				</form>
			</div>
			<!-- Table -->
			 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable" >
				<thead >
					<tr >
						<th width="50" style="text-align: center;" class="id">
							<input type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" />
						</th>
						<th >权限代码</th>
						<th >权限名称</th>
						<th >有效</th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${page.result}" var="item">
						<tr id="${item.id}">
							<td style="text-align: center;" class="id">
								<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.id}"/></td>
							<td > ${item.resources.resourceId }
							</td>
							<td>${item.resources.resourceName}
							</td>
							<td>${item.enabled }
							</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
			<div class="form-actions fluid" style="margin-top: 0px;">
			<div class="col-md-5">  
				<div class="dataTables_info" id="sample_3_info">共${page.totalCount}条记录 显示${page.start+1}到${page.start+page.pageSize}条记录</div>
			</div>
			<div class="col-md-7">
				<div class="dataTables_paginate paging_bootstrap  pull-right">    
				  <button class="btn btn-sm default">&lt;</button>
				  <button class="btn btn-sm default">1</button>
				  <button class="btn btn-sm default">&gt;</button>
				</div>
						
			</div>
			</div>
			</div>
			<!-- 搜索条件结束 -->
		
			
		</div>
		<div class="clearfix"></div>

		 <div class="modal fade" id="treeDialog" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">选择资源节点</h4>
					</div>
					<div class="modal-body">
						<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible1="1">  
							<ul id="rf_treeDemo" class="ztree"></ul> 
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn default" data-dismiss="modal">取消</button>
						<button type="button" onclick="rf_add()" class="btn blue">确定</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>


	



	</div>
	</div>
	</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/common.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/ucenter.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/bootstrap-switch/static/js/bootstrap-switch.min.js"></script>  



<script type="text/javascript">
var config = {
		id: 'userGrid',
	    pageNo: ${page.pageNo},
	    pageSize: ${page.pageSize},
	    totalCount: ${page.totalCount},
	    resultSize: ${page.resultSize},
	    pageCount: ${page.pageCount},
	    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
	    order: '${page.order=="ASC"?"DESC":"ASC"}',
	    params: {
	        'authorityId':'${parameterMap.authorityId }'
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'form1' 
		};

var rf_setting = {	
		check: {
			enable: true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			
		}
	};
var rf_tree;
function rf_add(){
	var nodes = rf_tree.getChangeCheckedNodes();
	var pars = 'authorityId=${tXtAuthorities.authorityId}';
	for(var i in nodes){
		pars += "&checkdel=" + nodes[i].id;
	}
	$.ajax({
		type: "POST",
	    url: "${ctx }/authorities_resources/authorities_resources_add.izhbg",
	    data: pars,
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}


var table;

$(function() {
	table = new Table(config);
    table.configPagination('.dataTables_paginate');
    table.configPageInfo('.dataTables_info'); 
    table.configPageSize('.m-wrap'); 


	var rf_zNodes =${result};
	rf_tree = $.fn.zTree.init($("#rf_treeDemo"), rf_setting, rf_zNodes);



});

function deleteInfo(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/authorities_resources/authorities_resources_dell.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function clearForm(){
	$(':input','#form1')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected'); 
}
</script>
</html>