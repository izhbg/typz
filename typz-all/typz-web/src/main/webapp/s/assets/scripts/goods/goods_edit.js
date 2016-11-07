//全局变量
// 点击平台分类框外的区域，自动滚动到可视范围
var flag=false;

var Goods = function(){
	//定义自动滚动方法
	var scrollauto=function(obj){
	    var $obj = obj.find("li.on");
	    var index = $obj.index();
	    var height = 30 * index;
	    var div_height = 300;
	    if (height >= div_height) {
	        obj.animate({
	            "scrollTop": height + "px"
	        },
	        500);
	    } else {
	        obj.animate({
	            "scrollTop": 0
	        },
	        500);
	    }
	};
	var initDiv = function(){
		$(".detaildiv").css("display","none");
	};
	var initInput = function(){
	    // 兼容placeholder
		$(".input-placeholder").each(function() {
		    var value = $(this).val();
		    var placeholder = $(this).attr("tag");
		    if(value==""){
		       $(this).val(placeholder);
		    }else if(value == placeholder) {
		        $(this).removeClass('on');
		    } else {
		        $(this).addClass('on');
		    }
		});
		$("body").on("focus", ".input-placeholder",function() {
		    var thisval = $(this).val();
		    var placeholder = $(this).attr("tag");
		    if (thisval == placeholder) {
		        $(this).val("").removeClass('on');
		    }
		    $(this).addClass('on');
		});
		$("body").on("blur", ".input-placeholder",function() {
		    var thisval = $(this).val();
		    var placeholder = $(this).attr("tag");
		    if (thisval == "" || thisval == placeholder) {
		        $(this).val(placeholder).removeClass('on');
		    } else {
		        $(this).val(thisval).addClass('on');
		    }
		});
	};
	//自动滚动到可视区域
	var initScrollauto = function(){
		$(".plat_classify").mouseenter(function(){
			flag=true;
		}).mouseleave(function(){
			flag=false;
		});
		$("html,body").click(function(){
		  if(flag){
		   	 return;
		  }else{
		   $(".plat_classify").each(function(){
		    	scrollauto($(this));
		   });
		  }
		});   
		//自动滚动，使已选类目显示在可见区
		$(".plat_classify").each(function() {
		    scrollauto($(this));
		});
		$(".sortable").find("div:first").addClass("add-main-img");
	    $(".sortable").sortable({
	        cursor: "move",
	        items: "div",
	        opacity: 0.6,
	        revert: true,
	        update: function () {
	            if($(".sortable").find('.no-imgs').length>0){
	                var str='';
	                var len=$(".sortable").find('.no-imgs').length;
	                $(".sortable").find('.no-imgs').remove();
	                for(var i=0;i<len;i++){
	                    str+="<div class='no-imgs'></div>";
	                }
	                $("#SortContaint").append(str);
	            }
	            $(".sortable").find("div:first").addClass("add-main-img").siblings("div").removeClass("add-main-img");
	            $('.add-main-img').css({'left': '2px', 'top': '2px'});
	        }
	    });
	};
	//联动事件
	var initMutiSelect = function(){
		// 点击第三个平台分类后展示已选分类
		$(".plat_classify li").click(function() {
		    $(this).addClass("on").siblings("li").removeClass("on");
		    var obj = $(this).parent();
		    var idx = $(".plat_classify").index(obj);
		    if (idx == 2) {
		      var arr = "";
		        $(".plat_classify li.on").each(function(i) {
		            var text = $.trim($(this).text());
		            arr += text;
		            if (i < 2) {
		                arr += ">";
		            }
		        });
		      $(".add-goods-haschoose").find("b").text(arr);
		      $("#typeId").val($(this).attr("id"));
		    }
		    if(idx==0){
		    	resetGoodsType2($(this).attr("id"),"goods_type_2");
		    	$("#goods_type_3").html("");
		    	$(".add-goods-haschoose").find("b").text("");
		    	$("#typeId").val("");
		    }else if(idx==1){
		    	resetGoodsType2($(this).attr("id"),"goods_type_3");
		    	$(".add-goods-haschoose").find("b").text("");
		    	$("#typeId").val("");
		    }
		});
	};
	var toDetail = function(){
		var typeId = $("#typeId").val();
		if(typeId!=""){
			$(".detaildiv").show();
			$(".basicdiv").hide();
			$("#stepImg").attr("src",$("#contextpath").val()+"/s/assets/img/goods/addgoods-step2.png");
		}else{
			alert("请选先择类目");
		}
		
	};
	var toBasic = function(){
		$(".detaildiv").hide();
		$(".basicdiv").show();
		$("#stepImg").attr("src",$("#contextpath").val()+"/s/assets/img/goods/addgoods-step1.png");
	};
	var resetGoodsType2 = function(pid,divId){
		$.ajax({
    		type: "GET",
    	    url: $("#contextpath").val()+"/goods_type/getTypeByPid_json.izhbg",
    	    data: "pid="+pid,
    	    dataType:"json",
    		cache: false,
    		success: function(data){
    	    	if(data.result==1){
    	    		var items = data.content;
    	    		var html = "";
    	    		for(var i=0;i<items.length;i++){
    	    			html+='<li id="'+items[i].id+'"><span title="'+items[i].name+'">'+items[i].name+'</span></li>';
    	    		}
    	    		$("#"+divId).html(html);
    	    		$(".plat_classify li").unbind("click");
    		    	initMutiSelect();
    	    	}else{
    	    		alert(data.msg);
    	    	}
    		}
    	});
	};
	var initFormVlidate = function(){
		$.validator.setDefaults({
		  submitHandler: function(form) {
			  if($(form).find(".has-error").length==0)
				  form.submit(); 
			 }
		});
		if($("#goodsEditForm").validate)
		{
			 $("#goodsEditForm").validate({
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
	};
	var formSubmit = function(){
		$("#goodsEditForm").submit();
	};
	var uploadImg = function(obj){
		var files = obj.files;
	    var pId = $(obj).attr("id");

	    if (files != undefined) {
            var fs = files.length;
            var uploadedCount = getUploadedCount();
            if (fs > 5 - uploadedCount) {
                alertUp("您最多能上传5张图片，请重新选择！");
                return;
            }

            if (fs == 1 && files[0].size > 2 * 1024 * 1024) {
                alertUp("您选择的图片过大，请重新选择！");
                return;
            }
            
            $.ajaxFileUpload ({
    	        url: $("#contextpath").val()+"/goodsImg/upload.izhbg?r=" + Math.random(),
    	        secureuri :false,
    	        fileElementId :'uploadFile',
    	        dataType : 'json',
    	        success: function (data,status) {
    	            var message = data.message;
    	            var code = data.code;
    	            var url = data.url;
    	            if(!code){
    	                alertUp("上传图片失败！");
    	                return;
    	            }
    	            if(code != '200') {
    	                alertUp(message);
    	                //验证不通过，清除文件信息
    	                //form[0].reset();
    	                return ;
    	            }
    	            var urls = url.split(",");
    	            var tfsBase = $("#contextpath").val();
    	            for (i = 0; i < urls.length; i++) {
	                    addShouImgOrder(tfsBase+"/goodsImg/downloaImgdFile.izhbg?attachId="+urls[i],urls[i]);
	                }

    	        },
    	        error: function (e) {
    	        	alertUp("上传图片失败！");
    	        }
    	    });
	    }

	    
	};
	var getUploadedCount = function(){
		return $("#SortContaint").find("p.add-img-p").length;
	};
	/*上传图片展示,填充空白位置*/
	var addShouImgOrder = function(url,id){
		 $('.sortable').find('div').each(function (i, o) {
		        if ($(o).html() == '') {
		            $(o).html("<input type='hidden'/>");
		            addShouImg(url,i,id);
		            return false;
		        }
		    });
	};
	//上传图片弹窗
	var alertUp = function(content) {
	    $("#msgDialog").find(".modal-body").html(content);
	    $("#msgDialog").modal({ 
			backdrop: 'static',  
			keyboard: false  
		}).modal('show');
	};
	var addShouImg = function(url, i,id){
		var rateImg = 4.938;//压缩比；
        var width;
        var height;
        var imgData = '<div class="add-no-big"  style=" ;width:0px;height:0px;overflow: hidden;background: #ff0000;"><img id="add-is-big'+i+'" class="add-is-big" src="' + url + '" alt="添加新图片"/></div>';
        $('body').append(imgData);
       $("#add-is-big"+i).load(function () {
            height = $(this).height();
            width = $(this).width();
            var htmlData = '';
            if (width > 800 || height > 800) {

                htmlData += '<p class="add-img-p add-upload-second">';
            } else {
                htmlData += '<p class="add-img-p">';
            }
            htmlData += '<img src="' + url + '" alt="添加新图片" class="add-hidden"/>';
            htmlData += '<input type="hidden" name="picIds" value="'+id+'" />';

            if (width > 800 && height < 800) {
                var n = parseInt(width / 162);
                var _top = parseInt(height / n / 2);
                htmlData += '<img style="top:' + -_top + 'px;width:100%"  src="' + url + '" id="'+id+'" alt="添加新图片"/></p> ';
            } else if (height > 800 && width < 800) {
                var m = parseInt(height / 162);
                var _left = parseInt(width / m / 2);
                htmlData += '<img style="left:' + -_left + 'px;height:100%;"  src="' + url + '" id="'+id+'" alt="添加新图片"/></p> ';
            } else if (height > 800 && width > 800) {
                if (height > width) {
                    var s = parseInt(height / 162);
                    var _width = parseInt(width / s);
                    htmlData += '<img style="height:100%;width:' + _width + 'px;left:' + -_width / 2 + 'px;"  src="' + url + '" id="'+id+'" alt="添加新图片"/></p> '
                } else {
                    var p = parseInt(width / 162);
                    var _height = parseInt(height / p);
                    htmlData += '<img style="width:100%;height:' + _height + 'px;top:' + -_height / 2 + 'px;"  src="' + url + '" id="'+id+'" alt="添加新图片"/></p> '
                }
            }
            else {
                var oHeight = height / rateImg;
                var oWidth = width / rateImg;
                htmlData += '<img class="" style="width:' + oWidth + 'px;height:' + oHeight + 'px;left:' + -oWidth / 2 + 'px;top:' + -oHeight / 2 + 'px;" src="' + url + '" id="'+id+'" alt="添加新图片"/></p>';
            }
            htmlData += ' <p class="add-cut-del"><span class="add-cut">裁剪</span><i></i> <span class="add-del">删除</span></p>';
            $('.sortable').find('div').eq(i).html(htmlData);
            $('.sortable').find('div').eq(i).removeClass('no-imgs');
            $(this).parent().remove();

        });
	};
	var initImgCut = function(){
		$("#SortContaint").children("div").each(function(i,o){
	        if(i==0){
	            $(o).addClass("add-main-img");
	        }
	        if(i>4){
	            $(o).remove();
	        }
	    })
		$(".sortable").on({
		    mouseenter: function () {
		        if ($(this).find('.add-img-p')) {
		            $(this).find('.add-cut-del').show();
		        }
		    },
		    mouseleave: function () {
		        $(this).find('.add-cut-del').hide();
		    }
		}, 'div');
		$("body").on("click", ".add-cut", function () {
            var parent = $(this).closest("div");
            var idx = $("#SortContaint div").index(parent);
            $("#idx").val(idx);
            var $src = $(this).parent().prev().find('img:last').attr('src');
            var $id = $(this).parent().prev().find('img:last').attr('id');
            var $width = $(this).parent().prev().find('.add-hidden').outerWidth();
            var $height = $(this).parent().prev().find('.add-hidden').outerHeight();
            if ($width > 800 && $height > 800) {
                var top = $("#pic_detail").offset().top-200;
                var clipLayer = "";
                clipLayer += "<div class='mask-layer'></div>";
                clipLayer += "<div class='clipping'>";
                clipLayer += "<div class='clip_img'>";
                clipLayer += "<img id='element_id' src='" + $src + "' idvalue='"+$id+"'/>";
                clipLayer += ' <div id="preview-pane"  class="show-img-wraps"> ' +
                '<span class="show-img-yulan"> 预览效果如下：</span>' +
                '<div class="preview-container" > <img src="' + $src + '" class="jcrop-preview" alt="Preview"> </div> ' +
                    '<div class="show-img-text"><p class="show-img-text-p">1.您可以拖动裁剪框定位您想裁剪的图片区域。 </p> <p>2.您可以拉大裁剪框扩大裁剪区域。 </p> <p>3.您可以在预览框查看裁剪后的图片效果。 </p> <p>4.当您确认裁剪效果后，点击【确认】按钮，保存您裁剪后的图片。 </p> </div>'
                '</div>';
                clipLayer += "<div class='coords'>";
                clipLayer += "<input type='hidden' value='' id='x1'/>";
                clipLayer += "<input type='hidden' value='' id='y1'/>";
                clipLayer += "<input type='hidden' value='' id='x2'/>";
                clipLayer += "<input type='hidden' value='' id='y2'/>";
                clipLayer += "<input type='hidden' value='' id='w'/>";
                clipLayer += "<input type='hidden' value='' id='h'/>";
                clipLayer += "<input type='hidden' value='' id='p'/>";
                clipLayer += "</div>";
                clipLayer += "</div>";
                clipLayer += "<div class='handle_btn'>";
                clipLayer += "<div class='confirm_btn btncommon'>确定</div>";
                clipLayer += "<div class='shut_btn btncommon'>关闭</div>";
                clipLayer += "</div>";
                clipLayer += "</div>";
                $("body").append(clipLayer);
                $(".clipping").css("top", top);
                initJcrop();
                $(".shut_btn").click(function () {
                    $(".mask-layer").remove();
                    $(".clipping").remove();
                });
            } else {
            	alertUp("该图片尺寸不足800*800，无法进行裁剪！");
            }
        });
		$("body").on("click", ".clipping div.confirm_btn", function () {
		    var imageUrl = $(".clip_img img#element_id").attr("idvalue");
		    $.ajax({
		        url: $("#contextpath").val()+"/goodsImg/cutImage.izhbg",
		        type: "post",
		        data: {
		            fileId: imageUrl,
		            imageScale: $("#p").val(),
		            selectorX: $("#x1").val(),
		            selectorY: $("#y1").val(),
		            selectorW: $("#w").val(),
		            selectorH: $("#h").val()
		        },
		        dataType: "json",
		        success: function (data) {
		            addShouImg($("#contextpath").val()+"/goodsImg/downloaImgdFile.izhbg?attachId="+data.url+"&r="+Math.random(),$("#idx").val(),data.url);
		            $(".shut_btn").click();
		        },
		        error: function () {

		        }
		    });
		});
		$("body").on("click",".add-del",function(){
			var $ele = $(this);
		    $.ajax({
		        url: $("#contextpath").val()+"/goodsImg/delImage.izhbg",
		        type: "get",
		        data:"imageId="+ $ele.parent().prev().find("input:first").val(),
		        
		        dataType: "json",
		        success: function (data) {
		        	$ele.parent().parent().attr("class","no-imgs");
		        	$ele.parent().parent().html("");
		        },
		        error: function () {

		        }
		    });
		});
	}
	var initJcrop = function() {
        $('#element_id').Jcrop({
            onChange: showCoords,
            onSelect: showCoords,
            boxWidth: 800,
            boxHeight: 800,
            aspectRatio: 1,
            allowSelect: false,
            minSize: [800, 800]
        },
        function () {
            var $preview = $('#preview-pane');
            jcrop_api = this;
            var dim = jcrop_api.getBounds();
            boundx = dim[0];
            boundy = dim[1];
            // console.log("原图长度为：" + dim[0]);
            // console.log("原图宽度为：" + dim[1]);
            // console.log("缩放比例为：" + jcrop_api.getScaleFactor()[0]);
            percent = jcrop_api.getScaleFactor()[0];
            $preview.appendTo(jcrop_api.ui.holder);
            var ratio = jcrop_api.getScaleFactor()[0];
            $("#p").val(percent);
            var m = boundx / 2 - 800 / 2;
            var n = boundy / 2 - 800 / 2;
            jcrop_api.animateTo([m, n, 800, 800]);
            var width = dim[0] / ratio;
            $(".clip_img").css("width", width);
            $('.show-img-wraps').css('left', width + 20);
        });
	};
	var showCoords = function(c) {
        var $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),
        xsize = $pcnt.width(),
        ysize = $pcnt.height();
	    if (parseInt(c.w) > 0) {
	        var rx = xsize / c.w;
	        var ry = ysize / c.h;
	        $pimg.css({
	            width: Math.round(rx * boundx) + 'px',
	            height: Math.round(ry * boundy) + 'px',
	            marginLeft: '-' + Math.round(rx * c.x) + 'px',
	            marginTop: '-' + Math.round(ry * c.y) + 'px'
	        });
	    }
	    $('#x1').val(c.x);
	    $('#y1').val(c.y);
	    $('#x2').val(c.x2);
	    $('#y2').val(c.y2);
	    $('#w').val(c.w);
	    $('#h').val(c.h);
	    // console.log("左上角坐标：(" + $('#x1').val() + "," + $('#y1').val() + ")");
	    // console.log("右下角坐标：(" + $('#x2').val() + "," + $('#y2').val() + ")");
	    // console.log("截图长宽距离：(" + $('#w').val() + "," + $('#h').val() + ")");

	};
	var initTextArea = function(id){
		new Simditor({
			  textarea:$('#'+id),
			  toolbar:[ 'title', 'bold', 'italic', 'underline', 'strikethrough',
			            'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|',
			            'link', 'image', 'hr', '|', 'indent', 'outdent' ],
			  defaultImage:'${ctx}/s/assets/plugins/simditor-1.0.5/images/image.png', //编辑器插入图片时使用的默认图片  
			  upload : {
				    url : $("#contextpath").val()+'/attachefile/upload.izhbg', //文件上传的接口地址
				    params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
				    fileKey: 'uploadFile', //服务器端获取文件数据的参数名
				    connectionCount: 3,
				    leaveConfirm: '正在上传文件'
				}
			});
		$(".simditor-toolbar").css("width","100%");
		$(".simditor .simditor-toolbar > ul > li > .toolbar-item").css("padding-top","0px");
	}
	return{
		init:function(){
			initDiv();
			initInput();
			initScrollauto();
			initMutiSelect();
			initFormVlidate();
			initImgCut();
		},
		toDetail:function(){
			toDetail();
		},
		toBasic:function(){
			toBasic();
		},
		formSubmit:function(){
			formSubmit();
		},
		uploadImg:function(obj){
			uploadImg(obj);
		},
		initJcrop:function(){
			initJcrop();
		},
		initTextArea:function(id){
			initTextArea(id);
		}
	}
	
}();