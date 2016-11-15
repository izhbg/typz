<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link href="${ctx}/s/assets/css/goods/goods_edit.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/plugins/jcrop/jquery.Jcrop.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/simditor-1.0.5/styles/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/s/assets/plugins/simditor-1.0.5/styles/simditor.css" />

 <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
 <style type="text/css">
 	.amap-sug-result{z-index: 99999;}
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
				<input id="contextpath" name="contextpath" value="${ctx}" type="hidden">
				<input type="hidden" value="" id="idx">
				<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
				<!-- 操作按钮如下 -->
				<div class="content content-inner">
					<div class="panel panel-default">
						<!-- Default panel contents -->
						<div class="panel-heading">
							<h3 class="panel-title">选择类目</h3>
						</div>
						<div class="panel-body">
							<form id="storeEditForm" name="storeEditForm" action="${ctx}/store/store_save.izhbg" method="post" class="form-horizontal" >
							
							<input type="hidden" name="id" id="id" value="${tShStore.id}">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class=" col-md-12" >1. 店铺描述</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" >* 店铺名称：</label>
										<div class="col-md-6">
											<input name="title" id="title"  class="form-control input-placeholder required" tag="店铺名称不能不好特殊字符，标点符号" type="text" value='${tShStore.title }'  />
										</div>
									</div>
								</div>
							</div>
							<c:if test="${empty isUpdate}">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2" >* 手机号：</label>
											<div class="col-md-6">
												<div class="input-group">
													<input name="phone" id="phone"  class="form-control input-placeholder required" tag="手机号码，绑定用户账户" type="text"   />
													<span class="input-group-addon" onclick="javascript:Store.getYzm(this)"><i class="fa fa-link" style="cursor: pointer;">发送短信</i></span>
												</div>
												
											</div>
										</div>
									</div>
									
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2" >* 手机验证码：</label>
											<div class="col-md-6">
												<input name="codeId" id="codeId" type="hidden"/>
												<input name="code" id="code"  class="form-control input-placeholder required" tag="手机短信验证码" type="text"   />
											</div>
										</div>
									</div>
									
								</div>
							</c:if>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" >* 店铺简介：</label>
										<div class="col-md-6">
											<textarea name="content" id="content" class="form-control input-placeholder required" tag="店铺描述中不能包含特殊字符，非法描述">${tShStore.content }</textarea>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" >* 店铺类型：</label>
										<div class="col-md-6">
											<select name="type" id="type" class="form-control required">
												<option value="1" <c:if test="${tShStore.type eq 1 }">selected="selected"</c:if>>厂家</option>
												<option value="2" <c:if test="${tShStore.type eq 2 }">selected="selected"</c:if>>加盟商</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class=" col-md-12" >2. 店铺LOGO及店铺证书</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group" id="pic_detail">
										<label class="control-label col-md-2" >店铺LOG:</label>
										<div class="col-md-10 add-goods-car">
											1、图片格式为jpg、jpeg、png，大小不超过2M.<br>
											2、上传优质图片容易让消费者下单。<br>
											3、优质图片定义：产品清晰原图，无人为修改痕迹，无水印，无锈蚀边框和文字<br>
											<span class="add-has-choosecar add-upload-btn">
										        <input type="file" multiple="multiple" id="uploadFile1" name="uploadFile" onchange="Store.uploadIndexImg(this,${tShStore.id })"/>
										      	  上传
									      	</span> 
									      	<span class="chexing-notice add-upload-limit">您还可以上传 <span>1</span>/1张</span>
									        <div class="sortable im-container1 add-upload-img clearfix" id="SortContaint">
									          <i class="add-border"></i>
									         	<c:if test="${!empty tShStore.logoAttache}">
									          	<div>
									                <p class="add-img-p add-upload-second">
									                	<input type="hidden" name="picIds" value="${tShStore.logoAttache.id}" />
									                    <img src="${ctx}/storeImg/downloaImgdFile.izhbg?attachId=${tShStore.logoAttache.id}" alt="添加新图片" class="add-hidden">
									                    <img class="add-upload-second" src="${ctx}/storeImg/downloaImgdFile.izhbg?attachId=${tShStore.logoAttache.id}" alt="添加新图片">
									                </p>
									                <p class="add-cut-del" style="display: none;">
									                    <span class="add-cut">裁剪</span><i></i>
									                    <span class="add-del">删除</span>
									                </p>
									            </div>
									            </c:if>
									            <c:if test="${empty tShStore.logoAttache}">
									          		<div class="no-imgs"></div>
									          	</c:if>
									        </div>
										</div>
									</div>
									<div class="form-group" id="pic_detail">
										<label class="control-label col-md-2" >店铺证书:</label>
										<div class="col-md-10 add-goods-car">
											1、图片格式为jpg、jpeg、png，大小不超过2M.<br>
											2、上传优质图片容易审核通过。<br>
											3、优质图片定义：产品清晰原图，无人为修改痕迹，无水印，无锈蚀边框和文字<br>
											<span class="add-has-choosecar add-upload-btn">
										        <input type="file" multiple="multiple" id="uploadFile" name="uploadFile" onchange="Store.uploadImg(this,${tShStore.id })"/>
										      	  批量上传
									      	</span> 
									      	<span class="chexing-notice add-upload-limit">您还可以上传 <span>5</span>/5张</span>
									        <div class="sortable im-container2 add-upload-img clearfix" id="SortContaint">
									          <i class="add-border"></i>
									          <c:forEach items="${tShStore.tShStoreAttachefiles}" var="item">
									          	<div>
									                <p class="add-img-p add-upload-second">
									                	<input type="hidden" name="picIds" value="${item.id}" />
									                    <img src="${ctx}/storeImg/downloaImgdFile.izhbg?attachId=${item.id}" alt="添加新图片" class="add-hidden">
									                    <img class="add-upload-second" src="${ctx}/storeImg/downloaImgdFile.izhbg?attachId=${item.id}" alt="添加新图片">
									                </p>
									                <p class="add-cut-del" style="display: none;">
									                    <span class="add-cut">裁剪</span><i></i>
									                    <span class="add-del">删除</span>
									                </p>
									            </div>
									          </c:forEach>
									          <div class="no-imgs"></div>
									          <div class="no-imgs"></div>
									          <div class="no-imgs"></div>
									          <div class="no-imgs"></div>
									          <div class="no-imgs"></div>
									        </div>
									       <div class="add-img-miaoshu">
											    <p class="main-img-must">主图片</p>
											    <p class="add-img-sort">您可以拖动图片进行排序</p>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class=" col-md-12" >3. 店铺定位</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2" >* 店铺地址：</label>
										<div class="col-md-6">
											<input name="la" id="la" value="${tShStore.la }" type="hidden"/>
											<input name="lo" id="lo" value="${tShStore.lo }" type="hidden"/>
											<div class="input-group">
												<input name="address" id="address" onclick="mapDialog(); return false;"  class="form-control input-placeholder required" tag="店铺地址不能不好特殊字符，标点符号" type="text" readonly="readonly" value='${tShStore.address }'  />
												<span class="input-group-addon" onclick="mapDialog(); return false;"><i class="fa fa-link" style="cursor: pointer;"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group" align="center">
									<a href="#this" class="btn red" onclick="Store.formSubmit()">确认提交</a>
								</div>
							</div>
							</form>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="mapDialog" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">确认信息  </h4>
				</div>
			<div class="modal-body" style="width: 870px;height: 400px;">
				<div  id="container"></div>
				<div id="myPageTop">
				    <table>
				        <tr>
				            <td>
				                <label>请输入关键字：</label>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <input id="tipinput"/>
				            </td>
				        </tr>
				    </table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn blue" id="cconfirmMap" >确定</button>
				<button type="button" class="btn default" data-dismiss="modal">取消</button>
			</div>
			</div>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/s/assets/plugins/jcrop/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/Sortable.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/ajaxupload/ajaxfileupload.js"></script>


<script src="http://cache.amap.com/lbs/static/es5.min.js"></script> 
<script src="http://webapi.amap.com/maps?v=1.3&key=646c64b1cfee1ccda3c138daf1f44a4d"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/simditor-1.0.5/scripts/js/simditor-all.js"></script>

<script type="text/javascript" src="${ctx}/s/assets/scripts/store/map.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/scripts/store/store_edit.js"></script>
<script type="text/javascript">
	$(function(){
		Store.init(); 
	});
</script>
</body>
</html>