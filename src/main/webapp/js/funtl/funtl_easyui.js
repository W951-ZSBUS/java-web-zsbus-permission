/**
 * 通用提示框工具
 */
var funtl_easyui_dialog = {
	/**
	 * 信息对话框
	 * @param message 提示信息
	 */
	info : function(message) {
		$.messager.alert("温馨提示", message, 'info');
	},
	
	/**
	 * 确认对话框
	 * @param message 提示信息
	 * @param callback 回调函数
	 */
	confirm : function(message, callback) {
		$.messager.confirm("温馨提示", message, function(r){
            if (r){
            	callback();
            }
        });
	},
	
	// 添加加载效果
	addMask : function(dlg){
    	var mask = $("<div class=\"datagrid-mask\"></div>");
    		mask.css(
				 {display:"block",
				 width:$("#"+dlg).width() + 12,
				 height:$("#"+dlg).height() + 33,
				 border:"0px solid red"
				 });
			mask.appendTo("#"+dlg); //等待效果显示在绑定的控件上
		var maskMsg = $("<div class=\"datagrid-mask-msg\" style=\"font-size:12px;\"></div>");
			maskMsg.html("正在加载，请稍候...");
			maskMsg.appendTo("#"+dlg);
			maskMsg.css({display:"block",left:($("#"+dlg).width() - maskMsg.width()) / 2,top:($("#"+dlg).height() -maskMsg.height()) / 2});
			maskMsg.focus();
    },
    
    // 移除加载效果
	removeMask : function(dlg){
		$("#"+dlg).find("div.datagrid-mask-msg").remove();
		$("#"+dlg).find("div.datagrid-mask").remove();
	}
};

/**
 * 通用窗口工具
 */
var funtl_easyui_windows = {
	/**
	 * Ajax加载内容
	 * @param id
	 * @param title
	 * @param url
	 */
	ajax : function(id, title, url) {
		$('#' + id).window({  
			title:title,
		    width:850,
		    height:400,
		    modal:true,
		    closed:true,
		    onMaximize: function() {
		    	$('#' + id).find('.easyui-datagrid').datagrid('resize', {
		    		width: '100%',
		    		height: $(this).window('options').height - 36
		    	});
		    },
		    onRestore: function() {
		    	$('#' + id).find('.easyui-datagrid').datagrid('resize', {
		    		width: 836,
		    		height: 345
		    	});
		    }
		});
		$('#' + id).window('open');
		$('#' + id).window('restore');
		$('#' + id).window('refresh', url);
	},
	
	/**
	 * 打开普通窗口
	 * @param id
	 * @param title
	 */
	open : function(id, title) {
		$('#' + id).window({  
			title:title,
			width:850,
		    height:400,
		    modal:true,
		    closed:true
		});
		$('#' + id).window('open');
		$('#' + id).window('restore');
	}
};

/**
 * 通用表单工具
 */
var funtl_easyui_form = {
	/**
	 * 提交表单
	 * @param formId 表单ID
	 * @param callback 回调函数
	 */
	submit : function(formId, callback) {
		$('#' + formId).form({
		    success:function(data){
		    	var jsonData = eval("(" + data + ")");
		    	callback(jsonData);
		    }
		});
		$('#' + formId).submit();
	}
};

/**
 * 通用Ajax工具
 */
var funtl_easyui_ajax = {
		
	/**
	 * 下拉框
	 * @param selId
	 * @param url
	 * @param key
	 * @param value
	 */
	combobox : function(selId, url, key, value) {
		ajax.post(url, null, function(data){
			$('#' + selId).empty();
			$('#' + selId).append(
				'<option value="">' + '请选择' + '</option>'
			);
			for (var i = 0 ; i < data.rows.length ; i++) {
				$('#' + selId).append(
					'<option value="' + eval("data.rows[" + i + "]." + key) + '">' + eval("data.rows[" + i + "]." + value) + '</option>'
				);
			}
		},'json');
	},
	
	/**
	 * easyUi 下拉列表
	 * @param selId
	 * @param url
	 * @param key
	 * @param value
	 * @param selObj
	 */
	jcombobox : function jcombobox(selId, url, key, value, selObj) {
		ajax.post(url, null, function(data){
			var strJsion = "[";
			for (var i = 0 ; i < data.rows.length ; i++) {
					strJsion += '{"id":"' + eval("data.rows[" + i + "]." + key) + '","text":"' + eval("data.rows[" + i + "]." + value) +'"},';
				}
			strJsion = strJsion.substring(0, strJsion.length -1) + "]";
			var data = JSON.parse(strJsion);
			if (selObj){
				data.unshift(selObj);
			}
			$('#' + selId).combobox({
				valueField:'id',
				textField:'text',
			 	data: data
			});
		},'json');
	},
	
	/**
	 * ajax请求 - 异步
	 */
	post : function(url, data, callback, isAsync) {
		$.ajax({
			url: url,
			data : data,
			type: "POST",
			dataType: "json",
			async: isAsync,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success: function(jsonData){
				callback(jsonData);
			},
			error: function(data) {
				var script = data.responseText;
				if (script.indexOf("<script>") != -1) {
					script = script.replace("<script>", "");
					script = script.replace("</script>", "");
					eval(script);
				}
			}
		});
	},
	
	onLoadError : function(data) {
		var script = data.responseText;
		if (script.indexOf("<script>") != -1) {
			script = script.replace("<script>", "");
			script = script.replace("</script>", "");
			eval(script);
		}
	}
};

/**
 * 通用tab工具
 */
var funtl_easyui_tab = {
	/**
	 * 增加tab
	 * @param id 嵌套层ID
	 * @param title 标题
	 * @param url 访问地址
	 * @param hasClosable 是否开启关闭
	 */
	append : function(id, title, url, hasClosable) {
		if ($('#' + id).tabs('exists', title)) {
			$('#' + id).tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
			$('#' + id).tabs('add', {
				title : title,
				content : content,
				closable : hasClosable
			});
		}
	}
};

/**
 * 格式化工具
 */
var funtl_easyui_formatter = {
	/**
	 * 格式化日期时间
	 * @param val
	 * @param row
	 * @returns
	 */
	datetime : function(val, row) {
		return val.substring(0, 19);
	},
	
	/**
	 * 格式化性别
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	sex : function(val, row) {
		if (val == 0) {
			return "男";
		} else {
			return "女";
		}
	},
	
	/**
	 * 格式化验证
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	verify : function(val, row) {
		if (val == 0) {
			return "启用";
		} else {
			return "禁用";
		}
	},
	
	/**
	 * 文章分类
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	artType : function(val, row) {
		if (val == 0) {
			return "系统";
		} else if (val == 1) {
			return "文章";
		}
	},
	
	/**
	 * 图片链接
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	imgLink : function(val, row) {
		return "<img src='" + val + "' />";
	},
	
	/**
	 * 是否
	 * @param val
	 * @param row
	 */
	yesOrNo : function(val, row) {
		if (val == 0) {
			return "是";
		} else if (val == 1) {
			return "否";
		}
	},
	
	/**
	 * 奖品类型
	 * @param val
	 * @param row
	 */
	prizeType : function(val, row) {
		if (val == 1) {
			return "金额";
		} else if (val == 2) {
			return "礼品";
		}  else if (val == 3) {
			return "其他";
		}
	}
};
