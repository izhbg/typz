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
					<h3 class="panel-title">待提现列表</h3>
				</div>
				<div class="panel-body">
					<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
					<a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
					<div class="btn-group" style="margin-left:1px;">
						<a class="btn default btn-sm dropdown-toggle" id="tagLinkTitle" data-toggle="dropdown" href="#">
						  提现
						    <span class="caret"></span>
					    </a>
					    <ul id="ul-tag" class="dropdown-menu" style="min-width:150px;">
					    	<li class="divider"></li>
					    	<li class="tag"><a href="#" onclick="setState()"><div class="tagColorSpan">&nbsp;</div>已提现</a></li>
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
			
					<div id="collapse-group" class="collapse <c:if test="${(!empty parameterMap.orderNo)}">in</c:if> ">
						 <div class="panel panel-default" style="margin-bottom: 0px;"> 
							<div class="panel-body">
							 <form id="form1" name="form1" action="${ctx}/storeWithdraw/store-withdraw-waite-list.izhbg" method="post" class="form-horizontal" >
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-5" >申请用户：</label>
												<div class="col-md-7">
													<input name="yhId" id="yhId" type="text" class="form-control" value='${parameterMap.yhId }' />
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
				 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable">
					<thead >
						<tr >
							<th width="50" style="text-align: center;" class="id">
								<input type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" />
							</th>
							<th width="140">操作</th>
							<th >申请用户</th>
							<th >申请提现额度</th>
							<th >可提现额度</th>
							<th >申请提现时间</th>
							<th >申请提现账户</th>
						</tr>
					</thead>
					
					<tbody >
						<c:forEach items="${page.result }" var="item">
							<tr id="${item.id}">
								<td style="text-align: center;" class="id">
									<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.id}"/></td>
								<td nowrap>
								</td>
								<td>${item.memberId }</td>
								<td>${item.money}</td>
								<td>${item.totalModey}</td>
								<td>${item.askTime}</td>
								<td>
									<c:if test="${item.accountTypeId eq 1 }">支付宝账户</c:if>
									<c:if test="${item.accountTypeId eq 2 }">银行账户
										【
											<c:if test="${item.bankId eq 1 }">工商银行</c:if>
											<c:if test="${item.bankId eq 2 }">招商银行</c:if>
											<c:if test="${item.bankId eq 3 }">建设银行</c:if>
											<c:if test="${item.bankId eq 4 }">中国银行</c:if>
											<c:if test="${item.bankId eq 5 }">农业银行</c:if>
											<c:if test="${item.bankId eq 6 }">交通银行</c:if>
											<c:if test="${item.bankId eq 7 }">浦发银行</c:if>
											<c:if test="${item.bankId eq 8 }">广发银行</c:if>
											<c:if test="${item.bankId eq 9 }">中信银行</c:if>
											<c:if test="${item.bankId eq 10 }">光大银行</c:if>
											<c:if test="${item.bankId eq 11 }">兴业银行</c:if>
											<c:if test="${item.bankId eq 12}">民生银行</c:if>
											<c:if test="${item.bankId eq 13}">平安银行</c:if>
											<c:if test="${item.bankId eq 14}">杭州银行</c:if>
											<c:if test="${item.bankId eq 15}">邮政储蓄银行</c:if>
											<c:if test="${item.bankId eq 16}">宁波银行</c:if>
										】
									</c:if>
									【${item.accountNo}】
									【${item.accountName}】
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
	function setState(){
		if(confirmIsSelectItems()){
			$.ajax({
				type: "POST",
			    url: "${ctx }/storeWithdraw/store-withdraw-setState.izhbg",
			    data: $("input:checkbox[name=checkdel]:checked").serialize(),
			    dataType:"html",
				cache: false,
				success: function(data){
			    	$("#form1").submit();
				}
				});
		}
	 }
	</script>
</body>
</html>