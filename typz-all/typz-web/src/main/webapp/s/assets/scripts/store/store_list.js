var StoreList = function(){
	
	var delStoreItem = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'此操作将有如下结果：<br/>1.删除店铺后不可找回。<br/>2.被删除的店铺将不在展示<br/>你确定要删除这些商品吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmDele" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
			$("#cconfirmDele").click(function(){
				deleteInfo(); 
			});
			$("#confirminfo").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var deleteInfo = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/store/store_delete.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
			});
	}
	var changePage = function(type){
		$("#form1").find("#state").val(type);
		$("#form1").submit();
	};
	var recoverState = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo_recover" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'确定恢复这些产品吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmDele_recover" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
			$("#cconfirmDele_recover").click(function(){
				recover(); 
			});
			$("#confirminfo_recover").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var recover = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/goods/goods_recover.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		 });
	};
	var confirmPass = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo_upState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'确定审核通过这些店铺吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmDele_upState" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
			$("#cconfirmDele_upState").click(function(){
				passStore(); 
			});
			$("#confirminfo_upState").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var passStore = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/store/store_batchPassStore.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		 });
	};
	var confirmNoPass = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo_downState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'确定下架这些产品吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmDele_downState" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
			$("#cconfirmDele_downState").click(function(){
				noPassStore(); 
			});
			$("#confirminfo_downState").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var noPassStore = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/store/store_batchNoPassStore.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		 });
	};
	return {
		delStoreItem:function(){
			delStoreItem();
		},
		changePage:function(type){
			changePage(type);
		},
		recoverState:function(){
			recoverState();
		},
		confirmPass:function(){
			confirmPass();
		},
		confirmNoPass:function(){
			confirmNoPass();
		}
	}
	
}();