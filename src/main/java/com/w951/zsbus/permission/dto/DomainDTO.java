package com.w951.zsbus.permission.dto;

import java.io.Serializable;

/**
 * 
 * 系统版本：v1.0<br>
 * 开发人员：Lusifer<br>
 * 日期：2014-05-13<br>
 * 时间：16:18:02<br>
 * 功能描述：写明作用，调用方式，使用场景，以及特殊情况<br>
 *
 */
public class DomainDTO implements Serializable {
	private static final long serialVersionUID = -1L;

    private String domainId;
    private String domainName;
    private String domainUrl;
    private String domainCreatename;
    private String domainCreatedate;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
    
    public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }
    
    public String getDomainCreatename() {
        return domainCreatename;
    }

    public void setDomainCreatename(String domainCreatename) {
        this.domainCreatename = domainCreatename;
    }
    
    public String getDomainCreatedate() {
        return domainCreatedate;
    }

    public void setDomainCreatedate(String domainCreatedate) {
        this.domainCreatedate = domainCreatedate;
    }
    

}