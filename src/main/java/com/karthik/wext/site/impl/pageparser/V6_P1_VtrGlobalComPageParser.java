package com.karthik.wext.site.impl.pageparser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.karthik.wext.core.PageParserStrategy;

public class V6_P1_VtrGlobalComPageParser extends PageParserStrategy {

	@Override
	protected void parseMainTitle() {
		Elements node = parentElement.select("h3");
		movieInfo.setMainTitle(node.html());
	}

	@Override
	protected void parseYear() {

		Element node = parentElement.select("p").first();
		String year = node.html();
		String pattern = "ntilde";
		if (year.contains(pattern)) {
			movieInfo.setYear(year.replaceAll(".*>", "").trim());
		}
	}

	@Override
	protected void parsePrice() {

	}

	@Override
	protected void parseActors() {
		Elements node = parentElement.select("p").eq(3);
		String actores = node.html();
		String pattern = "Actores";
		if (actores.contains(pattern)) {
			movieInfo.setActors(actores.replaceAll(".*>", "").trim());
		}

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
