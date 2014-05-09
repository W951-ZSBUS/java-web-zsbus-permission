package com.w951.zsbus.permission.service;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.Resource;

@Transactional
public interface ResourceService extends CommonService<Resource> {
	
}
