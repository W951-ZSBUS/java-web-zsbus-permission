package com.w951.zsbus.permission.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.MenuResource;

@Transactional
public interface MenuResourceService extends CommonService<MenuResource> {
	
	/**
	 * 查询分栏资源
	 * @param menuId 分栏ID
	 * @param resourceId 资源ID
	 * @return
	 */
	public MenuResource queryByMenuResource(String menuId, String resourceId);
	
	/**
	 * 查询分栏资源
	 * @param menuId 分栏ID
	 * @return
	 */
	public List<MenuResource> queryByMenu(String menuId);
	
	public void insertAll(List<MenuResource> entities, String menuId);
}
