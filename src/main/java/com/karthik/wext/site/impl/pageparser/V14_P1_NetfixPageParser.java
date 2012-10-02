package com.karthik.wext.site.impl.pageparser;

import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class V14_P1_NetfixPageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("span[class=detail-title] a");
		movieInfo.setMainTitle(node.text());
	}

	@Override
	protected void parseYear() {
		Elements node = parentElement.select("span[class=detail-item-description] a");
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

	}

	@Override
	protected void parseStartDate() {
		Elements node = parentElement.select("div[class=detail-container-inner]> div:eq(2)> dl > dd").eq(2).select("strong:eq(0)");
		movieInfo.setAvailableOn(node.text());

	}

	@Override
	protected void parseEndDate() {
		// TODO Auto-generated method stub

		Elements node = parentElement.select("div[class=detail-container-inner]> div:eq(2)> dl > dd").eq(2).select("strong:eq(1)");
		movieInfo.setExpiringOn(node.text());
	}

	@Override
	protected void parseLeft() {
		// TODO Auto-generated method stub
		Elements node = parentElement.select("div[class=detail-container-inner] > div:eq(2)  dl > dd ").eq(2).select("span");
		movieInfo.setDaysLeft(node.text());
	}

	@Override
	protected void parseEpisode() {
		// TODO Auto-generated method stub

	}
}
