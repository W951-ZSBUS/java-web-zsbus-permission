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
import com.w951.util.ui.easyui.TreeCheckbox;
import com.w951.zsbus.permission.dto.GroupDTO;
import com.w951.zsbus.permission.entity.Group;
import com.w951.zsbus.permission.entity.GroupMenu;
import com.w951.zsbus.permission.entity.Menu;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.entity.UserGroup;
import com.w951.zsbus.permission.service.GroupMenuService;
import com.w951.zsbus.permission.service.GroupService;
import com.w951.zsbus.permission.service.MenuService;
import com.w951.zsbus.permission.service.UserGroupService;
import com.w951.zsbus.permission.service.UserService;

public class GroupAction extends CommonAction {
	private static final long serialVersionUID = -2457301249358564061L;
	private JSONObject result;
	private JSONArray resultArray;
	private Map<String, Object> request;
	private Map<String, Object> session;
	
	@Resource
	private GroupService groupService;
	@Resource
	private UserService userService;
	@Resource
	private UserGroupService userGroupService;
	@Resource
	private MenuService menuService;
	@Resource
	private GroupMenuService groupMenuService;
	
	// 参数
	private Group group;
	private int page;
	private int rows;
	private String userIds;
	private String menuIds;
	
	// Action
	
	/**
	 * 查询组内成员
	 * @return
	 * @throws Exception
	 */
	public String queryUserByGroup() throws Exception {
		List<String> users = new ArrayList<String>();
		List<UserGroup> userList = userGroupService.queryByGroup(group.getGroupId());
		
		if (userList != null && userList.size() > 0) {
			for (UserGroup userGroup : userList) {
				String str = userGroup.getUser().getUserId();
				users.add(str);
			}
		}
		
		jsonData.put("users", users);
		result = JSONObject.fromObject(jsonData);
		return SUCCESS;
	}
	
	/**
	 * 查询组内分栏
	 * @return
	 * @throws Exception
	 */
	public String queryMenuByGroup() throws Exception {
		List<String> menus = new ArrayList<String>();
		List<GroupMenu> menuList = groupMenuService.queryByGroup(group.getGroupId());
		
		if (menuList != null && menuList.size() > 0) {
			for (GroupMenu groupMenu : menuList) {
				String str = groupMenu.getMenu().getMenuId();
				menus.add(str);
			}
		}
		
		jsonData.put("menus", menus);
		result = JSONObject.fromObject(jsonData);
		return SUCCESS;
	}
	
	/**
	 * 配置用户
	 * @return
	 * @throws Exception
	 */
	public String insertUserGroup() throws Exception {
		User admin = (User) session.get("admin");
		
		List<UserGroup> list = new ArrayList<UserGroup>();
		String[] users = userIds.split(";");
		if (users != null && users.length > 0) {
			for (String user : users) {
				if (user.length() > 0) {
					UserGroup group = new UserGroup();
					group.setUserGroupName(admin.getUserName());
					group.setUserGroupDate(DateUtil.getDateTime());
					group.setGroup(groupService.get(this.group.getGroupId()));
					group.setUser(userService.get(user));
					list.add(group);
				}
			}
		}
		
		userGroupService.insertAll(list, this.group.getGroupId());
		
		result = JSONObject.fromObject(jsonData);
		
		return SUCCESS;
	}
	
	/**
	 * 配置分栏
	 * @return
	 * @throws Exception
	 */
	public String insertGroupMenu() throws Exception {
		User admin = (User) session.get("admin");
		
		List<GroupMenu> list = new ArrayList<GroupMenu>();
		String[] menus = menuIds.split(";");
		if (menus != null && menus.length > 0) {
			for (String menu : menus) {
				if (menu.length() > 0) {
					GroupMenu groupMenu = new GroupMenu();
					groupMenu.setGroupMenuName(admin.getUserName());
					groupMenu.setGroupMenuDate(DateUtil.getDateTime());
					groupMenu.setGroup(groupService.get(this.group.getGroupId()));
					groupMenu.setMenu(menuService.get(menu));
					list.add(groupMenu);
				}
			}
		}
		
		groupMenuService.insertAll(list, this.group.getGroupId());
		
		result = JSONObject.fromObject(jsonData);
		
		return SUCCESS;
	}
	
	@Override
	public String insert() throws Exception {
		User admin = (User) session.get("admin");
		
		group.setGroupCreatename(admin.getUserName());
		group.setGroupCreatedate(DateUtil.getDateTime());
		String message = groupService.insert(group);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		String message = groupService.delete(group);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		String message = groupService.update(group);

		if (message != null) {
			jsonData.put("message", message);
		}
		
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		List<Group> list = groupService.queryPageList(page, rows);

		GroupDTO dto = null;
		List<GroupDTO> dtos = new ArrayList<GroupDTO>();
		if (list != null && list.size() > 0) {
			for (Group obj : list) {
				dto = new GroupDTO();
				BeanUtil.beanToBean(dto, obj);
				dtos.add(dto);
			}
		}

		jsonData.put("total", groupService.getCount());
		jsonData.put("rows", dtos);
		result = JSONObject.fromObject(jsonData);

		return SUCCESS;
	}
	
	// Array
	
	public String queryUserArray() throws Exception {
		List<User> list = userService.queryList();

		TreeCheckbox dto = null;
		List<TreeCheckbox> dtos = new ArrayList<TreeCheckbox>();
		if (list != null && list.size() > 0) {
			for (User obj : list) {
				dto = new TreeCheckbox();
				dto.setId(obj.getUserId());
				dto.setText(obj.getUserName());
				dtos.add(dto);
			}
		}
		
		resultArray = JSONArray.fromObject(dtos);
		
		return "array";
	}
	
	public String queryMenuArray() throws Exception {
		List<Menu> list = menuService.queryList("menuSort", "ASC");

		TreeCheckbox dto = null;
		List<TreeCheckbox> dtos = new ArrayList<TreeCheckbox>();
		if (list != null && list.size() > 0) {
			for (Menu obj : list) {
				dto = new TreeCheckbox();
				dto.setId(obj.getMenuId());
				dto.setText(obj.getMenuName());
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

	public JSONArray getResultArray() {
		return resultArray;
	}

	public void setResultArray(JSONArray resultArray) {
		this.resultArray = resultArray;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

}
