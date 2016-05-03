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
		<h3 class="panel-title">【${jgMc}】用户列表</h3>
	</div>
	<div class="panel-body">
		<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
		<a href="${ctx }/user/user-edit.izhbg?jgId=${parameterMap.sjjgId}&currentAppId=${parameterMap.currentAppId }" id="addDialog" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
		<a href="javascript:user_del2()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
		<a href="javascript:user_del3()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>从组织移除</a>
		<a class="btn btn-sm default"  type="button" onclick="user_updpd();"><i class="fa fa-circle-o"></i>重置密码</a>
		<a class="btn btn-sm default"  type="button" onclick="user_updflag();"><i class="fa fa-ban"></i>更改状态</a>
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

		<div id="collapse-group" class="collapse <c:if test="${(parameterMap.yxBj ne null&&parameterMap.yxBj ne '') || (parameterMap.yhDm ne null&&parameterMap.yhDm ne '') || (parameterMap.yhMc ne null&&parameterMap.yhMc ne '')||(parameterMap.includeFlag ne null&&parameterMap.includeFlag ne '') }">in</c:if> ">
			 <div class="panel panel-default" style="margin-bottom: 0px;"> 
				<div class="panel-body">
				 <form id="form1" name="form1" action="${ctx }/user/user-list.izhbg" method="post" class="form-horizontal" >
					<input type="hidden" name="sjjgId" id="sjjgId" value="${parameterMap.sjjgId }"> 
					<input type="hidden" name="jgId" id="jgId" value="${parameterMap.sjjgId }"> 
					<input type="hidden" name="currentAppId" id="currentAppId" value="${parameterMap.currentAppId }"> 
					<div class="form-body">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5" >登录名：</label>
									<div class="col-md-7">
										<input name="yhDm" id="yhDm" type="text" class="form-control" value='${parameterMap.yhDm }' />
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">姓名：</label>
									<div class="col-md-7">
										<input name="yhMc" id="yhMc"  class="form-control" type="text" value='${parameterMap.yhMc }'  />
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
									<label class="control-label col-md-5">包含子机构：</label>
									<div class="col-md-7" style=" padding-top: 10px; ">
										<input type="checkbox" class="form-control" name="includeFlag" id="includeFlag" <c:if test="${parameterMap.includeFlag != null && parameterMap.includeFlag != '' }">checked</c:if> />
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
				<th class="sorting" name="a.yhDm">登录名</th>
				<th class="sorting" name="a.yhMc">姓名</th>
				<th class="sorting" name="a.xb">性别</th>
				<!--  
				<th class="sorting" name="a.yhLx">类型</th>
				-->
				<th class="sorting" name="a.yxBj">有效标记</th>
				<th class="sorting" name="a.xh">序号</th>
			</tr>
		</thead>
		
		<tbody >
			<c:forEach items="${page.result}" var="item">
				<tr id="${item.yhId}">
					<td style="text-align: center;" class="id">
						<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.yhId}"/></td>
					<td nowrap>
							<a href="${ctx }/user/user-edit.izhbg?yhId=${item.yhId }&jgId=${item.jgId }&currentAppId=${parameterMap.currentAppId }">编辑</a>
						&nbsp; 
							<a href="${ctx }/user-role/user-role-list.izhbg?yhId=${item.yhId }&jgId=${parameterMap.sjjgId }&currentAppId=${parameterMap.currentAppId }" title="授权角色">授权角色</a>&nbsp;
					</td>
					<td>${item.yhDm}
					</td>
					<td>${item.yhMc }
					</td>
					<td> ${item.xb==1?'男':'女' }
					</td>
					<!--  
					<td>${item.yhLx }
					</td>-->
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
	        'yhDm':'${parameterMap.yhDm }',
	        'yhMc':'${parameterMap.yhMc }',
	        'includeFlag':'${parameterMap.includeFlag }',
	        'currentAppId':'${parameterMap.currentAppId }',
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
	    url: "${ctx }/user/deleteGuser.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function deleteInfo2(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/user/removeGuserFromGroup.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function resetMm(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/user/updPassword.izhbg",
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
	    url: "${ctx }/user/updGUserStatus.izhbg",
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