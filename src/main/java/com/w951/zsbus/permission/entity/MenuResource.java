package com.w951.zsbus.permission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "zsbus_permission_menu_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MenuResource implements java.io.Serializable {
	private static final long serialVersionUID = -6751225160087003072L;
	private String menuResouceId;
	private Menu menu;
	private Resource resource;
	private String menuResouceCreatename;
	private String menuResouceDate;
	private String menuResouceBack;
	private String menuResouceSave;
	private String menuResouceUpdate;
	private String menuResouceDelete;
	private String menuResouceSelect;
	private String menuResouceImport;
	private String menuResouceExport;
	private String menuResouceLike;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "menu_resouce_id", unique = true, nullable = false, length = 32)
	public String getMenuResouceId() {
		return this.menuResouceId;
	}

	public void setMenuResouceId(String menuResouceId) {
		this.menuResouceId = menuResouceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id")
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Column(name = "menu_resouce_createname", length = 20)
	public String getMenuResouceCreatename() {
		return this.menuResouceCreatename;
	}

	public void setMenuResouceCreatename(String menuResouceCreatename) {
		this.menuResouceCreatename = menuResouceCreatename;
	}

	@Column(name = "menu_resouce_date", length = 19)
	public String getMenuResouceDate() {
		return this.menuResouceDate;
	}

	public void setMenuResouceDate(String menuResouceDate) {
		this.menuResouceDate = menuResouceDate;
	}

	@Column(name = "menu_resouce_back", length = 300)
	public String getMenuResouceBack() {
		return this.menuResouceBack;
	}

	public void setMenuResouceBack(String menuResouceBack) {
		this.menuResouceBack = menuResouceBack;
	}

	@Column(name = "menu_resouce_save", length = 100)
	public String getMenuResouceSave() {
		return this.menuResouceSave;
	}

	public void setMenuResouceSave(String menuResouceSave) {
		this.menuResouceSave = menuResouceSave;
	}

	@Column(name = "menu_resouce_update", length = 100)
	public String getMenuResouceUpdate() {
		return this.menuResouceUpdate;
	}

	public void setMenuResouceUpdate(String menuResouceUpdate) {
		this.menuResouceUpdate = menuResouceUpdate;
	}

	@Column(name = "menu_resouce_delete", length = 100)
	public String getMenuResouceDelete() {
		return this.menuResouceDelete;
	}

	public void setMenuResouceDelete(String menuResouceDelete) {
		this.menuResouceDelete = menuResouceDelete;
	}

	@Column(name = "menu_resouce_select", length = 100)
	public String getMenuResouceSelect() {
		return this.menuResouceSelect;
	}

	public void setMenuResouceSelect(String menuResouceSelect) {
		this.menuResouceSelect = menuResouceSelect;
	}

	@Column(name = "menu_resouce_import", length = 100)
	public String getMenuResouceImport() {
		return this.menuResouceImport;
	}

	public void setMenuResouceImport(String menuResouceImport) {
		this.menuResouceImport = menuResouceImport;
	}

	@Column(name = "menu_resouce_export", length = 100)
	public String getMenuResouceExport() {
		return this.menuResouceExport;
	}

	public void setMenuResouceExport(String menuResouceExport) {
		this.menuResouceExport = menuResouceExport;
	}

	@Column(name = "menu_resouce_like", length = 100)
	public String getMenuResouceLike() {
		return this.menuResouceLike;
	}

	public void setMenuResouceLike(String menuResouceLike) {
		this.menuResouceLike = menuResouceLike;
	}

}