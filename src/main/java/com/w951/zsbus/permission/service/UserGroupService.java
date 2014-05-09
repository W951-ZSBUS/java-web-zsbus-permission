package com.w951.zsbus.permission.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.UserGroup;

@Transactional
public interface UserGroupService extends CommonService<UserGroup> {
	/**
	 * 查询组内成员
	 * @param groupId 组ID
	 * @return
	 */
	public List<UserGroup> queryByGroup(String groupId);
	
	public void insertAll(List<UserGroup> entities, String groupId);
}
