package com.karthik.wext.core;

import static com.karthik.wext.util.WextUtils.JAR_PATH;
import static com.karthik.wext.util.WextUtils.XLS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import lombok.Getter;
import lombok.Setter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.concurent.PageDownloaderCallable;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public abstract class AbstractSiteStrategy implements SiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(AbstractSiteStrategy.class);
	@Getter
	protected SiteName siteName;
	@Setter
	protected String baseUrl;
	protected Document baseDocument;
	protected Elements genres;
	protected Map<String, String> cookies;

	protected List<PageWithMovies> fullPageWithMoviesList = new ArrayList<PageWithMovies>(5000);
	protected List<MovieInfo> fullMovieInfoList = new ArrayList<MovieInfo>(100000);
	protected Map<String, HashSet<MovieInfo>> genreMap = new TreeMap<String, HashSet<MovieInfo>>();

	protected String outputXlSPath;

	protected WritableWorkbook workbook;
	protected WritableSheet sheet;

	protected WritableFont ARIAL;
	protected WritableFont ARIAL_BOLD;
	protected WritableCellFormat ARIAL_FORMAT;
	protected WritableCellFormat ARIAL_BOLD_FORMAT;

	protected String moviesSelector;
	protected ExecutorService pageDownloaderExecutor = Executors.newFixedThreadPool(SiteConfigs.DOWNLOAD_TP_SIZE);
	protected List<Future<List<MovieInfo>>> futureElementList = new ArrayList<Future<List<MovieInfo>>>();

	public AbstractSiteStrategy() {

		ARIAL = new WritableFont(WritableFont.ARIAL, 10);
		ARIAL_BOLD = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		ARIAL_FORMAT = new WritableCellFormat(ARIAL);
		ARIAL_BOLD_FORMAT = new WritableCellFormat(ARIAL_BOLD);

	}

	public void setSiteName(SiteName siteName) {
		this.siteName = siteName;
		this.baseUrl = SiteConfigs.getSiteBaseUrl(siteName);
		this.moviesSelector = SiteConfigs.getCssConstant(siteName);
		this.outputXlSPath = JAR_PATH + siteName.toString() + XLS;
		try {
			workbook = Workbook.createWorkbook(new File(outputXlSPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sheet = workbook.createSheet("MovieInfo", 0);
	}

	public void executeSite() {
		try {
			step1_DownloadBasePage();
			step2_CollectGenre();
			step3_CollectLinks();
			step4_CollectInfoFromLinks();
			step5_SaveInfoToXls();

			// clearLists();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void step1_DownloadBasePage() throws IOException {
		if (baseUrl != null && baseUrl.length() != 0) {

			LogUtils.logger.info("Try download baseurl={}", baseUrl);
			this.baseDocument = WextUtils.downloadPage(baseUrl);
		}
	}

	private void clearLists() {
		fullMovieInfoList.clear();
		genreMap.clear();
		futureElementList.clear();
	}

	protected void step4_CollectInfoFromLinks() {
		logger.info("parse pages to movie info list with urlSize={}...", fullPageWithMoviesList.size());
		int pageListSize = fullPageWithMoviesList.size();

		int i = 0;
		for (PageWithMovies page : fullPageWithMoviesList) {
			Callable<List<MovieInfo>> worker = new PageDownloaderCallable(moviesSelector, page, cookies);
			Future<List<MovieInfo>> futureList = pageDownloaderExecutor.submit(worker);
			futureElementList.add(futureList);

			if (SiteConfigs.isDev)
				if (i++ > 10)
					break;

		}

		for (Future<List<MovieInfo>> futureElements : futureElementList) {

			try {
				List<MovieInfo> movieElements = futureElements.get();
				futureElements = null;
				if (movieElements.size() != 0) {
					fullMovieInfoList.addAll(movieElements);

					String genre = movieElements.get(0).getGenre();

					if (!genreMap.containsKey(genre)) {
						genreMap.put(genre, new HashSet<MovieInfo>(movieElements));

					} else {
						genreMap.get(genre).addAll(movieElements);
					}
				}

				logger.info("Movie size:{}", fullMovieInfoList.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageDownloaderExecutor.shutdown();
		logger.info("Full movies size={}", fullMovieInfoList.size());
	}

	protected void setAutosizeColumn(int colSize, int colStep) {
		for (int i = 0; i <= colSize; i += colStep) {
			for (int j = 0; j < colSize; j++) {
				CellView cellView = sheet.getColumnView(j);
				cellView.setAutosize(true);
				sheet.setColumnView(j, cellView);
			}
		}
	}

	protected void addCells(Label[] labels) {
		for (int i = 0; i < labels.length; i++) {
			try {
				sheet.addCell(labels[i]);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected abstract void step2_CollectGenre() throws IOException;

	protected abstract void step3_CollectLinks() throws IOException;

	protected abstract void step5_SaveInfoToXls() throws WriteException, IOException;
}
