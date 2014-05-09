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
@Table(name = "zsbus_permission_user_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserGroup implements java.io.Serializable {
	private static final long serialVersionUID = 8170404825823710809L;
	private String userGroupId;
	private User user;
	private Group group;
	private String userGroupName;
	private String userGroupDate;
	private String userGroupBack;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_group_id", unique = true, nullable = false, length = 32)
	public String getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "user_group_name", length = 20)
	public String getUserGroupName() {
		return this.userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Column(name = "user_group_date", length = 19)
	public String getUserGroupDate() {
		return this.userGroupDate;
	}

	public void setUserGroupDate(String userGroupDate) {
		this.userGroupDate = userGroupDate;
	}

	@Column(name = "user_group_back", length = 300)
	public String getUserGroupBack() {
		return this.userGroupBack;
	}

	public void setUserGroupBack(String userGroupBack) {
		this.userGroupBack = userGroupBack;
	}

}