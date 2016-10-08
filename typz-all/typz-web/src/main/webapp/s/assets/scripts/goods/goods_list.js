var GoodsList = function(){
	
	var delGoodsItem = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'此操作将有如下结果：<br/>1.删除商品后不可找回。<br/>2.被删除的商品将不在店铺里展示<br/>你确定要删除这些商品吗？'
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
		    url: $("#contentpath").val()+"/goods/goods_delete.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
			});
	}
	var changePage = function(type){
		if(type==1){
			$("#form1").find("#status").val(1);
			$("#form1").find("#delStatus").val(1);
			$("#form1").submit();
		}else if(type==2){
			$("#form1").find("#status").val(-1);
			$("#form1").find("#delStatus").val(1);
			$("#form1").submit();
		}else{
			$("#form1").find("#status").val(-1);
			$("#form1").find("#delStatus").val(-1);
			$("#form1").submit();
		}
			
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
	var upState = function(){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirminfo_upState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'确定上架这些产品吗？'
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
				upGoods(); 
			});
			$("#confirminfo_upState").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var upGoods = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/goods/goods_batchUpGoods.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		 });
	};
	var downState = function(){
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
				downGoods(); 
			});
			$("#confirminfo_downState").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	};
	var downGoods = function(){
		var pars = $("#form1").serialize()+"&"+$("input:checkbox[name=checkdel]:checked").serialize();
		$.ajax({
			type: "POST",
		    url: $("#contentpath").val()+"/goods/goods_batchUnderGoods.izhbg",
		    data: pars,
		    dataType:"html",
			cache: false,
			success: function(data){
		    	$("#form1").submit();
			}
		 });
	};
	var setTag=function(id){
		if(confirmIsSelectItems()){
			if($("#confirminfoxhinput")){
				var str = '<div class="modal fade" id="confirminfoxhinput" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
					+'<div class="modal-dialog">'
					+'<div class="modal-content">'
					+'<div class="modal-header">'
					+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
					+'<h4 class="modal-title">序号</h4>'
					+'</div>'
					+'<div class="modal-body">'
					+'<div class="form-group">'
					+' <label class="col-md-3 control-label">序号：</label>'
					+' <div class="col-md-4">'
					+' 	<input name="xg" id="xh" class="form-control"  maxlength=30 />'
					+' 	<span class="help-block"></span>'
					+' </div>'
					+' </div>'
					+'</div>'
					+'<div class="modal-footer">'
					+'<button type="button" class="btn blue" id="cconfirmxhinput" >确定</button>'
					+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
					+'</div>'
					+'</div>'
					+'</div>'
					+'</div>';
				
				$("#form1").append(str);
			}
			
			$("#cconfirmxhinput").click(function(){
				
				$.ajax({
					type: "POST",
				    url: $("#contentpath").val()+"/goods/goods-setTag.izhbg",
				    data: $("input:checkbox[name=checkdel]:checked").serialize()+"&tagId="+id+"&xh="+$("#xh").val(),
				    dataType:"html",
					cache: false,
					success: function(data){
				    	$("#form1").submit();
					}
					});
				
			});
			$("#confirminfoxhinput").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');
			
			
			
		}
	}
	return {
		delGoodsItem:function(){
			delGoodsItem();
		},
		changePage:function(type){
			changePage(type);
		},
		recoverState:function(){
			recoverState();
		},
		upState:function(){
			upState();
		},
		downState:function(){
			downState();
		},
		setTag:function(id){
			setTag(id);
		}
	}
	
}();