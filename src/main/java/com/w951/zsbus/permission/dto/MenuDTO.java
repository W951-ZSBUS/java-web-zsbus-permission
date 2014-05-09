package com.w951.zsbus.permission.dto;

import java.io.Serializable;
import java.util.List;

public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1210158860423305672L;
	private String menuId;
	private String menuName;
	private String menuDate;
	private String menuCreatename;
	private String menuBack;
	private Integer menuSort;
	private List<String[]> resources;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDate() {
		return menuDate;
	}

	public void setMenuDate(String menuDate) {
		this.menuDate = menuDate;
	}

	public String getMenuCreatename() {
		return menuCreatename;
	}

	public void setMenuCreatename(String menuCreatename) {
		this.menuCreatename = menuCreatename;
	}

	public String getMenuBack() {
		return menuBack;
	}

	public void setMenuBack(String menuBack) {
		this.menuBack = menuBack;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public List<String[]> getResources() {
		return resources;
	}

	public void setResources(List<String[]> resources) {
		this.resources = resources;
	}
}
