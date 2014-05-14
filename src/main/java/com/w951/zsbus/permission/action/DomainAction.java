package com.w951.zsbus.permission.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonAction;
import com.w951.util.bean.BeanUtil;
import com.w951.util.date.DateUtil;
import com.w951.zsbus.permission.dto.DomainDTO;
import com.w951.zsbus.permission.entity.Domain;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.DomainService;

public class DomainAction extends CommonAction {
	private static final long serialVersionUID = -1L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;

	@Resource
	private DomainService domainService;
	
	// 参数

	private Domain domain;
	private int page;
	private int rows;
	
	// Action
	
	@Override
	public String insert() throws Exception {
		User admin = (User) session.get("admin");
		
		domain.setDomainCreatename(admin.getUserName());
		domain.setDomainCreatedate(DateUtil.getDateTime());
		String message = domainService.insert(domain);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		String message = domainService.delete(domain);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		String message = domainService.update(domain);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		List<Domain> list = domainService.queryPageList(page, rows);

		DomainDTO dto = null;
		List<DomainDTO> dtos = new ArrayList<DomainDTO>();
		if (list != null && list.size() > 0) {
			for (Domain obj : list) {
				dto = new DomainDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}

		jsonData.put("total", domainService.getCount());
		jsonData.put("rows", dtos);
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}
	
	// Array
	
	public String queryDomainArray() throws Exception {
		List<Domain> list = domainService.queryList();
		
		DomainDTO dto = null;
		List<DomainDTO> dtos = new ArrayList<DomainDTO>();
		if (list != null && list.size() > 0) {
			for (Domain obj : list) {
				dto = new DomainDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}
		
		resultArray = JSONArray.fromObject(dtos);
		return "array";
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

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
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

}
