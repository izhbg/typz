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
				<h3 class="panel-title">店铺授权销售商品列表【店铺名称：${store.title }】</h3>
				
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="#this" onclick="StoreSale.showDialog()" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
				<a href="javascript:StoreSale.delconfirm()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
				<a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
				<a href="${ctx}/store/store_list.izhbg" class="btn btn-sm default"  ><i class="fa fa-mail-reply-all"></i>返回</a>
				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
		
				<div id="collapse-group" class="collapse <c:if test="${(parameterMap.code ne null&&parameterMap.code ne '') || (parameterMap.gnjsMc ne null&&parameterMap.gnjsMc ne '') }">in</c:if> ">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="panel-body">
						 <form id="form1" name="form1" action="${ctx }/store/goods-sale-list.izhbg" method="post" class="form-horizontal" >
							<input type="hidden" name="storeId" id="storeId" value="${store.id}">
							<div class="form-body">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5" >产品名称：</label>
											<div class="col-md-7">
												<input name="name" id="name"  class="form-control" type="text" value=''  />
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">产品别名：</label>
											<div class="col-md-7">
												<input name="aliasName" id="aliasName"  class="form-control" type="text" value=''  />
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
						<th class="sorting"  name ="b.code">产品名称</th>
						<th class="sorting"  name ="b.gnjsMc">产品别名</th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${page.result}" var="item">
						<tr id="${item.id}">
							<td style="text-align: center;" class="id">
								<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.id}"/></td>
							<td > ${item.tShGoodsBasic.name }
							</td>
							<td>${item.tShGoodsBasic.aliasName }
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
							<table id="customerInfo" style="width:100%;" class="table table-striped table-bordered table-hover dataTable">
								<thead>
									<tr>
										<th></th>
										<th>产品名称</th>
										<th>别名</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								
							</table>
						</div>
						<div id="customerInfo_reslut"></div>
					</div> 
					<div class="modal-footer">
						<button type="button" class="btn default" data-dismiss="modal">取消</button>
						<button type="button" onclick="StoreSale.addSaleGoods()" class="btn blue">确定</button>
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
<script type="text/javascript" src="${ctx}/s/assets/plugins/data-tables/dataTables.bootstrap.js"></script> 
<script type="text/javascript" src="${ctx}/s/assets/scripts/store/store_sale.js"></script> 
<link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/dataTables.bootstrap.css">


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
	        'jgId':'${parameterMap.jgId }',
	        'yhId':'${user.yhId}',
	        'code':'${parameterMap.code}',
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
</script>
</html>