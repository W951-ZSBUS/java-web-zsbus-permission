package com.w951.zsbus.permission.dto;

import java.io.Serializable;

public class GroupDTO implements Serializable {
	private static final long serialVersionUID = 1151386294688803120L;
	private String groupId;
	private String groupName;
	private String groupCreatename;
	private String groupCreatedate;
	private Integer groupVerify;
	private String groupBack;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCreatename() {
		return groupCreatename;
	}

	public void setGroupCreatename(String groupCreatename) {
		this.groupCreatename = groupCreatename;
	}

	public String getGroupCreatedate() {
		return groupCreatedate;
	}

	public void setGroupCreatedate(String groupCreatedate) {
		this.groupCreatedate = groupCreatedate;
	}

	public Integer getGroupVerify() {
		return groupVerify;
	}

	public void setGroupVerify(Integer groupVerify) {
		this.groupVerify = groupVerify;
	}

	public String getGroupBack() {
		return groupBack;
	}

	public void setGroupBack(String groupBack) {
		this.groupBack = groupBack;
	}
}
