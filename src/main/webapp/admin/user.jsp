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
					$("#user_userId").val(row.userId);
					$("#user_userNm").val(row.userNm);
					$("#user_userName").val(row.userName);
					$("#user_userAge").val(row.userAge);
					$("#user_userCompany").val(row.userCompany);
					$("#user_userDept").val(row.userDept);
					$("#user_userZw").val(row.userZw);
					$("#user_userPhone").val(row.userPhone);
					$("#user_userQq").val(row.userQq);
					$("#user_userEmail").val(row.userEmail);
					$("#user_userBack").val(row.userBack);
					$("#user_userSex").combobox("setValue", row.userSex);
					$("#user_userVerify").combobox("setValue", row.userVerify);
				}
			}
			
			function del() {
				var row = $("#dg_list").datagrid("getSelected");
				if (row == null) {
					funtl_easyui_dialog.info("请选择一条记录");
				} else {
					funtl_easyui_dialog.confirm("确定要删除这条记录吗？", function() {
						var data = {
							"user.userId" : row.userId
						};
						funtl_easyui_ajax.post("permission/User/action/delete", data, function(data) {
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
			    	if ($("#user_userId").val() == "") {
			    		$("#fm_manager").attr("action", "permission/User/action/insert");
			    	} else {
			    		$("#fm_manager").attr("action", "permission/User/action/update");
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
		</script>
		<title><%=System.getProperty("systemName") %></title>
	</head>
	
	<body>
		<table id="dg_list" class="easyui-datagrid" data-options="rownumbers:true,singleSelect:true,pagination:true,pageSize:50,url:'permission/User/action/query',toolbar:'#dg_list_toolbar',onLoadError:funtl_easyui_ajax.onLoadError">
			<thead>
	  			<tr>
	  				<th data-options="field:'userNm'">账户名称</th>
	  				<th data-options="field:'userName'">真实名称</th>
	  				<th data-options="field:'userSex', formatter:funtl_easyui_formatter.sex">用户性别</th>
	  				<th data-options="field:'userAge'">用户年龄</th>
	  				<th data-options="field:'userDept'">用户部门</th>
	  				<th data-options="field:'userZw'">用户职位</th>
	  				<th data-options="field:'userPhone'">用户手机</th>
	  				<th data-options="field:'userQq'">用户QQ</th>
	  				<th data-options="field:'userEmail'">用户邮箱</th>
	  				<th data-options="field:'userDate', formatter:funtl_easyui_formatter.datetime">用户创建时间</th>
	  				<th data-options="field:'userCompany'">用户公司</th>
	  				<th data-options="field:'userCreatename'">创建用户者</th>
	  				<th data-options="field:'userVerify', formatter:funtl_easyui_formatter.verify">审核用户</th>
	  				<th data-options="field:'userBack'">用户备注</th>
	  			</tr>
  			</thead>
		</table>
		<div id="dg_list_toolbar" style="padding:5px;height:auto">
	   		<div style="margin-bottom:5px">
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add();">新增</a>
		  		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del();">删除</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit();">编辑</a>
			</div>
	   	</div>
		<script>
	   		$("#dg_list").height($(document).height() * 0.98);
	   	</script>
	   	
	   	<div id="dlg_manager" class="easyui-dialog" style="width:800px;height:auto;padding:10px" data-options="title:'管理',buttons:dlgManagerBtn,modal:true,closed:true">
	   		<form id="fm_manager" method="post" action="">
	   			<input id="user_userId" type="hidden" name="user.userId" />
	   			<table align="center">
	   				<tr>
		    			<td align="right">账户名称</td>
		    			<td><input id="user_userNm" class="easyui-validatebox" type="text" name="user.userNm" data-options="required:true"></input></td>
		    			<td align="right">账户密码</td>
		    			<td><input id="user_userPass" class="easyui-validatebox" type="password" name="user.userPass" data-options="required:true"></input></td>
		    			<td align="right">用户性别</td>
		    			<td>
		    				<select class="easyui-combobox" id="user_userSex" name="user.userSex" data-options="required:true, editable:false" style="width:150px;">
		    					<option value="0">男</option>
		    					<option value="1">女</option>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td align="right">真实姓名</td>
		    			<td><input id="user_userName" class="easyui-validatebox" type="text" name="user.userName" data-options="required:true"></input></td>
		    			<td align="right">用户年龄</td>
		    			<td><input id="user_userAge" class="easyui-validatebox" type="text" name="user.userAge" data-options="required:true"></input></td>
		    			<td align="right">审核用户</td>
		    			<td>
		    				<select class="easyui-combobox" id="user_userVerify" name="user.userVerify" data-options="required:true, editable:false" style="width:150px;">
		    					<option value="0">启用</option>
		    					<option value="1">禁用</option>
		    				</select>
						</td>
		    		</tr>
		    		<tr>
		    			<td align="right">用户公司</td>
		    			<td><input id="user_userCompany" class="easyui-validatebox" type="text" name="user.userCompany" data-options="required:true" style="width:100%;"></input></td>
		    			<td align="right">用户部门</td>
		    			<td><input id="user_userDept" class="easyui-validatebox" type="text" name="user.userDept" data-options="required:true"></input></td>
		    			<td align="right">用户职位</td>
		    			<td><input id="user_userZw" class="easyui-validatebox" type="text" name="user.userZw" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">用户手机</td>
		    			<td><input id="user_userPhone" class="easyui-validatebox" type="text" name="user.userPhone" data-options="required:true"></input></td>
		    			<td align="right">用户QQ</td>
		    			<td><input id="user_userQq" class="easyui-validatebox" type="text" name="user.userQq" data-options="required:true"></input></td>
		    			<td align="right">用户邮箱</td>
		    			<td><input id="user_userEmail" class="easyui-validatebox" type="text" name="user.userEmail" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td align="right">用户备注</td>
		    			<td colspan="5"><input id="user_userBack" class="easyui-validatebox" type="text" name="user.userBack" style="width:100%;"></input></td>
		    		</tr>
	   			</table>
	   		</form>
	   	</div>
	</body>
</html>