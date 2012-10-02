package com.karthik.wext.concurent;

import java.util.concurrent.Callable;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.core.PageParserStrategy;
import com.karthik.wext.core.PageStrategyFactory;
import com.karthik.wext.pojo.MovieInfo;

public class PageParserCallable implements Callable<MovieInfo> {
	public static final Logger logger = LoggerFactory.getLogger(PageParserCallable.class);
	private Element element;
	private SiteName siteName;

	public PageParserCallable(Element element, SiteName siteName) {
		this.element = element;
		this.siteName = siteName;
	}

	public MovieInfo call() throws Exception {

		PageParserStrategy pageParserStrategy = PageStrategyFactory.getInstanse(siteName);
		MovieInfo info = pageParserStrategy.parseMovieInfo(element);
		return info;
	}

}