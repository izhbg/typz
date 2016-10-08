<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="true" pageEncoding="UTF-8"%>
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
				<div class="row inbox">
				<div class="col-md-3">
					<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">组织管理</h3>
					</div>
					<div class="panel-body">
						<select style="width: 100%;" onchange="location.href='${ctx }/user/user-list.izhbg?currentAppId=' + this.value">
						  <c:forEach items="${tXtYyList}" var="item">
							 <option value="${item.yyId }" <c:if test="${item.yyId eq parameterMap.currentAppId}">selected="selected" </c:if> >${item.chineseName }</option>
						 </c:forEach>
						</select>
			            <ul id="user_treeDemo" class="ztree"></ul>
					</div>
					</div>
				 </div>
				 <div class="col-md-9" style="padding-left: 0px;"> 
					 <jsp:include page="/views/admin/guser/dirgusermini.jsp"></jsp:include>
				 </div>
			</div>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
<script>
var user_setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		async: {
			enable: true,
			url:"${ctx}/user/getOrgTree.izhbg",
			autoParam:["id=id"],
			otherParam: ["appId", '${parameterMap.currentAppId}'],
			type:"post"
		},
		callback : {
			onClick : user_onClick
		}
	};
function user_onClick(e, treeId, treeNode) {
	$("#sjjgId").val(treeNode ? treeNode.id : "");
	$("#form1").submit();
}
var user_zNodes =${result};
var treeObj = $.fn.zTree.init($("#user_treeDemo"), user_setting, user_zNodes);
var node = treeObj.getNodeByParam("id", $("#sjjgId").val());
treeObj.selectNode(node,false);
</script>
