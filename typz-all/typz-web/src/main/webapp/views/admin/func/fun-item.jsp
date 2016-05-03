<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!doctype html>
<html>

  <head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
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
			<div class="portlet">
				<div class="portlet-body">
					<div class="row">
						<c:set var="temp" value="init"></c:set>	
						<c:forEach items="${nodes}" var="item3">
							<c:if test="${not empty item3.icon}">
								<c:if test="${temp ne item3.icon}">
									</div>
									</div>
									<div class="portlet-title">
										<div class="caption"><i class="fa fa-reorder"></i></div>
									</div>
									<div class="portlet-body">
										<div class="row">
								</c:if>
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" title="${item3.name}" style="cursor: pointer;" onclick="setMenuState2('${item3.code}','${ctx}/${item3.url }')">
									<div class="dashboard-stat ${item3.other}">
										<div class="visual">
											<i class="fa ${item3.icon}"></i>
										</div>
										<a href="#this" class="more" onclick="setMenuState4('${item3.code}','${ctx}/${item3.url }')" id="${item3.code}">
										 ${item3.name} <i class="m-icon-swapright m-icon-white"></i>
										</a>                 
									</div>
								</div>
								
								<c:set var="temp" value="${item3.icon}"></c:set>	
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	</div>
	</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
	<script type="text/javascript">
		function setMenuState2(id,url){
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
		$(function(){
			$(".thumbnail").hover(function(){
					$(this).css("border","1px solid green");
               },function(){
            	    $(this).css("border","1px solid #ddd");
               });
			
		});
	</script>
</body>
</html>