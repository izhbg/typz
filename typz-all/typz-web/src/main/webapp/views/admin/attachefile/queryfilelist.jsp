<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<ul class="mod_files">
	<c:if test="${attachList != null}">
		<c:forEach var="list" items="${attachList}">
			<li class="cl"><span class="mod_att"> <i
					class="fa  fa-paperclip"></i>
			</span>
				<div class="title">
					<span class="del"> <c:choose>
							<c:when test="${user.id==list.yhId}">
								<em onclick="deleteAttach('${list.id}')">删除</em>
								<em onclick="updateFileName('${list.id}')">修改名称</em>
							</c:when>
						</c:choose>
					</span> <a href="javascript:downloadFile('${list.id}')" target="_blank">${list.attachShowName}</a>
				</div>
				<div class="info">
					<span><fmt:formatNumber
							value="${list.attachSize/(1024*1024)}" pattern="##.##"
							minFractionDigits="2"></fmt:formatNumber>MB</span> <span>|</span> <span><tags:user
							userId="${list.yhId}"></tags:user></span> <em>上传于</em> <span
						title="${list.attachTime}">${list.attachTime}</span>
				</div></li>
		</c:forEach>
	</c:if>
</ul>

<div style="display: none;" id="filedetaildiv"></div>
<style type="text/css" media="print">
.printhidden {
	display: none;
}

.printfontstyle {
	font-size: 11px;
	font-weight: normal;
	font-family: '宋体'
}
</style>
<script type="text/javascript">
			function downloadFile(attachId){
				var form = $("<form>");   //定义一个form表单
			       form.attr('style','display:none');   //在form表单中添加查询参数
			       form.attr('target','');
			       form.attr('method','post');
			       form.attr('action',"${pageContext.request.contextPath}/attachefile/downloadFile.izhbg");
			      
			       var input1 = $('<input>'); 
			       input1.attr('type','hidden'); 
			       input1.attr('name','attachId'); 
			       input1.attr('value',attachId); 
			      
			       $('body').append(form);  //将表单放置在web中
			       form.append(input1);   //将查询参数控件提交到表单上
			       form.submit();   //表单提交
			}
			
			
			function updateFileName(attachId){
				$.ajax({
					type: "POST",
				    url: "${pageContext.request.contextPath}/attachefile/getAttachName.izhbg?attachId="+attachId+"&realtableid="+$("#realtableid").val()+"&tableName="+$("#tableName").val(),
				    dataType:"html",
					cache: false,
					success: function(data){
				    	var str = '<div class="modal fade" id="filedetail" tabindex="-1" role="basic" aria-hidden="true" style="display: none;">'
							+'<div class="modal-dialog modal-wide">'
							+'<div class="modal-content">'
							+'<div class="modal-header">'
							+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>'
							+'<h4 class="modal-title">修改名称</h4>'
							+'</div>'
							+'<div class="modal-body">'
							+'<form action="${ctx}/attachefile/updateAttachName.izhbg" class="form-horizontal" id="cal-infoForm" method="post">'
							+ data
							+ '</form>'
							+'<div class="modal-footer">'
							+'<button type="button" class="btn blue" id="submitbutton2" >确定</button>'
							+'<button type="button" class="btn default" data-dismiss="modal">取消</button>'
							+'</div>'
							+'</div>'
							+'</div>'
							+'</div>';
							$("#filedetaildiv").html(str);
							$("#submitbutton2").click(function(){
								var pars = "attacheName="+$("#attacheName").val()+"&attacheId="+$("#attacheId").val();
								$.post("${pageContext.request.contextPath}/attachefile/updateAttachName.izhbg",pars,function(data){
									refresh_list();
									$("#filedetail").modal().modal('hide');
								});
							});
							$("#filedetail").modal({ 
						        backdrop: 'static',  
						        keyboard: false  
						    }).modal('show');
				   	 }
					});
				}
			function deleteAttach(attachId){
				  if(confirm("确定要删除该条记录吗？")){
					  $.post("${pageContext.request.contextPath}/attachefile/deleteFile.izhbg",{"attachId":attachId}, function(data) {
			              if (data.result == 0) {
			                   refresh_list();
			              } else {
			                  alert(data.msg);
			              }

			   			}, "json");
					  	return true;
					 }else 
						 return false;
			} 
		</script>
