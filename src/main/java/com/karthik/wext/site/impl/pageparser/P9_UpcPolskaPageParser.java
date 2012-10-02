package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class P9_UpcPolskaPageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select(".title a");// WextUtils.querySelector(parentElement, ".Label");
		movieInfo.setMainTitle(node.html());
	}

	@Override
	protected void parseYear() {

		Elements node = parentElement.select("td:eq(2)");
		movieInfo.setYear(node.html());

	}

	@Override
	protected void parsePrice() {
		Elements node = parentElement.select("td:eq(5)");
		movieInfo.setPrice(node.html().replaceAll("&nbsp;", " "));
	}

	@Override
	protected void parseActors() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseGenre() {
		Elements node = parentElement.select("td:eq(3)");
		movieInfo.setGenre(node.html());

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
