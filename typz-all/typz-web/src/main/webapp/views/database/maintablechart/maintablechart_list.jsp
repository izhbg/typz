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
				<h3 class="panel-title">${maintable.tableCName}（${maintable.tableName}）--列表</h3>    
			</div>
			<div class="panel-body">
			  <a href="javascript:location.href='${ctx}/maintablechart/maintablechart_edit.izhbg?maintableid=${maintable.tableid }'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>新增</a> 
			  <a class="btn default btn-sm" onclick="gobacks()" ><i class="m-icon-swapdown m-icon-blank"></i>返回</a>
			</div>
			<!-- Table -->
		    <form action="${ctx}/default/management/security/maintable/saveMainTableColumnList.do" method="post" onsubmit="return validateCallback(this, dialogAjaxDone);" class="required-validate pageForm">
			 <input type="hidden" name="sql" value="${maintable.sql }"> 
			 <table id="cal-infoGrid" class="table table-striped  table-hover dataTable" style="border-top: 1px solid green;">
			    <thead>
				  	<tr>
					   <th >分析图编号</th>
					   <th >分析图段名</th>
					   <th >X轴名</th>
					   <th >Y轴名</th>
					   <th >分类轴名</th>
					   <th >操作</th>
				   </tr>
					</thead>
					<tbody>
				   <c:forEach var="map" items="${listchart}">
				   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
				      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
				   <td >
				   ${map.chartid}
				   </td>
				   <td >
				   ${map.charttitle}
				   </td>
				   <td >
				   ${map.axisNameX}
				   </td>
				   <td >
				   ${map.axisNameY}
				   </td>
				   <td >
				   ${map.seriesName}
				   </td>
				   <td >
				   <a onclick="return removes();" href="${ctx}/maintablechart/maintablechart_delete.izhbg?maintableid=${maintable.tableid }&chartid=${map.chartid}" title="${maintable.tableCName}（${maintable.tableName}）">删除</a>|
				   <a href="${ctx}/maintablechart/maintablechart_edit.izhbg?maintableid=${maintable.tableid }&chartid=${map.chartid}" title="${maintable.tableCName}（${maintable.tableName}）"  mask="true">修改</a>
				   </td>
				   </tr>
				   </c:forEach>
					</tbody>
				   </table>
			</form>
			</div>
			<!-- 搜索条件结束 -->
		
			
		</div>
		<div class="clearfix"></div>
	</div>
	</div>
	</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${ctx}/s/guser/js/common.js"></script>  
<script type="text/javascript">
function gobacks(){
  	window.location.href="${ctx}/maintablecolumn/maintablecolumn_list.izhbg?maintableid=${maintable.tableid }";
} 
function removes(){
	if (confirm("您确实要删除此表的图表信息吗？")){
	}else{
	return false;
	}
}
	</script>
 <link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">
</html>