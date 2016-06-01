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
				<h3 class="panel-title">审计日志列表</h3>
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.location.reload();" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="${ctx}/client/register_client.izhbg" class="btn btn-sm default"><i class="fa fa-rotate-right"></i>注册client</a>
				<form action="${ctx}/audit/audit_list.izhbg" id="form1"></form>
			</div>
			<!-- Table -->
			 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable" >
				<thead >
					<tr >
						<th width="150">操作</th>
						<th width="150">client_id</th>
						<th>描述</th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${clientDetailsDtoList}" var="cli">
						<tr id="${cli.clientId}">
							<td nowrap>
								 <c:if test="${not cli.archived}">
			                        <a href="test_client/${cli.clientId}.izhbg" class="btn btn-sm default">测试</a>
			                        <a href="archive_client/${cli.clientId}.izhbg" class="btn btn-sm red"
			                           onclick="return confirm('确定发布 \'${cli.clientId}\'?')">发布</a>
			                    </c:if>
			                    <c:if test="${cli.archived}"><strong class="text-muted">已发布</strong></c:if>
							</td>
							<td>
								 ${cli.clientId}
								<small>${cli.authorizedGrantTypes}</small>
							</td>
							<td>
								 client_id: <span class="text-danger">${cli.clientId}</span>&nbsp;
			                    client_secret: <span class="text-primary">${cli.clientSecret}</span>&nbsp;
			                    <br/>
			                    authorized_grant_types: <span class="text-primary">${cli.authorizedGrantTypes}</span>&nbsp;
			                    resource_ids: <span class="text-primary">${cli.resourceIds}</span>&nbsp;
			                    <br/>
			                    scope: <span class="text-primary">${cli.scope}</span>&nbsp;
			                    web_server_redirect_uri: <span class="text-primary">${cli.webServerRedirectUri}</span>&nbsp;
			                    <br/>
			                    authorities: <span class="text-primary">${cli.authorities}</span>&nbsp;
			                    access_token_validity: <span class="text-primary">${cli.accessTokenValidity}</span>&nbsp;
			                    refresh_token_validity: <span class="text-primary">${cli.refreshTokenValidity}</span>&nbsp;
			                    <br/>
			                    create_time: <span class="text-primary">${cli.createTime}</span>&nbsp;
			                    archived: <strong class="${cli.archived?'text-warning':'text-primary'}">${cli.archived}</strong>&nbsp;
			                    trusted: <span class="text-primary">${cli.trusted}</span>&nbsp;
			                    additional_information: <span class="text-primary">${cli.additionalInformation}</span>&nbsp;
							</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
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
	        'appId':'${parameterMap.appId }'
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