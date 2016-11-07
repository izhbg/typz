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

		<input type="hidden" name="contentpath" id="contentpath" value="${ctx}"/> 
		<div class="content content-inner">
			<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
			<!-- 操作按钮如下 -->
			<div class="content content-inner">
			<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="tabbable tabbable-custom">
					<ul class="nav nav-tabs">
						<li  id="tab_1_1_" <c:if test="${tShStore.state==0||tShStore.state==null}">class="active"</c:if> > 
							<a href="#this" onclick="StoreList.changePage(0)" data-toggle="tab">待审核 &nbsp;&nbsp;&nbsp;</a>
						</li>
						<li id="tab_1_2_" <c:if test="${tShStore.state==1}">class="active"</c:if>>
							<a href="#this" onclick="StoreList.changePage(1)" data-toggle="tab" >审核已通过 &nbsp;&nbsp;&nbsp;</a>
						</li>
						<li id="tab_1_2_" <c:if test="${tShStore.state==2}">class="active"</c:if> >
							<a href="#this" onclick="StoreList.changePage(2)" data-toggle="tab" >审核未通过  &nbsp;&nbsp;&nbsp;</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active">
							<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
							<a href="${ctx }/store/store_edit.izhbg" id="addDialog" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
							<c:if test="${tShStore.state==2}">
								<a href="javascript:StoreList.delStoreItem()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
							</c:if>
							
							<c:if test="${tShStore.state==0||tShStore.state==null}">
								<a class="btn btn-sm default"  type="button" href="javascript:StoreList.confirmPass();"><i class="fa fa-ban"></i>审核通过</a>
								<a class="btn btn-sm default"  type="button" href="javascript:StoreList.confirmNoPass();"><i class="fa fa-ban"></i>审核未通过</a>
							</c:if>
							
							
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
					
							<div id="collapse-group" class="collapse in">
								 <div class="panel panel-default" style="margin-bottom: 0px;"> 
									<div class="panel-body">
									 <form id="form1" name="form1" action="${ctx }/store/store_list.izhbg" method="post" class="form-horizontal" >
										<input type="hidden" name="state" id="state" value="${tShStore.state }"> 
										<div class="form-body">
											<div class="row">
												<!--/span-->
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-5">店铺名称：</label>
														<div class="col-md-7">
															<input name="title" id="title"  class="form-control" type="text" value='${tShStore.title }'  />
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-5">店铺类型：</label>
														<div class="col-md-7">
															<select name="type" id="type" class="form-control">
																<option value="0" <c:if test="${empty tShStore.type|| tShStore.type eq 0 }">selected="selected"</c:if>></option>
																<option value="1" <c:if test="${tShStore.type eq 1 }">selected="selected"</c:if>>厂家</option>
																<option value="2" <c:if test="${tShStore.type eq 2 }">selected="selected"</c:if>>加盟商</option>
															</select>
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
									<th >店铺名称</th>
									<th >店铺类型</th>
									<th >店铺简介</th>
									<th >申请时间</th>
								</tr>
							</thead>
							
							<tbody >
								<c:forEach items="${page.result}" var="item">
									<tr id="${item.id}">
										<td style="text-align: center;" class="id">
											<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.id}"/></td>
										<td nowrap>
											<a href="${ctx }/store/store_edit.izhbg?id=${item.id }">编辑</a>
											<a href="${ctx }/store/goods-sale-list.izhbg?storeId=${item.id }">授权销售产品</a>
											<a href="${ctx }/store/goods-buyer-list.izhbg?storeId=${item.id }">授权进货产品</a>
										</td>
										<td>${item.title}</td>
										<td><c:if test="${item.type eq 1 }">厂家</c:if><c:if test="${item.type eq 2 }">加盟商</c:if></td>
										<td>
											<c:if test="${fn:length(item.content)>20 }">${ fn:substring( item.content ,0,20)} ...</c:if>
 											<c:if test="${fn:length(item.content)<=20 }">${  item.content }</c:if>
										</td>
										<td>${item.addTime }</td>
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
<script type="text/javascript" src="${ctx}/s/assets/scripts/store/store_list.js"></script>
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

function clearForm(){
	$(':input','#form1')  
	 .not(':button, :submit, :reset, :hidden')
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected'); 
}

</script>
</html>