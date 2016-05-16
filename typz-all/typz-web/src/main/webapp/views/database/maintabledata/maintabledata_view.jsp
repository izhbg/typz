<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>

<script type="text/javascript" src="${ctx}/s/assets/plugins/ajaxupload/ajaxupload.3.6.js"></script>
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
					<form action="${ctx}/default/management/security/maintable/saveMainTableColumn.do" class="form-horizontal" id="queryformtable" method="post">
					  <input id="action" type="hidden" name="action" value="${action }"> 
					  <input type="hidden" id="maintableid"name="maintableid" value="${maintable.tableid }" ispost="true">
					  <input type="hidden" id="realtableid"name="realtableid" value="${realTableMap[maintable.keyColumnName]}"ispost="true">
					  <input type="hidden" id="keyColumnName"name="keyColumnName" value="${maintable.keyColumnName}"ispost="true">
					  <input type="hidden" id="tableName"name="tableName" value="${maintable.tableName }" ispost="true">
					  <input type="hidden" id="tableRealName"name="tableRealName" value="${maintable.tableRealName }" ispost="true">
					  <input id="userid"name="userid" type="hidden" value="${userid }" ispost="true"/>
					  <c:set var="num" value="0"></c:set>
					  <c:if test="${realTableMap[maintable.keyColumnName] ne null}">
					   		<c:set var="isupdate" value="1"></c:set>
					  </c:if>
					  <c:if test="${realTableMap[maintable.keyColumnName] eq null or realTableMap[maintable.keyColumnName] eq ''}">
					   		<c:set var="isupdate" value="0"></c:set>
					  </c:if>
						<div class="form-body" style="margin: 10px;">
							<c:forEach var="list" items="${detailList}" varStatus="status">
							    <c:set var="valueType" value="true" scope="page" />
							 	<c:if test="${list.mainTableColumn.isMust eq 1 }">
									<c:set var="mustInput" value="true" scope="page" />
								</c:if>
								<c:if test="${list.mainTableColumn.isMust ne 1 }">
									<c:set var="mustInput" value="false" scope="page" />
								</c:if>
								<!--隐藏域  -->
							   <c:if test="${list.type eq 6}">
							   		<input type="hidden" name="${list.name}" id="${list.name}" value="${realTableMap[list.name]}" />
							   </c:if>

							   	<c:if test="${num%2 eq 0 }">
							    	<div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if>  >
							    </c:if>
								
								 <!--文本框  -->
							    <c:if test="${list.type eq 1}">
							    	<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${realTableMap[list.name]}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>

								<!--密码框  -->
							   <c:if test="${list.type eq 11}">
								   <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
								   </c:if>
							   	   <c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }"></c:if>：</label>  
											<div class="col-md-8">
												<p class="form-control-static">******</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>
								
							   <!--下拉列表  -->
							   <c:if test="${list.type eq 2}">
							   <c:set var="num" value="${num+1}"></c:set>
								<div class="col-md-6"> 
									<div class="form-group">
										<label class="control-label col-md-4">${list.cname}：</label>  
										<div class="col-md-8">
											<p class="form-control-static">
												<c:forEach var="map" items="${list.lists}">
											   		<c:if test="${map.id eq realTableMap[list.name]}">${map.name }</c:if>
											   	</c:forEach>
											</p>
											<span class="help-block">${list.mainTableColumn.bz}</span>
										</div>
									</div>
								</div>
							   </c:if>
					
							    <!--日期型  -->
							   <c:if test="${list.type eq 5}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${realTableMap[list.name]}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>

								<!--时间型  -->
							   <c:if test="${list.type eq 10}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${realTableMap[list.name]}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>
								<!--时间型  -->
							   <c:if test="${list.type eq 17}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${realTableMap[list.name]}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>
								<!--时间型  -->
							   <c:if test="${list.type eq 18}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${fn:substring(realTableMap[list.name],0,7)}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>
								<!--时间型  -->
							   <c:if test="${list.type eq 19}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${fn:substring(realTableMap[list.name],0,4)}</p>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>

								 <!--弹出树  -->
							   <c:if test="${list.type eq 8}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<c:set var="tanchuvalue" value='${fn:split(realTableMap[list.name],",")}'></c:set>
												<div class="input-group userPicker">
										   			<p class="form-control-static">${tanchuvalue[1]}</p>
												</div>
											</div>
										</div>
									</div>
							   </c:if>
								
							   <!--文本域  -->
							   <c:if test="${list.type eq 9}">
							     <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
								  </c:if>

							   	  <c:set var="num" value="${num+2}"></c:set>
								  <div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">${list.cname}：</label>  
											<div class="col-md-10"> 
								   				<p class="form-control-static" style="word-wrap: break-word;word-break: normal;">${realTableMap[list.name]}</p>
												<span class="help-block" style="margin-top: 20px;border-top: 1px dotted #CCCCCC;">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if> 
			
								<!-- iframe -->
								<c:if test="${list.type eq 13}">
								   <c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
												<input type="hidden" id="${list.name }" name="${list.name }" value="${realTableMap[list.name]}"/>
										  		<iframe src="${tablekey[0]}?id=${realTableMap[list.name]}&inputid=${list.name }" scrolling="no" frameborder="0" style="width: 260px;height:80px;"></iframe>
											</div>
										</div>
									</div>
							   </c:if> 
						
							    <!--文件上传  -->
						   	  <c:if test="${list.type eq 12}">
						      <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
							  </c:if>
						   	  <c:set var="num" value="${num+2}"></c:set>
							  <div class="col-md-12"> 
									<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
				    				<input id="fileconfid" type="hidden" name="${list.name}" value="${realTableMap[list.name]}" ispost="true"/>
									<input id="temppath" type="hidden" value="${list.mainTableColumn.tempPaths}"/>
									<div class="con_list_text attach_p_div" id="attach_p_div" style="display:block;width:100%;">
										<h5>
											<div id="attach_div" 
												style="background:url(${ctx}/s/assets/plugins/ajaxupload/images/fj_close.gif) no-repeat left;display:none;color:#1e6b91;padding-left:20px;">
												附件清单
												<span style="font-size: 10px; color: red;cursor: pointer;" onclick="atch_dis()">点击可展开或隐藏</span>
											</div>
										</h5>
										<table id="attach_fs" width="100%" cellspacing="0" cellpadding="0" style="display:none">
										</table>
									</div>
								</div>
						   </c:if>				
							
							<!-- 14 列表属性 -->
							<c:if test="${list.type eq 14}">
								<c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
								 </c:if>
						   	  <c:set var="num" value="${num+2}"></c:set>
								<div class="col-md-12">
									<div class="portlet box light-grey">
										<div class="portlet-title">
											<div class="caption">${list.cname}</div>
											<div class="tools">
												<a href="" onclick="expandTable('${list.name}')" class="expand"></a>
											</div>
										</div>
										<div class="portlet-body" style="display: none;">
											<div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
												<input id="${list.name}parent" type="hidden" name="${list.name}" value="${realTableMap[list.name]}" ispost="true"/>
												<input id="${list.name}tablename" type="hidden" value="${list.mainTableColumn.tempPaths}"/>
												<div class="col-md-12" id="${list.name}table">

												</div>
											</div>
										</div>
									</div>   
								</div>
						   </c:if> 
							<!--15ueditor  -->
							   <c:if test="${list.type eq 15}">
							     <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row" <c:if test="${(num/2)%2 eq 0 }">style="background-color: #f7f7f7;"</c:if> >
								  </c:if>

							   	  <c:set var="num" value="${num+2}"></c:set>
								  <div class="col-md-12">
										<div class="form-group">
											<div class="col-md-12" style="padding: 15px;padding-left: 30px;padding-right: 30px;">
												<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
								   				<p class="form-control-static">${realTableMap[list.name]}</p>
											</div>
										</div>
									</div>
							   </c:if>
							<!-- 地区选择 -->
									<!--时间型  -->
							   <c:if test="${list.type eq 16}">
							   		<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}：</label>  
											<div class="col-md-8">
												<p class="form-control-static">${realTableMap[list.name]}</p>
											</div>
										</div>
									</div>
							   </c:if>
							   <c:if test="${num%2 eq 0}">
								  </div>
							   </c:if>
							</c:forEach>
							<c:if test="${num%2 eq 1}">
								</div>
							 </c:if> 
						</div>


						<div class="form-actions right">  
							<div class="col-md-offset-3 col-md-9">
								<button type="button" onclick="returnTableDetail()" class="btn default">返回</button> 
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
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
		<img src="${ctx}/s/assets/img/ajax-modal-loading.gif" alt="" class="loading">
	</div>
	<script src="${ctx}/s/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>		
	<script src="${ctx}/s/assets/plugins/validation/jquery.validationEngine-en.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/validation/validationEngine.jquery.css"/>
	<script>
	$(function() {
		/**$("#queryformtable").validationEngine({
            promptPosition:"topLeft", 
            showTypes:2,
			success:false
          });**/
		var fileid = $("#fileconfid").val();
		if(fileid!=null&&fileid!="")
			$("#attach_div").css("display","block");
	});
	function returnTableDetail(){
		history.back();
	}
	 function type1Click(El){
			var selectValue = El.value;
			$("#type1").val(selectValue);
			if(selectValue == '1'){
				document.getElementById('title_add').style.display = 'inline';
				document.getElementById('gw_upload').style.display = 'none';
				
			}else{
				document.getElementById('title_add').style.display = 'none';
				document.getElementById('gw_upload').style.display = 'inline';
			}
		}
	//附件列表刷新
	 function atch_dis(){
		 var list = document.getElementById("attach_fs");
		 var flist = document.getElementById("attachList");
		 var cont = document.getElementById("attach_div");
		 var flag =$('#attach_fs').css("display");
			  if (flag == "none") {
			      $('#attach_fs').css("display", "");
			      $('#attach_div').css("background","url(${ctx}/s/assets/plugins/ajaxupload/images/fj_open.gif) no-repeat left");
			     refresh_list();
			  }else{
			      $('#attach_fs').css("display", "none");
			      $('#attach_div').css("background","url(${ctx}/s/assets/plugins/ajaxupload/images/fj_close.gif) no-repeat left");
			  }
		}
	function refresh_list(){
		  $('#attach_p_div').css("display", "block");
		  $('#attach_div').css("display", "block");
		  $('#attach_fs').css("display", "");
		  $('#attach_div').css("background","url(${ctx}/s/assets/plugins/ajaxupload/images/fj_open.gif) no-repeat left");
		  
		  var bizType = $('#bizType').val();
		   $.post("${ctx}/default/management/security/maintable/queryFilelist.do",{fileconfid:$("#fileconfid").val()},function(data){
	        	 $("#attach_fs").html(data);//.initUI();
	       });
		
		}


	 
  function openGroupWin1(url,title,rel,columnid,ischecked,id){
	  var $modal = $('#ajax');
	  $('body').modalmanager('loading');
	  setTimeout(function(){
          $modal.load(url+"?columnid="+columnid+"&id="+id+"&title="+title, '', function(){
          $modal.modal();
        });
      }, 500); 
	  /***
		dialog = $.dialog({ 
			id:rel,//rel
			title:title,
		    lock: true, 
		    max: false, 
		    min: false
		});
		$.ajax({ 
		    url:url,
		    data:"columnid="+columnid+"&id="+id, 
		    success:function(data){ 
		        dialog.content(data);
		    }, 
		    cache:false 
		});**/
	} 
  function expandTable(id){
	   var tablename = $("#"+id+"tablename").val();
	   var valid = $("#"+id+"parent").val();
	   if(valid=="")
	   {
		   valid = "  ";
		   }
	   url = "management/security/maintable/querylistattributepage.do?tableName="+tablename+"&"+id+"="+valid+"&linkcolumnname="+id+"&isview=1";
	   $.post("${ctx}/default/"+url,function(data){
       	 $("#"+id+"table").html(data);//.initUI();
      });
	}
	function expandPageTable(id,pageNo,pageSize){
	   var tablename = $("#"+id+"tablename").val();
	   var valid = $("#"+id+"parent").val();
	   if(valid=="")
	   {
		   valid = "  ";
		   }
	   url = "management/security/maintable/querylistattributepage.do?tableName="+tablename+"&"+id+"="+valid+"&linkcolumnname="+id+"&pageNo="+pageNo+"&pageSize="+pageSize+"&isview=1";
	   $.post("${ctx}/default/"+url,function(data){
       	 $("#"+id+"table").html(data);//.initUI();
      });
	}
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>