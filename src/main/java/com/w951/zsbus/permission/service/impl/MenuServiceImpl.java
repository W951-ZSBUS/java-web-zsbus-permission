package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.util.bean.BeanUtil;
import com.w951.zsbus.permission.entity.Menu;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.MenuService;

@Component
public class MenuServiceImpl implements MenuService {
	@Resource
	private HibernateDao hibernateDao;

	private static final String QUERY_MENU_BY_NAME = "FROM Menu t WHERE t.menuName = :menuName";
	
	private static final String DELETE_GROUP_BY_MENU = "DELETE FROM GroupMenu t WHERE t.menu.menuId = :menuId";
	private static final String DELETE_MENU_BY_RESOURCE = "DELETE FROM MenuResource t WHERE t.menu.menuId = :menuId";
	private static final String DELETE_BY_MENU = "DELETE FROM Menu t WHERE t.menuId = :menuId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(Menu entity) {
		String[][] param = new String[][] {new String[] {"menuId" ,entity.getMenuId()}};
		hibernateDao.excuteHQL(DELETE_GROUP_BY_MENU, param);
		hibernateDao.excuteHQL(DELETE_MENU_BY_RESOURCE, param);
		hibernateDao.excuteHQL(DELETE_BY_MENU, param);
		return null;
	}

	public Menu get(String id) {
		return hibernateDao.get(new Menu(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new Menu());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(Menu entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 验证分栏名称

		if (entity.getMenuName() == null || entity.getMenuName().length() == 0) {
			return "请输入分栏名称";
		}

		List<User> list = hibernateDao.queryListByHql(
				QUERY_MENU_BY_NAME,
				new String[][] { new String[] { "menuName",
						entity.getMenuName() } });
		if (list != null && list.size() > 0) {
			return "分栏名称已存在，请重新输入";
		}

		// 数据持久化

		hibernateDao.insert(entity);

		return null;
	}

	public List<Menu> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new Menu(), order);
		} else {
			return hibernateDao.queryList(new Menu());
		}
	}

	public List<Menu> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new Menu(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new Menu(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(Menu entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 获取原数据

		Menu oldMenu = hibernateDao.get(new Menu(), entity.getMenuId());
		BeanUtil.beanToBean(entity, oldMenu);

		// 验证组名称

		if (!entity.getMenuName().equals(oldMenu.getMenuName())) {
			if (entity.getMenuName() == null
					|| entity.getMenuName().length() == 0) {
				return "请输入分栏名称";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_MENU_BY_NAME,
					new String[][] { new String[] { "menuName",
							entity.getMenuName() } });
			if (list != null && list.size() > 0) {
				return "分栏名称已存在，请重新输入";
			}
		}

		// 数据持久化

		hibernateDao.update(entity);

		return null;
	}

}
