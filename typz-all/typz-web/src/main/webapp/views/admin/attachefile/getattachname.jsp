<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<div>

    <input name="attacheId" type="hidden" id="attacheId" value="${attacheId}">
	<div class="form-body">
		<div class="form-group">
			<label class="col-md-3 control-label">附件名称：</label>
			<div class="col-md-4">
				<input name="attacheName" id="attacheName" class="form-control required"  value="${attacheName }"/>
				<span class="help-block"></span>
			</div>
		</div>
	</div>
</div>
