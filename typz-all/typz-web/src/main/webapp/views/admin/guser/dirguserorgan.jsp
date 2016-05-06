<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<link href="pages/admin/css/ucenter.css" rel="stylesheet" type="text/css" />
<script src="pages/admin/js/common.js" type="text/javascript"></script>
<script src="pages/admin/js/ucenter.js" type="text/javascript"></script>
<div class="layoutBoxPadding">
	<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
	<div class="pageHeader pageHeader2">
		<div class="panelBar panelAction">
			<div class="search">
				<span class="tips">用户代码：<ww:property value="user.yhDm"/>&nbsp;&nbsp;&nbsp;&nbsp;用户名称：<ww:property value="user.yhMc"/></span>
			</div>
			<div class="actionBox" style="padding: 3px 15px;">
				<a href="javascript:;" onclick="navTab.reloadCurrentTab();"><i class="refresh"></i>刷新</a>
			</div>
		</div>
	</div>
	<form id="form1" name="form1" rel="pagerForm" onsubmit="return navTabSearch(this);" action="admin/dirGUserOrgan.action" method="post">
		<input type="hidden" name="yhId" id="yhId" type="text" value='<ww:property value="yhId"/>' />
		<input type="hidden" name="page.orderFlag" id="page.orderFlag" value="<ww:property value='page.orderFlag'/>"> 
		<input type="hidden" name="page.orderString" id="page.orderString" value="<ww:property value='page.orderString'/>">
		<div class="pageHeader search">
			<div id="searchBarId" class="searchBar" style="border: 0;margin-bottom:0;">
				<ul class="searchContent lix" >
					<li>
						<label>机构代码：</label>
						<input name="jgId" id="jgId" type="text" value='<ww:property value="jgId"/>' />&nbsp;
					</li>
					<li>
						<label>机构名称：</label>
						<input name="jgMc" id="jgMc" type="text" value='<ww:property value="jgMc"/>' />&nbsp;
					</li>
					<li><button class="btn_submit" type="submit">查&nbsp;&nbsp;询</button></li>
					<li>
						<a href="admin/getGUserOrganTree.action?yhId=<ww:property value="user.yhId"/>" target="dialog" rel="dialog1">
							<input type="button" class="btn_submit" style="border:0;" value="增&nbsp;&nbsp;加"/>
						</a>
					</li>
					<li>
						<a href="javascript:uo_del()">
							<input type="button" class="btn_submit" style="border:0;" value="删&nbsp;&nbsp;除"/>
						</a>
					</li>
					<li><span class="emptyButton"><a href="javascript:;" onClick="resetForm();"><i></i>清空</a></span></li>
				</ul>
			</div>
		</div>
		<!-- 搜索条件结束 -->

		<div class="pageContent lix">
			<div class="grid" style="padding-top: 0px;">
				<table class="table" id="test1" style="width: 100%" >
					<thead class="gridHeader">
						<tr class="gridThead">
							<th width="50" style="text-align:center;" class="id"><input type="checkbox" name="checkdelall" id="checkdelall" 
								 onclick="funCheck('checkdel')" /></th>
							<th class="sort"><a href="javascript:SetOrder('b.jg_id')">机构id</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.jg_dm')">机构代码</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.jg_mc')">机构名称</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.sjjg_id');">上级机构id</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.yx_bj')">有效标记</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.sc_bj')">删除标记</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.xh')">序号</a></th>
							<th class="sort"><a href="javascript:SetOrder('b.lr_rq')">录入日期</a></th>
						</tr>
					</thead>
					<tbody class="gridTbody">
						<ww:iterator value="page.results" status="status">
							<tr id="<ww:property value="duId"/>" onclick="trOnclick(event);" 
								<ww:if test='#status.count%2==0'>class='odd'</ww:if><ww:else>class='even'</ww:else>>
								<td style="text-align:center;" class="id">
									<input type="checkbox" name="checkdel" id="checkdel" value="<ww:property value="duId" />"/>
								</td>
								<td><ww:property value="jg.jgId" /></td>
								<td><ww:property value="jg.jgDm" /></td>
								<td><ww:property value="jg.jgMc" /></td>
								<td><ww:property value="jg.sjjgId" /></td>
								<td><ww:property value="jg.yxBj" /></td>
								<td><ww:property value="jg.scBj" /></td>
								<td><ww:property value="jg.xh" /></td>
								<td><ww:property value="jg.lrRq" /></td>
							</tr>
						</ww:iterator>
					</tbody>
				</table>
			</div>
			<div class="panelBar panelPages ">
				<ww:property value="page.pageSplit" />
			</div>
		</div>
	</form>
</div>