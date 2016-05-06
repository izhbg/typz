<%@ page language="java" contentType="text/html; charset=UTF-8"
	session="false" pageEncoding="UTF-8"%>
<script>
function ou_add(jgId){
	var pars = "jgId=" + jgId + "&" +$("#div_ou").find("#form1").serialize();
	$.post("admin/addOrganGUser.action",pars,function(data){
		switch(data.result){
			case 0:
				$.dialog({id:"dialog1"}).close();
				$.dialog.tip(data.msg);
				navTab.getCurrentPanel().find("#form1").submit();
				break;
			case 1:
				$.dialog.alert(data.msg);
				break;
		}
	},'json');
}
function ouskipToPage(page){
$("#div_ou").find('#page\\.currentPage').val(page);
$("#div_ou").find('#form1').submit();
}
function oucommonJump(){
ouskipToPage($("#div_ou").find('#page\\.currentPage').val());
}
</script>
<div class="layoutBoxPadding" style="width:600px;">
	<div id="div_Mask11" class="div_Mask" style="display: none;"></div>
	<form id="form1" name="form1" rel="pagerForm" onsubmit='return divSearch(this,"div_ou");' action="admin/getOrganGUserPage.action?jgId=<ww:property value="jgId"/>" method="post">
		<input type="hidden" name="page.orderFlag" id="page.orderFlag" value="<ww:property value='page.orderFlag'/>"> 
		<input type="hidden" name="page.orderString" id="page.orderString" value="<ww:property value='page.orderString'/>">
		<div class="pageHeader search">
			<div id="searchBarId" class="searchBar" style="border: 0;margin-bottom:0;">
				<ul class="searchContent lix" style="padding-left: 0;height:33px;">
					<li style="width:180px;">
						<label>用户代码：</label>
						<input style="width:80px;" name="yhDm" id="yhDm" type="text" value='<ww:property value="yhDm"/>' />&nbsp;
					</li>
					<li style="width:180px;">
						<label>用户名称：</label>
						<input style="width:80px;" name="yhMc" id="yhMc" type="text" value='<ww:property value="yhMc"/>' />&nbsp;
					</li>
					<li style="width:190px;">
						<button class="btn_submit"  type="submit" style="float:left;">查&nbsp;&nbsp;询</button>
						<span class="emptyButton" style="float:left;margin-left:15px;margin-right:0;"><a href="javascript:;" onClick="resetForm('div_ou');"><i></i>清空</a></span>
					</li>
				</ul>
			</div>
		</div>
		<!-- 搜索条件结束 -->

		<div class="pageContent lixdg">
			<div class="grid" style="padding-top: 0px;">
				<table class="table" id="test1" style="width: 100%;">
					<thead class="gridHeader">
						<tr class="gridThead">
							<th width="50" style="text-align:center;width:27px;" class="id"><input type="checkbox" name="checkdelall" id="checkdelall" 
								 onclick="funCheck('checkdel','div_ou')" /></th>
							<th>登录名</th>
							<th>姓名</th>
							<th>有效标记</th>
						</tr>
					</thead>
					<tbody class="gridTbody">
						<ww:iterator value="page.results" status="status">
							<tr id="<ww:property value="yhId"/>" onclick="trOnclick(event);" 
								<ww:if test='#status.count%2==0'>class='odd'</ww:if><ww:else>class='even'</ww:else>>
								<td style="text-align:center;" class="id">
									<input type="checkbox" name="checkdel" id="checkdel" value="<ww:property value="yhId" />"
										<ww:if test="check==true">disabled checked</ww:if>/>
								</td>
								<td><ww:property value="yhDm" /></td>
								<td><ww:property value="yhMc" /></td>
								<td><ww:property value="#dictNew.getDictItem('d_validflag',yxBj).itemName" /></td>
							</tr>
						</ww:iterator>
					</tbody>
				</table>
			</div>
			<div class="panelBar panelPages ">
				<ww:property value="page.getPageSplit('ou')" />
			</div>
		</div>
	</form>
</div>
<div style="text-align:center;">
	<input type="button" class="btn_submit" style="border:0;" value="提&nbsp;&nbsp;交"
			onclick="ou_add('<ww:property value="jgId"/>')">&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="button_nor" value="&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;"
					onclick='$.dialog({id:"dialog1"}).close()'>
</div>

