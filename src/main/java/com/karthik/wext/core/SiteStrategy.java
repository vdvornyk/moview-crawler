package com.karthik.wext.core;

import com.karthik.wext.configs.SiteName;

public interface SiteStrategy {
	public void executeSite();

	public SiteName getSiteName();
}
