package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.zsbus.permission.entity.MenuResource;
import com.w951.zsbus.permission.service.MenuResourceService;

@Component
public class MenuResourceServiceImpl implements MenuResourceService {
	@Resource
	private HibernateDao hibernateDao;
	
	private static final String QUERY_MENURESOURCE_BY_MENU = "FROM MenuResource t WHERE t.menu.menuId = :menuId";
	private static final String QUERY_MENURESOURCE_BY_MENU_RESOURCE = "FROM MenuResource t WHERE t.menu.menuId = :menuId AND t.resource.resourceId = :resourceId";
	
	private static final String DELETE_MENURESOURCE_BY_MENU = "DELETE FROM MenuResource t WHERE t.menu.menuId = :menuId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(MenuResource entity) {
		entity = get(entity.getMenuResouceId());
		hibernateDao.delete(entity);
		return null;
	}

	public MenuResource get(String id) {
		return hibernateDao.get(new MenuResource(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new MenuResource());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(MenuResource entity) {
		hibernateDao.insert(entity);
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAll(List<MenuResource> entities, String menuId) {
		hibernateDao.excuteHQL(DELETE_MENURESOURCE_BY_MENU, new String[][] {new String[] {"menuId", menuId}});
		
		if (entities != null && entities.size() > 0) {
			for (int i = 0; i < entities.size(); i++) {
				hibernateDao.insert(entities.get(i));;
			}
		}
		
//		hibernateDao.insertAll(entities);
	}

	public List<MenuResource> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new MenuResource(), order);
		} else {
			return hibernateDao.queryList(new MenuResource());
		}
	}

	public List<MenuResource> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new MenuResource(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new MenuResource(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(MenuResource entity) {
		hibernateDao.update(entity);
		return null;
	}
	
	/*----------自定义接口----------*/

	public List<MenuResource> queryByMenu(String menuId) {
		return hibernateDao.queryListByHql(QUERY_MENURESOURCE_BY_MENU, new String[][] {new String[] {"menuId", menuId}});
	}

	public MenuResource queryByMenuResource(String menuId, String resourceId) {
		List<MenuResource> list = hibernateDao.queryListByHql(QUERY_MENURESOURCE_BY_MENU_RESOURCE, new String[][] {new String[] {"menuId", menuId}, new String[] {"resourceId", resourceId}});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
