<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="org.springframework.context.ApplicationContext"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="com.izhbg.typz.sso.auth.manager.TXtYhManager"%>
<%@tag import="com.izhbg.typz.sso.auth.dto.TXtYh"%>
<%@attribute name="userId" type="java.lang.Object" required="true"%>
<%
  Object userId = jspContext.getAttribute("userId");
  if (userId == null) {
    out.print("");
  } else {

    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    TXtYhManager tXtYhManager = ctx.getBean(TXtYhManager.class);
    try {
      out.print(tXtYhManager.findUniqueBy("yhId",userId.toString()).getYhMc());
    } catch(Exception ex) {
      System.out.println(userId);
    }
  }
%>
