package com.karthik.wext.site;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V14_S1_NetflixSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V14_S1_NetflixSite.class);
	public static final String GENRE_URL = "http://www.instantwatchdb.com/search/main?search=1&searchPage=1&genre=2445&subGenre=0";
	public static final String SITE_URL = "http://www.instantwatchdb.com/search/main?search=1&searchPage=PAGE_ID&genre=GENRE_ID&subGenre=0";
	public static String BASE_URL = GENRE_URL;
	public static String CSS_SELECTOR = ".detail-container";

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub
		int k = 0;
		for (Element genreOption : genres) {

			String genreId = genreOption.attr("value");
			String genreName = genreOption.text();

			// if (k++ >= 2) {
			// / break;
			// }
			if (!genreId.equals("0")) {

				try {
					Document genrePage = WextUtils.downloadPage(SITE_URL.replaceAll("PAGE_ID", "1").replaceAll("GENRE_ID", genreId));
					Element element = genrePage.select("a.other-page").last();
					String hrefToParse = element.attr("href");
					Integer maxPage = Integer.parseInt(hrefToParse.replaceAll(".*searchPage=", "").replaceAll("&genre=.*", ""));
					logger.info("MaxPage={}", maxPage);

					for (int i = 1; i <= maxPage; i++) {
						String genreHref = SITE_URL.replaceAll("PAGE_ID", "" + i).replaceAll("GENRE_ID", genreId);
						logger.info("processing genre {} pageEndpoint {}", genreName, genreHref);
						PageWithMovies pageWithMovies = new PageWithMovies(genreName, siteName, genreHref);
						fullPageWithMoviesList.add(pageWithMovies);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.AvailableOn, COLUMN.ExpiringOn, COLUMN.DaysLeft, COLUMN.Genre }; xlsWriter.writeGenreList(genreMap, columnNames);
		 */

	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub
		genres = baseDocument.select("select#genre option");

	}

}
