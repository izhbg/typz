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
					<form action="${ctx}/maintable/maintable_addOrupdate.izhbg" class="form-horizontal" id="cal-infoForm" method="post">
					   <input type="hidden" name="methodCall" value="saveMainTable"> 
  					   <input type="hidden" name="tableid" value="${maintable.tableid }"> 
						<div class="form-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">表名：</label>
										<div class="col-md-8">
											<input type="text" class="form-control required" name="tableName" value="${maintable.tableName }" >
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">表中文名:</label>
										<div class="col-md-8">
											<input type="text" class="form-control required" name="tableCName" value="${maintable.tableCName }" >
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">格式类型：</label>
										<div class="col-md-8">
											<select name="formatType" id="formatType" class="form-control required">
										         <option value="0" <c:if test="${maintable.formatType eq 0 }">selected</c:if> >表</option>
										         <option value="1" <c:if test="${maintable.formatType eq 1 }">selected</c:if> >图</option>
										         <option value="2" <c:if test="${maintable.formatType eq 2 }">selected</c:if> >图表</option>
										      </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否多表拼接:</label>
										<div class="col-md-8">
											<select name="isMulti" id="isMulti" class="form-control required">
									         <option value="0" <c:if test="${maintable.isMulti eq 0 }">selected</c:if> >否</option>
									         <option value="1" <c:if test="${maintable.isMulti eq 1 }">selected</c:if> >是</option>         
									      </select>
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">唯一标识的字段：</label>
										<div class="col-md-8">
											<select name="keyColumnName" id="keyColumnName" class="form-control" >
											  <option value="" >请选择</option>
											  <c:forEach var="column" items="${columnlist}">
											      <option value="${column.columnName }" <c:if test="${column.columnName eq maintable.keyColumnName }">selected</c:if> >${column.columnCName }</option>
											  </c:forEach>  
											 </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">真正表名:</label>
										<div class="col-md-8">
											<input type="text" name="tableRealName"value="${maintable.tableRealName }" class="form-control required" />
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">是否进入临时表：</label>
										<div class="col-md-8">
											<select name="isPutTemp" id="isPutTemp" class="form-control">
										     <option value="" >请选择</option>
										     <option value="0" <c:if test="${maintable.isPutTemp eq 0 }">selected</c:if> >否</option>
										     <option value="1" <c:if test="${maintable.isPutTemp eq 1 }">selected</c:if> >是</option>       
										   </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">所属归类:</label>
										<div class="col-md-8">
											<select name="type" id="type" class="form-control">
											<c:forEach var="column" items="${typelist}">
											      <option value="${column.id }" <c:if test="${column.id eq maintable.type }">selected</c:if> >${column.name }</option>
											  </c:forEach> 
											</select> 
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">所需字段的sql：</label>
										<div class="col-md-10"> 
											<textarea id="sql"name="sql" rows="5" class="form-control required" cols="70">${maintable.sql }</textarea>
											<span class="help-block">sql中可用公式：@CurrentUserName；@CurrentUserId；@CurrentDepId；@CurrentDepName</span>
										</div>
									</div>
								</div>
							</div> 
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">获取表名的sql：</label>
										<div class="col-md-10">
											<textarea id="multiSql" name="multiSql" rows="5" class="form-control" cols="70">${maintable.multiSql }</textarea>
										</div>
									</div>
								</div>
							</div> 
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="submit"  class="btn green" onclick="return saveMainTable();">提交</button> 
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
		  window.location.href="${ctx}/maintable/maintable_list.izhbg"; 
		} 
		
		function saveMainTable(){
		  var xform = document.maintableform;
		  var isMulti=document.getElementById("isMulti").value;
		  var multiSql=document.getElementById("multiSql").value;
		  if(isMulti==1&&(multiSql==null||multiSql.length==0)){
		  	alert("当为拼接表时，获取表名的sql不能为空！");
		  	return false;
		  }else{
			  return true;
			  }
		 // alert(isMulti);
		  //alert(multiSql);
		 
		} 
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>