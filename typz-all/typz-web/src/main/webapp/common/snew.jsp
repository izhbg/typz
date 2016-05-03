<%@page language="java" pageEncoding="UTF-8" %>
<title>通用配置</title>
<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon" /> 
<!-- ************************************************************************-->
<!--******************************** style******************************** -->
<!-- ************************************************************************-->
<!--  字体 -->
<link href="${ctx}/s/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

<!-- 全局-->
<link href="${ctx}/s/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/s/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${ctx}/s/assets/css/custom.css" rel="stylesheet" type="text/css"/>

<!-- ************************************************************************-->
<!-- ******************************** script ********************************-->
<!-- ************************************************************************-->
<!-- jqeury -->
<script src="${ctx}/s/assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${ctx}/s/assets/scripts/app.js" type="text/javascript"></script>


<!-- ************************************************************************-->
<!--******************************** plugins ********************************-->
<!-- ************************************************************************-->
<!-- jquery validate -->
<link href="${ctx}/s/assets/plugins/jquery-validation/jquery.validate.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/additional-methods.min.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/jquery-validation/localization/messages_zh_CN.js"></script>
<!-- bootstrap -->
<script src="${ctx}/s/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/s/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>

<!-- cookies -->
<script src="${ctx}/s/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>

<!-- table -->
<script src="${ctx}/s/assets/plugins/table/table.js" type="text/javascript"></script>  

<!-- tree -->
<link rel="stylesheet" href="${ctx}/s/assets/plugins/ztree/css/zTreeStyle/metro.css" type="text/css" />
<script type="text/javascript" src="${ctx}/s/assets/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/s/assets/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>

<!-- icheck -->
<link rel="stylesheet" href="${ctx}/s/assets/plugins/iCheck/skins/square/blue.css" type="text/css" />
<script type="text/javascript" src="${ctx}/s/assets/plugins/iCheck/icheck.min.js"></script>

<script>
  jQuery(document).ready(function() {  
     App.init(); // initlayout and core plugins
  });
</script>
