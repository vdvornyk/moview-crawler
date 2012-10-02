package com.karthik.wext.pojo;

import jxl.Cell;
import lombok.Getter;

import com.karthik.wext.configs.PageParserClassCreator;
import com.karthik.wext.configs.PageParserMethodContainer;
import com.karthik.wext.configs.SiteName;
import com.karthik.wext.configs.Vendor;
import com.karthik.wext.xls.XlsConfigReader;

public class SiteBaseData {

	public static int DOWNLOAD_TP_SIZE = 10;
	public static int PARSE_TP_SIZE = 15;
	public static boolean isDev = false;

	@Getter
	private final String siteId;

	@Getter
	private Vendor vendor;
	@Getter
	private SiteName siteName;

	@Getter
	private final String baseUrl;

	@Getter
	private final String urlTemplate;

	@Getter
	private final String cssSelector;

	@Getter
	private final String genderSelector;

	@Getter
	private Class siteStrategyImplementation;

	@Getter
	private Class pageParserImplementation;

	@Getter
	private boolean active;

	public SiteBaseData(Vendor vendor, SiteName siteName, int siteId) {
		this.siteId = String.valueOf(siteId);
		this.vendor = vendor;
		this.siteName = siteName;

		SiteBaseData xlsBaseData = XlsConfigReader.getConfigForSite(this.siteId);
		this.baseUrl = xlsBaseData.getBaseUrl();
		this.urlTemplate = xlsBaseData.getUrlTemplate();
		this.cssSelector = xlsBaseData.getCssSelector();
		this.genderSelector = xlsBaseData.getGenderSelector();
		this.siteStrategyImplementation = xlsBaseData.getSiteStrategyImplementation();
		this.pageParserImplementation = xlsBaseData.getPageParserImplementation();
		this.active = xlsBaseData.isActive();

		String className = "PageParser_" + siteId + "_" + vendor + "_" + siteName;

	}

	public SiteBaseData(Cell[] siteConfigs, PageParserMethodContainer methodContainer) {
		siteId = siteConfigs[0].getContents();
		baseUrl = siteConfigs[3].getContents();
		urlTemplate = siteConfigs[4].getContents();
		cssSelector = siteConfigs[5].getContents();
		genderSelector = siteConfigs[6].getContents();
		String className = "PageParser_" + siteId + "_" + vendor + "_" + siteName;

		try {
			if (siteConfigs[7].getContents().length() > 0) {
				siteStrategyImplementation = Class.forName(siteConfigs[7].getContents());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		active = Integer.parseInt(siteConfigs[8].getContents()) == 1 ? true : false;

		pageParserImplementation = PageParserClassCreator.createClass(methodContainer, className);

	}

	@Override
	public String toString() {
		return "SiteBaseDataNew [siteId=" + siteId + ", vendor=" + vendor + ", siteName=" + siteName + ", baseUrl=" + baseUrl + ", urlTemplate="
				+ urlTemplate + ", cssSelector=" + cssSelector + ", getterSelector=" + genderSelector + ", siteStrategyImplementation="
				+ siteStrategyImplementation + ", pageParserImplementation=" + pageParserImplementation + ", active=" + active + "]";
	}
}
