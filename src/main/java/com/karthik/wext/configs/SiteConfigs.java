package com.karthik.wext.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.karthik.wext.pojo.SiteBaseData;
import com.karthik.wext.xls.writer.InjectorModule;

public class SiteConfigs {
	public static int DOWNLOAD_TP_SIZE = 10;
	public static int PARSE_TP_SIZE = 15;
	public static boolean isDev = false;
	public static Injector INJECTOR = Guice.createInjector(new InjectorModule());

	public static final List<SiteBaseData> baseData = new ArrayList<SiteBaseData>();

	private static HashMap<SiteName, SiteBaseData> sites = new HashMap<SiteName, SiteBaseData>();
	public static final Set<Vendor> ACTIVE_VENDORS = new LinkedHashSet<Vendor>();
	public static final Set<SiteName> ACTIVE_SITES = new HashSet<SiteName>();

	static {
		int id = 1;
		for (Vendor vendor : VendorMapping.VENDOR_SITE.keySet()) {
			Set<SiteName> siteNameSet = VendorMapping.VENDOR_SITE.get(vendor);
			for (SiteName siteName : siteNameSet) {
				SiteBaseData sbd = new SiteBaseData(vendor, siteName, id);
				if (sbd.isActive()) {
					ACTIVE_VENDORS.add(vendor);
					ACTIVE_SITES.add(siteName);
					baseData.add(sbd);
					sites.put(sbd.getSiteName(), sbd);
				}
				++id;
			}
		}
	}

	public static String getCssConstant(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}
		return sites.get(siteName).getCssSelector();
	}

	public static String getGenderSelector(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}
		return sites.get(siteName).getGenderSelector();
	}

	public static String getUrlTemplate(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}
		return sites.get(siteName).getUrlTemplate();
	}

	public static String getSiteBaseUrl(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}
		return sites.get(siteName).getBaseUrl();
	}

	public static Class getSiteStrategy(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}

		return sites.get(siteName).getSiteStrategyImplementation();
	}

	public static Class getPageParserStrategy(SiteName siteName) {
		if (!sites.containsKey(siteName)) {
			return null;
		}
		return sites.get(siteName).getPageParserImplementation();
	}

}
