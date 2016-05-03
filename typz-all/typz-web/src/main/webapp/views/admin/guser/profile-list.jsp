<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
<link href="${ctx}/s/assets/css/user.css" rel="stylesheet" type="text/css">
<link href='${ctx}/s/assets/plugins/jcrop/css/jquery.Jcrop.min.css' rel="stylesheet" style="text/css">
<script type="text/javascript" src="${ctx}/s/assets/scripts/userConfig.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/ajaxupload/ajaxupload.3.6.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/jcrop/js/jquery.Jcrop.js"></script>

</head>
<body class="page-header-fixed">
<jsp:include page="/common/header.jsp"></jsp:include> 
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
			<div class="row-fluid">
				<div class="col-md-12">
					<div class="row-fluid">
						<div class="tabbable tabs-left">
							<ul class="nav nav-tabs">
							  <li class="active"><a data-toggle="tab"  href="#user_basic">基本信息</a></li>
							  <li><a data-toggle="tab"  href="#user_pic">设置头像</a></li>
							  <li><a data-toggle="tab"  href="#user_pass">修改密码</a></li>
							</ul>
							<div class="tab-content">
								<input name="yhId" id="yhId" type="hidden"  value="${user.yhId }" />
								<!--基本资料-->
								<div class="tab-pane active" id="user_basic">
									<div>
										<h4><span style="font-size: 17.5;font-weight: bold;line-height: 20px;font-family: '黑体';">个人资料</span></h4>
										<hr>
										<div class="info-conv">
											<table >
												<tr>
													<td>姓名:</td>
													<td>
														<div id="showUser">
															<font id="user_name_t">${user.yhMc }</font><!--  <span style="margin:6px;">|</span><a href="#" onclick="showChange(true);">修改</a>-->
														</div>
														<div id="modifyUser"  style="display:none;">
															<input type="text" id="user_name_modify" style="height: 30px;" value="${user.yhMc }">
															<input type="button" class="btn btn-danger" value="保存" onclick="modifyUserName();"> 
															<input type="button" class="btn" value="取消" onclick="showChange(false);"> 
														</div>
													</td>
												</tr>
												<tr>
													<td>登陆名称:</td>
													<td>
														<div>
															<font id="user_name_p">${user.yhDm }</font>
														</div>
													</td>
												</tr>
												<tr>
													<td>个人签名:</td>
													<td>
														<div>
															<c:if test="${user.ddqmPath ne null && user.ddqmPath ne ''}">
																<img alt="签名" id="userqmImg" src="${ctx}/management/security/maintable/downloadFileImg.izhbg?attachId=${user.ddqmPath}">
															</c:if>
															<c:if test="${user.ddqmPath eq null || user.ddqmPath eq ''}">
																<img alt="签名" id="userqmImg" src="${ctx }/s/assets/img/qm_default.png">
															</c:if>
														</div>
													</td>
												</tr>
												
											</table>
										</div>
									</div>
								</div>
								
								<!--设置头像-->
								<div class="tab-pane" id="user_pic">
									<div id="userphoto">
										<h4><span style="font-size: 17.5;font-weight: bold;line-height: 20px;font-family: '黑体';">设置头像</span></h4>
										<hr>
										<div class="info-conv">
											<table>
												<tr>
													<td>头像:</td>
													<td>
														<div class="photo">
															<c:if test="${user.photoPath ne null && user.photoPath ne ''}">
																<img alt="头像" id="userImg" src="${ctx}/common/file/downloadFileImg.izhbg?attachId=${user.photoPath}">
															</c:if>
															<c:if test="${user.photoPath eq null || user.photoPath eq ''}">
																<img alt="头像" id="userImg" src="${ctx }/s/assets/img/default_user.png">
															</c:if>
															<div style="margin-top:10px;">
																<input type="button" class="btn btn-danger" data-toggle="modal" data-target="#add_user_photo" value="设置头像">
															</div>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
								
								<!--修改密码-->
								<div class="tab-pane" id="user_pass">
									<div id="userpass">
										<h4><span style="font-size: 17.5;font-weight: bold;line-height: 20px;font-family: '黑体';">修改密码</span></h4>
										<hr>
										<div class="info-conv">
											<table>
												<tr><td>现在的密码:</td><td><input class="form-control" type="password" id="now_pass"></td></tr>
												<tr><td>设置新密码:</td><td><input class="form-control" type="password" id="change_pass"></td></tr>
												<tr><td>重复新密码:</td><td><input class="form-control" type="password" id="change_pass_again"></td></tr>
												<tr><td colspan="2"><input type="button" class="btn btn-danger" value="保存" onclick="modifyPassword();"></td></tr>
											</table>
										</div>
									</div>
								</div>
								
								
								<!-- 其它配置 -->
								<div class="tab-pane" id="system"><!--系统设置-->
									<div class="row-fluid" style="margin-top:10px;"> 
								</div>
							</div>
							<!-- 修改用户头像 -->
							<div id="add_user_photo" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="tag modal" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
											<h4 class="modal-title">头像</h4>
										</div>
										<div class="modal-body">
											<div class="scroller" style="height:400px" data-always-visible="1" data-rail-visible1="1">
												<div class="col-md-12">
													<div class="btn btn-success" style="position: relative;overflow: hidden;direction: ltr;" id="picupload">
														<i class="icon-upload icon-white"></i> 
														上传头像图片
													</div>
													<span class="label label-warning"><span>（只能上传单张10M以下png、jpg、gif图片）</span></span>
													
													<div id="preview-out">
													  <div id="preview_src" style="width:350px;height:350px;">
													  	 <img src="${ctx }/s/assets/img/default_user.png" id="target" class="img-responsive" width="100%"/>
													  </div>
													  
													  <div id="preview-pane">
													  	<div id="preview_title">头像预览</div>
													  	<div id="preview_large_text" class="preview-text">100px × 100px</div>
													    <div class="preview-container">
													      <img src="${ctx }/s/assets/img/default_user.png" class="jcrop-preview" id="preview" alt="Preview" />
													    </div>
													  </div>
													</div>	
												
												<input id="fileId" type="hidden" />
												<input id="x" type="hidden" />
												<input id="y" type="hidden" />
												<input id="w" type="hidden" />
												<input id="h" type="hidden" />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" data-dismiss="modal" class="btn default">关闭</button>
											<button type="button" class="btn green" onclick="cutImage()">保存</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>	
		</div>
	</div>
	<script>
	function returnPage(){
		window.location.href="${ctx}/dashboard/dashboard.izhbg";
		}

	var ajaxupload = new AjaxUpload('#picupload', {
		action: '../common/file/uploadFileAttach.izhbg',
		name: 'uploadFile',
		autoSubmit: true,
		responseType: 'json',
		onSubmit: function(filename, ext) {
			ajaxupload.setData({ temppath:$("#temppath").val(),fileconfid:$("#fileconfid").val()});
			 if(!/\.(gif|jpg|jpeg|png|JPG|PNG)$/.test(filename)){
				showInfoWin("","未允许上传的文件格式!");
				return false;
			}
		},
		onComplete: function(filename, data) { 
			switch(data.result){
			case 0:
				//management/security/maintable/getAttachFilePathAndpdfToSwf.izhbg?attachId="+attachId;
				var fileId = data.mainTableAttachFile.id;
				$("#fileId").val(fileId);
	        	if(fileId)
	        		updateUserPicId(fileId);
	        	var url = getWebRootPath() + "/common/file/downloadFileImg.izhbg?attachId="+fileId;
	        	$("#target").attr("src",url);
	        	$("#preview").attr("src",url);
	        	initJcrops();
				break;
			case 1:
				break;
			}
		}
	});
	

	// 上传公文附件
	/**new AjaxUpload('#staffId', {
			action: 'CommonAttachmentBLH/uploadPortalImgFile.action',
			name: 'uploadFile',
			data: {
				yhid:$("#yhId").val(),
			},
			autoSubmit: true,
			responseType: 'json',
//				onSubmit: function(filename, ext) {
//					var type_file = navTab.getCurrentPanel().find("#CommonAttachmentType").val();
//					if (type_file.indexOf(ext)< 0) {
//						 $.dialog.tip('未允许上传的文件格式!');
//						return false;
//					}
//				},
			onComplete: function(filename, data) {
				switch(data.result){
				case 0:
					$("#staffId").val(data.msg);
					$.dialog.tip("图片上传成功！");
					break;
				case 1:
					$.dialog.alert("图片上传成功！");
					break;
				}
			}
		});**/
</script>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>