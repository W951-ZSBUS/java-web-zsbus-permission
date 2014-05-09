package com.w951.zsbus.permission.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonAction;
import com.w951.util.bean.BeanUtil;
import com.w951.util.bean.StringUtil;
import com.w951.util.date.DateUtil;
import com.w951.zsbus.permission.dto.UserDTO;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.UserService;

public class UserAction extends CommonAction {
	private static final long serialVersionUID = -4340850886835268431L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;

	@Resource
	private UserService userService;
	
	// 参数

	private User user;
	private int page;
	private int rows;
	private String valicode;
	
	// Action
	
	public String login() throws Exception {
		user.setUserPass(StringUtil.toMD5(user.getUserPass()));

		String serverCode = (String) session.get("SESSION_SECURITY_CODE");
		if (!serverCode.equals(valicode)) {
			jsonData.put("message", "验证码输入错误");
		} else {
			User admin = userService.getUserByPass(user.getUserNm(), user.getUserPass());
			if (admin == null) {
				jsonData.put("message", "用户名或密码错误");
			} else {
				session.put("admin", admin);
			}
		}

		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}
	
	@Override
	public String insert() throws Exception {
		User admin = (User) session.get("admin");
		
		user.setUserCreatename(admin.getUserName());
		user.setUserDate(DateUtil.getDateTime());
		String message = userService.insert(user);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		String message = userService.delete(user);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		String message = userService.update(user);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		List<User> list = userService.queryPageList(page, rows);

		UserDTO dto = null;
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		if (list != null && list.size() > 0) {
			for (User obj : list) {
				dto = new UserDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}

		jsonData.put("total", userService.getCount());
		jsonData.put("rows", dtos);
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	// getter setter

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

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public String getValicode() {
		return valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

}
