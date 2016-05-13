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
			<!-- Table -->
		    <form action="${ctx}/maintablecolumn/add_maintablecolumnlist.izhbg" method="post" onsubmit="return validateCallback(this, dialogAjaxDone);" class="required-validate pageForm">
			  <input type="hidden" name="maintableid" value="${maintable.tableid }">
			 <table id="cal-infoGrid" class="table table-striped  table-hover dataTable" style="border-top: 1px solid green;">
			    <thead>
			  	<tr>
				   <th >字段编号</th>
				   <th >字段名</th>
				   <th >字段中文名</th>
				   <th >字段类型</th>
				   <th >列表是否显示</th>
				   <th >是否查询字段</th>
				   <th >列表可导出</th>
				   <th >是否是插入字段</th>
			   </tr>
				</thead>
				<tbody>
			   <c:forEach var="map" items="${list}">
			   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
			      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
			   <td noWrap>
			   ${map.columnid}
			   </td>
			   <td noWrap>
			   ${map.columnName}
			   </td>
			   <td noWrap>
			   ${map.columnCName}
			   </td>
			   <td noWrap>
			   <c:if test="${map.propertyType eq 1 }">文本框</c:if>
			   <c:if test="${map.propertyType eq 2 }">下拉列表</c:if>
			   <c:if test="${map.propertyType eq 3 }">复选框</c:if>
			   <c:if test="${map.propertyType eq 4 }">单选框</c:if>
			   <c:if test="${map.propertyType eq 5 }">时间</c:if>
			   <c:if test="${map.propertyType eq 10 }">日期</c:if>
			   <c:if test="${map.propertyType eq 6 }">隐藏域</c:if>
			   <c:if test="${map.propertyType eq 7 }">扩展字段</c:if>
			   <c:if test="${map.propertyType eq 8 }">弹出选择</c:if>
			   <c:if test="${map.propertyType eq 9 }">文本域</c:if>
			   <c:if test="${map.propertyType eq 11 }">密码</c:if>
				<c:if test="${map.propertyType eq 12 }">附件列表</c:if>
				<c:if test="${map.propertyType eq 13 }">iframe</c:if>
				<c:if test="${map.propertyType eq 14 }">属性列表</c:if>
				<c:if test="${map.propertyType eq 16 }">地区选择</c:if>
			   </td>
			   <td noWrap>
			   <input name="isList" type="checkbox" value="${map.columnid}"<c:if test="${map.isList eq 1}">checked</c:if>>
			   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isListOrder}';}" 
			            value="${map.isListOrder}" name="isListOrder_${map.columnid}">
			   </td>
			   <td noWrap>
			   <input name="isQuery" type="checkbox" value="${map.columnid}"<c:if test="${map.isQuery eq 1}">checked</c:if>>
			   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isQueryOrder}';}" 
			            value="${map.isQueryOrder}" name="isQueryOrder_${map.columnid}">
			   </td>
			   <td noWrap>
			   <input name="isExport" type="checkbox" value="${map.columnid}"<c:if test="${map.isExport eq 1}">checked</c:if>>
			   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isExportOrder}';}" 
			            value="${map.isExportOrder}" name="isExportOrder_${map.columnid}">
			   </td>
			   <td noWrap>
			   <input name="isInsert" type="checkbox" value="${map.columnid}"<c:if test="${map.isInsert eq 1}">checked</c:if>>
			   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isInsertOrder}';}" 
			            value="${map.isInsertOrder}" name="isInsertOrder_${map.columnid}">
			   </td>
			   </tr>
			   </c:forEach> 
				</tbody>
			   </table>
			<div class="form-actions right"> 
				<div class="col-md-offset-3 col-md-9">
					<button type="submit"  class="btn green">提交</button> 
					<button type="button" onclick="gobacks();" class="btn default">返回</button>                              
				</div> 
			</div> 
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
	</script>
 <link rel="stylesheet" href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">
</html>