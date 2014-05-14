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
					$("#resource_resourceId").val(row.resourceId);
					$("#resource_resourceIdentif").val(row.resourceIdentif);
					$("#resource_resourceName").val(row.resourceName);
					$("#resource_resourceUrl").val(row.resourceUrl);
					$("#resource_resourceBack").val(row.resourceBack);
					$("#resource_resourceSaveUrl").val(row.resourceSaveUrl);
					$("#resource_resourceUpdateUrl").val(row.resourceUpdateUrl);
					$("#resource_resourceDeleteUrl").val(row.resourceDeleteUrl);
					$("#resource_resourceSelectUrl").val(row.resourceSelectUrl);
					$("#resource_resourceImportUrl").val(row.resourceImportUrl);
					$("#resource_resourceExportUrl").val(row.resourceExportUrl);
					$("#resource_resourceLikeUrl").val(row.resourceLikeUrl);
					$("#resource_resourceSort").val(row.resourceSort);
					$("#resource_resourceVerify").combobox("setValue", row.resourceVerify);
				}
			}
			
			function del() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					funtl_easyui_dialog.confirm("确定要删除这条记录吗？", function() {
						var data = {
							"resource.resourceId" : row.resourceId
						};
						funtl_easyui_ajax.post("permission/Resource/action/delete", data, function(data) {
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
			
			var dlgManagerBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	if ($("#resource_resourceId").val() == "") {
			    		$("#fm_manager").attr("action", "permission/Resource/action/insert");
			    	} else {
			    		$("#fm_manager").attr("action", "permission/Resource/action/update");
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
			
			function setDomain() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择需要配置的资源");
				} else {
					$("#dlg_manager_domain").dialog("open");
				}
			}
			
			var dlgManagerDomainBtn = [{
			    text:"保存",
			    iconCls:"icon-ok",
			    handler:function() {
			    	var rows = $("#dg_list").datagrid("getChecked");
			    	var domainId = $("#domain_domainId").combobox("getValue");
			    	var resourceIds = "";
			    	for (var i = 0 ; i < rows.length ; i++) {
			    		resourceIds += rows[i].resourceId + ",";
			    	}
			    	
			    	var data = {
						"domainId" : domainId,
						"resourceIds" : resourceIds
					};
			    	funtl_easyui_ajax.post("permission/Resource/action/updateResourceDomain", data, function(data) {
						if (data.message == null || data.message.length == 0) {
							$("#dlg_manager_domain").dialog("close");
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
			    	$("#dlg_manager_domain").dialog("close");
			    }
			}];
		</script>
		<title><%=System.getProperty("systemName") %></title>
	</head>
	
	<body>
		<table id="dg_list" class="easyui-datagrid" data-options="rownumbers:true,singleSelect:false,pagination:true,pageSize:50,url:'permission/Resource/action/query',toolbar:'#dg_list_toolbar',onLoadError:funtl_easyui_ajax.onLoadError">
			<thead>
	  			<tr>
	  				<th data-options="checkbox:true"></th>
	  				<th data-options="field:'domainName'">所属域</th>
	  				<th data-options="field:'resourceIdentif'">资源标识</th>
	  				<th data-options="field:'resourceName'">资源名称</th>
	  				<th data-options="field:'resourceUrl'">资源链接</th>
	  				<th data-options="field:'resourceCreatename'">资源创建者</th>
	  				<th data-options="field:'resourceDate', formatter:funtl_easyui_formatter.datetime">资源创建时间</th>
	  				<th data-options="field:'resourceVerify', formatter:funtl_easyui_formatter.verify">资源审核</th>
	  				<th data-options="field:'resourceBack'">资源备注</th>
	  				<th data-options="field:'resourceSort'">资源排序</th>
	  				<th data-options="field:'resourceSaveUrl'">保存</th>
	  				<th data-options="field:'resourceUpdateUrl'">修改</th>
	  				<th data-options="field:'resourceDeleteUrl'">删除</th>
	  				<th data-options="field:'resourceSelectUrl'">查询</th>
	  				<th data-options="field:'resourceImportUrl'">导入</th>
	  				<th data-options="field:'resourceExportUrl'">导出</th>
	  				<th data-options="field:'resourceLikeUrl'">检索</th>
	  			</tr>
  			</thead>
		</table>
		<div id="dg_list_toolbar" style="padding:5px;height:auto">
	   		<div style="margin-bottom:5px">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">编辑</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-layout-link',plain:true" onclick="setDomain();">域配置</a>
			</div>
	   	</div>
		<script>
	   		$("#dg_list").height($(document).height() * 0.98);
	   	</script>
	   	
	   	<div id="dlg_manager" class="easyui-dialog" style="width:800px;height:auto;padding:10px" data-options="title:'管理',buttons:dlgManagerBtn,modal:true,closed:true">
	   		<form id="fm_manager" method="post" action="">
	   			<input id="resource_resourceId" type="hidden" name="resource.resourceId" />
	   			<table align="center">
	   				<tr>
		    			<td align="right">资源标识</td>
		    			<td><input id="resource_resourceIdentif" class="easyui-validatebox" type="text" name="resource.resourceIdentif" data-options="required:true"></input></td>
		    			<td align="right">资源名称</td>
		    			<td><input id="resource_resourceName" class="easyui-validatebox" type="text" name="resource.resourceName" data-options="required:true"></input></td>
		    			<td align="right">资源审核</td>
		    			<td>
		    				<select class="easyui-combobox" id="resource_resourceVerify" name="resource.resourceVerify" data-options="required:true, editable:false" style="width:150px;">
		    					<option value="0">启用</option>
		    					<option value="1">禁用</option>
		    				</select>
						</td>
		    		</tr>
		    		<tr>
		    			<td align="right">资源链接</td>
		    			<td colspan="5"><input id="resource_resourceUrl" class="easyui-validatebox" type="text" name="resource.resourceUrl" data-options="required:true" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">保存</td>
		    			<td colspan="5"><input id="resource_resourceSaveUrl" class="easyui-validatebox" type="text" name="resource.resourceSaveUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">修改</td>
		    			<td colspan="5"><input id="resource_resourceUpdateUrl" class="easyui-validatebox" type="text" name="resource.resourceUpdateUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">删除</td>
		    			<td colspan="5"><input id="resource_resourceDeleteUrl" class="easyui-validatebox" type="text" name="resource.resourceDeleteUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">查询</td>
		    			<td colspan="5"><input id="resource_resourceSelectUrl" class="easyui-validatebox" type="text" name="resource.resourceSelectUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">导入</td>
		    			<td colspan="5"><input id="resource_resourceImportUrl" class="easyui-validatebox" type="text" name="resource.resourceImportUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">导出</td>
		    			<td colspan="5"><input id="resource_resourceExportUrl" class="easyui-validatebox" type="text" name="resource.resourceExportUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">检索</td>
		    			<td colspan="5"><input id="resource_resourceLikeUrl" class="easyui-validatebox" type="text" name="resource.resourceLikeUrl" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">备注</td>
		    			<td colspan="5"><input id="resource_resourceBack" class="easyui-validatebox" type="text" name="resource.resourceBack" style="width:100%;"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">资源排序</td>
		    			<td><input id="resource_resourceSort" class="easyui-numberbox" type="text" name="resource.resourceSort" data-options="required:true"></input></td>
		    		</tr>
	   			</table>
	   		</form>
	   	</div>
	   	
	   	<div id="dlg_manager_domain" class="easyui-dialog" style="width:350px;height:auto;padding:10px" data-options="title:'域配置',buttons:dlgManagerDomainBtn,modal:true,closed:true">
	   		<table align="center" style="width:100%;">
	   			<tr>
	   				<td align="right">选择对应域</td>
	   				<td><input class="easyui-combobox" id="domain_domainId" data-options="valueField:'domainId',textField:'domainName',url:'permission/Domain/action/queryDomainArray'" /></td>
	   			</tr>
	   		</table>
	   	</div>
	</body>
</html>