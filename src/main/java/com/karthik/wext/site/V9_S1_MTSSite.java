package com.karthik.wext.site;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.PageWithMovies;

public class V9_S1_MTSSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V9_S1_MTSSite.class);
	public static final String GENRE_URL = "http://www.mts.ca/portal/site/mts/menuitem.a57d62efc294d0f18e084c10408021a0/?vgnextoid=5911b48e27f15210VgnVCM1000002a040f0aRCRD";
	public static final String SITE_URL = "http://www.mts.ca";
	public static String BASE_URL = GENRE_URL;
	public static String CSS_SELECTOR = ".movies .clearfix";

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub
		boolean isContinue = true;
		for (Element genrePageLink : genres) {
			String genreName = genrePageLink.text();
			String genreHref = SITE_URL + genrePageLink.attr("href");
			if (isContinue) {
				if (genreName.equals("Same Day as DVD")) {
					isContinue = false;
				} else {
					continue;
				}
			}

			PageWithMovies pageWithMovies = new PageWithMovies(genreName, siteName, genreHref);
			fullPageWithMoviesList.add(pageWithMovies);
		}

	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle }; xlsWriter.writeGenreListGenreAlon(genreMap, columnNames);
		 * 
		 * try {
		 * 
		 * final int COL_N = 1; Label labels[] = new Label[COL_N];
		 * 
		 * int j = 0; for (String genre : genreMap.keySet()) {
		 * 
		 * int i = 0;
		 * 
		 * labels[0] = new Label(j, i, genre, ARIAL_BOLD_FORMAT);
		 * 
		 * addCells(labels); ++i; Set<MovieInfo> movieInfoSet = genreMap.get(genre); for (MovieInfo movieInfo : movieInfoSet) {
		 * 
		 * labels[0] = new Label(j, i, movieInfo.getMainTitle());
		 * 
		 * addCells(labels); ++i; }
		 * 
		 * j += COL_N; }
		 * 
		 * setAutosizeColumn(j, COL_N); workbook.write(); workbook.close();
		 * 
		 * } catch (WriteException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub
		genres = baseDocument.select("#vod_navigation a");
	}

}
