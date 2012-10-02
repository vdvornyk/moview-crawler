package com.karthik.wext.site.roger;

import java.io.IOException;

import org.jsoup.nodes.Element;

import com.karthik.wext.pojo.PageWithMovies;

public class V13_RogerS4 extends V13_RogerAbstract {
	public static String BASE_URL = "http://www.rogersondemand.com/tvshows/search?titletype=TVShow&searchtype=Genre";
	private static String PAGE_URL = "http://www.rogersondemand.com/movies/search?genreid=GENRE_ID&searchType=Genre";

	public static String CSS_SELECTOR = ".FindbytitleInfo";

	@Override
	protected void step2_CollectGenre() throws IOException {
		genres = baseDocument.select("#ctl00_ctl00_M_M_genres option");
	}

	@Override
	protected void step3_CollectLinks() throws IOException {
		// TODO Auto-generated method stub
		for (Element element : genres) {
			String genreId = element.val();
			String genreName = element.text();
			String pageUrl = PAGE_URL.replace("GENRE_ID", genreId);
			PageWithMovies page = new PageWithMovies(genreName, siteName, pageUrl);
			fullPageWithMoviesList.add(page);
		}
	}
}
