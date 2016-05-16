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
<script type="text/javascript" charset="utf-8" src="${ctx}/s/assets/plugins/ueditor_1.4.3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/s/assets/plugins/ueditor_1.4.3/ueditor.all.min.js"> </script>
 <script src="${ctx}/s/assets/plugins/chineserp-jquery-master/example/chineserp.min.js"></script>
 <script src="${ctx}/s/assets/plugins/chineserp-jquery-master/src/jquery.regionpicker.js"></script>
 <link rel="stylesheet" href="${ctx}/s/assets/plugins/chineserp-jquery-master/dist/jquery.chineserp.css" media="screen">
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
							   		<input type="hidden" name="${list.name}" id="${list.name}" value="${realTableMap[list.name]}" />
							   </c:if>

							   	<c:if test="${num%2 eq 0 }">
							    	<div class="row">
							    </c:if>
								
								 <!--文本框  -->
							    <c:if test="${list.type eq 1}">
							    	<c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input class='<c:if test="${list.mainTableColumn.isMust eq 1||(list.mainTableColumn.isUnique eq 1)||(list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null) }">validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if><c:if test="${list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null }">,custom[<c:if test="${list.mainTableColumn.validDataType eq 5 }">ip</c:if><c:if test="${list.mainTableColumn.validDataType eq 1 }">chinese</c:if><c:if test="${list.mainTableColumn.validDataType eq 2 }">number</c:if><c:if test="${list.mainTableColumn.validDataType eq 3 }">letter</c:if><c:if test="${list.mainTableColumn.validDataType eq 7 }">mac</c:if>]</c:if><c:if test="${list.mainTableColumn.isUnique eq 1 }">,ajax[ajaxIsUnique]</c:if>]</c:if> form-control'  type="text" id="${list.name }" name="${list.name }" value="${realTableMap[list.name]}" valueType="${list.mainTableColumn.validDataType}" isunique="${list.mainTableColumn.isUnique }"mustInput="${mustInput}" maxlength="${list.mainTableColumn.inputLength}" ispost="true"  <c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if> />
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>

								<!--密码框  -->
							   <c:if test="${list.type eq 11}">
								   <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row">
								   </c:if>
							   	   <c:set var="num" value="${num+2}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input class="form-control"  type="password" id="${list.name }" name="${list.name }" value="${realTableMap[list.name]}" valueType="${valueType}" valueSePass="true" mustInput="${mustInput}" onblur="validData('${list.name }');"ispost="true" />
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">确认${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input class="form-control"  type="password" id="${list.name }_se" name="${list.name }_se" value="${realTableMap[list.name]}" valueType="${valueType}" valueSePass="true" mustInput="${mustInput}" onblur="validData('${list.name }');"ispost="true" />
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
										<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
										<div class="col-md-8">
											<c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }">
												<select class='<c:if test="${list.mainTableColumn.isMust eq 1||(list.mainTableColumn.isUnique eq 1)||(list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null) }"> validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if><c:if test="${list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null }">,custom[<c:if test="${list.mainTableColumn.validDataType eq 5 }">ip</c:if><c:if test="${list.mainTableColumn.validDataType eq 1 }">chinese</c:if><c:if test="${list.mainTableColumn.validDataType eq 2 }">number</c:if><c:if test="${list.mainTableColumn.validDataType eq 3 }">letter</c:if><c:if test="${list.mainTableColumn.validDataType eq 7 }">mac</c:if>]</c:if><c:if test="${list.mainTableColumn.isUnique eq 1 }">,ajax[ajaxIsUnique]</c:if>] </c:if> form-control' name="${list.name }" id="${list.name }"  valueType="${valueType}" mustInput="${mustInput}" ispost="true" >
											   	<option value="" >请选择</option>
											   	<c:forEach var="map" items="${list.lists}">
											   		<option value="${map.id }" <c:if test="${map.id eq realTableMap[list.name]}">selected</c:if> >${map.name }</option>
											   	</c:forEach>
											    </select>
											</c:if>
											<c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">
												<select class='<c:if test="${list.mainTableColumn.isMust eq 1||(list.mainTableColumn.isUnique eq 1)||(list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null) }"> validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if><c:if test="${list.mainTableColumn.validDataType ne 0&&list.mainTableColumn.validDataType ne ''&&list.mainTableColumn.validDataType ne null }">,custom[<c:if test="${list.mainTableColumn.validDataType eq 5 }">ip</c:if><c:if test="${list.mainTableColumn.validDataType eq 1 }">chinese</c:if><c:if test="${list.mainTableColumn.validDataType eq 2 }">number</c:if><c:if test="${list.mainTableColumn.validDataType eq 3 }">letter</c:if><c:if test="${list.mainTableColumn.validDataType eq 7 }">mac</c:if>]</c:if><c:if test="${list.mainTableColumn.isUnique eq 1 }">,ajax[ajaxIsUnique]</c:if>] </c:if> form-control' name="${list.name }" id="${list.name }"  valueType="${valueType}" mustInput="${mustInput}" ispost="true" readonly="readonly" >
											   	<c:forEach var="map" items="${list.lists}">
											   		<c:if test="${map.id eq realTableMap[list.name]}"><option value="${map.id }" selected>${map.name }</option></c:if> 
											   	</c:forEach>
											    </select>
											</c:if>
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] lv_calendar form-control'   type="text"  runat="server" id="${list.name }" name="${list.name }" value="${fn:substring(realTableMap[list.name],0,19)}" size="18" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" </c:if>  valueType="${valueType}" mustInput="${mustInput}" ispost="true" readonly="readonly" />
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control lv_calendar_time'   type="text"  runat="server" id="${list.name }" name="${list.name }" value="${fn:substring(realTableMap[list.name],0,10)}" size="18" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" </c:if> valueType="${valueType}" mustInput="${mustInput}" ispost="true"  readonly="readonly" />
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control lv_calendar_time'   type="text"  runat="server" id="${list.name }" name="${list.name }" value="${fn:substring(realTableMap[list.name],0,10)}" size="18" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="WdatePicker({readOnly:true,dateFmt:'HH:mm'})" </c:if> valueType="${valueType}" mustInput="${mustInput}" ispost="true"  readonly="readonly" />
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control lv_calendar_time'   type="text"  runat="server" id="${list.name }" name="${list.name }" value="${fn:substring(realTableMap[list.name],0,7)}" size="18" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM'})" </c:if> valueType="${valueType}" mustInput="${mustInput}" ispost="true"  readonly="readonly" />
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<input  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control lv_calendar_time'   type="text"  runat="server" id="${list.name }" name="${list.name }" value="${fn:substring(realTableMap[list.name],0,4)}" size="18" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="WdatePicker({readOnly:true,dateFmt:'yyyy'})" </c:if> valueType="${valueType}" mustInput="${mustInput}" ispost="true"  readonly="readonly" />
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
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-8">
												<c:set var="tanchuvalue" value='${fn:split(realTableMap[list.name],",")}'></c:set>
												<div class="input-group userPicker">
													<input id="${list.name }" type="hidden" id="${list.name }" name="${list.name }" value="${realTableMap[list.name]}" ispost="true"/>
										   			<input  id="${list.name }_snb" class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control' id="${list.name }_sn" type="text"  runat="server" name="${list.name }_sn" value="${tanchuvalue[1]}" size="18" onclick="javascript:openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb');" valueType="${valueType}" mustInput="${mustInput}" ispost="true" readonly <c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if>/>
													<span class="input-group-addon add-on" <c:if test="${isupdate ne 1 or list.mainTableColumn.isUpdate ne 1 }"> onclick="openGroupWin1('${ctx}/default/management/security/maintable/gettree.do','${list.cname }','common_send_group','${list.mainTableColumn.columnid }','${list.mainTableColumn.isChecked }','${list.name }_snb') </c:if>"><i class="fa fa-search"></i></span>
												</div>
											</div>
										</div>
									</div>
							   </c:if>
								
							   <!--文本域  -->
							   <c:if test="${list.type eq 9}">
							     <c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row">
								  </c:if>

							   	  <c:set var="num" value="${num+2}"></c:set>
								  <div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
											<div class="col-md-10"> 
												<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
								   				<textarea  id="${list.name }" name="${list.name }" rows="${tablekey[1] }" cols="${tablekey[0] }" ispost="true" valueType="${valueType}" mustInput="${mustInput}" <c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if>  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] form-control' >${realTableMap[list.name]}</textarea>
												<span class="help-block">${list.mainTableColumn.bz}</span>
											</div>
										</div>
									</div>
							   </c:if>
			
								<!-- iframe -->
								<c:if test="${list.type eq 13}">
								   <c:set var="num" value="${num+1}"></c:set>
									<div class="col-md-6"> 
										<div class="form-group">
											<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
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
									 <div class="row">
							  </c:if>
						   	  <c:set var="num" value="${num+2}"></c:set>
							  <div class="col-md-12"> 
										<div class="form-group">
											<div class="col-md-12"> 
						   						<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
							    				<input id="fileconfid" type="hidden" name="${list.name}" value="${realTableMap[list.name]}" ispost="true"/>
												<input id="temppath" type="hidden" value="${list.mainTableColumn.tempPaths}"/>
						
												<div id="fujian"  style="display: block;width:100%;">  
												<table class="table_border" border="0" width="100%" cellspacing="0" cellpadding="0" style="background-color: #fff;">
												<tbody>
													<tr height="20" id="fileUpload" style="border-color: #f0f0f0; display: ''">
													<td nowrap="true">
													<div align="left" style="padding-left: 20px;">
														<table id="nopdf" style="display: block;">
															<tbody>
																<tr>
																	<td>
																		<form action="" style="" id="uploadForm" name="uploadForm">
																			<div class="form-group">
																				<label  class="">${list.cname}</label>
																					<div class="radio-list">
																						<label class="radio-inline">
																						<input type="radio" name="type" checked="checked" value="2" onclick="type1Click(this);"> 上传${list.cname}
																						</label>
																						<label class="radio-inline">
																						<input type="radio"  name="type" value="1" onclick="type1Click(this);" > 纸质${list.cname}
																						</label>
																					</div>
																			</div>
																		</form>
																	</td>
																</tr>
															</tbody>
														</table>
														<form class="uploadForm" action="upload" id="testupload"
															name="testupload" onsubmit= "return false;" enctype="multipart/form-data" method="post"
															target="upload_target">
															<input type="hidden" id="attach_gw_id" name="gw_id">
															<!--
									
															
															<input type="hidden" id="attach_entityFullName"
																name="entityFullName"
																value="com.css.miitoa.document.common.bo.SendAttBO">
															--><input type="hidden" id="type" name="type" value="1">
															<input type="hidden" id="type1" name="type1" value="2">
									                         <div id="title_add" style="display:none">
																	<div class="col-md-5"> 
																		<div class="form-group">
																		<label class="control-label col-md-4">标题</label>  
																		<div class="col-md-8">
																			<input size="10" type="text" id="file_title" name="file_title" class="form-control">
																		</div>
																		</div>
																	</div>
																	<div class="col-md-4"> 
																		<div class="form-group">
																		<label class="control-label col-md-4">份数</label>  
																		<div class="col-md-8">
																			<input size="10" type="text" id="file_size" name="file_size" class="form-control">
																		</div>
																		</div>
																	</div>
																	<div class="col-md-3">
																		<div class="form-group">
																		<input type="button" class="btn red" id="save_attach" onclick="addAttTitle()" value="添加" >
																		</div>
																	</div>
									                         </div>
									                         <div id="gw_upload" style="display:block">
									                            <input type="button"  id="upload_attch"  value="上传文件" class="btn red">
									                         </div>
														</form>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
						
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
										</div>
									</div>
									<script type="text/javascript">
										var ajaxupload = new AjaxUpload('#upload_attch', {
										action: '${ctx}/default/management/security/maintable/uploadFileAttach.do',
										name: 'uploadFile',
										autoSubmit: true,
										responseType: 'json',
										onSubmit: function(filename, ext) {
											wait();
											ajaxupload.setData({ temppath:$("#temppath").val(),fileconfid:$("#fileconfid").val()});
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
										onComplete: function(filename, data) { 
											switch(data.result){
											case 0:
												$("#fileconfid").val(data.mainTableAttachFile.confId);
												refresh_list();
												stop();
												break;
											case 1:
												break;
											}
										}
									});
									var fileid = $("#fileconfid").val();
									if(fileid!=null&&fileid!="")
										$("#attach_div").css("display","block");
									</script>
						   </c:if>				
							
							<!-- 14 列表属性 -->
							<c:if test="${list.type eq 14}">
								<c:if test="${num%2 eq 1}">
								     </div>
									 <div class="row">
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
											<div class="row">
												<input id="${list.name}parent" type="hidden" name="${list.name}" value="${realTableMap[list.name]}" ispost="true"/>
												<input id="${list.name}tablename" type="hidden" value="${list.mainTableColumn.tempPaths}"/>
												<div class="col-md-12" id="${list.name}table">

												</div>
											</div>
										</div>
									</div>   
								</div>
						   </c:if> 
							<!--ueeditor  -->
						   <c:if test="${list.type eq 15}">
						     <c:if test="${num%2 eq 1}">
							     </div>
								 <div class="row">
							  </c:if>

						   	  <c:set var="num" value="${num+2}"></c:set>
							  <div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
										<div class="col-md-10"> 
											<c:set var="tablekey" value='${fn:split(list.mainTableColumn.typeSql,"*")}'></c:set>
							   				<textarea  id="${list.name }" name="${list.name }"  <c:if test="${isupdate eq 1 and list.mainTableColumn.isUpdate eq 1 }">readonly="readonly"</c:if>  class='validate[<c:if test="${list.mainTableColumn.isMust eq 1 }">required</c:if>] ' >${realTableMap[list.name]}</textarea>
											<script type="text/javascript">
												window.UE.getEditor(${list.name},{
											           //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
											           toolbars:[[ 'undo', 'redo', '|','Bold', 'italic', 'underline','forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|','fontfamily', 'fontsize', '|', 'insertimage','map','gmap','wordimage']],
											           //focus时自动清空初始化时的内容
											           autoClearinitialContent:false,
											           //关闭字数统计
											           wordCount:false,
											           //关闭elementPath
											           elementPathEnabled:false,
											           //默认的编辑区域高度
											           initialFrameHeight:300
											           //更多其他参数，请参考ueditor.config.js中的配置项
											       })
											</script>
										</div>
									</div>
								</div>
						   </c:if>
						<!-- 地区选择 -->
						   <c:if test="${list.type eq 16}">
						   	  <c:set var="num" value="${num+1}"></c:set>
							  <div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-4">${list.cname}<c:if test="${list.mainTableColumn.isMust eq 1 }">（<font color="#ff0000" style="font-size: 8px;">必填</font>）</c:if>：</label>  
										<div class="col-md-8 picker" id="${list.name }"> 
											<div class="input-group">
												<input type="text" readonly="readonly" value="${realTableMap[list.name]}" name="${list.name }" class="form-control" />
												<span class="input-group-addon region-picker" data-remote="${ctx}/s/assets/plugins/chineserp-jquery-master/example" data-picked="${realTableMap[list.name]}" data-visible="5"><i class="fa fa-user"></i></span>
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
								<!-- 公告定制功能 -->
								<button type="button" onclick="return addObjectSave();"  class="btn green">提交</button>
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
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
		<img src="${ctx}/s/assets/img/ajax-modal-loading.gif" alt="" class="loading">
	</div>
	<script src="${ctx}/s/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>		
	<script src="${ctx}/s/assets/plugins/validation/jquery.validationEngine-en.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/validation/validationEngine.jquery.css"/>
	<script language="javascript" type="text/javascript" src="${ctx}/s/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#queryformtable").validationEngine({
            promptPosition:"topLeft", 
            showTypes:2,
			success:false
          });
	})
	function returnTableDetail(){
		  window.location.href="${ctx}/maintabledata/maintabledata_pagelist.izhbg?tableName=${maintable.tableName }";
		}
	 function addObjectSave(){
		 	var url="${ctx}/maintabledata/maintabledata_save.izhbg";
		 	$("#methodCall").val("addRealTable");
		 	$("#queryformtable").attr("action",url); 
		 	try{
		 		editor_a1.sync();
		 	}catch(e){}
		 	$("#queryformtable").submit();
		 	return true;
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

	function addAttTitle(){ 
	    var  fileconfid =  $('#fileconfid').val();
	    var title= $('#file_title').val();
	    var size= $('#file_size').val();
	    if($('#file_title').val().trim()==""){
	        alert("请输入标题");
	        return false;
	    }else{
	        if($('#file_title').val().trim().length>200){
	           alert("标题最多200个字！");
	           return false;
	        }
	    }
	    
	      $.post("${ctx}/default/management/security/maintable/addFileAttachTitle.do",{"title":title,"fileconfid":fileconfid,"filesize":size}, function(data) {
                     if (data.result == 0) {
                          $("#fileconfid").val(data.mainTableAttachFile.confId);
                          refresh_list();
                     } else {
                         alert(data.msg);
                     }

          }, "json"); 
	      $('#file_title').val("");
	      $('#file_size').val("");
	}

  function deleteAttach(attachId){
	  if(confirm("确定要删除该条记录吗？")){
		  $.post("${ctx}/default/management/security/maintable/deleteFile.do",{"attachId":attachId}, function(data) {
              if (data.result == 0) {
                   refresh_list();
              } else {
                  alert(data.msg);
              }

   			}, "json");
		  	return true;
		 }else return false;
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
	function expandTable(id){
	   var tablename = $("#"+id+"tablename").val();
	   var valid = $("#"+id+"parent").val();
	   if(valid=="")
	   {
		   valid = "  ";
		   }
	   url = "management/security/maintable/querylistattributepage.do?tableName="+tablename+"&"+id+"="+valid+"&linkcolumnname="+id;
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
	   url = "management/security/maintable/querylistattributepage.do?tableName="+tablename+"&"+id+"="+valid+"&linkcolumnname="+id+"&pageNo="+pageNo+"&pageSize="+pageSize;
	   $.post("${ctx}/default/"+url,function(data){
        	 $("#"+id+"table").html(data);//.initUI();
       });
	}
	function addObjectAttributeSave(formId,pageNo,pageSize){
		if(($("#"+formId).validationEngine('validate')+"")=="true"){
			var linkID = $("#linkcolumnname"+formId).val();
			$("#"+linkID).val($("#"+linkID+"parent").val());

			$.post("${ctx}/default/management/security/maintable/addRealTableAttribute.do",$("#"+formId).serialize(), function(data) {
	            if (data.result == 0) { 
	            	$("#"+linkID+"parent").val(data.linkcolumnname);
	            	expandPageTable(linkID,pageNo,pageSize);
	            } else {
	                alert(data.msg); 
	            }
			}, "json");
		}
	}
	function deleteInfo(realtableid,tableName,linkId,pageNo,pageSize){
		//href='${ctx}/default/management/security/maintable/removeRealTableo.do?realtableid=${resoult[maintable.keyColumnName]}&tableName=${maintable.tableName }'"
		if($("#confirmDelete").html())
		{
		}else{
			var str = '<div class="modal fade" id="confirmDelete" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">提示信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'确定要删除该条信息？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#queryformtable").append(str);
			
		}
		$("#cconfirmchange").unbind('click').bind('click',function(){
			deleteConfirm(realtableid,tableName,linkId,pageNo,pageSize); 
		});
		
		$("#confirmDelete").modal({
            backdrop: 'static',
            keyboard: false  
        }).modal('show'); 
	}
	function deleteConfirm(realtableid,tableName,linkId,pageNo,pageSize)
	{
		$.post("${ctx}/default/management/security/maintable/removeRealTableAttribute.do",{"realtableid":realtableid,"tableName":tableName}, function(data) {
			expandPageTable(linkId,pageNo,pageSize);
			$("#confirmDelete").modal().modal('hide'); 
		}, "json");
	}
	function modifyObject(realtableid,tableName,userid,opeid,id,pageNo,pageSize){
		 var tablename = $("#"+id+"tablename").val();
		   var valid = $("#"+id+"parent").val();
		   if(valid=="")
		   {
			   valid = "  ";
			   }
		   url = "management/security/maintable/querylistattributepage.do?tableName="+tablename+"&"+id+"="+valid+"&linkcolumnname="+id+"&realtableid="+realtableid+"&userid="+userid+"&opeid="+opeid+"&action=1"+"&pageNo="+pageNo+"&pageSize="+pageSize;
		   $.post("${ctx}/default/"+url,function(data){ 
	        	 $("#"+id+"table").html(data);//.initUI();
	       });
		 }
	 function regionInfo(r){
	      var list = [];
	      list.push('<li>ID = '+ r.i +'</li>');
	      list.push('<li>Name = '+ r.n +'</li>');
	      list.push('<li>Alias = '+ r.a +'</li>');
	      list.push('<li>Pinyin = '+ r.y +'</li>');
	      list.push('<li>Abbr = '+ r.b +'</li>');
	      list.push('<li>Postcode = '+ r.z +'</li>');
	      return '<ul>'+ list.join("\n") +'</ul>'
	    };
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>