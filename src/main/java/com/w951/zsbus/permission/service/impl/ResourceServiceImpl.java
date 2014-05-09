package com.w951.zsbus.permission.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.util.bean.BeanUtil;
import com.w951.zsbus.permission.entity.Resource;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.ResourceService;

@Component
public class ResourceServiceImpl implements ResourceService {
	@javax.annotation.Resource
	private HibernateDao hibernateDao;

	private static final String QUERY_RESOURCE_BY_IDENTIF = "FROM Resource t WHERE t.resourceIdentif = :resourceIdentif";
	
	private static final String DELETE_MENU_BY_RESOURCE = "DELETE FROM MenuResource t WHERE t.resource.resourceId = :resourceId";
	private static final String DELETE_BY_RESOURCE = "DELETE FROM Resource t WHERE t.resourceId = :resourceId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(Resource entity) {
		String[][] param = new String[][] {new String[] {"resourceId", entity.getResourceId()}};
		hibernateDao.excuteHQL(DELETE_MENU_BY_RESOURCE, param);
		hibernateDao.excuteHQL(DELETE_BY_RESOURCE, param);
		return null;
	}

	public Resource get(String id) {
		return hibernateDao.get(new Resource(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new Resource());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(Resource entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 验证资源标识

		if (entity.getResourceIdentif() == null
				|| entity.getResourceIdentif().length() == 0) {
			return "请输入资源标识";
		}

		List<User> list = hibernateDao.queryListByHql(
				QUERY_RESOURCE_BY_IDENTIF,
				new String[][] { new String[] { "resourceIdentif",
						entity.getResourceIdentif() } });
		if (list != null && list.size() > 0) {
			return "资源标识已存在，请重新输入";
		}

		// 数据持久化

		hibernateDao.insert(entity);

		return null;
	}

	public List<Resource> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new Resource(), order);
		} else {
			return hibernateDao.queryList(new Resource());
		}
	}

	public List<Resource> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new Resource(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new Resource(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(Resource entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 获取原数据

		Resource oldResource = hibernateDao.get(new Resource(),
				entity.getResourceId());
		BeanUtil.beanToBean(entity, oldResource);

		// 验证组名称

		if (!entity.getResourceIdentif().equals(
				oldResource.getResourceIdentif())) {
			if (entity.getResourceIdentif() == null
					|| entity.getResourceIdentif().length() == 0) {
				return "请输入资源标识";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_RESOURCE_BY_IDENTIF, new String[][] { new String[] {
							"resourceIdentif", entity.getResourceIdentif() } });
			if (list != null && list.size() > 0) {
				return "资源标识已存在，请重新输入";
			}
		}

		// 数据持久化

		hibernateDao.update(entity);

		return null;
	}
}
