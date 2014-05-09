package com.w951.zsbus.permission.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonAction;
import com.w951.util.bean.BeanUtil;
import com.w951.util.date.DateUtil;
import com.w951.zsbus.permission.dto.ResourceDTO;
import com.w951.zsbus.permission.entity.Resource;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.ResourceService;

public class ResourceAction extends CommonAction {
	private static final long serialVersionUID = 2726803032788372818L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	@javax.annotation.Resource
	private ResourceService resourceService;
	
	// 参数
	private Resource resource;
	private int page;
	private int rows;
	
	// Action
	
	@Override
	public String insert() throws Exception {
		User admin = (User) session.get("admin");
		
		resource.setResourceCreatename(admin.getUserName());
		resource.setResourceDate(DateUtil.getDateTime());
		String message = resourceService.insert(resource);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		String message = resourceService.delete(resource);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		String message = resourceService.update(resource);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		List<Resource> list = resourceService.queryPageList(page, rows, "resourceSort", "asc");

		ResourceDTO dto = null;
		List<ResourceDTO> dtos = new ArrayList<ResourceDTO>();
		if (list != null && list.size() > 0) {
			for (Resource obj : list) {
				dto = new ResourceDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}

		jsonData.put("total", resourceService.getCount());
		jsonData.put("rows", dtos);
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}
	
	//getter setter

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
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

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
