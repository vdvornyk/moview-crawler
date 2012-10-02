package com.karthik.wext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.AllArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;

public class CollectPageTest {
	private static final int NTHREDS = 10;
	public ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
	public ExecutorService pageParserExecutor = Executors.newFixedThreadPool(15);

	// @Test
	public void futureTest() throws IOException {
		StopWatch stopWatch = new LoggingStopWatch("codeBlock1");
		List<Future<PageWithMovies>> list = new ArrayList<Future<PageWithMovies>>();

		Document document = Jsoup.connect(SiteConfigs.getSiteBaseUrl(SiteName.S3_LoveFilm)).get();
		Elements links = document.select("#genre a");

		List<Future<List<PageWithMovies>>> futureFinalList = new ArrayList<Future<List<PageWithMovies>>>();
		List<Future<List<MovieInfo>>> futurePageParserList = new ArrayList<Future<List<MovieInfo>>>();
		List<PageWithMovies> pageFinalList = new ArrayList<PageWithMovies>();
		List<MovieInfo> movieInfoFinalList = new ArrayList<MovieInfo>(50000);

		for (Element elem : links) {
			Callable<List<PageWithMovies>> worker = new PageCallable(elem);
			Future<List<PageWithMovies>> futureList = executor.submit(worker);
			futureFinalList.add(futureList);
		}

		System.out.println("List size=" + futureFinalList.size());

		// Now retrieve the resultss
		for (Future<List<PageWithMovies>> future : futureFinalList) {
			try {
				List<PageWithMovies> partFinalList = future.get();

				for (PageWithMovies page : partFinalList) {
					Callable<List<MovieInfo>> workerPageParser = new PageParserCallable(page);
					Future<List<MovieInfo>> futureList = pageParserExecutor.submit(workerPageParser);
					futurePageParserList.add(futureList);
				}

				pageFinalList.addAll(partFinalList);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		for (Future<List<MovieInfo>> futureMovie : futurePageParserList) {
			try {
				List<MovieInfo> partFinalList = futureMovie.get();
				movieInfoFinalList.addAll(partFinalList);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Final List=" + pageFinalList.size());
		System.out.println("Final movie List=" + movieInfoFinalList.size());

		executor.shutdown();
		pageParserExecutor.shutdown();
		stopWatch.stop();

		FileUtils.writeLines(new File("/home/eacc/lovefilms.txt"), movieInfoFinalList);
	}

	@AllArgsConstructor
	class PageCallable implements Callable<List<PageWithMovies>> {
		public final Logger logger = LoggerFactory.getLogger(PageCallable.class);
		private final String sufix = "/?rows=50";
		private Element elem;

		public List<PageWithMovies> call() throws Exception {
			final List<PageWithMovies> bufPageList = new ArrayList<PageWithMovies>();
			String genreUrl = elem.attr("href");
			String genreName = elem.text();

			String itemSize = elem.select(".facet_results").html().replaceAll("[(,)]", "").trim();
			logger.info("itemSize={}", itemSize);
			Integer intItemSize = Integer.parseInt(itemSize);
			int pageSize = intItemSize / 50 + 1;
			logger.info("pageSize={}", pageSize);
			for (int pageIndex = 1; pageIndex <= pageSize; pageIndex++) {

				String pageUrl = getPageUrl(genreUrl, pageIndex);
				// logger.info("pageUrl={}", pageUrl);
				PageWithMovies page = new PageWithMovies(genreName, SiteName.S3_LoveFilm, pageUrl);
				bufPageList.add(page);
				// logger.info(page.toString());
			}

			return bufPageList;
		}

		private String getPageUrl(String genreUrl, int index) {
			return genreUrl + "p" + index + sufix;
		}

	}

	@AllArgsConstructor
	class PageParserCallable implements Callable<List<MovieInfo>> {
		public final Logger logger = LoggerFactory.getLogger(PageParserCallable.class);

		private PageWithMovies page;

		public List<MovieInfo> call() throws Exception {
			//
			// List<MovieInfo> partOfMovieList=new ArrayList<MovieInfo>();
			// // MoviesFromPageExtractor pageParser = new MoviesFromPageExtractor(SiteName.LoveFilm);
			// try {
			// logger.info("try parse {}",page.getUrl());
			// List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(page.getUrl());
			//
			// if(movieInfoList!=null&&movieInfoList.size()>0){
			// partOfMovieList.addAll(movieInfoList);
			// }
			//
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (NodeSelectorException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// return partOfMovieList;
			return null;
		}

	}
}
