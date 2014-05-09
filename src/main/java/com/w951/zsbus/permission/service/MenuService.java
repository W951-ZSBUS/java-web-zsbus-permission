package com.w951.zsbus.permission.service;

import org.springframework.transaction.annotation.Transactional;

import com.w951.util.service.CommonService;
import com.w951.zsbus.permission.entity.Menu;

@Transactional
public interface MenuService extends CommonService<Menu> {
}
