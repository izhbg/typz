
$.validator.setDefaults({
  submitHandler: function(form) {
	  if($(form).find(".has-error").length==0)
		  form.submit(); 
	 }
});

$().ready(function() {
	//表单验证
	if($("#queryformtable").validate)
	{
		 $("#queryformtable").validate({
	    	 errorElement: 'span', //default input error message container
	         errorClass: 'help-block', // default input error message class
	         focusInvalid: false, // do not focus the last invalid input
	         ignore: "",
	    	errorPlacement: function (error, element) { // render error placement for each input type
	             if (element.parent(".input-group").size() > 0) {
	                 error.insertAfter(element.parent(".input-group"));
	             } else if (element.attr("data-error-container")) { 
	                 error.appendTo(element.attr("data-error-container"));
	             } else if (element.parents('.radio-list').size() > 0) { 
	                 error.appendTo(element.parents('.radio-list').attr("data-error-container"));
	             } else if (element.parents('.radio-inline').size() > 0) { 
	                 error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
	             } else if (element.parents('.checkbox-list').size() > 0) {
	                 error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
	             } else if (element.parents('.checkbox-inline').size() > 0) { 
	                 error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
	             } else {
	                 error.insertAfter(element); // for other inputs, just perform default behavior
	             }
	         },
	         highlight: function (element) { // hightlight error inputs
	            $(element)
	                 .closest('.myform-input').addClass('has-error'); // set error class to the control group
	         },

	         unhighlight: function (element) { // revert the change done by hightlight
	             $(element)
	                 .closest('.myform-input').removeClass('has-error'); // set error class to the control group
	         },

	         success: function (label) {
	             label
	                 .closest('.myform-input').removeClass('has-error'); // set success class to the control group
	         }
	    });
	}
   
    //初始化页面日期插件
	if($('.datetimepicker').datepicker){
		setTimeout(function() {
			$('.datetimepicker').datepicker({
				autoclose: true,
				language: 'zh_CN',
				todayHighlight: true
			})
		}, 500);
	}
    
});
function returnTableDetail(tableName) {
	window.location.href = $("#ctx").val()+"/maintabledata/maintabledata_pagelist.izhbg?tableName="+tableName;
}
function addObjectSave() {
	var url = $("#ctx").val()+"/maintabledata/maintabledata_save.izhbg";
	$("#methodCall").val("addRealTable");
	$("#queryformtable").attr("action", url);
	try {
		editor_a1.sync();
	} catch (e) {
	}
	$("#queryformtable").submit();
	return true;
}

function type1Click(El) {
	var selectValue = El.value;
	$("#type1").val(selectValue);
	if (selectValue == '1') {
		document.getElementById('title_add').style.display = 'inline';
		document.getElementById('gw_upload').style.display = 'none';

	} else {
		document.getElementById('title_add').style.display = 'none';
		document.getElementById('gw_upload').style.display = 'inline';
	}
}
//附件列表刷新
function atch_dis() {
	var list = document.getElementById("attach_fs");
	var flist = document.getElementById("attachList");
	var cont = document.getElementById("attach_div");
	var flag = $('#attach_fs').css("display");
	if (flag == "none") {
		$('#attach_fs').css("display", "");
		$('#attach_div')
				.css("background",
						"url("+$("#ctx").val()+"/s/assets/plugins/ajaxupload/images/fj_open.gif) no-repeat left");
		refresh_list();
	} else {
		$('#attach_fs').css("display", "none");
		$('#attach_div')
				.css("background",
						"url("+$("#ctx").val()+"/s/assets/plugins/ajaxupload/images/fj_close.gif) no-repeat left");
	}
}
function refresh_list() {
	$('#attach_p_div').css("display", "block");
	$('#attach_div').css("display", "block");
	$('#attach_fs').css("display", "");
	$('#attach_div')
			.css("background",
					"url("+$("#ctx").val()+"/s/assets/plugins/ajaxupload/images/fj_open.gif) no-repeat left");

	var bizType = $('#bizType').val();
	$.post($("#ctx").val()+"/attachefile/queryFilelist.izhbg", {
		fileconfid : $("#fileconfid").val()
	}, function(data) {
		$("#attach_fs").html(data);
	});

}

function addAttTitle() {
	var fileconfid = $('#fileconfid').val();
	var title = $('#file_title').val();
	var size = $('#file_size').val();
	if ($('#file_title').val().trim() == "") {
		alert("请输入标题");
		return false;
	} else {
		if ($('#file_title').val().trim().length > 200) {
			alert("标题最多200个字！");
			return false;
		}
	}

	$.post(
			$("#ctx").val()+"/default/management/security/maintable/addFileAttachTitle.do",
			{
				"title" : title,
				"fileconfid" : fileconfid,
				"filesize" : size
			}, function(data) {
				if (data.result == 0) {
					$("#fileconfid").val(
							data.mainTableAttachFile.confId);
					refresh_list();
				} else {
					alert(data.msg);
				}

			}, "json");
	$('#file_title').val("");
	$('#file_size').val("");
}

function openGroupWin1(url, title, rel, columnid, ischecked, id) {
	var $modal = $('#ajax');
	$('body').modalmanager('loading');
	setTimeout(function() {
		$modal.load(url + "?columnid=" + columnid + "&id=" + id
				+ "&title=" + title + "&divid=ajax", '', function() {
			$modal.modal();
		});
	}, 500);
}
function expandTable(id) {
	var tablename = $("#" + id + "tablename").val();
	var valid = $("#" + id + "parent").val();
	if (valid == "") {
		valid = "  ";
	}
	url = $("#ctx").val()+"management/security/maintable/querylistattributepage.do?tableName="
			+ tablename
			+ "&"
			+ id
			+ "="
			+ valid
			+ "&linkcolumnname="
			+ id;
	$.post("${ctx}/default/" + url, function(data) {
		$("#" + id + "table").html(data);//.initUI();
	});
}
function expandPageTable(id, pageNo, pageSize) {
	var tablename = $("#" + id + "tablename").val();
	var valid = $("#" + id + "parent").val();
	if (valid == "") {
		valid = "  ";
	}
	url = $("#ctx").val()+"management/security/maintable/querylistattributepage.do?tableName="
			+ tablename
			+ "&"
			+ id
			+ "="
			+ valid
			+ "&linkcolumnname="
			+ id + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
	$.post($("#ctx").val()+"/default/" + url, function(data) {
		$("#" + id + "table").html(data);//.initUI();
	});
}
function addObjectAttributeSave(formId, pageNo, pageSize) {
	if (($("#" + formId).validationEngine('validate') + "") == "true") {
		var linkID = $("#linkcolumnname" + formId).val();
		$("#" + linkID).val($("#" + linkID + "parent").val());

		$
				.post(
						$("#ctx").val()+"/default/management/security/maintable/addRealTableAttribute.do",
						$("#" + formId).serialize(), function(data) {
							if (data.result == 0) {
								$("#" + linkID + "parent").val(
										data.linkcolumnname);
								expandPageTable(linkID, pageNo,
										pageSize);
							} else {
								alert(data.msg);
							}
						}, "json");
	}
}
function deleteInfo(realtableid, tableName, linkId, pageNo, pageSize) {
	if ($("#confirmDelete").html()) {
	} else {
		var str = '<div class="modal fade" id="confirmDelete" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
				+ '<div class="modal-dialog">'
				+ '<div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
				+ '<h4 class="modal-title">提示信息</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '确定要删除该条信息？'
				+ '</div>'
				+ '<div class="modal-footer">'
				+ '<button type="button" class="btn blue" id="cconfirmchange" >确定</button>'
				+ '<button type="button" class="btn default" data-dismiss="modal">取消</button>'
				+ '</div>' + '</div>' + '</div>' + '</div>';

		$("#queryformtable").append(str);

	}
	$("#cconfirmchange").unbind('click').bind(
			'click',
			function() {
				deleteConfirm(realtableid, tableName, linkId, pageNo,
						pageSize);
			});

	$("#confirmDelete").modal({
		backdrop : 'static',
		keyboard : false
	}).modal('show');
}
function deleteConfirm(realtableid, tableName, linkId, pageNo, pageSize) {
	$
			.post(
					$("#ctx").val()+"/default/management/security/maintable/removeRealTableAttribute.do",
					{
						"realtableid" : realtableid,
						"tableName" : tableName
					}, function(data) {
						expandPageTable(linkId, pageNo, pageSize);
						$("#confirmDelete").modal().modal('hide');
					}, "json");
}
function modifyObject(realtableid, tableName, userid, opeid, id,
		pageNo, pageSize) {
	var tablename = $("#" + id + "tablename").val();
	var valid = $("#" + id + "parent").val();
	if (valid == "") {
		valid = "  ";
	}
	url = $("#ctx").val()+"management/security/maintable/querylistattributepage.do?tableName="
			+ tablename
			+ "&"
			+ id
			+ "="
			+ valid
			+ "&linkcolumnname="
			+ id
			+ "&realtableid="
			+ realtableid
			+ "&userid="
			+ userid
			+ "&opeid="
			+ opeid
			+ "&action=1"
			+ "&pageNo="
			+ pageNo
			+ "&pageSize=" + pageSize;
	$.post("${ctx}/default/" + url, function(data) {
		$("#" + id + "table").html(data);//.initUI();
	});
}
function regionInfo(r) {
	var list = [];
	list.push('<li>ID = ' + r.i + '</li>');
	list.push('<li>Name = ' + r.n + '</li>');
	list.push('<li>Alias = ' + r.a + '</li>');
	list.push('<li>Pinyin = ' + r.y + '</li>');
	list.push('<li>Abbr = ' + r.b + '</li>');
	list.push('<li>Postcode = ' + r.z + '</li>');
	return '<ul>' + list.join("\n") + '</ul>'
};
function validData(taget_id){
	var sour = $("#"+taget_id);
	var target = $("#"+taget_id+"_se");
	if($.trim(target.val())){
		if(target.val()!=sour.val()){
			target.parent().addClass("has-error");
			if(target.parent().find("span[for='"+taget_id+"']").length!=0){
				target.parent().find("span[for='"+taget_id+"']").text("两次输入不一致，请确认");
				target.parent().find("span[for='"+taget_id+"']").css("display","block");
			}else{
				target.parent().append("<span class=\"help-block\" for=\""+taget_id+"\">两次输入不一致，请确认</span>");
			}
		}else{
			target.parent().removeClass("has-error");
			target.parent().find("span[for='"+taget_id+"']").text("");
		}
	}
	
}