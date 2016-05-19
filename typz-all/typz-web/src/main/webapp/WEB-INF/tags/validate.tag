<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@tag import="com.izhbg.typz.database.dto.MainTableColumn"%>
<%@attribute name="mainTableColumn" type="com.izhbg.typz.database.dto.MainTableColumn" required="true"%>
<%
	MainTableColumn mainTableColumn = (MainTableColumn)jspContext.getAttribute("mainTableColumn");
    if(mainTableColumn!=null)
	if("1".equals(mainTableColumn.getIsMust())||
	  ("1".equals(mainTableColumn.getIsUnique()))||
	  ("0".equals(mainTableColumn.getValidDataType())&&"".equals(mainTableColumn.getValidDataType())&&mainTableColumn.getValidDataType()==null)){
		
		if("1".equals(mainTableColumn.getIsMust())){
			out.print("required");
		}
	}
%>