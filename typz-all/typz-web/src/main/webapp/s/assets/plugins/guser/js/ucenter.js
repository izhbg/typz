


/*照片剪裁 begin*/
var dialog=null;
var xywh='';
var userImg_a={
	};
function u_setCoords(c){
	userImg_a.rx=c.x;
	userImg_a.ry=c.y;
	userImg_a.rw=c.w;
	userImg_a.rh=c.h;
}
function u_clearCoords(){
 	userImg_a.rx='';
	userImg_a.ry='';
	userImg_a.rw='';
	userImg_a.rh='';
}
function u_photoInit(){
	var js_html='<div class="info_tab02" id="u_box" style="padding-top:15px;">';
		js_html+='<table><tr><td><img src="'+$('#userImg')[0].alt+'.jpg?sessionId='+new Date().valueOf()+'" id="target" onError="logo_user(this);dialog.close();$.dialog.alert(\'请先上传图片！\');"/></td>';
    js_html+='</tr></table></div>';
	dialog = $.dialog({
    id:'userImgdia', 
    title:'照片调整',
    content: js_html,
    lock:true,
		fixed:true,
 		max: false,
    min: false,
    //top:50,
    init: function(){
      $('#target').Jcrop({
				onChange: 	u_setCoords,
      	onSelect: 	u_setCoords,
      	onRelease:	u_clearCoords,
      	setSelect: 	[ 0, 0, 50, 50 ], 
      	minSize: 		[ 50, 50 ],   	
        aspectRatio: 1
        }); 
      userImg_a.rx=0;
			userImg_a.ry=0;
			userImg_a.rw=50;
			userImg_a.rh=50;
			if(Get_IE_Version()==8) alert("请选择区域调整照片！");
    },
    ok: function(){
    		u_img_submit();
        return false;
    },
    okVal:'保存',
    cancelVal: '关闭',
    cancel: true
	});
	
}
function u_img_submit(){
	try{
		var toUrl='admin/cropSysImg.action?yyId='+$("#dgform #yyId").val();
		if('x'+userImg_a.rx=='x') {
			$.dialog.alert('请先在图片上选择剪裁区域！');
			return;
		}
		userImg_a.tSessionId=new Date().valueOf();
		var pars=$.param(userImg_a);
		$.post(toUrl,pars,function(data){
			switch(data.result){
				case 0:
					$.dialog.tips('图片剪裁成功！',1,'success.gif',function(){u_refreshImg();});
					$.dialog({id:"userImgdia"}).close();
					break;
				default:
						$.dialog.alert(data.msg);
					break;
			}
		},'json');
	}catch(ex){}	
}

function u_initUpload(){
	var name = $("#dgform #yyId").val();
	new AjaxUpload('#upload_button', {
		action: 'admin/uploadSysImg.action',
		name: 'uploadFile',
		data: {
			"yyId":name
		},
		autoSubmit: true,
		responseType: 'json',
		onSubmit: function(filename, ext) {
			if (!(ext && /^(jpg|jpeg)$/.test(ext))) {
				alert('未允许上传的文件格式!');
				return false;
			}
			$.dialog.tips('照片上传中，请稍候...',600,'loading.gif');
		},
		onComplete: function(filename, data) {
			switch(data.result){
				case 0:
					$.dialog.tips('文件上传成功，如果照片没有更新请刷新一下页面！',1,'success.gif',function(){u_refreshImg();});
					break;
				case 1:
					$.dialog.tips('文件上传失败！',0);
					$.dialog.alert(data.msg);
					break;
			}
		}
	});
}
function u_refreshImg(){
	setTimeout(function(){
		var pre = $('#dgform #userImg')[0].alt, uImg;
		if(pre.charAt(pre.length-1) == '/')
			uImg = pre + $("#dgform #yyId").val() +'.jpg.jpg?sessionId='+new Date().valueOf();
		else
			uImg = pre +'.jpg.jpg?sessionId='+new Date().valueOf();
		$('#dgform img.user-imgstyle').attr('src',uImg);
	},500);
}
/*照片剪裁 end*/
function sa_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.此系统将不能访问选中API。<br/>你确定要删除关联吗？', function(){
		$.post("admin/delSysApi.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}
function sso_addorupd(id){
	var action;
	if(id)
		action="admin/updSso.action";
	else
		action="admin/addSso.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
		case 0:
			$.dialog({id:"dialog1"}).close();
			$.dialog.tip(data.msg);
			navTab.getCurrentPanel().find("#form1").submit();
			break;
		case 1:
			$.dialog.alert(data.msg);
			break;
		}
	},'json');
}
function sso_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.被删除的sso不可找回。<br/>你确定要删除这些sso吗？', function(){
		$.post("admin/delSso.action", pars, function(data){
			switch(data.result){
			case 0:
				$.dialog.tip(data.msg);
				for (var i in pars) {
					if(pars[i].name=='checkdel')
						$tab.find("#" + pars[i].value).remove();
				}
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
			}
		},"json");
	}, function(){	    
	});
	
}
function api_addorupd(id){
	var action;
	if(id)
		action="admin/updApi.action";
	else
		action="admin/addApi.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function api_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.被删除的api不可找回。<br/>你确定要删除这些api吗？', function(){
		$.post("admin/delApi.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}
function param_addorupd(id){
	var action;
	if(id)
		action="admin/updParamAction.action";
	else
		action="admin/addParamAction.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function param_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('你确定要删除这些参数吗？', function(){
		$.post("admin/delParamAction.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}


function uo_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.此用户将不再属于选中的机构。<br/>你确定要删除关联吗？', function(){
		$.post("admin/delGUserOrgan.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}

function func_addorupd(id){
	var action;
	if(id)
		action="admin/updFunc.action";
	else
		action="admin/addFunc.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}


function func_get(gnDm){
	var $tab = navTab.getCurrentPanel();
	gnDm = gnDm? gnDm : "";
	var pars = "gnDm="+ gnDm +"&sjgnDm=" + $tab.find("#sjgnDm").val() + "&appId=" + $tab.find("#appId").val();
	dialog = $.dialog({ 
		id:"dialog1",
		title:"编辑功能",
	    lock: true, 
	    max: false, 
	    min: false 
	});
	$.ajax({ 
	    url:"admin/getFunc.action?" + pars, 
	    success:function(data){ 
	        dialog.content(data); 
	    }, 
	    cache:false 
	});
}
function sys_update(type){
		if(confirmIsSelectItems()){
			var str = '<div class="modal fade" id="confirmChangemm" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">确认信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'你确定要重置吗？'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" id="cconfirmchangemm" >确定</button>'
				+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
			$("#cconfirmchangemm").click(function(){
				changeState(type); 
			});
			$("#confirmChangemm").modal({ 
		        backdrop: 'static',  
		        keyboard: false  
		    }).modal('show');

		}
	}
function ou_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.此机构将不再拥有选中的用户。<br/>你确定要删除关联吗？'
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
}
function pu_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.此岗位将不再赋予选中的用户。<br/>你确定要删除关联吗？'
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
}
function up_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.此用户将不再拥有选中的岗位。<br/>你确定要删除关联吗？'
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
}
function ur_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.此用户将不再拥有选中的角色。<br/>你确定要删除关联吗？'
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
}
function rf_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.此角色将不能访问选中功能。<br/>你确定要删除关联吗？'
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
}

function func_updflag(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirmChangeState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要重置有效标记吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmchange").click(function(){
			changeUserState(); 
		});
		$("#confirmChangeState").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');

	}
}
function func_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.下级功能将会一并删除。<br/>2.与删除功能相关联的内容将不可见。<br/>3.被删除的功能不可找回。<br/>你确定要删除这些功能吗？'
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
}
function role_updflag(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirmChangeState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要重置有效标记吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmchange").click(function(){
			changeUserState(); 
		});
		$("#confirmChangeState").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');

	}
}
function role_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除角色相关联的内容将不可见。<br/>2.被删除的角色不可找回。<br/>你确定要删除这些角色吗？'
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
}
function resource_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除资源相关联的内容将不可见。<br/>2.被删除的资源不可找回。<br/>你确定要删除这些资源吗？'
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
}
function authority_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除权限相关联的内容将不可见。<br/>2.被删除的权限不可找回。<br/>你确定要删除这些权限吗？'
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
}
function org_del2(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要删除这些机构吗？'
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

}
function sys_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除系统相关联的内容将不可见。<br/>2.被删除的系统不可找回。<br/>你确定要删除这些系统吗？'
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
}
function org_updflag(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirmChangeState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要重置有效标记吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmchange").click(function(){
			changeUserState(); 
		});
		$("#confirmChangeState").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');

	}
}
function post_addorupd(id){
	var action;
	if(id)
		action="admin/updPost.action";
	else
		action="admin/addPost.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}

function sys_updflag(flag){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = "flag="+flag + "&" + $tab.find("#form1").serialize();
	$.dialog.confirm('你确定要重置状态吗？', function(){
		$.post("admin/updSystemStatus.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					navTab.getCurrentPanel().find("#form1").submit();
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});
}
function sys_addorupd(id){
//	if($("#dgform").find("#bz").val().length>100){
//		$.dialog.alert("备注不能超过100个字符。");
//		return;
//	}
	var action;
	if(id)
		action="admin/updSystem.action";
	else
		action="admin/addSystem.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog.tip(data.msg);
				if(action == "admin/addSystem.action") {
					$("#dgform #upload_div_pre").css("display","none");
					$("#dgform #upload_div").css("display","block");
					$("#dgform #yyId").val(data.info);
					u_initUpload();
					$.dialog({id:"dialog1"}).close();
					navTab.getCurrentPanel().find("#form1").submit();
				} else {
					$.dialog({id:"dialog1"}).close();
					navTab.getCurrentPanel().find("#form1").submit();
				}
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}


function org_get(jgId){
	var $tab = navTab.getCurrentPanel();
	jgId = jgId? jgId : "";
	var pars = "jgId="+ jgId +"&sjjgId=" + $tab.find("#sjjgId").val();
	dialog = $.dialog({ 
		id:"dialog1",
		title:"编辑机构",
	    lock: true, 
	    max: false, 
	    min: false 
	});
	$.ajax({ 
	    url:"admin/getOrganAction.action?" + pars, 
	    success:function(data){ 
	        dialog.content(data); 
	    }, 
	    cache:false 
	});
}
function org_addorupd(id){
	var action;
	if(id)
		action="admin/updOrganAction.action";
	else
		action="admin/addOrganAction.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function org_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.下级机构将会一并删除。<br/>2.与删除机构相关联的内容将不可见。<br/>3.被删除的机构不可找回。<br/>你确定要删除这些机构吗？', function(){
		$.post("admin/delOrganAction.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}



function user_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.与删除用户相关联的内容将不可见。<br/>2.被删除的用户不可找回。<br/>你确定要删除这些用户吗？', function(){
		$.post("admin/delGUser.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}
function post_del(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除岗位相关联的内容将不可见。<br/>2.被删除的岗位不可找回。<br/>你确定要删除这些岗位吗？'
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
}
function user_updflag(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirmChangeState" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要更改这些用户的有效标记吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmchange").click(function(){
			changeUserState(); 
		});
		$("#confirmChangeState").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');

	}
	
}
function confirmIsSelectItems(){
	if(!checkSelect('checkdel')){
		if($("#basic").html())
		{
			
		}else{
			var str = '<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">提示信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'请选择你要操作的记录。'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" data-dismiss="modal">确定</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#form1").append(str);
		}
		$("#basic").modal({
            backdrop: 'static',  
            keyboard: false  
        }).modal('show'); 
		
		
		return false;
	}else{
		return true;
	}
	
}
function confirmTableIsSelectItems(){
	if(!checkSelect('realtableid')){
		if($("#basic").html())
		{
			
		}else{
			var str = '<div class="modal fade" id="basic" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+'<div class="modal-dialog">'
				+'<div class="modal-content">'
				+'<div class="modal-header">'
				+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+'<h4 class="modal-title">提示信息</h4>'
				+'</div>'
				+'<div class="modal-body">'
				+'请选择你要操作的记录。'
				+'</div>'
				+'<div class="modal-footer">'
				+'<button type="button" class="btn blue" data-dismiss="modal">确定</button>'
				+'</div>'
				+'</div>'
				+'</div>'
				+'</div>';
			
			$("#searchForm").append(str);
		}
		$("#basic").modal({
			backdrop: 'static',  
			keyboard: false  
		}).modal('show'); 
		
		
		return false;
	}else{
		return true;
	}
	
}
function user_del2(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'此操作将有如下结果：<br/>1.与删除用户相关联的内容将不可见。<br/>2.被删除的用户不可找回。<br/>你确定要删除这些用户吗？'
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
}
function user_del3(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirminfo2" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要从组织中移除这些用户吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmDele2" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmDele2").click(function(){
			deleteInfo2(); 
		});
		$("#confirminfo2").modal({ 
			backdrop: 'static',  
			keyboard: false  
		}).modal('show');
		
	}
}
function user_updpd(){
	if(confirmIsSelectItems()){
		var str = '<div class="modal fade" id="confirmresetMm" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
			+'<div class="modal-dialog">'
			+'<div class="modal-content">'
			+'<div class="modal-header">'
			+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
			+'<h4 class="modal-title">确认信息</h4>'
			+'</div>'
			+'<div class="modal-body">'
			+'你确定要充值这些用户吗为初始密码（123456）吗？'
			+'</div>'
			+'<div class="modal-footer">'
			+'<button type="button" class="btn blue" id="cconfirmReset" >确定</button>'
			+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
			+'</div>'
			+'</div>'
			+'</div>'
			+'</div>';
		
		$("#form1").append(str);
		$("#cconfirmReset").click(function(){
			resetMm(); 
		});
		$("#confirmresetMm").modal({ 
	        backdrop: 'static',  
	        keyboard: false  
	    }).modal('show');

	}
}
function dict_addorupd(code){
	var action;
	if(code)
		action="admin/updDictAction.action";
	else
		action="admin/addDictAction.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function dict_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.所含字典项将会一并删除。<br/>2.被删除的字典不可找回。<br/>你确定要删除这些字典吗？', function(){
		$.post("admin/delDictAction.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}
function dicti_addorupd(id){
	var action;
	if(id)
		action="admin/updDictItemAction.action";
	else
		action="admin/addDictItemAction.action";
	var pars = $("#dgform").serialize();
	$.post(action,pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function dicti_del(){
	if(!checkSelect('checkdel')){
		$.dialog.tip('请选中！');
		return;
	}
	var $tab = navTab.getCurrentPanel();
	var pars = $tab.find("#form1").serializeArray();
	$.dialog.confirm('此操作将有如下结果：<br/>1.被删除的字典项不可找回。<br/>你确定要删除这些字典项吗？', function(){
		$.post("admin/delDictItemAction.action", pars, function(data){
			switch(data.result){
				case 0:
					$.dialog.tip(data.msg);
					for (var i in pars) {
						if(pars[i].name=='checkdel')
							$tab.find("#" + pars[i].value).remove();
					}
					break;
				case 1:
					$.dialog.alert(data.msg);
					break;
			}
		},"json");
	}, function(){	    
	});

}