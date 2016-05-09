<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/bootstrap-switch/static/stylesheets/bootstrap-switch-metro.css"/>
<style type="text/css">
	ul.ztree li a{width: 470px;}
</style>
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
				<h3 class="panel-title">角色授权功能列表【角色代码：${role.gnjsDm }角色名称：${role.gnjsMc }】</h3>
			</div>
			<div class="panel-body">
				<a href="javascript:;" onclick="javascript:window.form1.submit()" class="btn btn-sm default" ><i class="fa fa-rotate-right"></i>刷新</a>
				<a href="#treeDialog" data-toggle="modal" class="btn btn-sm default"  ><i class="fa fa-plus-square"></i>新增</a>
				<a href="javascript:rf_del()" class="btn btn-sm red" ><span class="glyphicon glyphicon-calendar"></span>删除</a>
				<a class="btn default btn-sm" href="#collapse-group" data-toggle="collapse" data-parent="#m-sidebar"><i class="m-icon-swapdown m-icon-blank"></i>查询</a>
				<a href="${ctx }/role/role-list.izhbg?appId=${parameterMap.currentAppId}" class="btn btn-sm default"  ><i class="fa fa-mail-reply-all"></i>返回</a>
				<div class="pull-right">
					每页显示
					<select class="m-wrap xsmall"> 
					    <option value="10">10</option> 
					    <option value="20">20</option>
					    <option value="50">50</option>
					 </select>
					条
				</div>
		
				<div id="collapse-group" class="collapse <c:if test="${(parameterMap.gnMc ne null&&parameterMap.gnMc ne '') || (parameterMap.gnzyDm ne null&&parameterMap.gnzyDm ne '') }">in</c:if> ">
					 <div class="panel panel-default" style="margin-bottom: 0px;"> 
						<div class="panel-body">
						 <form id="form1" name="form1" action="${ctx }/rolFun/rolefun_list.izhbg" method="post" class="form-horizontal" >
							<input type="hidden" name="jsDm" id="jsDm" value="${role.gnjsDm}"> 
							<input type="hidden" name="currentAppId" id="currentAppId" value="${parameterMap.currentAppId}"> 
							<div class="form-body">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5" >功能代码：</label>
											<div class="col-md-7">
												<input name="gnzyDm" id="gnzyDm"  class="form-control" type="text" value='${parameterMap.gnzyDm }'  />
											</div>
										</div>
									</div>
									<!--/span-->
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-5">功能名称：</label>
											<div class="col-md-7">
												<input name="gnMc" id="gnMc"  class="form-control" type="text" value='${parameterMap.gnMc }'  />
											</div>
										</div>
									</div>
									<!--/span-->
								</div>
								<!--/row-->
							</div>
						</form>
						</div>
						<div class="form-actions right" style="margin-top: 0px;">
							<button type="button" class="btn default" onclick="clearForm()">清空</button>
							<button type="button" onclick="javascript:document.form1.submit()" class="btn blue"><i class="fa fa-check"></i> 查询</button>
						</div>
					 </div>		    
			    </div>
			</div>
			<!-- Table -->
			 <table id="userGrid" class="table table-striped table-bordered table-hover dataTable" >
				<thead >
					<tr >
						<th width="50" style="text-align: center;" class="id">
							<input type="checkbox" name="checkAll" id="checkAll" onchange="toggleSelectedItems(this.checked)" />
						</th>
						<th class="sorting" name ="b.gnDm">功能代码</th>
						<th class="sorting" name ="b.gnMc">功能名称</th>
						<th class="sorting" name ="b.yxBj">有效</th>
						<th class="sorting" name ="b.gnXh">序号</th>
						<th class="sorting" name ="a.isCreate">创建</th>
						<th class="sorting" name ="a.isDelete">删除</th>
						<th class="sorting" name ="a.isUpdate">修改</th>
						<th class="sorting" name ="a.isRead">查看</th>
					</tr>
				</thead>
				
				<tbody >
					<c:forEach items="${page.result}" var="item">
						<tr id="${item.uuid}">
							<td style="text-align: center;" class="id">
								<input type="checkbox" name="checkdel" class="selectedItem a-check"  value="${item.uuid}"/></td>
							<td > ${item.gnzy.gnDm }
							</td>
							<td>${item.gnzy.gnMc}
							</td>
							<td>${item.gnzy.yxBj }<!-- dictNew.getDictItem('D_ROLETYPE',jsLx).itemName-->
							</td>
							<td>${item.gnzy.gnXh}<!-- ucUtil.getSystem(appId).appName-->
							</td>  
							<td>
								<div class="make-switch switch-mini myswitch" data-on="success" data-off="warning" data-on-label="是" data-off-label="否"> 
									<input type="checkbox"  ${item.isCreate==1?"checked='checked'":"''"}    class="toggle" uuid="${item.uuid}"  categery="isCreate" />
								</div>
							</td>  
							<td>
								<div class="make-switch switch-mini myswitch" data-on="success" data-off="warning" data-on-label="是" data-off-label="否">
									<input type="checkbox"  ${item.isDelete==1?"checked='checked'":"''"}  class="toggle" uuid="${item.uuid}" categery="isDelete" />
								</div>
							</td>  
							<td>
								<div class="make-switch switch-mini myswitch" data-on="success" data-off="warning" data-on-label="是" data-off-label="否">
									<input type="checkbox"  ${item.isUpdate==1?"checked='checked'":"''"}  class="toggle" uuid="${item.uuid}" categery="isUpdate" />
								</div>
							</td>  
							<td>
								<div class="make-switch switch-mini myswitch" data-on="success" data-off="warning" data-on-label="是" data-off-label="否">
									<input type="checkbox"  ${item.isRead==1?"checked='checked'":"''"}  class="toggle" uuid="${item.uuid}" categery="isRead" />
								</div>
							</td>  
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
			<div class="form-actions fluid" style="margin-top: 0px;">
			<div class="col-md-5">  
				<div class="dataTables_info" id="sample_3_info">共${page.totalCount}条记录 显示${page.start+1}到${page.start+page.pageSize}条记录</div>
			</div>
			<div class="col-md-7">
				<div class="dataTables_paginate paging_bootstrap  pull-right">    
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

		 <div class="modal fade" id="treeDialog" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">选择功能节点</h4>
					</div>
					<div class="modal-body">
						<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible1="1">  
							<ul id="rf_treeDemo" class="ztree"></ul> 
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn default" data-dismiss="modal">取消</button>
						<button type="button" onclick="rf_add()" class="btn blue">确定</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>


	



	</div>
	</div>
	</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/common.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/guser/js/ucenter.js"></script>  
<script type="text/javascript" src="${ctx}/s/assets/plugins/bootstrap-switch/static/js/bootstrap-switch.min.js"></script>  



<script type="text/javascript">
var config = {
		id: 'userGrid',
	    pageNo: ${page.pageNo},
	    pageSize: ${page.pageSize},
	    totalCount: ${page.totalCount},
	    resultSize: ${page.resultSize},
	    pageCount: ${page.pageCount},
	    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
	    order: '${page.order=="ASC"?"DESC":"ASC"}',
	    params: {
	        'gnzyDm':'${parameterMap.gnzyDm }',
	        'jsDm':'${role.gnjsDm}',
	        'gnMc':'${parameterMap.gnMc }'
	    },
		selectedItemClass: 'selectedItem',
		gridFormId: 'form1' 
		};

var rf_setting = {	
		check: {
			enable: true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		async: {
			enable: true,
			url:"${ctx }/rolFun/getRoleFuncTree.izhbg", 
			autoParam:["id=id"],
			otherParam: ["jsDm", '${role.gnjsDm}'],
			type:"post"
		},
		callback : {
			
		},
		view: {
			addDiyDom: addDiyDom
		}
	};
function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if (treeNode.tId == "rf_treeDemo_1") {
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		var editStr = "<span style='margin-right:100px;float:right;display:block;'>读取&nbsp;&nbsp;&nbsp;&nbsp;创建&nbsp;&nbsp;&nbsp;&nbsp;修改&nbsp;&nbsp;&nbsp;&nbsp;删除&nbsp;&nbsp;&nbsp;&nbsp;全部</span>";
		aObj.append(editStr);
	}else{
		if ($("#diyBtn_"+treeNode.id).length>0) return;
		var read;
		if(treeNode.chkDisabled){
			read = "disabled='disabled'";
		}
		var editStr = "<span style='margin-right:100px;float:right;display:block;'>";
					
					if(treeNode.isRead&&treeNode.isRead==1)
					{
						editStr+="<input type='checkbox' class='quanxianchk' checked='checked' "+read+" name='"+treeNode.id+"_isRead' id='"+treeNode.id+"_isRead' value=1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}else{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+" name='"+treeNode.id+"_isRead' id='"+treeNode.id+"_isRead' value=0>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					
					if(treeNode.isCreate&&treeNode.isCreate==1)
					{
						editStr+="<input type='checkbox' class='quanxianchk'  checked='checked' "+read+" name='"+treeNode.id+"_isCreate' id='"+treeNode.id+"_isCreate' value=1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}else{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+" name='"+treeNode.id+"_isCreate' id='"+treeNode.id+"_isCreate' value=0>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					
					if(treeNode.isUpdate&&treeNode.isUpdate==1)
					{
						editStr+="<input type='checkbox' class='quanxianchk'  checked='checked' "+read+" name='"+treeNode.id+"_isUpdate' id='"+treeNode.id+"_isUpdate' value=1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}else{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+" name='"+treeNode.id+"_isUpdate' id='"+treeNode.id+"_isUpdate' value=0>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					
					if(treeNode.isDelete&&treeNode.isDelete==1)
					{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+"  checked='checked' name='"+treeNode.id+"_isDelete' id='"+treeNode.id+"_isDelete' value=1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}else{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+" name='"+treeNode.id+"_isDelete' id='"+treeNode.id+"_isDelete' value=0>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					
					if(treeNode.isAll&&treeNode.isAll==1)
					{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+"  checked='checked' name='"+treeNode.id+"_isAll' id='"+treeNode.id+"_isAll' value=1>";
					}else{
						editStr+="<input type='checkbox' class='quanxianchk' "+read+" name='"+treeNode.id+"_isAll' id='"+treeNode.id+"_isAll' value=0>";
					}
					
					editStr+="</span>";
		aObj.append(editStr);
	}
};
	var rf_tree;
function rf_add(){
	var nodes = rf_tree.getChangeCheckedNodes();
	var pars = 'jsDm=${role.gnjsDm}';
	for(var i in nodes){
		pars += "&checkdel=" + nodes[i].id;
		pars +="&isRead="+$("#"+nodes[i].id+"_isRead").val();
		pars +="&isCreate="+$("#"+nodes[i].id+"_isCreate").val();
		pars +="&isUpdate="+$("#"+nodes[i].id+"_isUpdate").val();
		pars +="&isDelete="+$("#"+nodes[i].id+"_isDelete").val();
		pars +="&isAll="+$("#"+nodes[i].id+"_isAll").val();
	}
	$.ajax({
		type: "POST",
	    url: "${ctx }/rolFun/addRoleFunc.izhbg",
	    data: pars,
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}


var table;

$(function() {
	table = new Table(config);
    table.configPagination('.dataTables_paginate');
    table.configPageInfo('.dataTables_info'); 
    table.configPageSize('.m-wrap'); 


	var rf_zNodes =${result};
	rf_tree = $.fn.zTree.init($("#rf_treeDemo"), rf_setting, rf_zNodes);

	$(".quanxianchk").click(function(){
			if($(this).val()==0){
					$(this).val("1");
				}else{
					$(this).val("0");
				}
		});

	$(".myswitch").on('switch-change', function (e, data) {
	    var $el = $(data.el);
	    var uuid = $($el).attr("uuid");
	    var categery = $($el).attr("categery");

	    $.ajax({
			type: "POST",
		    url: "${ctx }/rolFun/changState.izhbg",
		    data: "uuid="+uuid+"&categery="+categery,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
			});
	});

});

function deleteInfo(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/rolFun/delRoleFunc.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function changeUserState(){
	$.ajax({
		type: "POST",
	    url: "${ctx }/role/updRoleStatus.izhbg",
	    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
	    dataType:"html",
		cache: false,
		success: function(data){
	    	$("#form1").submit();
		}
		});
}
function clearForm(){
	$(':input','#form1')  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected'); 
}
function changeState(gnzyDm,type){
	alert(gnzyDm);
}
</script>
</html>