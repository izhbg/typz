<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<input id="tabIDIDID" name="tabIDIDID" type="hidden" value="${tabIDIDID }" ispost="true"/>
<input id="ischecked" name="ischecked" type="hidden" value="${ischecked }" ispost="true"/>
<input id="divid" name="divid" type="hidden" value="${divid }" ispost="true"/>
<div class="clearfix"></div>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">选择</h4>
		</div>
		<div class="modal-body">
			<div class="scroller" style="height: 300px;overflow-y: scroll;overflow-x:hidden;" data-always-visible="1" data-rail-visible1="1">  
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">取消</button>
			<button type="button" class="btn blue" onclick="selectOrgTreeCallBack()">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
<script>
	 var signsetting = {	
		data: {
			key: {
				 name:"text",
				 title:"text"
			},
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pid"
			}
		},
		callback: {
			onClick:function(event, treeId, treeNode) {
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
              	treeObj.checkNode(treeNode, true, true);
			},
			beforeCheck:function(treeId, treeNode){
				return true;
			}
		},
		check:{
			enable:true,
			chkStyle:$("#ischecked").val(), 
			radioType:"all",
			chkboxType: {"Y":"p","N":"s"}
		}
	};
	
	function selectOrgTreeCallBack(){
	    var names = "";
		var caps = "";
		var id = "";
	    var c= ztree.getCheckedNodes(true);
		for(var i in c){
		    if(c[i].pid!=null){
			   names +=c[i].text+">";
			   id = c[i].id;
			}
		}
		 if($.trim(names)==""){
		  	alert("请选择!");
		    return false;
		 }else{
			 names = names.substring(0,names.length-1);
			 }
		 var idd = $("#tabIDIDID").val();
		 if(idd!=null&&idd!=""){
			 idd = idd.substring(0,idd.length-4);
			 }
		 $("#"+$("#tabIDIDID").val()).val(names);
		 $("#"+idd).val(id+","+names);
		 var $modal = $("#"+$("#divid").val());
		 $modal.modal("hide");
	
	}
	
	var zNodes =${result}
	var ztree=$.fn.zTree.init($("#treeDemo"), signsetting, zNodes);
</script>