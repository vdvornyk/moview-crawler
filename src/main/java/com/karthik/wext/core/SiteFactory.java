package com.karthik.wext.core;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;

public class SiteFactory {
	public static AbstractSiteStrategy getInstance(SiteName siteName) {
		Class clazz = SiteConfigs.getSiteStrategy(siteName);
		if (clazz != null) {
			AbstractSiteStrategy siteStrategy = (AbstractSiteStrategy) SiteConfigs.INJECTOR.getInstance(clazz);
			siteStrategy.setSiteName(siteName);
			return siteStrategy;
		}
		return null;
	}
}
