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
@Table(name = "zsbus_permission_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group implements java.io.Serializable {
	private static final long serialVersionUID = -7539733621266319685L;
	private String groupId;
	private String groupName;
	private String groupCreatename;
	private String groupCreatedate;
	private Integer groupVerify;
	private String groupBack;
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();
	private List<GroupMenu> groupMenus = new ArrayList<GroupMenu>();

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "group_id", unique = true, nullable = false, length = 32)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "group_name", length = 20)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "group_createname", length = 20)
	public String getGroupCreatename() {
		return this.groupCreatename;
	}

	public void setGroupCreatename(String groupCreatename) {
		this.groupCreatename = groupCreatename;
	}

	@Column(name = "group_createdate", length = 19)
	public String getGroupCreatedate() {
		return this.groupCreatedate;
	}

	public void setGroupCreatedate(String groupCreatedate) {
		this.groupCreatedate = groupCreatedate;
	}

	@Column(name = "group_verify")
	public Integer getGroupVerify() {
		return this.groupVerify;
	}

	public void setGroupVerify(Integer groupVerify) {
		this.groupVerify = groupVerify;
	}

	@Column(name = "group_back", length = 300)
	public String getGroupBack() {
		return this.groupBack;
	}

	public void setGroupBack(String groupBack) {
		this.groupBack = groupBack;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "group")
	public List<GroupMenu> getGroupMenus() {
		return this.groupMenus;
	}

	public void setGroupMenus(List<GroupMenu> groupMenus) {
		this.groupMenus = groupMenus;
	}

}