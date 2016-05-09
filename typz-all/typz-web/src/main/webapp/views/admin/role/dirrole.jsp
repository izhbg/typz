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

		<div class="content content-inner">
			<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
			<!-- 操作按钮如下 -->
			<div class="content content-inner">
			<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h3 class="panel-title">角色列表</h3>
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="${ctx }/role/role-edit.izhbg?currentAppId=${parameterMap.appId}"" id="addDialog" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
				<a href="javascript:role_del()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
				<a class="btn btn-sm default"  type="button" onclick="role_updflag();"><i class="fa fa-ban"></i>更改状态</a>
				<a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
		
				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
		
				<div id="collapse-group" class="collapse <c:if test="${(parameterMap.yxBj ne null&&parameterMap.yxBj ne '') || (parameterMap.appId ne null&&parameterMap.appId ne '') || (parameterMap.code ne null&&parameterMap.code ne '')||(parameterMap.gnjsMc ne null&&parameterMap.gnjsMc ne '') }">in</c:if> ">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="panel-body">
						 <form id="form1" name="form1" action="${ctx }/role/role-list.izhbg" method="post" class="form-horizontal" >
							<input type="hidden" name="sjjgId" id="sjjgId" value="${parameterMap.sjjgId }"> 
							<div class="form-body">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5" >所属系统：</label>
											<div class="col-md-7">
												<select name="appId" id="appId" class="form-control">
													<option value="">--请选择--</option>
						       						<c:forEach items="${tXtYyList}" var="item">
														 <option value="${item.yyId }" <c:if test="${item.yyId eq parameterMap.appId}">selected="selected" </c:if> >${item.chineseName }</option>
													 </c:forEach>
												</select>
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">角色代码：</label>
											<div class="col-md-7">
												<input name="code" id="code"  class="form-control" type="text" value='${parameterMap.code }'  />
											</div>
										</div>
									</div>
									<!--/span-->
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">有效标记： </label>
											<div class="col-md-7">
												<select name="yxBj" id="yxBj" class="form-control" >
													<option value="">--请选择--</option>
													<option value="1"   <c:if test="${parameterMap.yxBj==1}">selected="selected"</c:if> >有效</option>
													<option value="2" <c:if test="${parameterMap.yxBj==2}">selected="selected"</c:if>>无效</option>
												</select>
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">角色名称：</label>
											<div class="col-md-7" style=" padding-top: 10px; ">
												<input name="gnjsMc" id="gnjsMc"  class="form-control" type="text" value='${parameterMap.gnjsMc}'  />
											</div>
										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
							</div>
						</form>
						</div>
						<div class="form-actions right" style="margin-top: 0px;">
							<button type="button" class="btn default" onclick="clearForm()">清空</button>
							<button type="button" onclick="javascript:document.form1.submit()" class="btn blue"><i class="fa fa-check"></i> 查询</button>
						</div>
					 </div>		    
			    </div>
			</div>
			<!-- Table -->
			 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable" >
				<thead >
					<tr >
						<th width="50" style="text-align: center;" class="id">
							<input type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" />
						</th>
						<th width="140">操作</th>
						<th class="sorting" name ="a.code">角色代码</th>
						<th class="sorting" name ="a.gnjsMc">角色名称</th>
						<th class="sorting" name ="a.yxBj">有效标记</th>
						<th><a href="javascript:;">备注</a></th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${page.result}" var="item">
						<tr id="${item.gnjsDm}">
							<td style="text-align: center;" class="id">
								<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.gnjsDm}"/></td>
							<td nowrap>
									<a href="${ctx }/role/role-edit.izhbg?gnjsDm=${item.gnjsDm }&currentAppId=${parameterMap.appId}">编辑</a>
								&nbsp; 
									<a href="${ctx }/rolFun/roleFun-list.izhbg?jsDm=${item.gnjsDm }&currentAppId=${parameterMap.appId}" title="授权功能">授权功能</a>
								&nbsp;
									<a href="${ctx }/role_authorities/role_authorities_list.izhbg?jsDm=${item.gnjsDm }&currentAppId=${parameterMap.appId}" title="资源权限">资源权限</a>
							</td>
							<td>${item.code}
							</td>
							<td>${item.gnjsMc }
							</td>
							<td>${item.yxBj==1?'有效':'无效' }<!-- dictNew.getDictItem('D_VALIDFLAG',yxBj).itemName"-->
							</td>
							<td>${bz}
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
	</div>
	</div>
	</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/common.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/ucenter.js"></script>  
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
	        'appId':'${parameterMap.appId }',
	        'code':'${parameterMap.code }',
	        'yxBj':'${parameterMap.yxBj }',
	        'gnjsMc':'${parameterMap.gnjsMc }'
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'form1' 
		};

var table;

$(function() {
	table = new Table(config);
    table.configPagination('.dataTables_paginate');
    table.configPageInfo('.dataTables_info'); 
    table.configPageSize('.m-wrap'); 
});

function deleteInfo(){
	var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.post("${ctx }/role/deleteRole.izhbg", pars, function(data){
			$("#form1").submit();
		},"json");
}
function changeUserState(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/role/updRoleStatus.izhbg",
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