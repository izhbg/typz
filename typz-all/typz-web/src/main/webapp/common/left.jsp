<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar navbar-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->  
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!--<form class="sidebar-search" action="#this" method="POST">
						<div class="form-container">
							<div class="input-box">
								<a href="javascript:;" class="remove"></a>
								<input type="text" placeholder="查询..."/>
								<input type="button" class="submit" value=" "/>
							</div>
						</div>
					</form>-->
					<br>
					<br>
				</li>
				<c:forEach items="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.treeNode.children}" var="item">
					<c:if test="${fn:length(item.children) eq 0}">
						<li class="start <c:if test="${sessionScope.currentTopMenu==item.code}">active</c:if>  "> 
							<a href="${ctx}/${item.url }" onclick="setMenuState('${item.code}')" >
							<i class="fa ${item.icon}"></i> 
							<span class="title">${item.name }</span> 
							<span class=" <c:if test="${sessionScope.currentTopMenu==item.code}">selected</c:if> "></span>
							</a>
						</li>
					</c:if>
					<c:if test="${fn:length(item.children) ne 0}">
						<li class="<c:if test="${sessionScope.currentTopMenu==item.code}">active</c:if> " id="${item.code }">
							<a href="javascript:;">
							<i class="fa ${item.icon}"></i> 
							<span class="title">${item.name}</span>
							<c:if test="${sessionScope.currentTopMenu==item.code}"><span class="selected"></span></c:if>
							<span class="arrow <c:if test="${sessionScope.currentTopMenu==item.code}">open</c:if>"></span>
							</a>
							<ul class="sub-menu">
								<c:forEach items="${item.children}" var="item2">
									<c:if test="${fn:length(item2.children) ne 0}">
										<li id="${item2.code }" class="<c:if test="${sessionScope.currentSubMenu==item2.code}">active</c:if> "><a href="${ctx}/fun/fun-item.izhbg?gnDm=${item2.code }" onclick="setMenuState3('${item2.code }','${ctx}/fun/fun-item.izhbg?gnDm=${item2.code }')"><span>${item2.name}</span></a></li>
									</c:if>
									<c:if test="${fn:length(item2.children) eq 0}">
										<li id="${item2.code }" class="<c:if test="${sessionScope.currentSubMenu==item2.code}">active</c:if> "><a href="${ctx}/${item2.url }" onclick="setMenuState('${item2.code }')"><span>${item2.name}</span></a></li>
									</c:if>
									
								</c:forEach>
							</ul>
							</li>
					</c:if>
				</c:forEach>
				<!--
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-cogs"></i> 
					<span class="title">后台管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
					  	<li><a href="${ctx}/user/user-list.izhbg"><span class="badge badge-roundless badge-important">new</span>用户管理</a></li>
					  	<li><a href="${ctx}/role/role-list.izhbg"><span class="badge badge-roundless badge-important">new</span>角色管理</a></li>
					  	<li><a href="${ctx}/post/post-list.izhbg"><span class="badge badge-roundless badge-important">new</span>岗位管理</a></li>
					  	<li><a href="${ctx}/org/org-list.izhbg"><span class="badge badge-roundless badge-important">new</span>机构管理</a></li>
					  	<li><a href="${ctx}/fun/fun_list.izhbg"><span class="badge badge-roundless badge-important">new</span>功能管理</a></li>
					  	<li><a href="${ctx}/sys/sys_list.izhbg"><span class="badge badge-roundless badge-important">new</span>应用管理</a></li>
					</ul>
				</li>
			 
				 
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-cogs"></i> 
					<span class="title">我的流程</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/bpm/workspace-home.izhbg"><span class="badge badge-roundless badge-important">new</span>发起新流程</a></li>
					  	<li><a href="${ctx}/bpm/workspace-listRunningProcessInstances.izhbg"><span class="badge badge-roundless badge-important">new</span>运行的流程</a></li>
					  	<li><a href="${ctx}/bpm/workspace-listCompletedProcessInstances.izhbg"><span class="badge badge-roundless badge-important">new</span>办结的流程</a></li>
					  	<li><a href="${ctx}/bpm/workspace-listInvolvedProcessInstances.izhbg"><span class="badge badge-roundless badge-important">new</span>参与的流程</a></li>
					  	<li><a href="${ctx}/form/form-listDrafts.izhbg"><span class="badge badge-roundless badge-important">new</span>草稿箱</a></li>
					  	<li><a href="${ctx}/user/user-list.izhbg"><span class="badge badge-roundless badge-important">new</span>草稿箱</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-cogs"></i> 
					<span class="title">后台管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
					  	<li><a href="${ctx}/user/user-list.izhbg"><span class="badge badge-roundless badge-important">new</span>用户管理</a></li>
					  	<li><a href="${ctx}/role/role-list.izhbg"><span class="badge badge-roundless badge-important">new</span>角色管理</a></li>
					  	<li><a href="${ctx}/post/post-list.izhbg"><span class="badge badge-roundless badge-important">new</span>岗位管理</a></li>
					  	<li><a href="${ctx}/org/org-list.izhbg"><span class="badge badge-roundless badge-important">new</span>机构管理</a></li>
					  	<li><a href="${ctx}/fun/fun_list.izhbg"><span class="badge badge-roundless badge-important">new</span>功能管理</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-bookmark-o"></i> 
					<span class="title">我的任务</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
					  <li><a href="${ctx}/bpm/workspace-listPersonalTasks.izhbg">待办任务</a></li>
					  <li><a href="${ctx}/bpm/workspace-listGroupTasks.izhbg">待领任务</a></li>
					  <li><a href="${ctx}/bpm/workspace-listHistoryTasks.izhbg">已办任务</a></li>
					  <li><a href="${ctx}/bpm/workspace-listDelegatedTasks.izhbg">代理中的任务</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-table"></i> 
					<span class="title">委托设置</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/bpm/delegate-prepareAutoDelegate.izhbg">设置自动委托</a></li>
			 			<li><a href="${ctx}/bpm/delegate-listMyDelegateInfos.izhbg">自动委托规则</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-sitemap"></i> 
					<span class="title">系统管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
					  <li><a href="${ctx}/user/user-base-list.izhbg"><i class="icon-user"></i>用户管理</a></li>
		              <li><a href="${ctx}/group/org-users.izhbg"><i class="icon-user"></i>组织机构</a></li>
					  <li><a href="${ctx}/form/form-template-list.izhbg"><i class="icon-user"></i>表单管理</a></li>
		              <li><a href="${ctx}/cms/cms-article-list.izhbg"><i class="icon-user"></i>公告管理</a></li>
		              <li><a href="${ctx}/party/tree-list.izhbg"><i class="icon-user"></i>系统配置</a></li>
					</ul>
				</li>
				<li class="active ">
					<a href="javascript:;">
					<i class="fa fa-gift"></i> 
					<span class="title">统计报表</span>
					<span class="selected"></span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu">
						 <li><a href="${ctx}/report/chart-mostActiveProcess.izhbg"><i class="icon-user"></i>最活跃流程</a></li>
					</ul>
				</li>
				<li>
					<a class="active" href="javascript:;">
					<i class="fa fa-leaf"></i> 
					<span class="title">权限配置</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						 <li><a href="${ctx}/auth/user-connector-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.usermanage" text="用户管理"/></a></li>
						  <li><a href="${ctx}/auth/role-def-list.izhbg"><i class="icon-user"></i>角色模板</a></li>
						  <li><a href="${ctx}/auth/role-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.rolemanage" text="角色管理"/></a></li>
						  <li><a href="${ctx}/auth/perm-type-list.izhbg"><i class="icon-user"></i>授权分类</a></li>
						  <li><a href="${ctx}/auth/perm-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.permmanage" text="授权管理"/></a></li>
						  <li><a href="${ctx}/auth/access-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.accessmanage" text="访问权限"/></a></li>
					</ul>
				</li>
				<li>
					<a href="javascript:;">
					<i class="fa fa-folder-open"></i> 
					<span class="title">流程管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/bpm/bpm-category-list.izhbg"><i class="icon-user"></i>流程分类</a></li>
						  <li><a href="${ctx}/bpm/bpm-process-list.izhbg"><i class="icon-user"></i>流程定义</a></li>
						  <li><a href="${ctx}/bpm/bpm-mail-template-list.izhbg"><i class="icon-user"></i>邮件模板</a></li>
						  <li><a href="${ctx}/modeler/modeler-list.izhbg"><i class="icon-user"></i>发布流程</a></li>
						  <li><a href="${ctx}/bpm/console-listProcessDefinitions.izhbg"><i class="icon-user"></i>流程定义</a></li>
						  <li><a href="${ctx}/bpm/console-listProcessInstances.izhbg"><i class="icon-user"></i>流程实例</a></li>
						  <li><a href="${ctx}/bpm/console-listTasks.izhbg"><i class="icon-user"></i>任务</a></li>
						  <li><a href="${ctx}/bpm/console-listDeployments.izhbg"><i class="icon-user"></i>部署</a></li>
						  <li><a href="${ctx}/bpm/console-listHistoricProcessInstances.izhbg"><i class="icon-user"></i>历史流程实例</a></li>
						  <li><a href="${ctx}/bpm/console-listHistoricActivityInstances.izhbg"><i class="icon-user"></i>历史节点</a></li>
						  <li><a href="${ctx}/bpm/console-listHistoricTasks.izhbg"><i class="icon-user"></i>历史任务</a></li>
						  <li><a href="${ctx}/bpm/delegate-listDelegateInfos.izhbg"><i class="icon-user"></i>自动委托</a></li>
						  <li><a href="${ctx}/bpm/delegate-listDelegateHistories.izhbg"><i class="icon-user"></i>自动委托记录</a></li>
						  <li><a href="${ctx}/bpm/job-list.izhbg"><i class="icon-user"></i>异步消息管理</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-user"></i> 
					<span class="title">应用管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/scope/scope-info-list.izhbg"><i class="icon-user"></i>应用管理</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-th"></i> 
					<span class="title">用户配置</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/user/user-repo-list.izhbg"><i class="icon-user"></i>用户库列表</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-file-text"></i> 
					<span class="title">组织管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						 <li><a href="${ctx}/group/org-company-list.izhbg"><i class="icon-user"></i>公司</a></li>
					  <li><a href="${ctx}/group/org-department-list.izhbg"><i class="icon-user"></i>部门</a></li>
					  <li><a href="${ctx}/group/org-group-list.izhbg"><i class="icon-user"></i>小组</a></li>
					  <li><a href="${ctx}/group/org-position-type-list.izhbg"><i class="icon-user"></i>岗位类型</a></li>
					  <li><a href="${ctx}/group/org-position-list.izhbg"><i class="icon-user"></i>岗位</a></li>
					</ul>
				</li>
				<li class="">
					<a href="javascript:;">
					<i class="fa fa-map-marker"></i> 
					<span class="title">组织机构管理</span>
					<span class="arrow "></span>
					</a>
					<ul class="sub-menu">
						<li><a href="${ctx}/party/tree-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.tree" text="组织机构图"/></a></li>
						  <li><a href="${ctx}/party/party-entity-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.org" text="组织机构"/></a></li>
						  <li><a href="${ctx}/party/party-struct-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.struct" text="组织机构结构"/></a></li>
						  <li><a href="${ctx}/party/party-type-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.type" text="组织机构类型"/></a></li>
						  <li><a href="${ctx}/party/party-struct-type-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.struct.type" text="组织机构结构类型"/></a></li>
						  <li><a href="${ctx}/party/party-struct-rule-list.izhbg"><i class="icon-user"></i><spring:message code="layout.leftmenu.struct.rule" text="组织机构结构规则"/></a></li>
						  <li><a href="${ctx}/party/party-dim-list.izhbg"><i class="icon-user"></i>维度</a></li>
						  <li><a href="${ctx}/party/party-dim-root-list.izhbg"><i class="icon-user"></i>顶级组织</a></li>
					</ul>
				</li>
				<div class="accordion-group">
 +          <div class="accordion-heading">
 +            <a class="accordion-toggle" data-toggle="collapse" data-parent="#m-sidebar" href="#collapse-workcal">
 +              <i class="icon-user"></i>
 +              <span class="title">工作日历</span>
 +            </a>
 +          </div>
 +          <div id="collapse-workcal" class="accordion-body collapse ${currentMenu == 'workcal' ? 'in' : ''}">
 +            <ul class="accordion-inner nav nav-list">
 +			  <li class="m-icn-view-users"><a href="${ctx}/workcal/workcal-type-list.izhbg">工作日历类型</a></li>
 +			  <li class="m-icn-view-users"><a href="${ctx}/workcal/workcal-rule-list.izhbg">工作日历规则</a></li>
 +			  <li class="m-icn-view-users"><a href="${ctx}/workcal/workcal-part-list.izhbg">工作日历时间段</a></li>
 +			  <li class="m-icn-view-users"><a href="${ctx}/workcal/workcal-view.izhbg">工作日历</a></li>
 +            </ul>
 +          </div>
 +        </div>
 +
				<li class="last ">
					<a href="#this">
					<i class="fa fa-bar-chart-o"></i> 
					<span class="title">Visual Charts</span>
					</a>
				</li>-->
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<script type="text/javascript">
			
			function setMenuState(id){
				wait();
				var currentTopMenu = id;
				var currentSubMenu = id;
				var currentSubMenuName = $("#"+id+" span:last").text();
				var currentTopMenuName = currentSubMenuName;
				
				try{
					var idtop = $("#"+id).parent().parent().attr("id");
					if(idtop!=undefined){
						currentTopMenu = idtop
						currentTopMenuName =$("span:first",$("#"+id).parent().parent()).text();
						}
					}catch(e){
						
					}
			 jQuery.ajax({
	              type: "POST",
	              url: "${ctx}/session/setSession.izhbg",
	              async: false,      //是否ajax同步
	              data: "currentTopMenu=" + currentTopMenu+"&currentSubMenu="+currentSubMenu+"&currentTopMenuName="+currentTopMenuName+"&currentSubMenuName="+currentSubMenuName+"&gnDm="+id,
	              success: function(msg) {
	              }
	          	});
	          
			}
			function setMenuState3(id,url){
				wait();
				var currentTopMenu = id;
				var currentSubMenu = id;
				var currentSubMenuName = $("#"+id+" span:last").text();
				var currentTopMenuName = currentSubMenuName;
				
				try{
					var idtop = $("#"+id).parent().parent().attr("id");
					if(idtop!=undefined){
						currentTopMenu = idtop
						currentTopMenuName =$("span:first",$("#"+id).parent().parent()).text();
						}
					}catch(e){
						
					}
			 jQuery.ajax({
	              type: "POST",
	              url: "${ctx}/session/setSession.izhbg",
	              async: false,      //是否ajax同步
	              data: "currentTopMenu=" + currentTopMenu+"&currentSubMenu="+currentSubMenu+"&currentTopMenuName="+currentTopMenuName+"&currentSubMenuName="+currentSubMenuName+"&gnDm="+id+"&url="+url,
	              success: function(msg) {
	              }
	          	});
	          
			}
			function setMenuState4session(id,url,name){
				wait();
				 jQuery.ajax({
		          type: "POST",
		          url: "${ctx}/session/setSession.izhbg",
		          async: false,      //是否ajax同步
		          data: "submenutext="+name+"&gnDm="+id,
		          success: function(msg) {
			          window.location.href = url;
		          }
		      	});
		      
			}
		</script>
<!-- END SIDEBAR -->