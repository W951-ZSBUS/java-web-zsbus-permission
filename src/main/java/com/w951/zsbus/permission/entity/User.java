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
@Table(name = "zsbus_permission_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 8803821759605380509L;
	private String userId;
	private String userNm;
	private String userName;
	private String userPass;
	private Integer userSex;
	private Integer userAge;
	private String userDept;
	private String userZw;
	private String userPhone;
	private String userQq;
	private String userEmail;
	private String userDate;
	private String userCompany;
	private String userCreatename;
	private Integer userVerify;
	private String userBack;
	private List<UserGroup> userGroups = new ArrayList<UserGroup>();

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "user_id", unique = true, nullable = false, length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_nm", length = 20)
	public String getUserNm() {
		return this.userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	@Column(name = "user_name", length = 8)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_pass", length = 32)
	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	@Column(name = "user_sex")
	public Integer getUserSex() {
		return this.userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	@Column(name = "user_age")
	public Integer getUserAge() {
		return this.userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	@Column(name = "user_dept", length = 20)
	public String getUserDept() {
		return this.userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	@Column(name = "user_zw", length = 20)
	public String getUserZw() {
		return this.userZw;
	}

	public void setUserZw(String userZw) {
		this.userZw = userZw;
	}

	@Column(name = "user_phone", length = 20)
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	@Column(name = "user_qq", length = 20)
	public String getUserQq() {
		return this.userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	@Column(name = "user_email", length = 50)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "user_date", length = 19)
	public String getUserDate() {
		return this.userDate;
	}

	public void setUserDate(String userDate) {
		this.userDate = userDate;
	}

	@Column(name = "user_company", length = 50)
	public String getUserCompany() {
		return this.userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	@Column(name = "user_createname", length = 20)
	public String getUserCreatename() {
		return this.userCreatename;
	}

	public void setUserCreatename(String userCreatename) {
		this.userCreatename = userCreatename;
	}

	@Column(name = "user_verify")
	public Integer getUserVerify() {
		return this.userVerify;
	}

	public void setUserVerify(Integer userVerify) {
		this.userVerify = userVerify;
	}

	@Column(name = "user_back", length = 300)
	public String getUserBack() {
		return this.userBack;
	}

	public void setUserBack(String userBack) {
		this.userBack = userBack;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}