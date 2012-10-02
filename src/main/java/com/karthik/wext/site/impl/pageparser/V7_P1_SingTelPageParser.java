package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.PageParserStrategy;

public class V7_P1_SingTelPageParser extends PageParserStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V7_P1_SingTelPageParser.class);

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("h6");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {

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
