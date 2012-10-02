package com.karthik.wext.core;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;

public class PageStrategyFactory {
	public static PageParserStrategy getInstanse(SiteName siteName) {

		try {
			PageParserStrategy strategy = (PageParserStrategy) SiteConfigs.getPageParserStrategy(siteName).newInstance();
			strategy.setSiteName(siteName);
			return strategy;

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
