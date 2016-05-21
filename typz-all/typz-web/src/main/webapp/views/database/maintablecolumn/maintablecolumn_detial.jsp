<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
</head>
<body class="page-header-fixed">
<jsp:include page="/common/header.jsp"></jsp:include> 
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
				<div class="row inbox">
				 <div class="col-md-12">
				<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title">
					编辑
				</h3></div>
				<div class="panel-body form"> 
					<!-- BEGIN FORM-->
					<form action="${ctx}/maintablecolumn/add_miantablecolumn.izhbg" class="form-horizontal" id="cal-infoForm" method="post">
					   <input type="hidden" name="methodCall" value="saveMainTableColumn"> 
					   <input type="hidden" name="maintableid" value="${maintableid }"> 
					   <input type="hidden" name="columnid" value="${maintablecolumn.columnid }"> 
					   <input type="hidden" name="isQueryOrder" value="${maintablecolumn.isQueryOrder }">
					   <input type="hidden" name="isExportOrder" value="${maintablecolumn.isExportOrder }">
					   <input type="hidden" name="isListOrder" value="${maintablecolumn.isListOrder }">
					   <input type="hidden" name="isInsertOrder" value="${maintablecolumn.isInsertOrder }">
						<div class="form-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">字段名：</label>
										<div class="col-md-8">
											<input type="text" name="columnName" class="form-control required" value="${maintablecolumn.columnName }"/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">字段中文名:</label>
										<div class="col-md-8">
											<input type="text" name="columnCName" class="form-control required" value="${maintablecolumn.columnCName }"/>
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否可查询：</label>
										<div class="col-md-8">
											<select name="isQuery" id="isQuery" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isQuery eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isQuery eq 1 }">selected</c:if> >是</option>
									      	</select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否不可修改:</label>
										<div class="col-md-8">
											<select name="isUpdate" id="isUpdate" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isUpdate eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isUpdate eq 1 }">selected</c:if> >是</option>
									      	 </select> 
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否可导出：</label>
										<div class="col-md-8">
											 <select name="isExport" id="isExport" class="form-control" >
									         <option value="0" <c:if test="${maintablecolumn.isExport eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isExport eq 1 }">selected</c:if> >是</option>
									      	 </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否可插入:</label>
										<div class="col-md-8">
											<select name="isInsert" id="isInsert" class="form-control">
								         <option value="0" <c:if test="${maintablecolumn.isInsert eq 0 }">selected</c:if> >否</option>
								         <option value="1" <c:if test="${maintablecolumn.isInsert eq 1 }">selected</c:if> >是</option>
								      	 </select> 
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否在列表中显示：</label>
										<div class="col-md-8">
											<select name="isList" id="isList" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isList eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isList eq 1 }">selected</c:if> >是</option>
									      	 </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">字段类型：</label>
										<div class="col-md-8">
											<select name="propertyType" id="propertyType" class="form-control">
									         <option value="1" <c:if test="${maintablecolumn.propertyType eq 1 }">selected</c:if> >文本框</option>
									         <option value="2" <c:if test="${maintablecolumn.propertyType eq 2 }">selected</c:if> >下拉列表</option>
									         <option value="3" <c:if test="${maintablecolumn.propertyType eq 3 }">selected</c:if> >复选框</option>
									         <option value="4" <c:if test="${maintablecolumn.propertyType eq 4 }">selected</c:if> >单选框</option>
									        <%--  <option value="5" <c:if test="${maintablecolumn.propertyType eq 5 }">selected</c:if> >时间(yyyy-mm-dd hh:mi:ss)</option> --%>
									         <option value="10" <c:if test="${maintablecolumn.propertyType eq 10 }">selected</c:if> >日期(yyyy-mm-dd)</option>
									       <%--   <option value="17" <c:if test="${maintablecolumn.propertyType eq 17 }">selected</c:if> >时间(hh:mi)</option>
									         <option value="18" <c:if test="${maintablecolumn.propertyType eq 18 }">selected</c:if> >月份(yyyy-mm)</option>
									         <option value="19" <c:if test="${maintablecolumn.propertyType eq 19 }">selected</c:if> >年(yyyy)</option> --%>
									         <option value="6" <c:if test="${maintablecolumn.propertyType eq 6 }">selected</c:if> >隐藏域</option>
									        <%--  <option value="7" <c:if test="${maintablecolumn.propertyType eq 7 }">selected</c:if> >扩展字段</option> --%>
									        <%--  <option value="8" <c:if test="${maintablecolumn.propertyType eq 8 }">selected</c:if> >弹出选择</option> --%>
									         <option value="9" <c:if test="${maintablecolumn.propertyType eq 9 }">selected</c:if> >文本域</option>
									         <option value="11" <c:if test="${maintablecolumn.propertyType eq 11 }">selected</c:if> >密码</option>
									         <option value="12" <c:if test="${maintablecolumn.propertyType eq 12 }">selected</c:if> >文件上传</option>
											<%-- <option value="13" <c:if test="${maintablecolumn.propertyType eq 13 }">selected</c:if> >iframe</option>
											<option value="14" <c:if test="${maintablecolumn.propertyType eq 14 }">selected</c:if> >属性列表</option> --%>
											<option value="15" <c:if test="${maintablecolumn.propertyType eq 15 }">selected</c:if> >富文本</option>
											<%-- <option value="16" <c:if test="${maintablecolumn.propertyType eq 16 }">selected</c:if> >地区选择</option> --%>
									      	 </select> 
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">数据类型：</label>
										<div class="col-md-8">
											 <select name="dataType" id="dataType" class="form-control">
									     	<!--  <option value="">请选择</option> -->
									         <option value="1" <c:if test="${maintablecolumn.dataType eq 1 }">selected</c:if> >字符串</option>
									         <option value="2" <c:if test="${maintablecolumn.dataType eq 2 }">selected</c:if> >数字</option>
									         <option value="3" <c:if test="${maintablecolumn.dataType eq 3 }">selected</c:if> >日期</option>
									     </select>  
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否必填：</label>
										<div class="col-md-8">
											<select name="isMust" id="isMust" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isMust eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isMust eq 1 }">selected</c:if> >是</option>
									      	 </select> 
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">下拉列表sql/url链接：</label>
										<div class="col-md-10"> 
											<textarea id="typeSql" name="typeSql" class="form-control" rows="5" cols="50">${maintablecolumn.typeSql }</textarea>
											<span class="help-block">【下拉列表：例如】select column1 name,column2 id from tablename <br>【弹出框例如：例如】select column1 as id ,column2 as name,column3 as pid from tablename</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">备注：</label>
										<div class="col-md-10"> 
											<textarea id="bz" name="bz" class="form-control" rows="5" cols="50">${maintablecolumn.bz }</textarea>
											<span class="help-block">输入内容的提示</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">默认值：</label>
										<div class="col-md-8">
										  <input type="text" name="tolerant" class="form-control " value="${maintablecolumn.tolerant }"/>
										  <span class="help-block">可选公式：Date();DateTime();LongDateTime();CurrentUserName;CurrentUserDisName;CurrentDepName;CurrentDepId;CurrentUserId;CurrentAppId</span>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">当是弹出树是否多选：</label>
										<div class="col-md-8">
											<select name="isChecked" id="isChecked" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isChecked eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isChecked eq 1 }">selected</c:if> >是</option>
									      	 </select>
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">根据父ID生成子ID</label>
										<div class="col-md-8">
										  <select name="upColumnName" id="upColumnName" class="form-control">
										     	 <option value="" >请选择</option>
										     <c:forEach var="column" items="${columnlist}">
										         <option value="${column.columnName }" <c:if test="${column.columnName eq maintablecolumn.upColumnName }">selected</c:if> >${column.columnName }</option>
										     </c:forEach>  
								     	  </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否验证唯一性：</label>
										<div class="col-md-8">
											<select name="isUnique" id="isUnique" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.isUnique eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isUnique eq 1 }">selected</c:if> >是</option>
									      	</select> 
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">模糊查询范围:</label>
										<div class="col-md-8">
										  <select name="likescope" id="likescope" class="form-control">
									     	<option value=""  >无</option>
									         <option value="1" <c:if test="${maintablecolumn.likescope eq 1 }">selected</c:if> >1:%data</option>
									         <option value="2" <c:if test="${maintablecolumn.likescope eq 2 }">selected</c:if> >2:data%</option>
									      	 <option value="3" <c:if test="${maintablecolumn.likescope eq 3 }">selected</c:if> >3:%data%</option>
									      	 <option value="4" <c:if test="${maintablecolumn.likescope eq 4 }">selected</c:if> >4:data</option>
									      	</select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">验证类型：</label>
										<div class="col-md-8">
											<select name="validDataType" id="validDataType" class="form-control">
									         <option value="0" <c:if test="${maintablecolumn.validDataType eq 0 }">selected</c:if> >无</option>
									         <option value="1" <c:if test="${maintablecolumn.validDataType eq 1 }">selected</c:if> >中文</option>
									         <option value="2" <c:if test="${maintablecolumn.validDataType eq 2 }">selected</c:if> >数字</option>
									         <option value="3" <c:if test="${maintablecolumn.validDataType eq 3 }">selected</c:if> >英文</option>
									         <option value="4" <c:if test="${maintablecolumn.validDataType eq 4 }">selected</c:if> >邮箱</option>
									         <option value="5" <c:if test="${maintablecolumn.validDataType eq 5 }">selected</c:if> >IP地址</option>
									         <option value="6" <c:if test="${maintablecolumn.validDataType eq 6 }">selected</c:if> >电话号码</option>
									         <option value="7" <c:if test="${maintablecolumn.validDataType eq 7 }">selected</c:if> >MAC地址</option>
									      	</select> 
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">列表宽度:</label>
										<div class="col-md-8">
										  <input type="text" id="columnLength" name="columnLength" class="form-control" value="${maintablecolumn.columnLength}"/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">输入内容长度：</label>
										<div class="col-md-8">
											<input type="text" id="inputLength" name="inputLength" value="${maintablecolumn.inputLength}" class="form-control"/>
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">文件存放路径（属性列表表名称）:</label>
										<div class="col-md-8">
										  <input type="text" id="tempPaths" name="tempPaths" value="${maintablecolumn.tempPaths}" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">列表内容是否关联详情：</label>
										<div class="col-md-8">
											<select name="isTip" id="isTip" class="form-control"> 
									         <option value="0" <c:if test="${maintablecolumn.isTip eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintablecolumn.isTip eq 1 }">selected</c:if> >是</option>
									      	</select>
										</div>
									</div>
								</div>
							</div> 
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="submit"  class="btn green">提交</button> 
								<button type="button" onclick="returnTableDetail();" class="btn default">返回</button>                              
							</div> 
						</div>
					</form>
					<!-- END FORM-->                
					</div>
				</div>
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/s/My97DatePicker/WdatePicker.js"></script>
	<script>
	$(function() {
	    $("#cal-infoForm").validate({
	        submitHandler: function(form) {
				bootbox.animate(false);
				var box = bootbox.dialog('<div class="progress progress-striped active" style="margin:0px;"><div class="bar" style="width: 100%;"></div></div>');
	            form.submit();
	        },
	        errorClass: 'validate-error'
	    });

	    
	})
	function returnTableDetail(){
		  window.location.href="${ctx}/maintablecolumn/maintablecolumn_list.izhbg?maintableid=${maintableid }";
		} 
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>