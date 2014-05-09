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
@Table(name = "zsbus_permission_group_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupMenu implements java.io.Serializable {
	private static final long serialVersionUID = 818406979610572144L;
	private String groupMenuId;
	private Menu menu;
	private Group group;
	private String groupMenuName;
	private String groupMenuDate;
	private String groupMenuBack;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "group_menu_id", unique = true, nullable = false, length = 32)
	public String getGroupMenuId() {
		return this.groupMenuId;
	}

	public void setGroupMenuId(String groupMenuId) {
		this.groupMenuId = groupMenuId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_id")
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "group_menu_name", length = 20)
	public String getGroupMenuName() {
		return this.groupMenuName;
	}

	public void setGroupMenuName(String groupMenuName) {
		this.groupMenuName = groupMenuName;
	}

	@Column(name = "group_menu_date", length = 19)
	public String getGroupMenuDate() {
		return this.groupMenuDate;
	}

	public void setGroupMenuDate(String groupMenuDate) {
		this.groupMenuDate = groupMenuDate;
	}

	@Column(name = "group_menu_back", length = 300)
	public String getGroupMenuBack() {
		return this.groupMenuBack;
	}

	public void setGroupMenuBack(String groupMenuBack) {
		this.groupMenuBack = groupMenuBack;
	}

}