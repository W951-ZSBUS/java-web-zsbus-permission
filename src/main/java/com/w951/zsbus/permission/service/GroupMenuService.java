package com.w951.zsbus.permission.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.GroupMenu;

@Transactional
public interface GroupMenuService extends CommonService<GroupMenu> {
	/**
	 * 查询组内分栏
	 * @param groupId 组ID
	 * @return
	 */
	public List<GroupMenu> queryByGroup(String groupId);
	
	public void insertAll(List<GroupMenu> entities, String groupId);
}
