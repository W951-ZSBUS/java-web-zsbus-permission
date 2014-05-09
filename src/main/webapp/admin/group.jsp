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
					$("#group_groupId").val(row.groupId);
					$("#group_groupName").val(row.groupName);
					$("#group_groupBack").val(row.groupBack);
					$("#group_groupVerify").combobox("setValue", row.groupVerify);
				}
			}
			
			function del() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					funtl_easyui_dialog.confirm("确定要删除这条记录吗？", function() {
						var data = {
							"group.groupId" : row.groupId
						};
						funtl_easyui_ajax.post("permission/Group/action/delete", data, function(data) {
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
			
			function user() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					$("#dlg_user").dialog("open");
					var data = {
						"group.groupId" : row.groupId
					};
					funtl_easyui_ajax.post("permission/Group/action/queryUserByGroup", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							//分组用户
							var results = $('#per_user').tree('getRoots');
							
							//清空勾选
							if (results != null && results.length > 0) {
								for (var x = 0 ; x < results.length ; x++) {
									$('#per_user').tree('uncheck', results[x].target);
								}
							}
							
							if (data.users != null && data.users.length > 0 && results != null && results.length > 0) {
								for (var i = 0 ; i < data.users.length ; i++) {
									for (var x = 0 ; x < results.length ; x++) {
										if (data.users[i] == results[x].id) {
											$('#per_user').tree('check', results[x].target);
										}
									}
								}
							}
						} else {
							funtl_easyui_dialog.info(data.message);
						}
					}, false);
				}
			}
			
			function menu() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					$("#dlg_menu").dialog("open");
					var data = {
						"group.groupId" : row.groupId
					};
					funtl_easyui_ajax.post("permission/Group/action/queryMenuByGroup", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							//分组分栏
							var results = $('#per_menu').tree('getRoots');
							
							//清空勾选
							if (results != null && results.length > 0) {
								for (var x = 0 ; x < results.length ; x++) {
									$('#per_menu').tree('uncheck', results[x].target);
								}
							}
							
							if (data.menus != null && data.menus.length > 0 && results != null && results.length > 0) {
								for (var i = 0 ; i < data.menus.length ; i++) {
									for (var x = 0 ; x < results.length ; x++) {
										if (data.menus[i] == results[x].id) {
											$('#per_menu').tree('check', results[x].target);
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
			    	if ($("#group_groupId").val() == "") {
			    		$("#fm_manager").attr("action", "permission/Group/action/insert");
			    	} else {
			    		$("#fm_manager").attr("action", "permission/Group/action/update");
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
			
			var dlgUserBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	var row = $("#dg_list").datagrid("getSelected");
			    	var userIds = "";
			    	var nodes = $('#per_user').tree('getChecked');
			    	if (nodes != null && nodes.length > 0) {
			    		for (var i = 0 ; i < nodes.length ; i++) {
			    			userIds += nodes[i].id + ";";
			    		}
			    	}
			    	
			    	var data = {
						"group.groupId" : row.groupId,
						"userIds" : userIds
					};
			    	funtl_easyui_ajax.post("permission/Group/action/insertUserGroup", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							$("#dlg_user").dialog("close");
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
			    	$("#dlg_user").dialog("close");
			    }
			}];
			
			var dlgMenuBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	var row = $("#dg_list").datagrid("getSelected");
			    	var menuIds = "";
			    	var nodes = $('#per_menu').tree('getChecked');
			    	if (nodes != null && nodes.length > 0) {
			    		for (var i = 0 ; i < nodes.length ; i++) {
			    			menuIds += nodes[i].id + ";";
			    		}
			    	}
			    	
			    	var data = {
						"group.groupId" : row.groupId,
						"menuIds" : menuIds
					};
			    	funtl_easyui_ajax.post("permission/Group/action/insertGroupMenu", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							$("#dlg_menu").dialog("close");
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
			    	$("#dlg_menu").dialog("close");
			    }
			}];
		</script>
		<title><%=System.getProperty("WEB_NAME") %></title>
	</head>
	
	<body>
		<table id="dg_list" class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:50,url:'permission/Group/action/query',toolbar:'#dg_list_toolbar',onLoadError:funtl_easyui_ajax.onLoadError">
			<thead>
	  			<tr>
	  				<th data-options="field:'groupName'">组名称</th>
	  				<th data-options="field:'groupCreatename'">组创建者</th>
	  				<th data-options="field:'groupCreatedate', formatter:funtl_easyui_formatter.datetime">组创建时间</th>
	  				<th data-options="field:'groupVerify', formatter:funtl_easyui_formatter.verify">组审核</th>
	  				<th data-options="field:'groupBack'">组备注</th>
	  			</tr>
  			</thead>
		</table>
		<div id="dg_list_toolbar" style="padding:5px;height:auto">
	   		<div style="margin-bottom:5px">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">编辑</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-group',plain:true" onclick="user();">配置用户</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-application-side-list',plain:true" onclick="menu();">配置分栏</a>
			</div>
	   	</div>
		<script>
	   		$("#dg_list").height($(document).height() * 0.98);
	   	</script>
	   	
	   	<div id="dlg_manager" class="easyui-dialog" style="width:600px;height:auto;padding:10px" data-options="title:'管理',buttons:dlgManagerBtn,modal:true,closed:true">
	   		<form id="fm_manager" method="post" action="">
	   			<input id="group_groupId" type="hidden" name="group.groupId" />
	   			<table align="center">
	   				<tr>
		    			<td align="right">组名称</td>
		    			<td><input id="group_groupName" class="easyui-validatebox" type="text" name="group.groupName" data-options="required:true"></input></td>
		    			<td align="right">组审核</td>
		    			<td>
		    				<select class="easyui-combobox" id="group_groupVerify" name="group.groupVerify" data-options="required:true, editable:false" style="width:150px;">
		    					<option value="0">启用</option>
		    					<option value="1">禁用</option>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td align="right">组备注</td>
		    			<td colspan="3"><input id="group_groupBack" class="easyui-validatebox" type="text" name="group.groupBack" style="width:100%;"></input></td>
		    		</tr>
	   			</table>
	   		</form>
	   	</div>
	   	
	   	<div id="dlg_user" class="easyui-dialog" style="width:600px;height:400px;padding:10px" data-options="title:'用户',buttons:dlgUserBtn,modal:true,closed:true">
	   		<ul id="per_user" class="easyui-tree" data-options="url:'permission/Group/action/queryUserArray', checkbox:true"></ul>
	   	</div>
	   	
	   	<div id="dlg_menu" class="easyui-dialog" style="width:600px;height:400px;padding:10px" data-options="title:'分栏',buttons:dlgMenuBtn,modal:true,closed:true">
	   		<ul id="per_menu" class="easyui-tree" data-options="url:'permission/Group/action/queryMenuArray', checkbox:true"></ul>
	   	</div>
	</body>
</html>