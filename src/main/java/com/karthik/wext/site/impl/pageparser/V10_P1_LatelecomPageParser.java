package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.PageParserStrategy;

public class V10_P1_LatelecomPageParser extends PageParserStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V10_P1_LatelecomPageParser.class);

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("h2 a");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {
		Elements node = parentElement.select("p a");// WextUtils.querySelector(parentElement, ".Label");
		try {
			int year = Integer.parseInt(node.eq(0).text());
			movieInfo.setYear("" + year);
		} catch (NumberFormatException ex) {
			logger.info("Error in year parsing");
		}
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
