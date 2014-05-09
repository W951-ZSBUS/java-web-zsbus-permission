package com.w951.zsbus.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "zsbus_permission_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu implements java.io.Serializable {
	private static final long serialVersionUID = -2517388537473293543L;
	private String menuId;
	private String menuName;
	private String menuDate;
	private String menuCreatename;
	private String menuBack;
	private Integer menuSort;
	private List<MenuResource> menuResources = new ArrayList<MenuResource>();
	private List<GroupMenu> groupMenus = new ArrayList<GroupMenu>();

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "menu_id", unique = true, nullable = false, length = 32)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_name", length = 20)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "menu_date", length = 19)
	public String getMenuDate() {
		return this.menuDate;
	}

	public void setMenuDate(String menuDate) {
		this.menuDate = menuDate;
	}

	@Column(name = "menu_createname", length = 20)
	public String getMenuCreatename() {
		return this.menuCreatename;
	}

	public void setMenuCreatename(String menuCreatename) {
		this.menuCreatename = menuCreatename;
	}

	@Column(name = "menu_back", length = 300)
	public String getMenuBack() {
		return this.menuBack;
	}

	public void setMenuBack(String menuBack) {
		this.menuBack = menuBack;
	}

	@Column(name = "menu_sort")
	public Integer getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "menu")
	public List<MenuResource> getMenuResources() {
		return this.menuResources;
	}

	public void setMenuResources(List<MenuResource> menuResources) {
		this.menuResources = menuResources;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
	public List<GroupMenu> getGroupMenus() {
		return this.groupMenus;
	}

	public void setGroupMenus(List<GroupMenu> groupMenus) {
		this.groupMenus = groupMenus;
	}

}