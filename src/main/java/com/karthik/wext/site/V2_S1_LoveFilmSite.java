package com.karthik.wext.site;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.xls.XlsWriterInterface;

public class V2_S1_LoveFilmSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V2_S1_LoveFilmSite.class);

	public static String BASE_URL = "http://www.lovefilm.com/browse/film/watch-online/";
	public static String CSS_SELECTOR = ".fl_detail_info";
	private static String sufix = "/?rows=50";

	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Price };

	@Inject
	@Named("XlsWriter3")
	public XlsWriterInterface xlsWriter;

	protected void step2_CollectGenre() throws IOException {
		genres = baseDocument.select("#genre a");

	}

	@Override
	protected void step3_CollectLinks() throws IOException {

		for (Element elem : genres) {
			String genreUrl = elem.attr("href");
			String genreName = elem.text();

			String itemSize = elem.select(".facet_results").html().replaceAll("[(,)]", "").trim();
			logger.info("itemSize={}", itemSize);
			Integer intItemSize = Integer.parseInt(itemSize);
			int pageSize = intItemSize / 50 + 1;
			logger.info("pageSize={}", pageSize);
			for (int pageIndex = 1; pageIndex <= pageSize; pageIndex++) {

				String pageUrl = getPageUrl(genreUrl, pageIndex);
				// logger.info("pageUrl={}", pageUrl);
				PageWithMovies page = new PageWithMovies(genreName, siteName, pageUrl);
				fullPageWithMoviesList.add(page);
				// logger.info(page.toString());
			}
		}

	}

	@Override
	protected void step5_SaveInfoToXls() {

	}

	private String getPageUrl(String genreUrl, int index) {
		return genreUrl + "p" + index + sufix;
	}
}
