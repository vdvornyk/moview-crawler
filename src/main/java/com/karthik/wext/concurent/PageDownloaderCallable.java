package com.karthik.wext.concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class PageDownloaderCallable implements Callable<List<MovieInfo>> {

	public static final Logger logger = LoggerFactory.getLogger(PageDownloaderCallable.class);
	private String moviesSelector;
	private PageWithMovies page;
	Map<String, String> cookies;

	public PageDownloaderCallable(String moviesSelector, String pageUrl, SiteName siteName) {

		this.moviesSelector = moviesSelector;
		this.page = new PageWithMovies("", siteName, pageUrl);

	}

	public PageDownloaderCallable(String moviesSelector, PageWithMovies page, Map<String, String> cookies) {

		this.moviesSelector = moviesSelector;
		this.page = page;
		this.cookies = cookies;
	}

	public List<MovieInfo> call() throws Exception {
		List<MovieInfo> fullMovieInfoList = new ArrayList<MovieInfo>(2000);
		List<Future<MovieInfo>> futureMovieInfoList = new ArrayList<Future<MovieInfo>>(2000);
		ExecutorService moviParserExecutor = Executors.newFixedThreadPool(SiteConfigs.PARSE_TP_SIZE);

		Document htmlPage;

		if (page.isPost()) {
			htmlPage = Jsoup.parse(WextUtils.excutePost(page.getUrl(), page.getParams()));
		} else {
			htmlPage = WextUtils.downloadPage(page.getSiteName(), page.getUrl(), cookies);
		}

		Elements elements = htmlPage.select(moviesSelector);
		int i = 0;
		for (Element element : elements) {
			logger.info("try download {} and element {}", page.getUrl(), (i++));
			Callable<MovieInfo> worker = new PageParserCallable(element, page.getSiteName());
			Future<MovieInfo> future = moviParserExecutor.submit(worker);
			futureMovieInfoList.add(future);

		}

		for (Future<MovieInfo> futureElement : futureMovieInfoList) {
			MovieInfo movieInfo;
			try {
				movieInfo = futureElement.get();
				movieInfo.setGenre(page.getGenre());
				movieInfo.setSubGenre(page.getSubGenre());
				fullMovieInfoList.add(movieInfo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		moviParserExecutor.shutdown();
		return fullMovieInfoList;
	}
}