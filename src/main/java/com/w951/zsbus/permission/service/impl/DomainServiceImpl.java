package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.zsbus.permission.entity.Domain;
import com.w951.zsbus.permission.service.DomainService;
import com.w951.orm.hibernate.HibernateDao;
import com.w951.util.bean.BeanUtil;

/**
 * 
 * 系统版本：v1.0<br>
 * 开发人员：Lusifer<br>
 * 日期：2014-05-13<br>
 * 时间：16:18:03<br>
 * 功能描述：写明作用，调用方式，使用场景，以及特殊情况<br>
 *
 */
@Component
public class DomainServiceImpl implements DomainService {
	@Resource
	private HibernateDao hibernateDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(Domain entity) {
		entity = get(entity.getDomainId());
		hibernateDao.delete(entity);
		return null;
	}

	public Domain get(String id) {
		return hibernateDao.get(new Domain(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new Domain());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(Domain entity) {
		hibernateDao.insert(entity);
		return null;
	}

	public List<Domain> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new Domain(), order);
		} else {
			return hibernateDao.queryList(new Domain());
		}
	}

	public List<Domain> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new Domain(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new Domain(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(Domain entity) {
		Domain obj = get(entity.getDomainId());
		BeanUtil.beanToBean(entity, obj);
		hibernateDao.update(entity);
		return null;
	}

}