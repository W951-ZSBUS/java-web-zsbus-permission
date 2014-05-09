package com.w951.zsbus.permission.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.w951.util.action.CommonAction;
import com.w951.util.bean.BeanUtil;
import com.w951.util.date.DateUtil;
import com.w951.util.ui.easyui.TreeCheckbox;
import com.w951.util.ui.easyui.TreeCheckboxChildren;
import com.w951.zsbus.permission.dto.MenuDTO;
import com.w951.zsbus.permission.entity.Menu;
import com.w951.zsbus.permission.entity.MenuResource;
import com.w951.zsbus.permission.entity.Resource;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.MenuResourceService;
import com.w951.zsbus.permission.service.MenuService;
import com.w951.zsbus.permission.service.ResourceService;

public class MenuAction extends CommonAction {
	private static final long serialVersionUID = 7406406585860720451L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	@javax.annotation.Resource
	private MenuService menuService;
	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private MenuResourceService menuResourceService;
	
	// 参数
	private Menu menu;
	private int page;
	private int rows;
	private String resourceId;
	private String resourceIds;
	private String resourceOpts;
	
	// Action
	
	/**
	 * 查询分栏资源
	 * @return
	 * @throws Exception
	 */
	public String queryResourceByMenu() throws Exception {
		List<String> resources = new ArrayList<String>();
		List<MenuResource> resourceList = menuResourceService.queryByMenu(menu.getMenuId());
		
		if (resourceList != null && resourceList.size() > 0) {
			for (MenuResource menuResource : resourceList) {
				String str = menuResource.getResource().getResourceId();
				resources.add(str);
			}
		}
		
		jsonData.put("resources", resources);
		result = JSONObject.fromObject(jsonData);
		return SUCCESS;
	}
	
	public String queryResourceByMenuResrouce() throws Exception {
		List<String> options = new ArrayList<String>();
		MenuResource resource = menuResourceService.queryByMenuResource(menu.getMenuId(), resourceId);
		if (resource != null) {
			options.add(resource.getMenuResouceSave() == null ? "" : resource.getMenuResouceSave());
			options.add(resource.getMenuResouceDelete() == null ? "" : resource.getMenuResouceDelete());
			options.add(resource.getMenuResouceUpdate() == null ? "" : resource.getMenuResouceUpdate());
			options.add(resource.getMenuResouceSelect() == null ? "" : resource.getMenuResouceSelect());
			options.add(resource.getMenuResouceImport() == null ? "" : resource.getMenuResouceImport());
			options.add(resource.getMenuResouceExport() == null ? "" : resource.getMenuResouceExport());
			options.add(resource.getMenuResouceLike() == null ? "" : resource.getMenuResouceLike());
		}
		
		jsonData.put("options", options);
		result = JSONObject.fromObject(jsonData);
		return SUCCESS;
	}
	
	/**
	 * 配置资源
	 * @return
	 * @throws Exception
	 */
	public String insertMenuResource() throws Exception {
		User admin = (User) session.get("admin");
		
		List<MenuResource> list = new ArrayList<MenuResource>();
		String[] resources = resourceIds.split(";");
		String[] options = resourceOpts.split(";");
		if (resources != null && resources.length > 0) {
			for (String resource : resources) {
				if (resource.length() > 0) {
					MenuResource menuResource = new MenuResource();
					menuResource.setMenuResouceCreatename(admin.getUserName());
					menuResource.setMenuResouceDate(DateUtil.getDateTime());
					menuResource.setMenu(menuService.get(this.menu.getMenuId()));
					menuResource.setResource(resourceService.get(resource));
					
					//操作权限
					
					if (options != null && options.length > 0) {
						for (String opt : options) {
							if (opt.equals(menuResource.getResource().getResourceSaveUrl())) {
								menuResource.setMenuResouceSave(opt);
							} else if (opt.equals(menuResource.getResource().getResourceDeleteUrl())) {
								menuResource.setMenuResouceDelete(opt);
							} else if (opt.equals(menuResource.getResource().getResourceUpdateUrl())) {
								menuResource.setMenuResouceUpdate(opt);
							} else if (opt.equals(menuResource.getResource().getResourceSelectUrl())) {
								menuResource.setMenuResouceSelect(opt);
							} else if (opt.equals(menuResource.getResource().getResourceImportUrl())) {
								menuResource.setMenuResouceImport(opt);
							} else if (opt.equals(menuResource.getResource().getResourceExportUrl())) {
								menuResource.setMenuResouceExport(opt);
							} else if (opt.equals(menuResource.getResource().getResourceLikeUrl())) {
								menuResource.setMenuResouceLike(opt);
							}
						}
					}
					
					list.add(menuResource);
				}
			}
		}
		
		menuResourceService.insertAll(list, this.menu.getMenuId());
		
		result = JSONObject.fromObject(jsonData);
		
		return SUCCESS;
	}
	
	@Override
	public String insert() throws Exception {
		User admin = (User) session.get("admin");
		
		menu.setMenuCreatename(admin.getUserName());
		menu.setMenuDate(DateUtil.getDateTime());
		String message = menuService.insert(menu);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		String message = menuService.delete(menu);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		String message = menuService.update(menu);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		List<Menu> list = menuService.queryPageList(page, rows, "menuSort", "asc");

		MenuDTO dto = null;
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		if (list != null && list.size() > 0) {
			for (Menu obj : list) {
				dto = new MenuDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}

		jsonData.put("total", menuService.getCount());
		jsonData.put("rows", dtos);
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}
	
	// Array
	
	public String queryResourceArray() throws Exception {
		List<Resource> list = resourceService.queryList("resourceSort", "ASC");

		TreeCheckbox dto = null;
		List<TreeCheckbox> dtos = new ArrayList<TreeCheckbox>();
		if (list != null && list.size() > 0) {
			for (Resource obj : list) {
				//操作权限
				
				List<TreeCheckboxChildren> childrens = new ArrayList<TreeCheckboxChildren>();
				childrens.add(new TreeCheckboxChildren(obj.getResourceSaveUrl(), "增加"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceDeleteUrl(), "删除"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceUpdateUrl(), "修改"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceSelectUrl(), "查询"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceImportUrl(), "导入"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceExportUrl(), "导出"));
				childrens.add(new TreeCheckboxChildren(obj.getResourceLikeUrl(), "检索"));
				
				dto = new TreeCheckbox();
				dto.setId(obj.getResourceId());
				dto.setText(obj.getResourceName());
				dto.setChildren(childrens);
				dtos.add(dto);
			}
		}
		
		resultArray = JSONArray.fromObject(dtos);
		
		return "array";
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

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceOpts() {
		return resourceOpts;
	}

	public void setResourceOpts(String resourceOpts) {
		this.resourceOpts = resourceOpts;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
