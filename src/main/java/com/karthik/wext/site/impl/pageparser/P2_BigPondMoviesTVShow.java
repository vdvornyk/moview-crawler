package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class P2_BigPondMoviesTVShow extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		org.jsoup.select.Elements node = parentElement.select(".Label");
		movieInfo.setMainTitle(node.html().replaceAll("\\(.*", "").replaceAll("&amp;", "&").trim());
	}

	@Override
	protected void parseYear() {

		Elements node = parentElement.select(".Label");
		movieInfo.setYear(node.html().replaceAll(".*\\(", "").replaceAll("\\)", "").trim());

	}

	@Override
	protected void parsePrice() {

		Elements node = parentElement.select(".Amount");
		movieInfo.setPrice(node.html().trim());

	}

	@Override
	protected void parseActors() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseGenre() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseStartDate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseEndDate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseEpisode() {
		// TODO Auto-generated method stub

	}
}
