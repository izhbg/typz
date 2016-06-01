<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<%@include file="/common/snew.jsp"%>
</head>
<body class="page-header-fixed">
<jsp:include page="/common/header.jsp"></jsp:include> 
<div class="clearfix"></div>
<div class="page-container" id="dgformdiv">
	<jsp:include page="/common/left.jsp"></jsp:include>
		<div class="page-header" style="margin-top: 0px;margin-bottom: 0px;padding-bottom: 0px; "> 
			<jsp:include page="/common/pageheader.jsp"></jsp:include>
		</div>
		<div class="page-content">
				<div class="row inbox">
				 <div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading"><h3 class="panel-title">
							Test [${clientDetailsDto.clientId}]
						</h3></div>
						 <div ng-controller="TestClientCtrl">
				        <c:if test="${clientDetailsDto.containsAuthorizationCode}">
				            <div class="panel panel-default">
				                <div class="panel-heading">Test [authorization_code]</div>
				                <div class="panel-body">
				                    <p class="text-muted">输入每一步必要的信息后点击其下面的测试按钮.</p>
				                    <ol>
				                        <li>
				                            <p>
				                                <code>从 spring-oauth-server获取 'code'<span class="label label-info">GET</span></code>
				                                <form action="${ctx}/oauth/authorize.izhbg" method="get" target="_blank" class="form-horizontal">
				                                	<div class="form-group">
													  <label class="col-md-2 control-label">redirect_uri</label>
													  <div class="col-md-8">
													  	<input name="redirect_uri" id="redirect_uri" class="form-control" value="${clientDetailsDto.webServerRedirectUri}" readonly="readonly">
													  </div>
													</div>
				                                	<div class="form-group">
													  <label class="col-md-2 control-label">client_id</label>
													  <div class="col-md-8">
													  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
													  </div>
													</div>
				                                	<div class="form-group">
													  <label class="col-md-2 control-label">response_typ</label>
													  <div class="col-md-8">
													  	<input name="response_type" class="form-control" id="response_type" value="code" readonly="readonly">
													  </div>
													</div>
				                                	<div class="form-group">
													  <label class="col-md-2 control-label">scope</label>
													  <div class="col-md-8">
													  	 <input name="scope" id="scope" class="form-control" value="${fn:replace(clientDetailsDto.scope,',', ' ')}" readonly="readonly">
													  </div>
													</div>
							                        <button class="btn green" type="submit" >
					                                  	 测试
					                                </button>
												</form>
				                                
				                            </p>
				                        </li>
				                        <li>
				                            <code>用 'code' 换取 'access_token'<span class="label label-warning">POST</span></code><br>
				                            <form action="${ctx}/oauth/token.izhbg"  method="post" target="_blank" class="form-horizontal">
				                                  <div class="form-group">
													  <label class="col-md-2 control-label"> 输入第一步获取的code:</label>
													  <div class="col-md-8">
													  	<input name="code" id="code" class="form-control" value="">
													  </div>
												  </div>
				                                  <div class="form-group">
													  <label class="col-md-2 control-label"> client_id</label>
													  <div class="col-md-8">
													  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
													  </div>
												  </div>
				                                  <div class="form-group">
													  <label class="col-md-2 control-label"> client_secret</label>
													  <div class="col-md-8">
													  	<input name="client_secret" id="client_secret" class="form-control" value="${clientDetailsDto.clientSecret}" readonly="readonly">
													  </div>
												  </div>
				                                  <div class="form-group">
													  <label class="col-md-2 control-label"> grant_type</label>
													  <div class="col-md-8">
													  	<input name="grant_type" id="grant_type" class="form-control" value="authorization_code" readonly="readonly">
													  </div>
												  </div>
				                                  <div class="form-group">
													  <label class="col-md-2 control-label">redirect_uri</label>
													  <div class="col-md-8">
													  	<input name="redirect_uri" id="redirect_uri" class="form-control" value="${clientDetailsDto.webServerRedirectUri}" readonly="readonly">
													  </div>
												  </div>
				                                <button class="btn green" type="submit">
				                                  	 测试
				                                </button>
				                            </form>
				                        </li>
				                    </ol>
				                </div>
				            </div>
				        </c:if>
				
				        <c:if test="${clientDetailsDto.containsPassword}">
				            <div class="panel panel-default">
				                <div class="panel-heading">Test [password]</div>
				                <div class="panel-body">
				                    <p class="text-muted">输入username, password 后点击测试按钮.<span class="label label-warning">POST</span></p>
				
				                    <form action="${ctx}/oauth/token.izhbg" method="post" target="_blank" class="form-horizontal">
				                    	 <div class="form-group">
											  <label class="col-md-2 control-label"> username</label>
											  <div class="col-md-8">
											  	<input name="username" id="username" class="form-control" value="">
											  </div>
										  </div>
				                    	 <div class="form-group">
											  <label class="col-md-2 control-label"> password</label>
											  <div class="col-md-8">
											  	<input name="password" id="password" class="form-control" value="">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_id</label>
											  <div class="col-md-8">
											  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_secret</label>
											  <div class="col-md-8">
											  	<input name="client_secret" id="client_secret" class="form-control" value="${clientDetailsDto.clientSecret}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> grant_type</label>
											  <div class="col-md-8">
											  	<input name="grant_type" id="grant_type" class="form-control" value="password" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label">scope</label>
											  <div class="col-md-8">
											  	<input name="scope" id="scope" class="form-control" value="${fn:replace(clientDetailsDto.scope,',', ' ')}" readonly="readonly">
											  </div>
										  </div>
				                        <button class="btn green" type="submit">
				                        	    测试
				                        </button>
				                        
				                    </form>
				                </div>
				            </div>
				        </c:if>
				
				        <c:if test="${clientDetailsDto.containsImplicit}">
				            <div class="panel panel-default">
				                <div class="panel-heading">Test [implicit]</div>
				                <div class="panel-body">
				                    <p class="text-muted">输入redirect_uri 后点击链接地址. 获取access_token后注意查看redirect_uri的hash部分(#号后边部分)<span class="label label-info">GET</span></p>
									<form action="${ctx}/oauth/authorize.izhbg" method="get" target="_blank" class="form-horizontal">
										<div class="form-group">
											  <label class="col-md-2 control-label"> redirect_uri</label>
											  <div class="col-md-8">
											  	<input name="redirect_uri" id="redirect_uri" class="form-control" value="${clientDetailsDto.implicitRedirectUri}">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_id</label>
											  <div class="col-md-8">
											  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_secret</label>
											  <div class="col-md-8">
											  	<input name="client_secret" id="client_secret" class="form-control" value="${clientDetailsDto.clientSecret}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> response_typ</label>
											  <div class="col-md-8">
											  	<input name="response_typ" id="response_typ" class="form-control" value="token" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label">scope</label>
											  <div class="col-md-8">
											  	<input name="scope" id="scope" class="form-control" value="${fn:replace(clientDetailsDto.scope,',', ' ')}" readonly="readonly">
											  </div>
										  </div>
				                           <button class="btn green" type="submit">
				                        	    测试
				                         	</button>
									</form>
				                </div>
				            </div>
				        </c:if>
				
				        <c:if test="${clientDetailsDto.containsClientCredentials}">
				            <div class="panel panel-default">
				                <div class="panel-heading">Test [client_credentials]</div>
				                <div class="panel-body">
				                    <p class="text-muted">点击测试按钮即可测试<span class="label label-warning">POST</span></p>
				
				                    <form action="${ctx}/oauth/token.izhbg" method="post" target="_blank" class="form-horizontal">
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_id</label>
											  <div class="col-md-8">
											  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_secret</label>
											  <div class="col-md-8">
											  	<input name="client_secret" id="client_secret" class="form-control" value="${clientDetailsDto.clientSecret}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> grant_type</label>
											  <div class="col-md-8">
											  	<input name="grant_type" id="grant_type" class="form-control" value="client_credentials" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label">scope</label>
											  <div class="col-md-8">
											  	<input name="scope" id="scope" class="form-control" value="${fn:replace(clientDetailsDto.scope,',', ' ')}" readonly="readonly">
											  </div>
										  </div>
				                        <button class="btn green" type="submit">
				                        	    测试
				                        </button>
				                        
				                    </form>
				                </div>
				            </div>
				        </c:if>
				
				        <c:if test="${clientDetailsDto.containsRefreshToken}">
				            <div class="panel panel-default">
				                <div class="panel-heading">Test [refresh_token]</div>
				                <div class="panel-body">
				                    <p class="text-muted">输入refresh_token 后点击测试按钮.<span class="label label-warning">POST</span></p>
				                    <br/>
				                    <form action="${ctx}/oauth/token.izhbg" method="post" target="_blank" class="form-horizontal">
				                    	 <div class="form-group">
											  <label class="col-md-2 control-label">refresh_token</label>
											  <div class="col-md-8">
											  	<input name="refresh_token" id="refresh_token" class="form-control" value="">
											  </div>
										  </div>
				                    	  <div class="form-group">
											  <label class="col-md-2 control-label"> client_id</label>
											  <div class="col-md-8">
											  	<input name="client_id" id="client_id" class="form-control" value="${clientDetailsDto.clientId}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> client_secret</label>
											  <div class="col-md-8">
											  	<input name="client_secret" id="client_secret" class="form-control" value="${clientDetailsDto.clientSecret}" readonly="readonly">
											  </div>
										  </div>
		                                  <div class="form-group">
											  <label class="col-md-2 control-label"> grant_type</label>
											  <div class="col-md-8">
											  	<input name="grant_type" id="grant_type" class="form-control" value="refresh_token" readonly="readonly">
											  </div>
										  </div>
				                        <button class="btn green" type="submit">
				                          	  测试
				                        </button>
				                        
				                    </form>
				                </div>
				            </div>
				        </c:if>
				
				        <div class="text-center">
				            <a href="${ctx}/client/client_list.izhbg" class="btn btn-default">Back</a>
				        </div>
				    </div>
				</div>
				 </div>
				</div>
			</div>
</div>
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>