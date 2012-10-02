package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class P8_SingleTelPageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("a[class=cover content-detail] h3");// WextUtils.querySelector(parentElement, ".Label");
		movieInfo.setMainTitle(node.html());
	}

	@Override
	protected void parseYear() {

		Elements node = parentElement.select(".overlay > p");
		movieInfo.setYear(node.html().split(",")[1].trim());

	}

	@Override
	protected void parsePrice() {

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
