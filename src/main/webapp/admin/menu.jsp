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
			function add() {
				$("#dlg_manager").dialog("open");
				$("#fm_manager").form("clear");
			}
			
			function edit() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					$("#dlg_manager").dialog("open");
					$("#menu_menuId").val(row.menuId);
					$("#menu_menuName").val(row.menuName);
					$("#menu_menuBack").val(row.menuBack);
					$("#menu_menuSort").val(row.menuSort);
				}
			}
			
			function del() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					funtl_easyui_dialog.confirm("确定要删除这条记录吗？", function() {
						var data = {
							"menu.menuId" : row.menuId
						};
						funtl_easyui_ajax.post("permission/Menu/action/delete", data, function(data) {
							if (data.message == null || data.message.length == 0) {
								$("#dg_list").datagrid("reload");
								funtl_easyui_dialog.info("数据已删除");
							} else {
								funtl_easyui_dialog.info(data.message);
							}
						});
					});
				}
			}
			
			function resource() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					$("#dlg_resource").dialog("open");
					
					// 让Tree横向对齐
					$("#per_resource").find("li").attr("style", "float:left;");
					
					var data = {
						"menu.menuId" : row.menuId
					};
					funtl_easyui_ajax.post("permission/Menu/action/queryResourceByMenu", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							//分栏资源
							var results = $('#per_resource').tree('getRoots');
							
							//清空勾选
							if (results != null && results.length > 0) {
								for (var x = 0 ; x < results.length ; x++) {
									$('#per_resource').tree('uncheck', results[x].target);
								}
							}
							
							if (data.resources != null && data.resources.length > 0 && results != null && results.length > 0) {
								for (var i = 0 ; i < data.resources.length ; i++) {
									for (var x = 0 ; x < results.length ; x++) {
										if (data.resources[i] == results[x].id) {
											$('#per_resource').tree('check', results[x].target);
											
											// 清空操作权限勾选
											
											var children = $('#per_resource').tree('getChildren', results[x].target);
											for (var y = 0 ; y < children.length ; y++) {
												$('#per_resource').tree('uncheck', children[y].target);
											}
											
											// 操作权限
											
											var param = {
												"menu.menuId" : row.menuId,
												"resourceId" : data.resources[i]
											};
											funtl_easyui_ajax.post("permission/Menu/action/queryResourceByMenuResrouce", param, function(param) {
												if (param.message == null || param.message.length == 0) {
													for (var y = 0 ; y < children.length ; y++) {
														for (var z = 0 ; z < param.options.length ; z++) {
															if (param.options[z] == children[y].id) {
																$('#per_resource').tree('check', children[y].target);
															}
														}
													}
												} else {
													funtl_easyui_dialog.info(param.message);
												}
											}, false);
											break;
										}
									}
								}
							}
						} else {
							funtl_easyui_dialog.info(data.message);
						}
					});
				}
			}
			
			var dlgManagerBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	if ($("#menu_menuId").val() == "") {
			    		$("#fm_manager").attr("action", "permission/Menu/action/insert");
			    	} else {
			    		$("#fm_manager").attr("action", "permission/Menu/action/update");
			    	}
			    	
			    	funtl_easyui_form.submit("fm_manager", function(data) {
						if (data.message == null || data.message.length == 0) {
							$("#fm_manager").form("clear");
							$("#dlg_manager").dialog("close");
							$("#dg_list").datagrid("reload");
							funtl_easyui_dialog.info("数据已保存");
						} else {
							funtl_easyui_dialog.info(data.message);
						}
					});
			    }
			},{
			    text:"取消",
			    iconCls:"icon-cancel",
			    handler:function() {
			    	$("#dlg_manager").dialog("close");
			    }
			}];
			
			var dlgResourceBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	var row = $("#dg_list").datagrid("getSelected");
			    	var resourceIds = "";
			    	var resourceOpts = "";
			    	var nodes = $('#per_resource').tree('getChecked');
			    	if (nodes != null && nodes.length > 0) {
			    		for (var i = 0 ; i < nodes.length ; i++) {
			    			var parent = $('#per_resource').tree('getParent', nodes[i].target);
			    			if (parent != null && resourceIds.indexOf(parent.id) == -1) {
			    				resourceIds += parent.id + ";";
			    			}
			    			
			    			if (nodes[i].id.indexOf("action") != -1) {
			    				resourceOpts += nodes[i].id + ";";
			    			}
			    		}
			    	}
			    	
			    	var data = {
						"menu.menuId" : row.menuId,
						"resourceIds" : resourceIds,
						"resourceOpts" : resourceOpts
					};
			    	funtl_easyui_ajax.post("permission/Menu/action/insertMenuResource", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							$("#dlg_resource").dialog("close");
							funtl_easyui_dialog.info("数据已保存");
						} else {
							funtl_easyui_dialog.info(data.message);
						}
					});
			    }
			},{
			    text:"取消",
			    iconCls:"icon-cancel",
			    handler:function() {
			    	$("#dlg_resource").dialog("close");
			    }
			}];
		</script>
		<title><%=System.getProperty("systemName") %></title>
	</head>
	
	<body>
		<table id="dg_list" class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:50,url:'permission/Menu/action/query',toolbar:'#dg_list_toolbar',onLoadError:funtl_easyui_ajax.onLoadError">
			<thead>
	  			<tr>
	  				<th data-options="field:'menuName'">分栏名称</th>
	  				<th data-options="field:'menuCreatename'">分栏创建者</th>
	  				<th data-options="field:'menuDate', formatter:funtl_easyui_formatter.datetime">分栏创建时间</th>
	  				<th data-options="field:'menuBack'">分栏备注</th>
	  				<th data-options="field:'menuSort'">分栏排序</th>
	  			</tr>
  			</thead>
		</table>
		<div id="dg_list_toolbar" style="padding:5px;height:auto">
	   		<div style="margin-bottom:5px">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">编辑</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-application-side-tree',plain:true" onclick="resource();">资源配置</a>
			</div>
	   	</div>
		<script>
	   		$("#dg_list").height($(document).height() * 0.98);
	   	</script>
	   	
	   	<div id="dlg_manager" class="easyui-dialog" style="width:600px;height:auto;padding:10px" data-options="title:'管理',buttons:dlgManagerBtn,modal:true,closed:true">
	   		<form id="fm_manager" method="post" action="">
	   			<input id="menu_menuId" type="hidden" name="menu.menuId" />
	   			<table align="center">
	   				<tr>
		    			<td align="right">分栏名称</td>
		    			<td><input id="menu_menuName" class="easyui-validatebox" type="text" name="menu.menuName" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">分栏备注</td>
		    			<td colspan="3"><input id="menu_menuBack" class="easyui-validatebox" type="text" name="menu.menuBack" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">分栏排序</td>
		    			<td colspan="3"><input id="menu_menuSort" class="easyui-numberbox" type="text" name="menu.menuSort" data-options="required:true" style="width:100%;"></input></td>
		    		</tr>
	   			</table>
	   		</form>
	   	</div>
	   	
	   	<div id="dlg_resource" class="easyui-dialog" style="width:700px;height:400px;padding:10px" data-options="title:'资源',buttons:dlgResourceBtn,modal:true,closed:true">
	   		<ul id="per_resource" class="easyui-tree" data-options="url:'permission/Menu/action/queryResourceArray', checkbox:true"></ul>
	   	</div>
	</body>
</html>