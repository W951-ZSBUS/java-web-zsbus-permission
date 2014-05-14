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
@Table(name = "zsbus_permission_domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Domain implements java.io.Serializable {
	private static final long serialVersionUID = 2905478148644625608L;
	private String domainId;
	private String domainName;
	private String domainUrl;
	private String domainCreatename;
	private String domainCreatedate;
	private List<Resource> resources = new ArrayList<Resource>();

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "domain_id", unique = true, nullable = false, length = 32)
	public String getDomainId() {
		return this.domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	@Column(name = "domain_name", length = 20)
	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@Column(name = "domain_url", length = 200)
	public String getDomainUrl() {
		return this.domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	@Column(name = "domain_createname", length = 20)
	public String getDomainCreatename() {
		return this.domainCreatename;
	}

	public void setDomainCreatename(String domainCreatename) {
		this.domainCreatename = domainCreatename;
	}

	@Column(name = "domain_createdate", length = 19)
	public String getDomainCreatedate() {
		return this.domainCreatedate;
	}

	public void setDomainCreatedate(String domainCreatedate) {
		this.domainCreatedate = domainCreatedate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
	public List<Resource> getResources() {
		return this.resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

}