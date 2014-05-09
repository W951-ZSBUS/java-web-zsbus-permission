package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.util.bean.BeanUtil;
import com.w951.zsbus.permission.entity.Group;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
	@Resource
	private HibernateDao hibernateDao;

	private static final String QUERY_GROUP_BY_NAME = "FROM Group t WHERE t.groupName = :groupName";
	
	private static final String DELETE_USER_BY_GROUP = "DELETE FROM UserGroup t WHERE t.group.groupId = :groupId";
	private static final String DELETE_GROUP_BY_MENU = "DELETE FROM GroupMenu t WHERE t.group.groupId = :groupId";
	private static final String DELETE_BY_GROUP = "DELETE FROM Group t WHERE t.groupId = :groupId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(Group entity) {
		String[][] param = new String[][] {new String[] {"groupId", entity.getGroupId()}};
		hibernateDao.excuteHQL(DELETE_USER_BY_GROUP, param);
		hibernateDao.excuteHQL(DELETE_GROUP_BY_MENU, param);
		hibernateDao.excuteHQL(DELETE_BY_GROUP, param);
		return null;
	}

	public Group get(String id) {
		return hibernateDao.get(new Group(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new Group());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(Group entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 验证组名称

		if (entity.getGroupName() == null
				|| entity.getGroupName().length() == 0) {
			return "请输入组名称";
		}

		List<User> list = hibernateDao.queryListByHql(
				QUERY_GROUP_BY_NAME,
				new String[][] { new String[] { "groupName",
						entity.getGroupName() } });
		if (list != null && list.size() > 0) {
			return "组名称已存在，请重新输入";
		}

		// 数据持久化

		hibernateDao.insert(entity);

		return null;
	}

	public List<Group> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new Group(), order);
		} else {
			return hibernateDao.queryList(new Group());
		}
	}

	public List<Group> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new Group(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new Group(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(Group entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 获取原数据

		Group oldGroup = hibernateDao.get(new Group(),
				entity.getGroupId());
		BeanUtil.beanToBean(entity, oldGroup);

		// 验证组名称

		if (!entity.getGroupName().equals(oldGroup.getGroupName())) {
			if (entity.getGroupName() == null
					|| entity.getGroupName().length() == 0) {
				return "请输入组名称";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_GROUP_BY_NAME,
					new String[][] { new String[] { "groupName",
							entity.getGroupName() } });
			if (list != null && list.size() > 0) {
				return "组名称已存在，请重新输入";
			}
		}

		// 数据持久化

		hibernateDao.update(entity);

		return null;
	}

}
