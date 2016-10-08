<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
</head>

<body>

<div class="content content-inner">
	<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
	<!-- 操作按钮如下 -->
	<div class="content content-inner">
			<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
			<!-- 操作按钮如下 -->
			<div class="content content-inner">
			<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h3 class="panel-title">列表</h3>
			</div>
			<div class="panel-body">
					<a href="#treeDialog" data-toggle="modal" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
					<!--
				  <a href="javascript:location.href='${ctx}/default/management/security/maintable/modifyMainTable.do'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>新建</a> 
				  --><!--<a href="javascript:table.removeAll()" class="btn btn-sm default"><span class="glyphicon glyphicon-calendar"></span> 删除</a> 
				  --><a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
				<div class="btn-group">
					<a class="btn btn-sm red" href="#" data-toggle="dropdown">
					<i class="icon-user"></i> 其它
					<i class="icon-angle-down "></i>
					</a>
					<ul class="dropdown-menu pull-right">
						<li><a href="javascript:table.exportExcel()"><i class="icon-ban-circle"></i>导出</a></li>
						<li class="divider"></li>
					</ul>
				</div>
				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
		
				<div id="collapse-group" class="collapse <c:if test="${(tableName ne null&&tableName ne '')||(tableCName ne null&&tableCName ne '') }">in</c:if> ">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="panel-body">
						<form id="searchForm" name="searchForm" method='post' action="${ctx}/maintable/maintable_list.izhbg" class="m-form-blank">
							<input type="hidden" name="methodCall" value="findMainTableListByPars">
							<input type="hidden" name="type" id="type" value="${type}">
							<div class="form-body">
								<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5" >字段名：</label>
									<div class="col-md-7">
										<input name="tableName" id="tableName" type="text" class="form-control" value='${tableName}' />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5" >字段中文名：</label>
									<div class="col-md-7">
										<input name="tableCName" id="tableCName" type="text" class="form-control" value='${tableCName}' />
									</div>
								</div>
							</div>
							<!--/span-->
							<!--/span-->
						</div>
								<!--/row-->
							</div>
						</form>
						</div>
						<div class="form-actions right" style="margin-top: 0px;">
							<button type="button" class="btn default" onclick="clearForm()">清空</button>
							<button type="button" onclick="javascript:document.searchForm.submit()" class="btn blue"><i class="fa fa-check"></i> 查询</button> 
						</div>
					 </div>		    
			    </div> 
			</div>
			<!-- Table -->
		    <form id="cal-infoGridForm" name="cal-infoGridForm" method='post' action="cal-info-remove.do" class="m-form-blank">
			 <table id="cal-infoGrid" class="table table-striped  table-hover dataTable" style="border-top: 1px solid green;">
			    <thead>
			      <tr>
			        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
			        <th width="20%">表编号</th>
					<th width="20%">表名</th>
					<th width="20%">表中文名</th>
					<th width="20%">格式类型</th>
					<th width="20%">操作</th>
			      </tr>
			    </thead>
			
			    <tbody>
			      <c:forEach items="${page.result}" var="item">
			      <tr>
			        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${item.tableid}"></td>
			        <td><a href="${ctx}/maintablecolumn/maintablecolumn_list.izhbg?maintableid=${item.tableid}"  title="${item.tableCName}（${item.tableName}）">${item.tableCName}(点击进入修改表字段)</a></td>
			        <td>${item.tableName}</td>
			        <td>${item.tableCName}</td>
			        <td><c:if test="${item.formatType eq 0 }">表</c:if> <c:if
						test="${item.formatType eq 1 }">图</c:if> <c:if
						test="${item.formatType eq 2 }">图表</c:if></td>
			        <td> 
			          <a  href="${ctx}/maintable/maintable_remove.izhbg?maintableid=${item.tableid}" >删除</a>|
					<a 
						href="${ctx}/maintable/maintable_detail.izhbg?maintableid=${item.tableid}" title="${item.tableCName}（${item.tableName}）">修改</a>|
					<a 
						href="${ctx}/maintabledata/maintabledata_pagelist.izhbg?tableName=${item.tableName}">预览表格</a>
			        </td>
			      </tr>
			      </c:forEach>
			    </tbody>
			  </table>
			</form>
			<div class="form-actions fluid" style="margin-top: 0px;">
			<div class="col-md-5">  
				<div class="dataTables_info" id="sample_3_info">共${page.totalCount}条记录 显示${page.start+1}到${page.start+page.pageSize}条记录</div>
			</div>
			<div class="col-md-7">
				<div class="dataTables_paginate paging_bootstrap  pull-right pagination">    
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
	</div>
</div>
<div class="clearfix"></div>
<div class="modal fade" id="treeDialog" tabindex="-1" user="basic" aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">选择新建方式</h4>
			</div>
			<div class="modal-body">
				<div class="scroller" style="height: 170px;" data-always-visible="1" data-rail-visible1="1">  
					<form class="form-horizontal" action="${ctx}/maintable/maintable_copy.izhbg" id="formnew" method="post">
						<div class="form-group">
							<label class="col-md-4 control-label">表单名称：</label>
							<div class="col-md-6">
								<input name="formname" id="formname" class="form-control"  maxlength=30 value="" />
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">表单类型：</label>
							<div class="col-md-6">
								<select name="formtype" id="formtype" class="form-control">
									<option value="">请选择表单类型</option>
									<c:forEach items="${typelist}" var="item">
										<option value="${item.id }">${item.name }</option>
									</c:forEach>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">复制自：</label>
							<div class="col-md-6">
								<select name="fromid" id="fromid" class="form-control">
									<option value="">请选择复制表单</option>
								</select>
								<span class="help-block"></span>
							</div>
						</div>
					  	
				  	</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">取消</button>
				<button type="button" onclick="form_add()" class="btn blue">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

</body>
<script type="text/javascript">
var config = {
	    id: 'cal-infoGridForm',
	    pageNo: ${page.pageNo},
	    pageSize: ${page.pageSize},
	    totalCount: ${page.totalCount},
	    resultSize: ${page.resultSize},
	    pageCount: ${page.pageCount},
	    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
	    asc: ${page.asc},
	    params: {
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'dynamicModelGridForm',
		exportUrl: 'form-template-export.do'
	};

	var table;

	$(function() {
		table = new Table(config);
	    table.configPagination('.pagination');
	    table.configPageInfo('.dataTables_info'); 
	    table.configPageSize('.m-wrap');
	});
</script>

<script type="text/javascript">
	$(function(){
		 $("#formtype").change(function(){
				var id  = $(this).val();
				if(id!=null&&id!=""){
					$.ajax({
						   type: "POST",
						   url: "${pageContext.request.contextPath}/default/management/security/maintable/getFormlistByType.do",
						   data: {id:id},
						   dataType:"json",
						   cache: false,
						   success: function(data){
							  var json = eval(data);
							   var options=""; 
							   if(json.length>0){ 
							   options+="<option value=''>请选择复制表单</option>"; 
							   for(var i=0;i<json.length;i++){ 
							   	options+="<option value="+json[i].table_id+">"+json[i].table_cname+"</option>"; 
							   }
							   }
							   $("#fromid").html(options); 
						   },
						   error:function(e){}
						   });
				}
			});
	})
	function querylist(){
		var xform = document.queryform;
		xform.submit();
	}
	function form_add(){
		var val = $("#formname").val();
		if(val){
			$("#formnew").submit();
		}else{
			alert("表单名称不能为空");
		}
		
	}
</script>

 <link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">
</html>