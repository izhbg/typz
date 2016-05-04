
function logo_user(obj){
	try{
		obj.onerror=null;
		obj.src='pages/images/bfw.gif';
	}catch(ex){}
}
function Get_IE_Version(){
	var v;
	if (navigator.userAgent.indexOf("MSIE 6.0") > 0)
		v = 6;
	 else if (navigator.userAgent.indexOf("MSIE 7.0") > 0)
		v = 7
	 else if (navigator.userAgent.indexOf("MSIE 8.0") > 0)
		v = 8;
	return v;
} 
function isnull(str){if(str==null||str==""||str=="undefine")return true;
 return false;
}
String.prototype.lengthchinese = function(){
			return this.replace(/[^\x00-\xff]/g,"**").length;
}
String.prototype.lenB = function(){return this.replace(/[^\x00-\xff]/g,"**").length;}   
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.allTrim=function(){
	return this.replace(/(\s*)/g, "");
}
function getExt(file) {
	return (/[.]/.exec(file)) ? /[^.]+$/.exec(file.toLowerCase()) : '';
}
function strReverse(s){
	return s.replace(/(<BR>|<BR\/>)/ig, "\n");
}
function strPackage(s){
	if(isnull(s)) return '';
	s=s.replace(/(@)/ig, "＠");
	s=s.replace(/(,)/g, "，");
	return s;
}
function dePackage(s){
	if(isnull(s)) return '';
	s=s.replace(/(＠)/ig, "@");
	return s;
}

function strRemoveBr(s){
	return s.replace(/(<BR>|<BR\/>)/ig, "");
}
function strConvert(s){
	s=s.replace(/(<)/ig, "&lt;");
	s=s.replace(/(\n)/g, "<BR>");
	return s;
}
function GetLocationString(key){
	var aParams = document.location.search.substr(1).split('&') ;
	for (i = 0 ; i < aParams.length ; i++) {
		var aParam = aParams[i].split('=');
		var actionName = aParam[0];
		if(actionName == key){
			return aParam[1];
		}
	}
	return "";
}

function killErrors() { 
return true; 
} 
window.onerror = killErrors; 
function substr(str, len) {
	if(!str || !len) { return ''; }
	var a = 0;
	var temp = '';
	for (var i=0;i<str.length;i++){
		a+=str.charCodeAt(i)>255?2:1;
		if(a >len)return temp;
		temp += str.charAt(i);
	}
	return str;
};

var backHtml='';
function backupInfo(target){
	try{
	if(isnull(target))
		target='submitId';
	backHtml=$('#'+target)[0].innerHTML;
	}catch(ex){}
}
function restoreInfo(target){
	try{
	if(isnull(target))
		target='submitId';
	$('#'+target)[0].innerHTML=backHtml;
	}catch(ex){}
}
function processing(m,target){
try{
	if(isnull(m)) m='<span class=loading>数据处理中，请稍候...</span>';
	if(isnull(target)) target='submitId';
	$('#'+target)[0].innerHTML=m;
	}catch(ex){}
}
function reportError(request){
	alert('系统繁忙，请稍候再试');
}

function BASEisNotNum(theNum){
	//判断是否为数字
	if (isnull(theNum))
	return true;
	for(var i=0;i<theNum.length;i++){
	oneNum=theNum.substring(i,i+1);
	if (oneNum<"0" || oneNum>"9")
	return true;
	}
	return false;
	}

	function BASEisNotInt(theInt){
	  //判断是否为整数
		theInt=theInt.trim();
	  if ((theInt.length>1 && theInt.substring(0,1)=="0") || BASEisNotNum(theInt)){
	  return true;
	  }
	  return false;
	}
	function BASEisNotSignInt(theInt){
	  if(BASEisNotInt(theInt)) return true;
	  if(parseInt(theInt)<0) return true;
	  else return false; 
	}

	function BASEisNotFloat(theFloat){
	  //判断是否为浮点数
	  len=theFloat.length;
	  dotNum=0;
	  if (len==0)
	  return true;
	  for(var i=0;i<len;i++){
	  oneNum=theFloat.substring(i,i+1);
	  if (oneNum==".")
	  dotNum++;
	  if ( ((oneNum<"0" || oneNum>"9") && oneNum!=".") || dotNum>1)
	  return true;
	  }
	  if (len>1 && theFloat.substring(0,1)=="0"){
	  if (theFloat.substring(1,2)!=".")
	  return true;
	  }
	  return false;
	}
	
	//-----------------分页-----------------------------------------------

/*function skip(index){
	var page=document.getElementById('cp'+index).value;
	skipToPage(page);	
}

function skipToPage(page){
	document.getElementById('page.currentPage').value=page;
	$('#form1').submit();
}
function commonJump(){
	skipToPage(document.getElementById('page.currentPage').value);
}
function SetOrder(str){
	var orderFlag=0;
	document.getElementById('page.orderString').value=str;
	if(!isnull(document.getElementById('page.orderFlag').value)) 
		orderFlag=document.getElementById('page.orderFlag').value;
	document.getElementById('page.orderFlag').value=1 - orderFlag;
	$('#form1').submit();
}*/

function skip(index){
	$tab = navTab.getCurrentPanel();
	var page=$tab.find('#cp'+index).val();
	skipToPage(page);
}

function skipToPage(page){
	$tab = navTab.getCurrentPanel();
	$tab.find('#page\\.currentPage').val(page);
	$tab.find('#form1').submit();
}
function commonJump(){
	$tab = navTab.getCurrentPanel();
	skipToPage($tab.find('#page\\.currentPage').val());
}
function SetOrder(str){
	//$tab = navTab.getCurrentPanel();
	var orderFlag=0;
	$('#order').val(str);
	if(!isnull($('#orderBy').val())) 
		orderFlag=$('#orderBy').val();
	if(orderFlag=="ASC")
		orderFlag = "DESC";
	else
		orderFlag = "ASC";
	$('#orderBy').val(orderFlag);
	$('#form1').submit();
}
//-----------------ajax-----------------------------------------------
	function c_updatePars(toUrl,pars,tarDiv){
		try{
			$.ajax({
				  url: toUrl,
			    data: pars,
			    type: 'POST',
				  error: reportError,
				  dataType: 'html',
				  success: function(data){
				 	 $("#"+tarDiv).html(data);
				  }
				});
		}catch(ex){}	
	}
	function reportError(){
		alert('系统繁忙或网络异常！');
		//restoreInfo();
	}
//-----------------checkbox-----------------------------------------------
function funCheck(chkName,div){
	$("[name='"+chkName+"']",$("#"+div)).each(function(){
		if($('#'+chkName+'all').attr("checked"))
			$(this).attr("checked",'true');
		else
			$(this).removeAttr("checked");
	}); 
}

function checkSelect(chkName, div){
	var bRet=false; 
	$("input:checkbox[name='"+chkName+"']:checked").each(function(){
		bRet=true;
		return;
	});
	return bRet;
}
function resetForm(div){
	var $p = navTab.getCurrentPanel();
	if(div)
		$p = $("#" + div);
	$p.find("#form1 input").each(function(){
		if($(this).attr('type')!='button')
			$(this).val("");
	});
	$p.find(".curSelectedNode").removeClass("curSelectedNode");
	$p.find("select option").removeAttr("selected");
	$p.find("select option:first").attr("selected", "true");
}
function resetFormExpApp(div){
	var $p = navTab.getCurrentPanel();
	if(div)
		$p = $("#" + div);
	$p.find("#form1 input[name!='appId']").each(function(){
		if($(this).attr('type')!='button')
			$(this).val("");
	});
	$p.find("#form1 .curSelectedNode").removeClass("curSelectedNode");
	$p.find("#form1 select[name!='appId'] option").removeAttr("selected");
	$p.find("#form1 select[name!='appId'] option:first").attr("selected", "true");
}
function trOnclick(event) {
	var el = event.srcElement ? event.srcElement : event.target;
	$(el).parents("tbody").find('tr.select').removeClass('select');
	$(el).parents("tr").addClass('select');
}
