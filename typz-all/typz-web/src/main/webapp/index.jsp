<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@include file="/common/meta.jsp"%>
    <%@include file="/common/snew.jsp"%> 
  </head>
  <body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="clearfix"></div>
	<div class="page-container">
		<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
			<dl class="frame" data-minheight="65" id="leftcontent" style="min-height: 65px;margin-top: 0px;">
			<div class="portlet box light-grey">
				<div class="portlet-title">
					<div class="caption"><i class="fa fa-reorder"></i>快捷操作</div>
					<div class="tools">
						<a href="" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body" style="display: block;">
					<div class="row">
						<c:forEach items="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.treeNode.children}" var="item">
						<c:if test="${item.sqbj eq 2}">
							<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" title="${item.name}" onclick="setMenuState4('${item.code}','${ctx}/${item.url }')" style="cursor: pointer;">
								<div class="dashboard-stat ${item.other}<c:if test="${item.other eq ''}">blue</c:if>">
									<div class="visual">
										<i class="fa ${item.icon}<c:if test="${item.icon eq ''}">fa-folder</c:if>"></i>
									</div>
									<a class="more" href="#this" id="${item.code}" onclick="setMenuState4('${item.code}','${ctx}/${item.url }')">
									 	${item.name} <i class="m-icon-swapright m-icon-white"></i>
									</a>                 
								</div>
							</div>
						</c:if>	 
						<c:if test="${fn:length(item.children) ne 0}">
							<c:forEach items="${item.children}" var="item2">
								<c:if test="${item2.sqbj eq 2}">
									<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" title="${item2.name}" onclick="setMenuState4('${item2.code}','${ctx}/${item2.url }')" style="cursor: pointer;">
										<div class="dashboard-stat ${item2.other}<c:if test="${item2.other eq ''}">blue</c:if>">
											<div class="visual">
												<i class="fa ${item2.icon}<c:if test="${item2.icon eq ''}">fa-folder</c:if>"></i>
											</div>
											<a class="more" href="#this" id="${item2.code}" onclick="setMenuState4('${item2.code}','${ctx}/${item2.url }')">
												 ${item2.name} <i class="m-icon-swapright m-icon-white"></i>
											</a>                 
										</div>
									</div>
								</c:if>	
							</c:forEach>
						</c:if>
					</c:forEach>
					</div>
				</div>
			</div>
			</dl>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
  </body>
</html>