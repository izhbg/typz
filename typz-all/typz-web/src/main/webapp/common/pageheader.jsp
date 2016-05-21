<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
	<input type="hidden" id="ctx" name="ctx" value="${ctx}">
	<ul class="page-breadcrumb breadcrumb breadcrumbs" style="margin-bottom: 0px;padding:10px 26px;border-bottom:1px solid #e1e1e1;-webkit-box-shadow:1px 0 3px #eee,inset 0 0 3px #fff;box-shadow:1px 0 3px #eee,inset 0 0 3px #fff;background-color: #fafafa;">  
		<li>
			<a href="#"  onclick="setMenuState4('2222','${ctx}/main/common.izhbg')" style="font-size: 20px;font-weight: 500;color: #d84c31;"><i class="fa fa-home"></i>首页</a> 
		</li>
		<li>
			<a href="#" style="font-size: 20px;font-weight: 500;color: #d84c31;">${sessionScope.currentTopMenuName}</a> 
		</li>
		<c:if test="${sessionScope.currentTopMenuName ne sessionScope.currentSubMenuName}">
			<li><a href="#"  onclick="setMenuState4('2222','${sessionScope.url}')" style="font-size: 20px;font-weight: 500;color: #d84c31;">${sessionScope.currentSubMenuName}</a></li>
		</c:if>
		<c:if test="${not empty sessionScope.submenutext}">
			<li><a href="#" style="font-size: 20px;font-weight: 500;color: #d84c31;">${sessionScope.submenutext}</a></li>
		</c:if>
		<jsp:useBean id="now" class="java.util.Date" />   
		<div class="pull-right" style="padding-right: 0px;padding-top: 5px;"><span class="ng-binding" style="font-size: 14px;color: #8a959e;">今天是<fmt:formatDate value="${now}" type="date" dateStyle="full"/></span></div>
	</ul>
	<script type="text/javascript">
		function setMenuState4(id,url){
			wait();
			var submenutext = $("#"+id).text();
		 jQuery.ajax({
	          type: "POST",
	          url: "${ctx}/session/setSession.izhbg",
	          async: false,      //是否ajax同步
	          data: "submenutext="+submenutext+"&gnDm="+id,
	          success: function(msg) {
		          window.location.href = url;
	          }
	      	});
	      
		}
		 function wait(){
			  $("#basicmodel").modal('show');
		  }
		 function stop(){
			  $("#basicmodel").modal('hide');
		  }
	</script>
	<div class="modal fade" id="basicmodel" tabindex="-1" role="basic" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<img src="${ctx}/s/assets/img/loading_ui.gif">
					正在提交数据，请耐心等待...
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
