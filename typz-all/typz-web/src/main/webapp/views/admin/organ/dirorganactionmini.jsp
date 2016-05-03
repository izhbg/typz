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
	<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<h3 class="panel-title">【${jgMc}】子机构列表</h3>
	</div>
	<div class="panel-body">
		<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
		<a href="${ctx }/org/org-edit.izhbg?sjjgId=${parameterMap.sjjgId}&currentAppId=${parameterMap.currentAppId}" id="addDialog" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
		<a href="javascript:org_del2()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
		<a class="btn btn-sm default"  type="button" onclick="org_updflag();"><i class="fa fa-ban"></i>更改状态</a>
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

		<div id="collapse-group" class="collapse <c:if test="${(parameterMap.yxBj ne null&&parameterMap.yxBj ne '') || (parameterMap.jgDm ne null&&parameterMap.jgDm ne '') || (parameterMap.jgMc ne null&&parameterMap.jgMc ne '')}">in</c:if> ">
			 <div class="panel panel-default" style="margin-bottom: 0px;"> 
				<div class="panel-body">
				 <form id="form1" name="form1" action="${ctx }/org/org-list.izhbg" method="post" class="form-horizontal" >
					<input type="hidden" name="sjjgId" id="sjjgId" value="${parameterMap.sjjgId }"> 
					<input type="hidden" name="currentAppId" id="currentAppId" value="${parameterMap.currentAppId }"> 
					<div class="form-body">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5" >机构代码：</label>
									<div class="col-md-7">
										<input name="jgDm" id="jgDm" type="text" class="form-control" value='${parameterMap.jgDm }' />
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">机构名称：</label>
									<div class="col-md-7">
										<input name="jgMc" id="jgMc"  class="form-control" type="text" value='${parameterMap.jgMc }'  />
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
				<th class="sorting" name="a.jgDm">机构代码</th>
				<th class="sorting" name="a.jgMc">机构名称</th>
				<th class="sorting" name="a.yxBj">有效标记</th>
				<th class="sorting" name="a.xh">序号</th>
			</tr>
		</thead>
		
		<tbody >
			<c:forEach items="${page.result}" var="item">
				<tr id="${item.jgId}">
					<td style="text-align: center;" class="id">
						<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.jgId}"/></td>
					<td nowrap>
							<a href="${ctx }/org/org-edit.izhbg?jgId=${item.jgId }&currentAppId=${parameterMap.currentAppId}&sjjgId=${parameterMap.sjjgId}">编辑</a>
						&nbsp; 
							<a href="${ctx }/org-user/org-user-list.izhbg?jgId=${item.jgId }&sjjgId=${parameterMap.sjjgId}&currentAppId=${parameterMap.currentAppId}"
								 title="授权用户">授权用户</a>&nbsp;
					</td>
					<td>${item.jgDm}
					</td>
					<td>${item.jgMc }
					</td>
					<td>${item.yxBj==1?'有效':'无效' }
					</td>
					<td>${item.xh}
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
</div>
<div class="clearfix"></div>


</body>
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/common.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/ucenter.js"></script>  
<script>
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
	        'sjjgId':'${parameterMap.sjjgId }',
	        'jgDm':'${parameterMap.jgDm }',
	        'currentAppId':'${parameterMap.currentAppId }',
	        'jgMc':'${parameterMap.jgMc }',
	        'yxBj':'${parameterMap.yxBj }'
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
	$.ajax({
		type: "POST",
	    url: "${ctx }/org/deleteOrg.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function changeUserState(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/org/updOrgStatus.izhbg",
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