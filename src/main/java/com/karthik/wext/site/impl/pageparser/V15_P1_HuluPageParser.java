package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.PageParserStrategy;

public class V15_P1_HuluPageParser extends PageParserStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V15_P1_HuluPageParser.class);

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select(".info > div > div > a");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {
		Elements node = parentElement.select(".info > div > font");
		movieInfo.setYear(node.text());
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
		Elements node = parentElement.select(".info > div> div").eq(1).select("a");
		movieInfo.setGenreHard(node.text());

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
