package com.karthik.wext.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.PageWithMovies;

public class V8_S1_UPCPolskaSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V8_S1_UPCPolskaSite.class);
	public static String BASE_URL = "http://aplikacje.upc.pl/vod/index.php";
	public static String CSS_SELECTOR = ".item_short";

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub
		logger.info("baseUrl={}", baseUrl);
		PageWithMovies page = new PageWithMovies("general", siteName, baseUrl);
		fullPageWithMoviesList.add(page);
	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Genre, COLUMN.Price }; xlsWriter.writeSimpleList(fullMovieInfoList, columnNames);
		 */

	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub

	}

}
