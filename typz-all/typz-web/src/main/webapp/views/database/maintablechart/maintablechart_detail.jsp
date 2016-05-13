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
					<form action="${ctx}/maintablechart/maintablechart_add.izhbg" class="form-horizontal" id="cal-infoForm" method="post">
					  <input type="hidden" name="tableid" value="${maintable.tableid }">
					  <input type="hidden" name="chartid" value="${mainTableChart.chartid }">  
						<div class="form-body">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">标题：</label>
										<div class="col-md-8">
											<input type="text" name="charttitle" value="${mainTableChart.charttitle }" class="form-control required"/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">X轴名称:</label>
										<div class="col-md-8">
											<input type="text" name="axisNameX" value="${mainTableChart.axisNameX }" class="form-control required"/>
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">Y轴名称：</label>
										<div class="col-md-8">
											<input type="text" name="axisNameY"value="${mainTableChart.axisNameY }" class="form-control required"/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">分类轴:</label>
										<div class="col-md-8">
											<input type="text" name="seriesName"value="${mainTableChart.seriesName }" class="form-control"/>
										</div>
									</div>
								</div> 
							</div> 
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">X轴值：</label>
										<div class="col-md-8">
											<select name="axisvalueX" id="axisvalueX" class="form-control required">
											   <option value="" selected>请选择</option>
											   <c:forEach var="map" items="${list}">
											   <option value="${map.columnName }" <c:if test="${map.columnName eq mainTableChart.axisvalueX }">selected</c:if> >${map.columnName }</option>
											   </c:forEach>
											   </select> 
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">Y轴值:</label>
										<div class="col-md-8">
											<select name="axisvalueY" id="axisvalueY" class="form-control required">
											   <option value="" selected>请选择</option>
											   <c:forEach var="map" items="${list}">
											   		<option value="${map.columnName }" <c:if test="${map.columnName eq mainTableChart.axisvalueY }">selected</c:if> >${map.columnName }</option>
											 	</c:forEach>
											</select>
										</div>
									</div>
								</div> 
							</div> 
						</div>
						<div class="form-actions right"> 
							<div class="col-md-offset-3 col-md-9">
								<button type="submit"  class="btn green" >提交</button> 
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
	  window.location.href="${ctx}/maintablechart/maintablechart_list.izhbg?maintableid=${maintable.tableid }";
	}  
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>