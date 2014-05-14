package com.w951.zsbus.permission.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonBaseAction;
import com.w951.util.bean.CookieUtil;
import com.w951.zsbus.permission.dto.MenuDTO;
import com.w951.zsbus.permission.entity.GroupMenu;
import com.w951.zsbus.permission.entity.MenuResource;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.entity.UserGroup;

public class MainAction extends CommonBaseAction {
	private static final long serialVersionUID = -7852656253031757393L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	// Page
	
	public String login() throws Exception {
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String main() throws Exception {
		User admin = (User) session.get("admin");
		
		MenuDTO dto = null;
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		
		List<UserGroup> groups = admin.getUserGroups();
		for (UserGroup group : groups) {
			List<GroupMenu> menus = group.getGroup().getGroupMenus();
			
			//分栏排序
			
			Collections.sort(menus, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					GroupMenu t1 = (GroupMenu) o1;
					GroupMenu t2 = (GroupMenu) o2;
					if (t1.getMenu().getMenuSort() > t2.getMenu().getMenuSort()) {
						return 1;
					} else if (t1.getMenu().getMenuSort() == t2.getMenu().getMenuSort()) {
						return 0;
					} else {
						return -1;
					}
				}
			});

			for (GroupMenu menu : menus) {
				dto = new MenuDTO();
				dto.setMenuName(menu.getMenu().getMenuName());
				dto.setResources(new ArrayList<String[]>());
				
				List<MenuResource> resources = menu.getMenu().getMenuResources();
				
				//资源排序
				
				Collections.sort(resources, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						MenuResource t1 = (MenuResource) o1;
						MenuResource t2 = (MenuResource) o2;
						if (t1.getResource().getResourceSort() > t2.getResource().getResourceSort()) {
							return 1;
						} else if (t1.getResource().getResourceSort() == t2.getResource().getResourceSort()) {
							return 0;
						} else {
							return -1;
						}
					}
				});
				
				for (MenuResource resource : resources) {
					dto.getResources().add(new String[] {resource.getResource().getResourceName(), resource.getResource().getDomain().getDomainUrl() + resource.getResource().getResourceUrl()});
				}
				
				dtos.add(dto);
			}
		}
		
		request.put("menus", dtos);
		
		return SUCCESS;
	}
	
	public String user() throws Exception {
		return SUCCESS;
	}
	
	public String group() throws Exception {
		return SUCCESS;
	}
	
	public String menu() throws Exception {
		return SUCCESS;
	}
	
	public String resource() throws Exception {
		return SUCCESS;
	}
	
	public String loginOut() throws Exception {
		session.clear();
		CookieUtil.removeCookie(ServletActionContext.getResponse(), "loginInfo");
		return SUCCESS;
	}
	
	public String domain() throws Exception {
    	return SUCCESS;
    }
	
	// Action
	
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
}
