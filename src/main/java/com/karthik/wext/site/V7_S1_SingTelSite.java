package com.karthik.wext.site;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.PageWithMovies;

public class V7_S1_SingTelSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V7_S1_SingTelSite.class);
	public static final String GENRE_URL = "http://mio.singtel.com/miotv/channels-on-demand_on-demand_details.asp";
	public static final String SITE_URL = "http://mio.singtel.com/miotv/";
	public static String BASE_URL = GENRE_URL;
	public static String CSS_SELECTOR = ".list-poster";

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub
		boolean isContinue = true;
		for (Element genrePageLink : genres) {
			String genreName = genrePageLink.text();
			String genreHref = SITE_URL + genrePageLink.attr("href");

			PageWithMovies pageWithMovies = new PageWithMovies(genreName, siteName, genreHref);
			fullPageWithMoviesList.add(pageWithMovies);
		}

	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle }; xlsWriter.writeGenreListGenreAlon(genreMap, columnNames);
		 */

	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub
		genres = baseDocument.select(".parent > ul > li > a");
	}

}
