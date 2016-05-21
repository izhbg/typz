<%@tag pageEncoding="UTF-8"%>
<%@tag import="java.util.*"%>
<%@tag import="com.izhbg.typz.sso.util.SpringSecurityUtils"%>
<%@tag import="com.izhbg.typz.sso.auth.UserAuthDTO"%>
<%@attribute name="type" type="java.lang.String" required="true"%>
<%@attribute name="gnDm" type="java.lang.String" required="true"%>
<%
  String type = jspContext.getAttribute("type")+"";
  String gnDm = jspContext.getAttribute("gnDm")+"";
  UserAuthDTO userAuthDTO = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
  List<Map<String,Object>> permissions = userAuthDTO.getPermissions();
  boolean flag = true;
  if(type.equals("create")){
	  for(Map map:permissions){
		  if(gnDm.equals(map.get("GNZY_DM"))&&"1".equals(map.get("is_create")+"")){
			  flag=false;
			  break;
		  }
	  }
	  if(flag){
		  %>
		  <jsp:doBody/>
		  <%
	  }
  }else if(type.equals("delete")){
	  for(Map map:permissions){
		  if(gnDm.equals(map.get("GNZY_DM"))&&"1".equals(map.get("is_delete")+"")){
			   flag=false;
			  break;
		  }
	  }
	  if(flag){
		  %>
		  <jsp:doBody/>
		  <%
	  }
  }else if(type.equals("update")){
	  for(Map map:permissions){
		  if(gnDm.equals(map.get("GNZY_DM"))&&"1".equals(map.get("is_update")+"")){
			  flag=false;
			  break;
		  }
	  }
	  if(flag){
		  %>
		  <jsp:doBody/>
		  <%
	  }
  }else if(type.equals("read")){
	  for(Map map:permissions){
		  if(gnDm.equals(map.get("GNZY_DM"))&&"1".equals(map.get("is_read")+"")){
			  flag=false;
			  break;
		  }
	  }
	  if(flag){
		  %>
		  <jsp:doBody/>
		  <%
	  }
  }
  %>


