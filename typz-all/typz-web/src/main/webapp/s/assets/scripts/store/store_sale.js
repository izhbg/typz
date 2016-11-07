var datatable;
var StoreSale = function(){
	
	var datatableInit = function(){
		if(datatable==null){
			datatable=$('#customerInfo').DataTable({
		         "processing": true,
		         "serverSide": true,
		         "autoWidth": true,
		         "lengthChange": false,
		         "pageLength": 4,
				 "sAjaxSource": $("#ctx").val()+"/store/goods-sale-goodsList.izhbg",
				 "language": {
				            'lengthMenu': '每页显示 _MENU_ 记录', 
				 			'zeroRecords': '没有数据 - 抱歉', 
				            'info': ' 第_PAGE_ 页/共 _PAGES_页', 
				            'infoEmpty': '没有符合条件的记录', 
				 			'search': '查找', 
				          	'infoFiltered': '(从  _MAX_ 条记录中过滤)', 
				 			'paginate': { "first":  "首页 ", "last": "末页", "next": "下一页","previous": "上一页"} 
				        	},
		        "ajax" : "goods-sale-goodsList.izhbg",
		        "columns":[
							{ "data": null},
							{ "data": "name", 'sClass':'left'},
							{ "data": "aliasName", 'sClass':'left'}
		                  ],
		        "columnDefs":[
		                      {
		                    	  "targets":[0],
		                    	  "data":"id",
		                    	  "render":function(data,type,full){
		                    		  return "<input type='checkbox' id='ele_"+data.id+"' onclick='StoreSale.itemSelect(this)' class='selectedItem' title='"+data.name+"' value='"+data.id+"'>";
		                    	  }
		                      }
		                      ]
		            
		    });
		}
	};
	var itemSelect = function(e){
		if (e.checked) {
			var html = '&nbsp;<span class="label label-default" id="' + e.value + '" title="' +  e.title + '">' +  e.title + '<i class="glyphicon glyphicon-remove" style="cursor:pointer;" onclick="StoreSale.removeItem(this)"></i></span>';
			$('#customerInfo_reslut').append(html);
		} else {
			$('#customerInfo_reslut #' + e.value).remove();
		}
	};
	var removeItem = function(e){
		var id = e.parentElement.id;
		$('#ele_' + id).prop('checked', false);
		e.parentElement.remove();
	};
	var showDialog = function (){
		$("#treeDialog").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');
		datatableInit();
		
	 };
	 var deleteInfo=function(){
		$.ajax({
			type: "POST",
		    url: $("#ctx").val()+"/store/goods-sale-delete.izhbg",
		    data: $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize(),
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		});
	 };
	 var clearForm = function(){
			$(':input','#form1')  
			 .not(':button, :submit, :reset, :hidden')  
			 .val('')  
			 .removeAttr('checked')  
			 .removeAttr('selected'); 
	 };
	 var addSaleGoods = function(){
		 var ids="";
		 $("#customerInfo_reslut span").each(function(e){
			 ids = ids+ "&checkdel="+$(this).attr("id");
		 });
		 if(ids==null||ids==undefined){
			 alert("请最少选择一个授权产品");
		 }else{
			 ids = "storeId="+$("#storeId").val()+ids;
		 }
		 $.ajax({
				type: "POST",
			    url: $("#ctx").val()+"/store/goods-sale-add.izhbg",
			    data: ids,
			    dataType:"html",
				cache: false,
				success: function(data){
			    	$("#form1").submit();
				}
			});
	 };
	 var delconfirm = function(){
		 if(confirmIsSelectItems()){
				var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
					+'<div class="modal-dialog">'
					+'<div class="modal-content">'
					+'<div class="modal-header">'
					+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
					+'<h4 class="modal-title">确认信息</h4>'
					+'</div>'
					+'<div class="modal-body">'
					+'此操作将有如下结果：<br/>你确定要删除授权产品吗？'
					+'</div>'
					+'<div class="modal-footer">'
					+'<button type="button" class="btn blue" id="cconfirmDele" >确定</button>'
					+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>';
				if($("#confirminfo").length<=0){
					$("#form1").append(str);
				}
				$("#cconfirmDele").click(function(){
					deleteInfo(); 
				});
				$("#confirminfo").modal({ 
			        backdrop: 'static',  
			        keyboard: false  
			    }).modal('show');

			}
	 };
	 return {
		 showDialog:function(){
			 showDialog();
		 },
		 deleteInfo:function(){
			 deleteInfo();
		 },
		 clearForm:function(){
			 clearForm();
		 },
		 itemSelect:function(e){
			 itemSelect(e);
		 },
		 removeItem:function(e){
			 removeItem(e);
		 },
		 addSaleGoods:function(){
			 addSaleGoods();
		 },
		 delconfirm:function(){
			 delconfirm();
		 }
		 
	 }
	
}();
