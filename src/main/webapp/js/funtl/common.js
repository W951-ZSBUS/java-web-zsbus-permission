var Util = {
	// 设置Cookie
	setcookie : function (cookieName, cookieValue, seconds, path, domain, secure) {
		var expires = new Date();
		expires.setTime(expires.getTime() + seconds);
		document.cookie = escape(cookieName) + '=' + escape(cookieValue)
			 + (expires ? '; expires=' + expires.toGMTString() : '')
			 + (path ? '; path=' + path : '/')
			 + (domain ? '; domain=' + domain : '')
			 + (secure ? '; secure' : '');
	},
	
	// 获取Cookie
	getcookie : function (name) {
		var cookie_start = document.cookie.indexOf(name);
		var cookie_end = document.cookie.indexOf(";", cookie_start);
		return cookie_start == -1 ? '' :
		unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
	},
	
	//js获取url参数值, decodeURI(request("Due"));
	request : function (paras) {
		var url = location.href;
		var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
		var paraObj = {};
		for (i = 0; j = paraString[i]; i++) {
			paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
		}
		var returnValue = paraObj[paras.toLowerCase()];
		if (typeof(returnValue) == "undefined") {
			return "";
		} else {
			return returnValue;
		}
	}
};