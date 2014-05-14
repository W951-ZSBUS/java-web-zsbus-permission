<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<jsp:include page="/resources.jsp" />
		<script type="text/javascript">
			$(document).ready(function() {
				$("#tree_menu").tree({
					onClick: function(node) {
						if (node.id != null && node.id.indexOf("action") == -1) {
							funtl_easyui_tab.append("controler", node.text, node.id, true);
						} else if (node.id.indexOf("action") != -1) {
							funtl_easyui_ajax.post(node.id, null, function(data) {
								if (data.message == null || data.message.length == 0) {
									funtl_easyui_dialog.info("OK");
								} else {
									if (data.message == "clear") {
										location.reload();
									} else {
										funtl_easyui_dialog.info(data.message);
									}
								}
							});
						}
					}
				});
			});
		</script>
		<title><%=System.getProperty("systemName") %></title>
	</head>
	
	<body class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'菜单'" style="width:180px;">
			<ul id="tree_menu" class="easyui-tree">
				<s:iterator value="#request.menus" var="m" status="state">
					<li>
						<span><s:property value='#m.menuName' /></span>
						<ul>
							<s:iterator value="#m.resources" var="r" status="statem">
								<li id="<s:property value="#r[1]" />"><s:property value="#r[0]" /></li>
							</s:iterator>
						</ul>
					</li>
				</s:iterator>
			</ul>
		</div>
		<div data-options="region:'center',title:'控制台'">
			<div id="controler" class="easyui-tabs" data-options="fit:'true', border:'false'"></div>
		</div>
	</body>
</html>