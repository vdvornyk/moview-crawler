package com.karthik.wext.core;

import org.jsoup.nodes.Element;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;

public abstract class PageParserStrategy {
	protected Element parentElement;
	protected MovieInfo movieInfo = new MovieInfo();
	protected SiteName siteName;

	public void setSiteName(SiteName siteName) {
		this.siteName = siteName;
	}

	public MovieInfo parseMovieInfo(Element parentElement) {
		this.parentElement = parentElement;
		parseMainTitle();
		parseYear();
		parsePrice();
		parseActors();
		parseGenre();
		parseStartDate();
		parseEndDate();
		parseLeft();
		parseEpisode();
		return movieInfo;
	};

	protected abstract void parseMainTitle();

	protected abstract void parseYear();

	protected abstract void parsePrice();

	protected abstract void parseActors();

	protected abstract void parseGenre();

	protected abstract void parseStartDate();

	protected abstract void parseEndDate();

	protected abstract void parseLeft();

	protected abstract void parseEpisode();

}
