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
@Table(name = "zsbus_permission_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource implements java.io.Serializable {
	private static final long serialVersionUID = -6701941706383339136L;
	private String resourceId;
	private String resourceIdentif;
	private String resourceName;
	private String resourceUrl;
	private String resourceCreatename;
	private String resourceDate;
	private Integer resourceVerify;
	private String resourceBack;
	private String resourceSaveUrl;
	private String resourceUpdateUrl;
	private String resourceDeleteUrl;
	private String resourceSelectUrl;
	private String resourceImportUrl;
	private String resourceExportUrl;
	private String resourceLikeUrl;
	private Integer resourceSort;
	private List<MenuResource> menuResources = new ArrayList<MenuResource>();

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "resource_id", unique = true, nullable = false, length = 32)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "resource_identif", length = 20)
	public String getResourceIdentif() {
		return this.resourceIdentif;
	}

	public void setResourceIdentif(String resourceIdentif) {
		this.resourceIdentif = resourceIdentif;
	}

	@Column(name = "resource_name", length = 20)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "resource_url", length = 100)
	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	@Column(name = "resource_createname", length = 20)
	public String getResourceCreatename() {
		return this.resourceCreatename;
	}

	public void setResourceCreatename(String resourceCreatename) {
		this.resourceCreatename = resourceCreatename;
	}

	@Column(name = "resource_date", length = 19)
	public String getResourceDate() {
		return this.resourceDate;
	}

	public void setResourceDate(String resourceDate) {
		this.resourceDate = resourceDate;
	}

	@Column(name = "resource_verify")
	public Integer getResourceVerify() {
		return this.resourceVerify;
	}

	public void setResourceVerify(Integer resourceVerify) {
		this.resourceVerify = resourceVerify;
	}

	@Column(name = "resource_back", length = 300)
	public String getResourceBack() {
		return this.resourceBack;
	}

	public void setResourceBack(String resourceBack) {
		this.resourceBack = resourceBack;
	}

	@Column(name = "resource_save_url", length = 100)
	public String getResourceSaveUrl() {
		return this.resourceSaveUrl;
	}

	public void setResourceSaveUrl(String resourceSaveUrl) {
		this.resourceSaveUrl = resourceSaveUrl;
	}

	@Column(name = "resource_update_url", length = 100)
	public String getResourceUpdateUrl() {
		return this.resourceUpdateUrl;
	}

	public void setResourceUpdateUrl(String resourceUpdateUrl) {
		this.resourceUpdateUrl = resourceUpdateUrl;
	}

	@Column(name = "resource_delete_url", length = 100)
	public String getResourceDeleteUrl() {
		return this.resourceDeleteUrl;
	}

	public void setResourceDeleteUrl(String resourceDeleteUrl) {
		this.resourceDeleteUrl = resourceDeleteUrl;
	}

	@Column(name = "resource_select_url", length = 100)
	public String getResourceSelectUrl() {
		return this.resourceSelectUrl;
	}

	public void setResourceSelectUrl(String resourceSelectUrl) {
		this.resourceSelectUrl = resourceSelectUrl;
	}

	@Column(name = "resource_import_url", length = 100)
	public String getResourceImportUrl() {
		return this.resourceImportUrl;
	}

	public void setResourceImportUrl(String resourceImportUrl) {
		this.resourceImportUrl = resourceImportUrl;
	}

	@Column(name = "resource_export_url", length = 100)
	public String getResourceExportUrl() {
		return this.resourceExportUrl;
	}

	public void setResourceExportUrl(String resourceExportUrl) {
		this.resourceExportUrl = resourceExportUrl;
	}

	@Column(name = "resource_like_url", length = 100)
	public String getResourceLikeUrl() {
		return this.resourceLikeUrl;
	}

	public void setResourceLikeUrl(String resourceLikeUrl) {
		this.resourceLikeUrl = resourceLikeUrl;
	}

	@Column(name = "resource_sort")
	public Integer getResourceSort() {
		return this.resourceSort;
	}

	public void setResourceSort(Integer resourceSort) {
		this.resourceSort = resourceSort;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	public List<MenuResource> getMenuResources() {
		return this.menuResources;
	}

	public void setMenuResources(List<MenuResource> menuResources) {
		this.menuResources = menuResources;
	}

}