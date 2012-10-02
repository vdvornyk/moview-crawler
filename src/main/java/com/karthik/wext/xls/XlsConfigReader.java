package com.karthik.wext.xls;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.karthik.wext.configs.PageParserMethodContainer;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.SiteBaseData;

public class XlsConfigReader {
	private final static String SITE_CONFIG_SHEET = "Sheet1";
	private final static String PAGE_PARSER_SHEET = "Sheet2";
	private final static String CONFIG_TABLE_NAME = "ConfigTable.xls";

	private static Workbook workbook;
	private static Sheet sheet1;
	private static Sheet sheet2;

	private static Map<String, SiteBaseData> idToSiteConfigs;

	static {
		try {
			idToSiteConfigs = new HashMap<String, SiteBaseData>();
			workbook = Workbook.getWorkbook(XlsConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_TABLE_NAME));

			sheet1 = workbook.getSheet(SITE_CONFIG_SHEET);
			sheet2 = workbook.getSheet(PAGE_PARSER_SHEET);
			Cell[] methodsName = sheet2.getRow(0);
			LogUtils.logger.info("size={}", sheet1.getRows());
			for (int j = 1; j < sheet1.getRows(); j++) {

				Cell[] siteConfig = sheet1.getRow(j);
				Cell[] methodsBody = sheet2.getRow(j);
				// LogUtils.logger.info("data={}", siteConfig[0].getContents());
				idToSiteConfigs.put(siteConfig[0].getContents(),
						new SiteBaseData(siteConfig, new PageParserMethodContainer(methodsName, methodsBody)));
			}

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workbook.close();
		}
	}

	public static SiteBaseData getConfigForSite(String siteId) {
		return idToSiteConfigs.get(siteId);
	}
}
