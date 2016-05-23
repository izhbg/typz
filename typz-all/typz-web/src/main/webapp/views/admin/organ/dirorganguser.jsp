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
				<h3 class="panel-title">机构授权人员列表【机构代码：${organ.jgDm } &nbsp;&nbsp;机构名称：${organ.jgMc }】</h3>
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="#treeDialog" data-toggle="modal" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
				<a href="javascript:ou_del()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
				<a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
				<a href="${ctx }/org/org-list.izhbg?sjjgId=${parameterMap.sjjgId }&currentAppId=${currentAppId}" class="btn btn-sm default"  ><i class="fa fa-mail-reply-all"></i>返回</a>
				

				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
		
				<div id="collapse-group" class="collapse <c:if test="${(parameterMap.yhDm ne null&&parameterMap.yhDm ne '') || (parameterMap.yhMc ne null&&parameterMap.yhMc ne '') }">in</c:if> ">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="panel-body">
						 <form id="form1" name="form1" action="${ctx }/org-user/org-user-list.izhbg" method="post" class="form-horizontal" >
							<input type="hidden" name="jgId" id="jgId" value="${organ.jgId}"> 
							<input type="hidden" name="sjjgId" id="sjjgId" value="${parameterMap.sjjgId }"> 
							<input type="hidden" name="currentSysId" id="currentSysId" value="${currentSysId}"> 
							<div class="form-body">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5" >登录名：</label>
											<div class="col-md-7">
												<input name="yhDm" id="yhDm"  class="form-control" type="text" value='${parameterMap.yhDm }'  />
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
						<th class="sorting" name ="b.yhDm">登录名</th>
						<th class="sorting" name ="b.yhMc">姓名</th>
						<th class="sorting" name ="b.xb">性别</th>
						<th class="sorting" name ="b.yhLx">类型</th>
						<th class="sorting" name ="b.yxBj">有效标记</th>
						<th class="sorting" name ="b.xh">序号</th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${page.result}" var="item">
						<tr id="${item.duId}">
							<td style="text-align: center;" class="id">
								<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.duId}"/></td>
							<td > ${item.yh.yhDm }
							</td>
							<td>${item.yh.yhMc}
							</td>
							<td>${item.yh.xb}
							</td>
							<td>${item.yh.yhLx } <!--  ucUtil.getOrgan(jgId).jgMc-->
							</td>
							<td>${item.yh.yxBj }<!-- dictNew.getDictItem('D_postTYPE',jsLx).itemName-->
							</td>
							<td>${item.yh.xh }<!-- dictNew.getDictItem('D_VALIDFLAG',yxBj).itemName"-->
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
		 <div class="modal fade" id="treeDialog" tabindex="-1" post="basic" aria-hidden="true" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">选择功能节点</h4>
					</div>
					<div class="modal-body">
						<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible1="1">  
						<div class="row well " style="margin: 0px;">
							<form id="form2" name="form2"  method="post" class="form-horizontal" >
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-md-5" >代码：</label>
									<div class="col-md-7">
										<input name="yhDm2" id="yhDm2"  class="form-control" type="text"   />
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-md-5">名称：</label>
									<div class="col-md-7">
										<input name="yhMc2" id="yhMc2"  class="form-control" type="text"   />
									</div>
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group">
									<button type="button" onclick="searchUser()" class="btn blue"><i class="fa fa-check"></i> 查询</button>
								</div>
							</div>
							</form>
							<!--/span-->
						</div>
						<table id="customerInfo" class="table table-striped table-bordered table-hover dataTable">
							<thead>
								<tr>
									<th><input type="checkbox"  name="checkAll" id="checkAll" onchange="toggleSelectedItems2(this.checked)" /></th>
									<th>用户代码</th>
						            <th>用户名称</th>
									<th>有效标记</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="8"></td> 
								</tr>
							</tbody>
							
						</table>
					</div>
					</div> 
					<div class="modal-footer">
						<button type="button" class="btn default" data-dismiss="modal">取消</button>
						<button type="button" onclick="ou_add('${organ.jgId}')" class="btn blue">确定</button>
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
<script type="text/javascript" src="${ctx}/s/assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.js"></script> 
<link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">


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
		    'yhDm':'${parameterMap.yhDm }',
	        'jgId':'${organ.jgId}',
	        'yhMc':'${parameterMap.yhMc }'
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'form1' 
		};
function ou_add(jgId){
	var pars = "jgId=" + jgId + "&" +$("input:checkbox[name=checkdel2]:checked").serialize();
	$.ajax({
		type: "POST",
	    url: "${ctx }/org-user/addOrgUser.izhbg",
	    data: pars,
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
var datatable;
function  loadCustomerInfo(){

	if (datatable == null) { //仅第一次检索时初始化Datatable
		
		datatable = $('#customerInfo').dataTable( {
			"pageResize": true,
			"bAutoWidth": true,					//不自动计算列宽度
			"aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0 ] }]  ,
			"aoColumns": [
				            { "mData": "yhId" ,"mRender": function(data, type, full) {  
                                return '<input type="checkbox" name="checkdel2" class="selectedItem2 a-check"  value="'+data+'"/>';} },
				            { "mData": "yhDm" },
				            { "mData": "yhMc" },
				            { "mData": "scBj" }
				        ],
			"bProcessing": true,					//加载数据时显示正在加载信息
			"bServerSide": true,					//指定从服务器端获取数据
			"bFilter": false,						//不使用过滤功能
			"bLengthChange": false,					//用户不可改变每页显示数量
			"iDisplayLength": 10,					//每页显示8条数据
			"bInfo":false,
			"sAjaxSource": "${ctx}/org-user/getOrgUserPage.izhbg",//获取数据的url
			"fnServerData": retrieveData,			//获取数据的处理函数
			//"sPaginationType": "full_numbers",		//翻页界面类型
			"oLanguage": {							//汉化
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "没有检索到数据",
				"sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
				"sInfoEmtpy": "没有数据",
				"sProcessing": "正在加载数据...",
				"sInfoFiltered":"",
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "前页",
					"sNext": "后页",
					"sLast": "尾页"
				}
			}
		});
	}

	//刷新Datatable，会自动激发retrieveData
	//datatable.fnDraw();
}
function searchUser(){
	datatable.fnDraw();
}
function retrieveData( sSource, aoData, fnCallback ) {
    //将客户名称加入参数数组
	aoData.push( { "name":"jgId", "value":"${organ.jgId}" },
				 { "name":"yhDm", "value":$("#yhDm2").val() },
				 { "name":"yhMc", "value":$("#yhMc2").val() }
				  );

	$.ajax( {
		"type": "POST", 
		"contentType": "application/json",
		"url": sSource, 
		"dataType": "json",
		"data": JSON.stringify(aoData), //以json格式传递
		"success": function(resp) {
			fnCallback(resp); //服务器端返回的对象的returnObject部分是要求的格式
		}
	});
}
var table;
$(function() {
	table = new Table(config);
    table.configPagination('.dataTables_paginate');
    table.configPageInfo('.dataTables_info'); 
    table.configPageSize('.m-wrap'); 

	//初始化用户选择列表
	
	loadCustomerInfo();

});

function deleteInfo(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/org-user/delOrgUser.izhbg",
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