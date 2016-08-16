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
							<form id="goodsEditForm" name="goodsEditForm" action="${ctx}/goods/goods_save.izhbg" method="post" class="form-horizontal" >
							
							<input type="hidden" name="basicId" id="basicId" value="${tShGoodsBasic.id}">
							<input type="hidden" name="detailId" id="detailId" value="${tShGoodsBasic.tShGoodsDetail.id}">
							<input type="hidden" name="typeId" id="typeId" value="${tShGoodsBasic.typeId}">
							<div class="row">
								<div class="col-md-12 ">
									<div class="well" align="right"><img src="${ctx}/s/assets/img/goods/addgoods-step1.png" alt="选择类目" width="492" height="32" id="stepImg"></div>
								</div>
							</div>
							<div class="basicdiv">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2" >查找类目：</label>
											<div class="col-md-6">
												<div class="input-group">
													<input name="code" id="code"  class="form-control" type="text" value='${parameterMap.code }'  />
													<span class="input-group-addon add-on"><i class="fa fa-search">快速查找</i></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2" >选择类目：</label>
											<div class="col-md-10">
												<div class="choose-plat clearfix">
											        <ul class="plat_classify col-md-3" id="goods_type_1" style="margin-left: 0px;">
											          <c:forEach var="list" items="${tShGoodsTypes}">
											          		<li id="${list.id}" <c:if test="${tShGoodsType_fir.id eq list.id}" > class="on" </c:if> ><span title="${list.name}">${list.name}</span></li>
											          </c:forEach>
											        </ul>
											        <ul class="plat_classify col-md-3" id="goods_type_2">
											          	<c:if test="${tShGoodsType_sec!=null}">
											          		<li id="${tShGoodsType_sec.id}" class="on" ><span title="${tShGoodsType_sec.name}">${tShGoodsType_sec.name}</span></li>
											          	</c:if>
											        </ul>
											        <ul class="plat_classify col-md-3 " id="goods_type_3">
											          	<c:if test="${tShGoodsType_thir!=null}">
											          		<li id="${tShGoodsType_thir.id}" class="on" ><span title="${tShGoodsType_thir.name}">${tShGoodsType_thir.name}</span></li>
											          	</c:if>
											        </ul>
											      </div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 ">
									<div class="add-goods-haschoose"> 
										<span class="add-goods-redstar">*</span>
										<strong>已选类目：</strong>
										<b></b>
										<span class="detaildiv add-goods-return" onclick="Goods.toBasic()">返回修改类目</span> 
									</div>
								</div>
							</div>
							<div class="basicdiv">
								<div class="row">
									<div class="col-md-12" align="center">
										<a class="btn red" href="#this" onclick="Goods.toDetail()">立即去发布新商品</a>
									</div>
								</div>
							</div>
							<div class="detaildiv">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class=" col-md-12" >1. 商品信息 (请按商品实际情况正确填写商品名称、别名、原厂编码、品牌、型号，以便修理厂快速找到您的商品)</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2" >* 商品名称：</label>
											<div class="col-md-6">
												<input name="name" id="name"  class="form-control input-placeholder required" tag="商品名称中不能包含OEM/OE，品牌名称，标点符号" type="text" value='${tShGoodsBasic.name }'  />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >* 价  格：</label>
											<div class="col-md-8">
												<input name="defaultPrice" id="defaultPrice"  class="form-control input-placeholder required" tag="有优势的价格能为您带来更多销量" type="text" value='${tShGoodsBasic.tShGoodsDetail.defaultPrice }'  />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >别      名：</label>
											<div class="col-md-8">
												<input name="aliasName" id="aliasName"  class="form-control input-placeholder" type="text" tag=" 添加商品的行业别名（俗称），别名不能与商品名称相同" value='${tShGoodsBasic.aliasName }'  />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >品      牌：</label>
											<div class="col-md-8">
												<input name="brandId" id="brandId"  class="form-control input-placeholder" type="text" tag="品牌" value='${tShGoodsBasic.brandId }'  />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >规      格：</label>
											<div class="col-md-8">
												<input name="specificationsId" id="specificationsId"  class="form-control input-placeholder" tag="请输入商品的规格（体积、长度、包装规格等）" type="text" value='${tShGoodsBasic.specificationsId }'  />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >* 型       号：</label>
											<div class="col-md-8">
												<input name="modelId" id="modelId"  class="form-control input-placeholder required" tag="请输入商品型号（不能输入汉字）" type="text" value='${tShGoodsBasic.modelId }'  />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >*  单      位：</label>
											<div class="col-md-8">
												<input name="unit" id="unit"  class="form-control input-placeholder required" type="text" tag="请输入商品的单位，如：件，桶，箱等" value='${tShGoodsBasic.unit }'  />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-4" >* 库       存：</label>
											<div class="col-md-8">
												<input name="stockStatus" id="stockStatus"  class="form-control input-placeholder required" tag="请输入商品的库存，以保证商品正常销售" type="text" value='${tShGoodsBasic.tShGoodsDetail.stockStatus }'  />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class=" col-md-12" >2. 商品图片及详情</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group" id="pic_detail">
											<label class="control-label col-md-2" >商品图片:</label>
											<div class="col-md-10 add-goods-car">
												1、图片格式为jpg、jpeg、png，大小不超过2M.<br>
												2、上传优质图片容易让修理厂下单。<br>
												3、优质图片定义：产品清晰原图，无人为修改痕迹，无水印，无锈蚀边框和文字<br>
												<span class="add-has-choosecar add-upload-btn">
											        <input type="file" multiple="multiple" id="uploadFile" name="uploadFile" onchange="Goods.uploadImg(this)"/>
											      	  批量上传
										      	</span> 
										      	<span class="chexing-notice add-upload-limit">您还可以上传 <span>5</span>/5张</span>
										        <div class="sortable add-upload-img clearfix" id="SortContaint">
										          <i class="add-border"></i>
										          <c:forEach items="${tShGoodsBasic.tShGoodsImages}" var="item">
										          	<div>
										                <p class="add-img-p add-upload-second">
										                	<input type="hidden" name="picIds" value="${item.id}" />
										                    <img src="${ctx}/goodsImg/downloaImgdFile.izhbg?attachId=${item.id}" alt="添加新图片" class="add-hidden">
										                    <img class="add-upload-second" src="${ctx}/goodsImg/downloaImgdFile.izhbg?attachId=${item.id}" alt="添加新图片">
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
											<label class="control-label col-md-2" >商品详情：</label>
											<div class="col-md-10">
												商品详情一般包含商品功能属性、商品细节图片、售后服务、公司实力等内容。<br>
												<textarea id="content" name="content" class="required">${tShGoodsBasic.tShGoodsDetail.content }</textarea>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 form-group" align="center">
										<a href="#this" class="btn red" onclick="Goods.formSubmit()">发布商品</a>
									</div>
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
	<div class="modal fade" id="msgDialog" tabindex="-1" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">确认信息</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn blue" data-dismiss="modal">确定</button>
					<button type="button" class="btn default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/s/assets/plugins/jcrop/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/Sortable.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/ajaxupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/scripts/goods/goods_edit.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/simditor-1.0.5/scripts/js/simditor-all.js">
</script>
<script type="text/javascript">
	$(function(){
		Goods.init(); 
		Goods.initTextArea("content");
		
	});
	
</script>
</body>
</html>