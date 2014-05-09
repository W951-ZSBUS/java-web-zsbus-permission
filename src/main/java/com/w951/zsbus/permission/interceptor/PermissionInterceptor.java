package com.w951.zsbus.permission.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.w951.zsbus.permission.common.PermissionVerify;
import com.w951.zsbus.permission.entity.GroupMenu;
import com.w951.zsbus.permission.entity.MenuResource;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.entity.UserGroup;

/**
 * 权限拦截器
 * @author lusifer
 *
 */
public class PermissionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5725074215333805471L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setContentType("text/html;charset=UTF-8");
		String requestURI = request.getRequestURI();
		
		User admin = (User) request.getSession().getAttribute("admin");
		
		if (!requestURI.endsWith("login")
				&& !requestURI.endsWith("loginOut")) {
			// 判断是否超时
			
			if (admin == null) {
				response.getWriter().print("<script>alert('请求超时，请重新登录');</script>");
				return null;
			}
			
			// 判断用户状态
			
			if (admin.getUserVerify() == PermissionVerify.DISABLE) {
				response.getWriter().print("<script>alert('该账号已被禁用，请联系管理员');</script>");
				return null;
			}
			
			// 判断用户组状态
			
			List<UserGroup> groups = admin.getUserGroups();
			if (groups == null || groups.size() == 0) {
				response.getWriter().print("<script>alert('该账号没有任何操作权限');</script>");
				return null;
			} else {
				int gCount = 0;
				for (UserGroup group : groups) {
					if (group.getGroup().getGroupVerify() == PermissionVerify.ENABLE) {
						gCount++;
						break;
					}
				}
				
				if (gCount == 0) {
					response.getWriter().print("<script>alert('该账号没有任何操作权限');</script>");
					return null;
				} else {
					// 判断分组分栏
					
					for (UserGroup group : groups) {
						List<GroupMenu> menus = group.getGroup().getGroupMenus();
						if (menus == null || menus.size() == 0) {
							response.getWriter().print("<script>alert('该账号没有任何操作权限');</script>");
							return null;
						}
						
						// 判断分栏资源
						
						int mCount = 0;
						
						// 判断操作权限
						
						int rCount = 0;
						
						for (GroupMenu menu : menus) {
							List<MenuResource> resources = menu.getMenu().getMenuResources();
							if (resources == null || resources.size() == 0) {
								continue;
							}
							
							//有分栏权限
							
							mCount++;
							
							for (MenuResource resource : resources) {
								// 请求地址和设定地址相同
								
								if (requestURI.endsWith(resource.getResource().getResourceUrl())
										&& resource.getResource().getResourceVerify() == PermissionVerify.ENABLE
										|| requestURI.indexOf("Main") != -1) {
									rCount++;
									break;
								} else if (requestURI.indexOf("action") != -1) {
									if (requestURI.indexOf("query") != -1 && resource.getMenuResouceSelect() != null) {
										if (requestURI.indexOf(resource.getMenuResouceSelect()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("insert") != -1 && resource.getMenuResouceSave() != null) {
										if (requestURI.indexOf(resource.getMenuResouceSave()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("update") != -1 && resource.getMenuResouceUpdate() != null) {
										if (requestURI.indexOf(resource.getMenuResouceUpdate()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("delete") != -1 && resource.getMenuResouceDelete() != null) {
										if (requestURI.indexOf(resource.getMenuResouceDelete()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("export") != -1 && resource.getMenuResouceExport() != null) {
										if (requestURI.indexOf(resource.getMenuResouceExport()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("import") != -1 && resource.getMenuResouceImport() != null) {
										if (requestURI.indexOf(resource.getMenuResouceImport()) != -1) {
											rCount++;
											break;
										}
									} else if (requestURI.indexOf("like") != -1 && resource.getMenuResouceLike() != null) {
										if (requestURI.indexOf(resource.getMenuResouceLike()) != -1) {
											rCount++;
											break;
										}
									}
								}
							}
						}
						
						// 没有分栏权限
						
						if (mCount == 0) {
							response.getWriter().print("<script>alert('该账号没有任何操作权限');</script>");
							return null;
						}
						
						// 没有操作权限
						
						if (rCount == 0) {
							response.getWriter().print("<script>alert('该账号没有操作权限，请联系管理员');</script>");
							return null;
						}
					}
				}
			}
		}
		
		return invocation.invoke();
	}
}
