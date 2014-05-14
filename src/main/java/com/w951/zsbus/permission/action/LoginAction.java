package com.w951.zsbus.permission.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonBaseAction;
import com.w951.util.bean.CookieUtil;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.UserService;

public class LoginAction extends CommonBaseAction {
	private static final long serialVersionUID = -2273339998283963764L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	@Resource
	private UserService userService;
	
	// Parameter
	
	private String loginId;
	private String loginPwd;
	
	// Action
	
	public String checkLogin() throws Exception {
		User admin = userService.getUserByPass(loginId, loginPwd);
		if (admin == null) {
			jsonData.put("msg", "<script>alert('请求超时，请重新登录');</script>");
		} else {
			jsonData.put("admin", admin.getUserName());
		}
		result = JSONObject.fromObject(jsonData);
		return SUCCESS;
	}
	
	public String checkPermission() throws Exception {
		// 加入到Cookie
		HttpServletResponse response = ServletActionContext.getResponse();
		String cookieName = "loginInfo";
		String cookieValue = loginId + "," + loginPwd;
		int cookieMaxAge = 60 * 30; // 保存30分钟
		CookieUtil.addCookie(response, cookieName, cookieValue, cookieMaxAge);
		return SUCCESS;
	}
	
	// getter setter

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
}
