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
			<div class="panel panel-default">
				<!-- Default panel contents -->
				<div class="panel-heading">
					<h3 class="panel-title">订单列表</h3>
				</div>
				<div class="panel-body">
					<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
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
			
					<div id="collapse-group" class="collapse <c:if test="${(!empty parameterMap.orderNo)||(!empty parameterMap.state)}">in</c:if> ">
						 <div class="panel panel-default" style="margin-bottom: 0px;"> 
							<div class="panel-body">
							 <form id="form1" name="form1" action="${ctx }/store/store-order-list.izhbg" method="post" class="form-horizontal" >
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-5" >订单号：</label>
												<div class="col-md-7">
													<input name="orderNo" id="orderNo" type="text" class="form-control" value='${parameterMap.orderNo }' />
												</div>
											</div>
										</div>
										<!--/span-->
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-5" >订单状态：</label>
												<div class="col-md-7">
													<select name="state" id="state" class="form-control"> 
														<option  <c:if test="${empty parameterMap.state }">selected="selected"</c:if>></option>
														<option value="0" <c:if test="${parameterMap.state eq '0' }">selected="selected"</c:if>>待付款（已下单）</option>
														<option value="1" <c:if test="${parameterMap.state eq '1' }">selected="selected"</c:if>>待发货（已支付/到付）</option>
														<option value="2" <c:if test="${parameterMap.state eq '2' }">selected="selected"</c:if>>已发货（待收货）</option>
														<option value="3" <c:if test="${parameterMap.state eq '3' }">selected="selected"</c:if>>完成（已收货）</option>
													</select>
												</div>
											</div>
										</div>
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
				 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable">
					<thead >
						<tr >
							<th width="50" style="text-align: center;" class="id">
								<input type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" />
							</th>
							<th >订单号</th>
							<th >金额</th>
							<th >交易号</th>
							<th>交易状态</th>
							<th >下单人</th>
							<th >下单时间</th>
							<th>交易状态</th>
							<th width="140">操作</th>
						</tr>
					</thead>
					
					<tbody >
						<c:forEach items="${page.result }" var="item">
							<tr id="${item.id}">
								<td style="text-align: center;" class="id">
									<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.id}"/></td>
								
								<td><%-- <a href="${ctx}/np/np-order-detail?id=${item.id}" target="_blank">${item.id}</a> --%></td>
								<td>${item.totalPrice}</td>
								<td>${item.tShOrderDetail.tradeNo}</td>
								<td>${item.tShOrderDetail.tradeStatus}</td>
								<td>${item.yhId}</td>
								<td>${item.time}</td>
								<td>
									<c:if test="${item.status eq 0 }">待付款（已下单）</c:if>
									<c:if test="${item.status eq 1 }">待发货（已支付/到付）</c:if>
									<c:if test="${item.status eq 2 }">已发货（待收货）</c:if>
									<c:if test="${item.status eq 3 }">完成（已收货）</c:if>
								</td>
								<td nowrap>
									<%-- <a href="${ctx }/np/web/np-goods-detail?id=${item.goodsId}" target="_blank">查看下单产品</a> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					
				</table>
				<div class="form-actions fluid" style="margin-top: 0px;">
				<div class="col-md-5">  
					<div class="dataTables_info" id="sample_3_info"></div>
				</div>
				<div class="col-md-7">
					<div class="dataTables_paginate pagination  pull-right">    
					  <button class="btn btn-sm default">&lt;</button>
					  <button class="btn btn-sm default">1</button>
					  <button class="btn btn-sm default">&gt;</button>
					</div>
							
				</div>
				</div>
				</div>
				
				
			</div>
		</div>
	</div>
	<%@include file="/common/footer.jsp" %>
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
		        'filter_LIKES_username': '${param.filter_LIKES_username}'
		    },
			selectedItemClass: 'selectedItem',
			gridFormId: 'userGridForm',
			exportUrl: 'user-connector-export.do'
		};
		
		var table;
		
		$(function() {
			table = new Table(config);
		    table.configPagination('.pagination');
		    table.configPageInfo('.dataTables_info'); 
		    table.configPageSize('.m-wrap'); 
		});
	</script>
</body>
</html>