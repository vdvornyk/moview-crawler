package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class V12_P1_ShawCablePageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("h2");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {

		org.jsoup.select.Elements node = parentElement.select("span.year");
		movieInfo.setYear(node.html().replaceAll(".*\\(", "").replaceAll("\\)", "").trim());

	}

	@Override
	protected void parsePrice() {

		org.jsoup.select.Elements node = parentElement.select(".price");
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
