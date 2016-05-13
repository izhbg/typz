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
			  <a href="javascript:location.href='${ctx}/maintablecolumn/edit_miantablecolumn.izhbg?maintableid=${maintable.tableid }'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>新增</a> 
			  <a href="javascript:location.href='${ctx}/maintablecolumn/load_miantablecolumn.izhbg?maintableid=${maintable.tableid }'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>加载字段信息</a> 
			  <a href="javascript:location.href='${ctx}/maintablecolumn/maintablecolumn_set.izhbg?maintableid=${maintable.tableid}'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>设置字段的状态</a> 
			  <a href="javascript:location.href='${ctx}/maintablechart/maintablechart_list.izhbg?maintableid=${maintable.tableid}'" class="btn btn-sm default"><i class="fa fa-plus-square"></i>设置图形报表</a> 
			  <a class="btn default btn-sm" href="javascript:location.href='${ctx}/maintable/maintable_list.izhbg'" ><i class="m-icon-swapdown m-icon-blank"></i>返回</a>
			</div>
			<!-- Table -->
		    <form id="cal-infoGridForm" name="cal-infoGridForm" method='post' action="cal-info-remove.do" class="m-form-blank">
			 <table id="cal-infoGrid" class="table table-striped  table-hover dataTable" style="border-top: 1px solid green;">
			    <thead>
					 <tr>
					   <th width="100">字段编号</th>
					   <th width="100">字段名</th>
					   <th width="100">字段中文名</th>
					   <th width="100">字段类型</th>
					   <th width="100">操作</th>
				   </tr>
					</thead>
					<tbody>
				   <c:forEach var="map" items="${list}"> 
				   <tr>
				   <td >
				   ${map.columnid}
				   </td>
				   <td >
				   ${map.columnName}
				   </td>
				   <td >
				   ${map.columnCName}
				   </td>
				   <td >
				   <c:if test="${map.propertyType eq 1 }">文本框</c:if>
				   <c:if test="${map.propertyType eq 2 }">下拉列表</c:if>
				   <c:if test="${map.propertyType eq 3 }">复选框</c:if>
				   <c:if test="${map.propertyType eq 4 }">单选框</c:if>
				   <c:if test="${map.propertyType eq 5 }">时间</c:if>
				   <c:if test="${map.propertyType eq 10 }">日期</c:if>
				   <c:if test="${map.propertyType eq 17 }">时间</c:if>
				   <c:if test="${map.propertyType eq 18 }">月份</c:if>
				   <c:if test="${map.propertyType eq 19 }">年</c:if>
				   <c:if test="${map.propertyType eq 6 }">隐藏域</c:if>
				   <c:if test="${map.propertyType eq 7 }">扩展字段</c:if>
				   <c:if test="${map.propertyType eq 8 }">弹出选择</c:if>
				   <c:if test="${map.propertyType eq 9 }">文本域</c:if>
				   <c:if test="${map.propertyType eq 11 }">密码</c:if>
					<c:if test="${map.propertyType eq 12 }">附件列表</c:if>
					<c:if test="${map.propertyType eq 13 }">iframe</c:if>
					<c:if test="${map.propertyType eq 14 }">属性列表</c:if>
					<c:if test="${map.propertyType eq 15 }">UEeditor</c:if>
					<c:if test="${map.propertyType eq 16 }">地区选择</c:if>
				   </td>
				   <td >
				   <a onclick="return removes();" href="${ctx}/maintablecolumn/remove_miantablecolumn.izhbg?maintablecolumnid=${map.columnid}&maintableid=${maintable.tableid }">删除</a>|
				   <a href="${ctx}/maintablecolumn/edit_miantablecolumn.izhbg?maintablecolumnid=${map.columnid}&maintableid=${maintable.tableid }">修改</a>
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
	function loadColumn(){
		var sql="${fn:substring(maintable.sql,0,5) }";
		if(sql.length==0){
			alert("您没有写sql语句指明要哪些字段，因此无法自动加载！");
			return false;
		}else{
			return true;
			}
	} 
	function removes(){
		if (confirm("您确实要删除此表的字段信息吗？")){
		}else{
		return false;
		}
	}
	</script>
 <link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">
</html>