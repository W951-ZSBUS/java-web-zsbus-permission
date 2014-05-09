package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.zsbus.permission.entity.UserGroup;
import com.w951.zsbus.permission.service.UserGroupService;

@Component
public class UserGroupServiceImpl implements UserGroupService {
	@Resource
	private HibernateDao hibernateDao;
	
	private static final String QUERY_USERGROUP_BY_GROUP = "FROM UserGroup t WHERE t.group.groupId = :groupId";
	private static final String DELETE_USERGROUP_BY_GROUP = "DELETE FROM UserGroup t WHERE t.group.groupId = :groupId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(UserGroup entity) {
		entity = get(entity.getUserGroupId());
		hibernateDao.delete(entity);
		return null;
	}

	public UserGroup get(String id) {
		return hibernateDao.get(new UserGroup(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new UserGroup());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(UserGroup entity) {
		hibernateDao.insert(entity);
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAll(List<UserGroup> entities, String groupId) {
		hibernateDao.excuteHQL(DELETE_USERGROUP_BY_GROUP, new String[][] {new String[] {"groupId", groupId}});
		
		if (entities != null && entities.size() > 0) {
			for (int i = 0; i < entities.size(); i++) {
				hibernateDao.insert(entities.get(i));;
			}
		}
		
//		hibernateDao.insertAll(entities);
	}

	public List<UserGroup> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new UserGroup(), order);
		} else {
			return hibernateDao.queryList(new UserGroup());
		}
	}

	public List<UserGroup> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new UserGroup(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new UserGroup(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(UserGroup entity) {
		hibernateDao.update(entity);
		return null;
	}
	
	/*----------自定义接口----------*/

	public List<UserGroup> queryByGroup(String groupId) {
		return hibernateDao.queryListByHql(QUERY_USERGROUP_BY_GROUP, new String[][] {new String[] {"groupId", groupId}});
	}

}
