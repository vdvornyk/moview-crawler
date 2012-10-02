package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class P5_PortugalTelecomPageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("h2[class=movie-title]");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parsePrice() {
		// TODO Auto-generated method stub

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
