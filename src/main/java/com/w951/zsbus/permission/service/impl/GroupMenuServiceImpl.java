package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.zsbus.permission.entity.GroupMenu;
import com.w951.zsbus.permission.service.GroupMenuService;

@Component
public class GroupMenuServiceImpl implements GroupMenuService {
	@Resource
	private HibernateDao hibernateDao;
	
	private static final String QUERY_GROUPMENU_BY_GROUP = "FROM GroupMenu t WHERE t.group.groupId = :groupId";
	private static final String DELETE_GROUPMENU_BY_GROUP = "DELETE FROM GroupMenu t WHERE t.group.groupId = :groupId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(GroupMenu entity) {
		entity = get(entity.getGroupMenuId());
		hibernateDao.delete(entity);
		return null;
	}

	public GroupMenu get(String id) {
		return hibernateDao.get(new GroupMenu(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new GroupMenu());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(GroupMenu entity) {
		hibernateDao.insert(entity);
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAll(List<GroupMenu> entities, String groupId) {
		hibernateDao.excuteHQL(DELETE_GROUPMENU_BY_GROUP, new String[][] {new String[] {"groupId", groupId}});
		
		if (entities != null && entities.size() > 0) {
			for (int i = 0; i < entities.size(); i++) {
				hibernateDao.insert(entities.get(i));;
			}
		}
		
//		hibernateDao.insertAll(entities);
	}

	public List<GroupMenu> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new GroupMenu(), order);
		} else {
			return hibernateDao.queryList(new GroupMenu());
		}
	}

	public List<GroupMenu> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new GroupMenu(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new GroupMenu(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(GroupMenu entity) {
		hibernateDao.update(entity);
		return null;
	}
	
	/*----------自定义接口----------*/

	public List<GroupMenu> queryByGroup(String groupId) {
		return hibernateDao.queryListByHql(QUERY_GROUPMENU_BY_GROUP, new String[][] {new String[] {"groupId", groupId}});
	}

}
