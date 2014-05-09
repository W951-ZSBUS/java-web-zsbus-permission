package com.w951.zsbus.permission.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.w951.orm.hibernate.HibernateDao;
import com.w951.util.bean.BeanUtil;
import com.w951.util.bean.StringUtil;
import com.w951.zsbus.permission.entity.User;
import com.w951.zsbus.permission.service.UserService;

@Component
public class UserServiceImpl implements UserService {
	@Resource
	private HibernateDao hibernateDao;

	private static final String QUERY_USER_BY_NM = "FROM User t WHERE t.userNm = :userNm";
	private static final String QUERY_USER_BY_PHONE = "FROM User t WHERE t.userPhone = :userPhone";
	private static final String QUERY_USER_BY_QQ = "FROM User t WHERE t.userQq = :userQq";
	private static final String QUERY_USER_BY_EMAIL = "FROM User t WHERE t.userEmail = :userEmail";

	private static final String USER_LOGIN_BY_USERNM_USERPASS = "FROM User t WHERE t.userNm = :userNm AND t.userPass = :userPass";
	
	private static final String DELETE_USER_BY_GROUP = "DELETE FROM UserGroup t WHERE t.user.userId = :userId";
	private static final String DELETE_BY_USER = "DELETE FROM User t WHERE t.userId = :userId";

	@Transactional(propagation = Propagation.REQUIRED)
	public String delete(User entity) {
		String[][] param = new String[][] {new String[] {"userId", entity.getUserId()}};
		hibernateDao.excuteHQL(DELETE_USER_BY_GROUP, param);
		hibernateDao.excuteHQL(DELETE_BY_USER, param);
		return null;
	}

	public User get(String id) {
		return hibernateDao.get(new User(), id);
	}

	public long getCount() {
		return hibernateDao.getCount(new User());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String insert(User entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 验证账户名称

		if (entity.getUserNm() == null || entity.getUserNm().length() < 6
				|| entity.getUserNm().length() > 20) {
			return "账户名称长度为6-20位";
		}

		List<User> list = hibernateDao
				.queryListByHql(QUERY_USER_BY_NM, new String[][] { new String[] {
						"userNm", entity.getUserNm() } });
		if (list != null && list.size() > 0) {
			return "账户名称已存在，请重新输入";
		}

		// 验证用户姓名

		if (entity.getUserName() == null || entity.getUserName().length() > 8) {
			return "用户姓名输入有误，请重新输入";
		}

		// 验证用户密码

		if (entity.getUserPass() == null || entity.getUserPass().length() < 6
				|| entity.getUserPass().length() > 20) {
			return "用户密码长度为6-20位";
		}

		// 验证用户手机

		if (entity.getUserPhone() == null
				|| entity.getUserPhone().length() == 0) {
			return "请输入用户手机";
		}

		list = hibernateDao.queryListByHql(
				QUERY_USER_BY_PHONE,
				new String[][] { new String[] { "userPhone",
						entity.getUserPhone() } });
		if (list != null && list.size() > 0) {
			return "用户手机已存在，请重新输入";
		}

		// 验证用户QQ

		if (entity.getUserQq() == null || entity.getUserQq().length() == 0) {
			return "请输入用户QQ";
		}

		list = hibernateDao
				.queryListByHql(QUERY_USER_BY_QQ, new String[][] { new String[] {
						"userQq", entity.getUserQq() } });
		if (list != null && list.size() > 0) {
			return "用户QQ已存在，请重新输入";
		}

		// 验证用户邮箱

		if (entity.getUserEmail() == null
				|| entity.getUserEmail().length() == 0) {
			return "请输入用户邮箱";
		}

		list = hibernateDao.queryListByHql(
				QUERY_USER_BY_EMAIL,
				new String[][] { new String[] { "userEmail",
						entity.getUserEmail() } });
		if (list != null && list.size() > 0) {
			return "用户邮箱已存在，请重新输入";
		}

		// 数据持久化
		entity.setUserPass(StringUtil.toMD5(entity.getUserPass()));
		hibernateDao.insert(entity);

		return null;
	}

	public List<User> queryList(String... order) {
		if (order != null) {
			return hibernateDao.queryList(new User(), order);
		} else {
			return hibernateDao.queryList(new User());
		}
	}

	public List<User> queryPageList(int pageIndex, int pageSize,
			String... order) {
		if (order != null) {
			return hibernateDao.queryPageList(new User(), pageIndex,
					pageSize, order);
		} else {
			return hibernateDao.queryPageList(new User(), pageIndex,
					pageSize);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String update(User entity) {
		if (entity == null) {
			return "传入了空对象";
		}

		// 获取原数据

		User oldUser = hibernateDao.get(new User(), entity.getUserId());
		BeanUtil.beanToBean(entity, oldUser);

		// 验证账户名称

		if (!entity.getUserNm().equals(oldUser.getUserNm())) {
			if (entity.getUserNm() == null || entity.getUserNm().length() < 6
					|| entity.getUserNm().length() > 20) {
				return "账户名称长度为6-20位";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_USER_BY_NM,
					new String[][] { new String[] { "userNm",
							entity.getUserNm() } });
			if (list != null && list.size() > 0) {
				return "账户名称已存在，请重新输入";
			}
		}

		// 验证用户姓名

		if (!entity.getUserName().equals(oldUser.getUserName())) {
			if (entity.getUserName() == null
					|| entity.getUserName().length() > 8) {
				return "用户姓名输入有误，请重新输入";
			}
		}

		// 验证用户密码

		if (!entity.getUserPass().equals(oldUser.getUserPass())) {
			if (entity.getUserPass() == null
					|| entity.getUserPass().length() < 6
					|| entity.getUserPass().length() > 20) {
				return "用户密码长度为6-20位";
			}
		}

		// 验证用户手机

		if (!entity.getUserPhone().equals(oldUser.getUserPhone())) {
			if (entity.getUserPhone() == null
					|| entity.getUserPhone().length() == 0) {
				return "请输入用户手机";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_USER_BY_PHONE,
					new String[][] { new String[] { "userPhone",
							entity.getUserPhone() } });
			if (list != null && list.size() > 0) {
				return "用户手机已存在，请重新输入";
			}
		}

		// 验证用户QQ

		if (!entity.getUserQq().equals(oldUser.getUserQq())) {
			if (entity.getUserQq() == null || entity.getUserQq().length() == 0) {
				return "请输入用户QQ";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_USER_BY_QQ,
					new String[][] { new String[] { "userQq",
							entity.getUserQq() } });
			if (list != null && list.size() > 0) {
				return "用户QQ已存在，请重新输入";
			}
		}

		// 验证用户邮箱

		if (!entity.getUserEmail().equals(oldUser.getUserEmail())) {
			if (entity.getUserEmail() == null
					|| entity.getUserEmail().length() == 0) {
				return "请输入用户邮箱";
			}

			List<User> list = hibernateDao.queryListByHql(
					QUERY_USER_BY_EMAIL,
					new String[][] { new String[] { "userEmail",
							entity.getUserEmail() } });
			if (list != null && list.size() > 0) {
				return "用户邮箱已存在，请重新输入";
			}
		}

		// 数据持久化

		entity.setUserPass(StringUtil.toMD5(entity.getUserPass()));
		hibernateDao.update(entity);

		return null;
	}

	/*----------自定义接口----------*/

	public User getUserByPass(String userNm, String userPass) {
		List<User> list = hibernateDao.queryListByHql(USER_LOGIN_BY_USERNM_USERPASS,
				new String[][] { new String[] { "userNm", userNm },
						new String[] { "userPass", userPass } });

		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}
