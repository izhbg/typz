<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/s/assets/css/pages/filelist.css" />
<script type="text/javascript"
	src="${ctx}/s/assets/plugins/ajaxupload/ajaxupload.3.6.js"></script>
	
<link rel="stylesheet" type="text/css"
	href="${ctx}/s/assets/plugins/bootstrap-datepicker/datepicker.css" />
<script type="text/javascript"
	src="${ctx}/s/assets/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="${ctx}/s/assets/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/simditor-1.0.5/styles/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/simditor-1.0.5/styles/simditor.css" />
<script type="text/javascript" src="${ctx}/s/assets/plugins/simditor-1.0.5/scripts/js/simditor-all.js">
</script>
</head>
<body class="page-header-fixed">
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="clearfix"></div>
	<div class="page-container" id="dgformdiv">
		<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header"
			style="margin-top: 0px; margin-bottom: 0px; padding-bottom: 0px;">
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
			<div class="row inbox">
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">编辑</h3>
						</div>
						<div class="panel-body form">
							<!-- BEGIN FORM-->
							<form action="" class="form-horizontal" id="queryformtable"
								method="post">
								<input id="action" type="hidden" name="action"
									value="${action }"> <input type="hidden"
									id="maintableid" name="maintableid"
									value="${maintable.tableid }"> <input type="hidden"
									id="realtableid" name="realtableid"
									value="${realTableMap[maintable.keyColumnName]}"> <input
									type="hidden" id="keyColumnName" name="keyColumnName"
									value="${maintable.keyColumnName}"> <input
									type="hidden" id="tableName" name="tableName"
									value="${maintable.tableName }"> <input type="hidden"
									id="tableRealName" name="tableRealName"
									value="${maintable.tableRealName }"> <input
									id="userid" name="userid" type="hidden" value="${userid }" />
								<c:if test="${realTableMap[maintable.keyColumnName] ne null}">
									<c:set var="isupdate" value="1"></c:set>
								</c:if>

								<c:if
									test="${realTableMap[maintable.keyColumnName] eq null or realTableMap[maintable.keyColumnName] eq ''}">
									<c:set var="isupdate" value="0"></c:set>
								</c:if>

								<div class="form-body">
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
											<input type="hidden" name="${list.name}" id="${list.name}"
												value="${realTableMap[list.name]}" />
										</c:if>

										<!--文本框  -->
										<c:if test="${list.type eq 1}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<input
																class="form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>"
																type="text" id="${list.name }" name="${list.name }"
																value="${realTableMap[list.name]}"
																type="${list.mainTableColumn.validDataType}"
																maxlength="${list.mainTableColumn.inputLength}"
																<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />
															<span class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<!--密码框  -->
										<c:if test="${list.type eq 11}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<input class="form-control" type="password"
																id="${list.name }" name="${list.name }"
																value="${realTableMap[list.name]}"
																type="${valueType}" 
																onblur="validData('${list.name }')" /> 
															<span class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">确认${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<input class="form-control" type="password"
																id="${list.name }_se" name="${list.name }_se"
																value="${realTableMap[list.name]}"
																type="${valueType}" 
																onblur="validData('${list.name }');" /> <span
																class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--复选  -->
										<c:if test="${list.type eq 3}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<div class="checkbox-list">
																
																<c:forEach var="map" items="${list.lists}">
																	<label class="checkbox-inline">
																		<input
																			class="form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>"
																			type="checkbox"  name="${list.name }"
																			value="${map.id }" 
																			<c:if test="${map.id eq realTableMap[list.name]}">checked="checked"</c:if>
																			<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />${map.name}
																	 </label>
																</c:forEach>
																<span class="help-block">${list.mainTableColumn.bz}</span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--单选  -->
										<c:if test="${list.type eq 4}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<div class="radio-list">
																
																<c:forEach var="map" items="${list.lists}">
																	<label class="radio-inline">
																		<input
																			class="form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>"
																			type="radio"  name="${list.name }"
																			value="${map.id }" 
																			<c:if test="${map.id eq realTableMap[list.name]}">checked="checked"</c:if>
																			<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />${map.name}
																	 </label>
																</c:forEach>
																<span class="help-block">${list.mainTableColumn.bz}</span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--下拉列表  -->
										<c:if test="${list.type eq 2}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}<c:if
																test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<c:if
																test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }">
																<select
																	class='form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>'
																	name="${list.name }" id="${list.name }"
																	type="${valueType}" style="width:250px;">
																	<option value="">请选择</option>
																	<c:forEach var="map" items="${list.lists}">
																		<option value="${map.id }"
																			<c:if test="${map.id eq realTableMap[list.name]}">selected</c:if>>${map.name
																			}</option>
																	</c:forEach>
																</select>
															</c:if>
															<c:if
																test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">
																<select
																	class='form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>'
																	name="${list.name }" id="${list.name }"
																	valueType="${valueType}" mustInput="${mustInput}"
																	readonly="readonly">
																	<c:forEach var="map" items="${list.lists}">
																		<c:if test="${map.id eq realTableMap[list.name]}">
																			<option value="${map.id }" selected>${map.name
																				}</option>
																		</c:if>
																	</c:forEach>
																</select>
															</c:if>
															<span class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--日期型  -->
										<c:if test="${list.type eq 5||list.type eq 10||list.type eq 17||list.type eq 18||list.type eq 19}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														
														<div class="myform-input">
															<div class="input-group date" style="width: 250px;">
															<input
																class='form-control datetimepicker <tags:validate mainTableColumn="${list.mainTableColumn}"/>'
																data-date-format="<c:if test="${list.type eq 5}">yyyy-mm-dd hh:mm</c:if><c:if test="${list.type eq 10}">yyyy-mm-dd</c:if><c:if test="${list.type eq 17}">hh:ii</c:if><c:if test="${list.type eq 18}">yyyy-mm</c:if><c:if test="${list.type eq 19}">yyyy</c:if>"
																type="text" runat="server" id="${list.name }"
																name="${list.name }"
																value="${fn:substring(realTableMap[list.name],0,19)}"
																size="18"
																type="${valueType}"
																 />
																<span class="input-group-addon"><i class="fa fa-calendar-o"></i></span> 
															</div>
															<span class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--弹出树  -->
										<c:if test="${list.type eq 8}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<c:set var="tanchuvalue"
																value='${fn:split(realTableMap[list.name],",")}'></c:set>
															<div class="input-group userPicker">
																<input id="${list.name }" type="hidden"
																	id="${list.name }" name="${list.name }"
																	value="${realTableMap[list.name]}" />
																<input id="${list.name }_snb"
																	class='form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>'
																	id="${list.name }_sn" type="text" runat="server"
																	name="${list.name }_sn" value="${tanchuvalue[1]}"
																	size="18"
																	onclick="javascript:openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb');"
																	valueType="${valueType}" mustInput="${mustInput}"
																	readonly
																	<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />
																<span class="input-group-addon add-on"<c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb') </c:if>"><i
																	class="fa fa-search"></i></span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<!--文本域  -->
										<c:if test="${list.type eq 9}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-2">${list.cname}
															<c:if test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<c:set var="tablekey"
																value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
															<textarea id="${list.name }" name="${list.name }"
																rows="8" valueType="${valueType}"
																mustInput="${mustInput}"
																<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if>
																class='form-control <tags:validate mainTableColumn="${list.mainTableColumn}"/>'>${realTableMap[list.name]}</textarea>
															<span class="help-block">${list.mainTableColumn.bz}</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<!-- iframe -->
										<c:if test="${list.type eq 13}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}<c:if
																test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<c:set var="tablekey"
																value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
															<input type="hidden" id="${list.name }"
																name="${list.name }" value="${realTableMap[list.name]}" />
															<iframe
																src="${tablekey[0]}?id=${realTableMap[list.name]}&inputid=${list.name }"
																scrolling="no" frameborder="0"
																style="width: 260px; height: 80px;"></iframe>
														</div>
													</div>
												</div>
											</div>
										</c:if>

										<!--文件上传  -->
										<c:if test="${list.type eq 12}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}<c:if
																test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input">
															<c:set var="tablekey"
																value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
															<input id="fileconfid" type="hidden" name="${list.name}"
																value="${realTableMap[list.name]}" class="<tags:validate mainTableColumn="${list.mainTableColumn}"/>"/> 
															<input id="temppath" type="hidden" value="${list.mainTableColumn.tempPaths}" />
															<div id="fujian" style="display: block; width: 100%;">
																<table class="table_border" border="0" width="100%"
																	cellspacing="0" cellpadding="0"
																	style="background-color: #fff;">
																	<tbody>
																		<tr height="20" id="fileUpload"
																			style="border-color: #f0f0f0; display: ''">
																			<td nowrap="true">
																				<div align="left">
																					<input type="hidden" id="attach_gw_id" name="gw_id"> 
																					<input type="hidden" id="type" name="type" value="1"> 
																					<input type="hidden" id="type1" name="type1" value="2">
																					<div id="gw_upload">
																						<input type="button" id="upload_attch" value="上传文件" class="btn red">
																					</div>
																				</div>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>

															<div class="con_list_text attach_p_div" id="attach_p_div"
																style="display: block; width: 100%;">
																<h5>
																	<div id="attach_div"
																		style="background:url(${ctx}/s/assets/plugins/ajaxupload/images/fj_close.gif) no-repeat left;display:none;color:#1e6b91;padding-left:20px;">
																		附件清单 <span
																			style="font-size: 10px; color: red; cursor: pointer;"
																			onclick="atch_dis()">点击可展开或隐藏</span>
																	</div>
																</h5>
																<table id="attach_fs" width="100%" cellspacing="0"
																	cellpadding="0" style="display: none">
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
											<script type="text/javascript">
												var ajaxupload = new AjaxUpload(
														'#upload_attch',
														{
															action : '${ctx}/attachefile/upload.izhbg',
															name : 'uploadFile',
															autoSubmit : true,
															responseType : 'json',
															onSubmit : function(
																	filename,
																	ext) {
																wait();
																ajaxupload
																		.setData({
																			temppath : $("#temppath").val(),
																			fileconfid : $("#fileconfid").val()
																		});
																/***
																if($("#type").val()==3){
																	if (!(ext && /^(pdf|PDF)$/.test(ext))) {
																		alert('请上传的pdf文件格式!');
																		return false;
																	}
																}
																
																var type_file = navTab.getCurrentPanel().find("#CommonAttachmentType").val();
																if (type_file.indexOf(ext)< 0) {
																	 $.dialog.tip('未允许上传的文件格式!');
																	return false;
																}**/
															},
															onComplete : function(
																	filename,
																	data) {
																switch (data.result) {
																case 0:
																	$(
																			"#fileconfid")
																			.val(
																					data.mainTableAttachFile.confId);
																	refresh_list();
																	stop();
																	break;
																case 1:
																	break;
																}
															}
														});
												var fileid = $("#fileconfid")
														.val();
												if (fileid != null
														&& fileid != "")
													$("#attach_div").css(
															"display", "block");
											</script>
										</c:if>

										<!-- 14 列表属性 -->
										<c:if test="${list.type eq 14}">
											<div class="row">
												<div class="col-md-12">
													<div class="portlet box light-grey">
														<div class="portlet-title">
															<div class="caption">${list.cname}</div>
															<div class="tools">
																<a href="" onclick="expandTable('${list.name}')"
																	class="expand"></a>
															</div>
														</div>
														<div class="portlet-body" style="display: none;">
															<div class="row">
																<input id="${list.name}parent" type="hidden"
																	name="${list.name}" value="${realTableMap[list.name]}" />
																<input id="${list.name}tablename" type="hidden"
																	value="${list.mainTableColumn.tempPaths}" />
																<div class="col-md-12" id="${list.name}table"></div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!--富文本  -->
										<c:if test="${list.type eq 15}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-2">${list.cname}<c:if
																test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input" style="padding-bottom: 7px;">
															<c:set var="tablekey"
																value='${fn:split(list.mainTableColumn.typeSql,"*")}' />
															<textarea id="${list.name }" name="${list.name }"
																<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if>
																class='<tags:validate mainTableColumn="${list.mainTableColumn}"/>'>${realTableMap[list.name]}</textarea>
															<script type="text/javascript">
																new Simditor({
																  textarea:$('#${list.name}'),
																  toolbar:[ 'title', 'bold', 'italic', 'underline', 'strikethrough',
																            'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|',
																            'link', 'image', 'hr', '|', 'indent', 'outdent' ],
																  defaultImage:'${ctx}/s/assets/plugins/simditor-1.0.5/images/image.png', //编辑器插入图片时使用的默认图片  
																  upload : {
																	    url : '${ctx}/attachefile/upload.izhbg', //文件上传的接口地址
																	    params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
																	    fileKey: 'uploadFile', //服务器端获取文件数据的参数名
																	    connectionCount: 3,
																	    leaveConfirm: '正在上传文件'
																	}
																});
															</script>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<!-- 地区选择 -->
										<c:if test="${list.type eq 16}">
											<div class="row">
												<div class="col-md-12">
													<div class="myform-group">
														<label class="myform-label col-md-4">${list.cname}<c:if
																test="${list.mainTableColumn.isMust eq 1 }">
																<font color="#ff0000" style="font-size: 8px;">*</font>
															</c:if>
														</label>
														<div class="myform-input picker" id="${list.name }">
															<div class="input-group">
																<input type="text" readonly="readonly"
																	value="${realTableMap[list.name]}" name="${list.name }"
																	class="form-control" /> <span
																	class="input-group-addon region-picker"
																	data-remote="${ctx}/s/assets/plugins/chineserp-jquery-master/example"
																	data-picked="${realTableMap[list.name]}"
																	data-visible="5"><i class="fa fa-user"></i></span>
															</div>
															<script type="text/javascript">
																$('#${list.name} .region-picker').regionPicker().on('picked.rp',
																				function(e,regions) {
																					$('#${list.name} input').val(regions.map(function(r) {
																														return r.n;
																													}).join(", "));
																					});
															</script>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</div>
								<div class="form-actions right">
									<div class="col-md-offset-3 col-md-9">
										<!-- 公告定制功能 -->
										<button type="button" onclick="return addObjectSave();"
											class="btn green">提交</button>
										<button type="button" onclick="returnTableDetail('${maintable.tableName }');"
											class="btn default">返回</button>
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
	<div class="modal fade" id="ajax" tabindex="-1" role="basic"
		aria-hidden="true">
		<img src="${ctx}/s/assets/img/ajax-modal-loading.gif" alt=""
			class="loading">
	</div>
	<script type="text/javascript" src="${ctx}/s/assets/scripts/maintabledata_edit.js"></script>
	<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>