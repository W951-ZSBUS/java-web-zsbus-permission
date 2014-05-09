package com.w951.zsbus.permission.service;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.User;

@Transactional
public interface UserService extends CommonService<User> {
	/**
	 * 使用用户名密码获取对象
	 * @param userNm
	 * @param userPass
	 * @return
	 */
	public User getUserByPass(String userNm, String userPass);
	
}
