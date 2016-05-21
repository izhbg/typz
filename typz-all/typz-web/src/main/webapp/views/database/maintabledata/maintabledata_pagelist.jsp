<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<div class="page-header"
			style="margin-top: 0px; margin-bottom: 0px; padding-bottom: 0px;">
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content" style="min-height: 660px;">

			<div class="content content-inner">
				<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
				<!-- 操作按钮如下 -->
				<div class="content content-inner">
					<div class="panel panel-default">
						<!-- Default panel contents -->
						<div class="panel-heading">
							<h3 class="panel-title">${maintable.tableCName }列表</h3>
						</div>
						<div class="panel-body">
							<tags:permission type="create" gnDm="${sessionScope.gnDm}">
								<a href="javascript:addObject()" class="btn btn-sm default"><i
									class="fa fa-plus-square"></i>新建</a>
							</tags:permission>
							<tags:permission type="delete" gnDm="${sessionScope.gnDm}">
								<a href="javascript:deleteInfo()" class="btn btn-sm default"><span
									class="glyphicon glyphicon-calendar"></span> 删除</a>
							</tags:permission>
							<a class="btn default btn-sm" href="#collapse-group"
								data-toggle="collapse" data-parent="#m-sidebar"><i
								class="m-icon-swapdown m-icon-blank"></i>查询</a>
							<c:forEach var="buttons" items="${buttonList}">
								<c:if test="${buttons.button_target eq 'ajaxTodo'}">
									<c:if test="${buttons.button_executetype eq 1}">
										<a class="btn default btn-sm" href="#this"
											onclick="useraction('${buttons.button_target}','${ctx}/default/${fn: replace(buttons.button_action,'&amp;','&')}?tableName=${maintable.tableName }&executsql=${buttons.button_sql}')">${buttons.button_name}</a>
									</c:if>
									<c:if test="${buttons.button_executetype eq 2}">
										<a class="btn default btn-sm"
											href="javascript:${buttons.button_action}('${fn: replace(buttons.button_sql,'&amp;','&')}')">${buttons.button_name}</a>
									</c:if>
									<c:if test="${buttons.button_executetype eq 3}">
										<a class="btn default btn-sm" href="#this"
											onclick="setMenuState4session('${buttons.button_other}','${ctx}/default/${fn: replace(buttons.button_action,'&amp;','&')}','${buttons.button_name}')">${buttons.button_name}</a>
									</c:if>
								</c:if>
								<c:if test="${buttons.button_target eq 'dialog'}">
									<a class="btn default btn-sm" href="#this"
										onclick="dialogInfo('${ctx}/default/${fn: replace(buttons.button_action,'&amp;','&')}?tableName=${maintable.tableName }&executsql=${buttons.button_sql}')">${buttons.button_name}</a>
								</c:if>
							</c:forEach>
							<!--<div class="btn-group">
						<a class="btn btn-sm red" href="#" data-toggle="dropdown">
					<i class="icon-user"></i> 其它
					<i class="icon-angle-down "></i>
					</a>
					<ul class="dropdown-menu pull-right">
					  <tags:permission type="delete" gnDm="${sessionScope.gnDm}">
						<li><a href="javascript:deleteInfo()"><i class="icon-trash"></i> 删除</a></li>
					  </tags:permission>
						<li><a href="javascript:table.exportExcel()"><i class="icon-ban-circle"></i>导出</a></li>
						<li class="divider"></li>
					</ul>
				</div>
		
				-->
							<div class="pull-right">
								每页显示 <select class="m-wrap xsmall">
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="50">50</option>
								</select> 条
							</div>

							<div id="collapse-group"
								class="collapse <c:if test="${isSearch}">in</c:if> ">
								<div class="panel panel-default" style="margin-bottom: 0px;">
									<div class="panel-body">
										<form id="searchForm" name="searchForm" method='post'
											action="${ctx}/maintabledata/maintabledata_pagelist.izhbg"
											class="form-horizontal">
											<input id="maintableid" type="hidden" name="maintableid"
												value="${maintable.tableid }" ispost="true" /> <input
												id="tableName" type="hidden" name="tableName"
												value="${maintable.tableName }" ispost="true" /> <input
												id="realtableid" name="realtableid" type="hidden" value=""
												ispost="true" /> <input id="userid" name="userid"
												type="hidden" value="${userid }" ispost="true" />
											<c:set var="num" value="0"></c:set>
											<div class="form-body">
												<c:forEach var="list" items="${queryList}"
													varStatus="status">
													<c:if test="${list.type eq 6}">
														<input type="hidden" id="${list.name}" name="${list.name}"
															value="${list.value}" ispost="true" />
													</c:if>
													<c:if test="${num%2 eq 0 }">
														<div class="row">
													</c:if>
													<c:if test="${list.type eq 1 or list.type eq 9}">
														<c:set var="num" value="${num+1}"></c:set>
														<div class="col-md-6">
															<div class="form-group">
																<label class="control-label col-md-5">${list.cname}：</label>
																<div class="col-md-7">
																	<input type="text" id="${list.name }"
																		name="${list.name }" value="${list.value}"
																		class="form-control" />
																</div>
															</div>
														</div>
													</c:if>
													<c:if test="${list.type eq 2}">
														<c:set var="num" value="${num+1}"></c:set>
														<div class="col-md-6">
															<div class="form-group">
																<label class="control-label col-md-5">${list.cname}：</label>
																<div class="col-md-7">
																	<select name="${list.name }" id="${list.name }"
																		class="form-control">
																		<option value="">请选择</option>
																		<c:forEach var="map" items="${list.lists}">
																			<option value="${map.id }"
																				<c:if test="${map.id eq list.value}">selected</c:if>>${map.name
																				}</option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
													</c:if>
													<c:if
														test="${list.type eq 5 and list.mainTableColumn.dataType ne 3}">
														<c:set var="num" value="${num+1}"></c:set>
														<div class="col-md-6">
															<div class="form-group">
																<label class="control-label col-md-5">${list.cname}：</label>
																<div class="col-md-7">
																	<input class="Wdate form-control" type="text"
																		runat="server" id="${list.name }" name="${list.name }"
																		value="${fn:substring(list.value,0,19) }" size="18"
																		onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																		ispost="true" isclear="true" />
																</div>
															</div>
														</div>
													</c:if>
													<c:if
														test="${list.type eq 5 and list.mainTableColumn.dataType eq 3}">
														<c:set var="num" value="${num+1}"></c:set>
														<div class="col-md-6">
															<div class="form-group">
																<label class="control-label col-md-5">开始${list.cname}：</label>
																<div class="col-md-7">
																	<input class="Wdate form-control" type="text"
																		runat="server" id="${list.name }start"
																		name="${list.name }start"
																		value="${fn:substring(list.startdate,0,19) }"
																		size="18"
																		onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																		ispost="true" isclear="true" />
																</div>
															</div>
														</div>
														<c:if test="${num%2 eq 0 }">
															<c:set var="num" value="0"></c:set>
											</div>
											<div class="row">
												</c:if>
												<c:set var="num" value="${num+1}"></c:set>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-5">结束${list.cname}：</label>
														<div class="col-md-7">
															<input class="Wdate form-control" type="text"
																runat="server" id="${list.name }end"
																name="${list.name }end"
																value="${fn:substring(list.enddate,0,19) }" size="18"
																onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																ispost="true" isclear="true" />
														</div>
													</div>
												</div>
												</c:if>
												<c:if
													test="${list.type eq 10 and list.mainTableColumn.dataType ne 3}">
													<c:set var="num" value="${num+1}"></c:set>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-5">${list.cname}：</label>
															<div class="col-md-7">
																<input class="Wdate form-control" type="text"
																	runat="server" id="${list.name }" name="${list.name }"
																	value="${fn:substring(list.value,0,19) }" size="18"
																	onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
																	ispost="true" isclear="true" />
															</div>
														</div>
													</div>
												</c:if>
												<c:if
													test="${list.type eq 10 and list.mainTableColumn.dataType eq 3}">
													<c:set var="num" value="${num+1}"></c:set>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-5">开始${list.cname}：</label>
															<div class="col-md-7">
																<input class="Wdate form-control" type="text"
																	runat="server" id="${list.name }start"
																	name="${list.name }start"
																	value="${fn:substring(list.startdate,0,10)}" size="18"
																	onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
																	ispost="true" isclear="true" />
															</div>
														</div>
													</div>
													<c:if test="${num%2 eq 0 }">
														<c:set var="num" value="0"></c:set>
											</div>
											<div class="row">
												</c:if>
												<c:set var="num" value="${num+1}"></c:set>
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-5">结束${list.cname}：</label>
														<div class="col-md-7">
															<input class="Wdate form-control" type="text"
																runat="server" id="${list.name }end"
																name="${list.name }end"
																value="${fn:substring(list.enddate,0,10) }" size="18"
																onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
																ispost="true" isclear="true" />
														</div>
													</div>
												</div>
												</c:if>
												<c:if test="${list.type eq 8}">
													<c:set var="num" value="${num+1}"></c:set>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-5">${list.cname}：</label>
															<div class="col-md-7">
																<c:set var="tanchuvalue"
																	value='${fn:split(list.value,",")}'></c:set>
																<div class="input-group userPicker">
																	<input id="${list.name }" type="hidden"
																		id="${list.name }" name="${list.name }"
																		value="${realTableMap[list.name]}" ispost="true" /> <input
																		id="${list.name }_snb"
																		class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control'
																		id="${list.name }_sn" type="text" runat="server"
																		name="${list.name }_sn" value="${tanchuvalue[1]}"
																		size="18"
																		onclick="javascript:openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb');"
																		valueType="${valueType}" mustInput="${mustInput}"
																		ispost="true" readonly
																		<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />
																	<span class="input-group-addon add-on"
																		onclick="openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb')"><i
																		class="fa fa-search"></i></span>
																</div>
															</div>
														</div>
													</div>
												</c:if>
												<c:if test="${list.type eq 16}">
													<c:set var="num" value="${num+1}"></c:set>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-5">${list.cname}：</label>
															<div class="col-md-7 picker" id="${list.name }">
																<div class="input-group">
																	<input type="text" readonly="readonly"
																		value="${list.value}" name="${list.name }"
																		class="form-control" /> <span
																		class="input-group-addon region-picker"
																		data-remote="${ctx}/s/assets/plugins/chineserp-jquery-master/example"
																		data-picked="${realTableMap[list.name]}"
																		data-visible="5"><i class="fa fa-user"></i></span>
																</div>
																<script type="text/javascript">
															$('#${list.name} .region-picker').regionPicker().on('picked.rp', function(e, regions){
														      $('#${list.name} input').val(regions.map(function(r){ return r.n; }).join(", "));
														    });
														</script>
															</div>
														</div>
													</div>
												</c:if>
												<c:if test="${num%2 eq 0 }">
													<c:set var="num" value="0"></c:set>
											</div>
											</c:if>
											</c:forEach>
											<c:if test="${num%2 eq 1 }">
									</div>
									</c:if>
									<!--/row-->
								</div>
								</form>
							</div>
							<div class="form-actions right" style="margin-top: 0px;">
								<button type="button" class="btn default" onclick="clearForm()">清空</button>
								<button type="button"
									onclick="javascript:document.searchForm.submit()"
									class="btn blue">
									<i class="fa fa-check"></i> 查询
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- Table -->
				<div class="table-scrollable">
					<form
						action="${ctx}/default/management/security/maintable/removeRealTable.do"
						id="gridform" method="post">
						<table id="meeting-infoGrid"
							class="table table-striped  table-hover"
							style="border-top: 1px solid green;">
							<thead class="gridHeader">
								<tr class="gridThead">
									<th width="50" style="text-align: center;" class="id"><input
										type="checkbox" name="checkAll" id="checkAll"
										onchange="toggleSelectedItems(this.checked)" /></th>
									<th class="id">序号</th>
									<c:forEach var="list" items="${viewList}" varStatus="status">
										<th>${list.cname }</th>
									</c:forEach>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="resoult" items="${page.result}"
									varStatus="status">
									<tr
										rel="realtableid=${resoult[maintable.keyColumnName]}&tableName=${maintable.tableName }">
										<td style="text-align: center;" class="id"><input
											type="checkbox" name="realtableid"
											class="selectedItem a-check"
											value="${resoult[maintable.keyColumnName]}" /></td>
										<td>${(page.pageNo-1)*page.pageSize+status.index+1 }</td>
										<c:forEach var="list" items="${viewList}" varStatus="status">
											<!-- 点击查看详情 -->
											<c:if test="${list.mainTableColumn.propertyType eq 7}">
												<td width="${list.mainTableColumn.columnLength}"><c:set
														var="tablekey"
														value='${fn:split(fn:substringAfter(list.mainTableColumn.typeSql,"#"),":")}'></c:set>
													<a
													href='${ctx}/default/management/security/maintable/tableDetailView.do?${fn:substringBefore(list.mainTableColumn.typeSql,"#")}&${tablekey[0]}=${resoult[tablekey[1]]}'>${resoult[list.name]
														}</a></td>
											</c:if>

											<c:if
												test="${list.mainTableColumn.propertyType eq 2 or list.mainTableColumn.propertyType eq 4 }">
												<td width="${list.mainTableColumn.columnLength}"><c:forEach
														var="item" items="${list.lists}">
														<c:if test="${item.id eq resoult[list.name]}">${item.name}</c:if>
													</c:forEach></td>
											</c:if>

											<c:if test="${list.mainTableColumn.propertyType eq 8}">
												<td width="${list.mainTableColumn.columnLength}">
													${resoult[list.name] }</td>
											</c:if>

											<c:if
												test="${list.mainTableColumn.propertyType eq 5 or (list.mainTableColumn.dataType eq 3 and list.mainTableColumn.propertyType eq 6)}">
												<td width="${list.mainTableColumn.columnLength}">
													${fn:substring(resoult[list.name],0,19) }</td>
											</c:if>

											<c:if test="${list.mainTableColumn.propertyType eq 10 }">
												<td width="${list.mainTableColumn.columnLength}">
													${fn:substring(resoult[list.name],0,10) }</td>
											</c:if>

											<c:if test="${list.mainTableColumn.propertyType eq 9 }">
												<td width="${list.mainTableColumn.columnLength}"
													title="${resoult[list.name] }" onmouseover="tipMsg()">
													${fn:substring(resoult[list.name],0,10) }...</td>
											</c:if>

											<c:if
												test="${list.mainTableColumn.propertyType ne 7 and list.mainTableColumn.propertyType ne 2 and list.mainTableColumn.propertyType ne 4 and list.mainTableColumn.propertyType ne 8 and list.mainTableColumn.propertyType ne 5 and list.mainTableColumn.propertyType ne 10 and list.mainTableColumn.propertyType ne 9}">
												<td width="${list.mainTableColumn.columnLength}"
													title="${resoult[list.name] }">${resoult[list.name] }
												</td>
											</c:if>
										</c:forEach>

										<td align="center" style="text-align: center;"><tags:permission
												type="update" gnDm="${sessionScope.gnDm}">
												<a
													href="javascript:modifyObject('${resoult[maintable.keyColumnName]}','${maintable.tableName }','${userid }','${opeid }');">修改</a>
											</tags:permission> <tags:permission type="read" gnDm="${sessionScope.gnDm}">
												<a
													href="javascript:viewObject('${resoult[maintable.keyColumnName]}','${maintable.tableName }');">查看</a>
											</tags:permission> <tags:permission type="delete" gnDm="${sessionScope.gnDm}">
												<a
													href="javascript:deleteInfoByOne('${resoult[maintable.keyColumnName]}')"
													style="cursor: pointer;">删除</a>
											</tags:permission></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
				<div class="form-actions fluid" style="margin-top: 0px;">
					<div class="col-md-5">
						<div class="dataTables_info" id="sample_3_info">共${page.totalCount}条记录
							显示${page.start+1}到${page.start+page.pageSize}条记录</div>
					</div>
					<div class="col-md-7">
						<div
							class="dataTables_paginate paging_bootstrap  pull-right pagination">
							<button class="btn btn-sm default">&lt;</button>
							<button class="btn btn-sm default">1</button>
							<button class="btn btn-sm default">&gt;</button>
						</div>
					</div>
				</div>
			</div>
			<!-- 搜索条件结束 -->


		</div>
		<div class="clearfix"></div>
	</div>
	</div>
	</div>
	<div class="modal fade" id="ajax" tabindex="-1" role="basic"
		aria-hidden="true">
		<img src="${ctx}/s/assets/img/ajax-modal-loading.gif" alt=""
			class="loading">
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript"
	src="${ctx}/s/assets/plugins/guser/js/common.js"></script>
<script type="text/javascript"
	src="${ctx}/s/assets/plugins/guser/js/ucenter.js"></script>
<script type="text/javascript">
var config = {
	    id: 'meeting-infoGrid',
	    pageNo: ${page.pageNo},
	    pageSize: ${page.pageSize},
	    totalCount: ${page.totalCount},
	    resultSize: ${page.resultSize},
	    pageCount: ${page.pageCount},
	    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
	    asc: ${page.asc},
	    params: {
	        'wheresql': '${wheresql}',
	        'maintableid': '${maintable.tableid}',
	        'tableName': '${maintable.tableName}',
	        'realtableid': '',
	        'userid': '${userid}',
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'searchForm',
		exportUrl: 'meeting-info-export.do'
	};

	var table;

	$(function() {
		table = new Table(config);
	    table.configPagination('.dataTables_paginate');
	    table.configPageInfo('.dataTables_info');
	    table.configPageSize('.m-wrap');
	});
	function clearForm(){
		$(':input','#searchForm')  
		 .not(':button, :submit, :reset, :hidden')
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected'); 
	}
	function deleteInfo(){
		if(confirmTableIsSelectItems()){
			
			var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'此操作将有如下结果：<br/>1.删除数据后不可恢复。<br/>你确定要删除吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmDele" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			if($("#confirminfo").html()==undefined||$("#confirminfo").html()=="undefined"||$("#confirminfo").html()==""){
				$("#searchForm").append(str);
			}
			$("#cconfirmDele").click(function(){
				deleteConfirm(); 
			});
			$("#confirminfo").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	}
	function dialogInfo(url){
		if(confirmTableIsSelectItems()){
			  var $modal = $('#ajax');
			  $('body').modalmanager('loading');
			  setTimeout(function(){
				 var pars = $("input:checkbox[name=realtableid]:checked").serialize();
		         $modal.load(url+"?1=1&"+pars, '', function(){
		         $modal.modal();
		       });
		     }, 500); 
		}
	}
	function deleteInfoByOne(id){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.删除数据后不可恢复。<br/>你确定要删除吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmDele" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		if($("#confirminfo").length==0){
			$("#searchForm").append(str);
		}

		$("#cconfirmDele").click(function(){
			var pars = $("#searchForm").serialize()+"&realtableid="+id;
			$.post("${ctx}/maintabledata/maintabledata_remove.izhbg", pars, function(data){
				$("#searchForm").submit();
			},"json");
		});
		$("#confirminfo").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');
	}
	function deleteConfirm()
	{
		var pars = $("#searchForm").serialize()+"&"+$("input:checkbox[name=realtableid]:checked").serialize();
		$.post("${ctx}/maintabledata/maintabledata_remove.izhbg", pars, function(data){
			$("#searchForm").submit();
		},"json");
	}
 function modifyObject(realtableid,tableName,userid,opeid){
	 window.location.href="${ctx}/maintabledata/maintabledata_edit.izhbg?realtableid="+realtableid+"&tableName="+tableName;
	 }
 function addObject(){
	 	window.location.href="${ctx}/maintabledata/maintabledata_edit.izhbg?"+$("#searchForm").serialize();
	 }
 function openGroupWin1(url,title,rel,columnid,ischecked,id){
	  var $modal = $('#ajax');
	  $('body').modalmanager('loading');
	  setTimeout(function(){
         $modal.load(url+"?columnid="+columnid+"&id="+id+"&title="+title+"&divid=ajax", '', function(){
         $modal.modal();
       });
     }, 500); 
	}
 function viewObject(realtableid,tableName){
	 var url="${ctx}/maintabledata/maintabledata_view.izhbg?realtableid="+realtableid+"&tableName="+tableName;
	 window.location.href= url; 
 }	
 function useraction(target,url){
	 if(target=='ajaxTodo'){
			if(confirmTableIsSelectItems()){
				var str = '<div class="modal fade" id="confirminfo2" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
					+'<div class="modal-dialog">'
					+'<div class="modal-content">'
					+'<div class="modal-header">'
					+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
					+'<h4 class="modal-title">确认信息</h4>'
					+'</div>'
					+'<div class="modal-body">'
					+'此操作将有如下结果：<br/>1.执行操作后不可恢复。<br/>你确定要执行吗？'
					+'</div>'
					+'<div class="modal-footer">'
					+'<button type="button" class="btn blue" id="cconfirmDele2" >确定</button>'
					+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>';
				
				$("#searchForm").append(str);
				$("#cconfirmDele2").click(function(){
					 var pars = $("input:checkbox[name=realtableid]:checked").serialize();
						$.post(url, pars, function(data){
							$("#searchForm").submit();
						},"json");
				});
				$("#confirminfo2").modal({ 
			        backdrop: 'static',  
			        keyboard: false  
			    }).modal('show');
			}
	  }
 } 
</script>

<link rel="stylesheet"
	href="${ctx}/s/assets/plugins/data-tables/DT_bootstrap.css">
<script language="javascript" type="text/javascript"
	src="${ctx}/s/My97DatePicker/WdatePicker.js"></script>
</html>