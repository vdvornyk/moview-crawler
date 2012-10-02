package com.karthik.wext.site;

import java.io.IOException;

import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V10_S1_LatelecomSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V10_S1_LatelecomSite.class);
	public static final String GENRE_URL = "http://tv.lattelecom.lv/visi-zanri/?sort=title";
	public static final String SITE_URL = "http://tv.lattelecom.lv/visi-zanri/?page=PAGE_ID&sort=title";
	private int pageCount;
	public static String BASE_URL = GENRE_URL;
	public static String CSS_SELECTOR = "#movie-list .movie";
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year };

	@Inject
	@Named("SimpleList")
	private XlsWriterInterface xlsWriter;

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub

		for (int i = 1; i <= pageCount; i++) {
			PageWithMovies pageWithMovies = new PageWithMovies("", siteName, SITE_URL.replaceAll("PAGE_ID", "" + i));
			fullPageWithMoviesList.add(pageWithMovies);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		xlsWriter.writeXls(new XlsWriterData(siteName).with(fullMovieInfoList).with(columnNames));
	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub
		pageCount = baseDocument.select(".pager a").size() / 2;
	}

}
