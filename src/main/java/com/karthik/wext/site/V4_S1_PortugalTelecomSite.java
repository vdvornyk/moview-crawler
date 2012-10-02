package com.karthik.wext.site;

import java.io.IOException;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V4_S1_PortugalTelecomSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V4_S1_PortugalTelecomSite.class);

	public static final String SITE_URL = "http://online.meo.pt";
	public static final String URL_PART1 = "?pagina=";
	public static final String URL_PART2 = "&AluguerNoPC=False";

	public static String BASE_URL = "http://online.meo.pt/Videoclube/Catalogo";
	public static String CSS_SELECTOR = ".movie-horizontal";

	@Override
	protected void step3_CollectLinks() {

		// TODO Auto-generated method stub
		logger.info("baseUrl={}", baseUrl);

		for (Element genrePageLink : genres) {
			logger.info("baseUrl={} Collected {} endpoint links", SITE_URL, fullPageWithMoviesList.size());
			String genreName = genrePageLink.text();
			String genreHref = SITE_URL + genrePageLink.attr("href");
			Document genrePage;
			try {
				genrePage = WextUtils.downloadPage(genreHref);
				Elements subGenre = genrePage.select("#leftmenu > ul > li > .lateralmenu_txt");
				if (subGenre.size() != 0) {
					Document subGenrePage;

					for (Element subGenreLink : subGenre) {
						String subgenreHref = SITE_URL + subGenreLink.attr("href");
						String subGenreName = subGenreLink.text();
						subGenrePage = WextUtils.downloadPage(subgenreHref);
						addPageWithMovies(genreName, subGenrePage, subGenreName, subgenreHref);
					}

				} else {
					addPageWithMovies(genreName, genrePage, null, genreHref);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addPageWithMovies(String genreName, Document document, String subGenre, String href) {
		Elements isPagination = document.select("#pagination-group .pagination-no-pagination");
		Elements pageNums = document.select("#pagination-group [filter_pc_only]");
		int pageCount;
		if (isPagination.size() != 0) {
			pageCount = Integer.parseInt(pageNums.get(pageNums.size() - 2).text());
		} else if (pageNums.size() != 0) {
			pageCount = Integer.parseInt(pageNums.last().text());
		} else {
			pageCount = 1;
		}

		for (int i = 1; i <= pageCount; i++) {
			StringBuilder endpointUrl = new StringBuilder(href).append(URL_PART1).append(i).append(URL_PART2);
			String endpointDone = endpointUrl.toString();
			logger.info("processing pageEndpoint={}; genre={}; subgenre={};", new String[] { endpointDone, genreName, subGenre });
			PageWithMovies page = new PageWithMovies(genreName, subGenre, siteName, endpointDone);
			fullPageWithMoviesList.add(page);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * logger.info("write info to file...{}", outputXlSPath);
		 * 
		 * try { final int colN = 1; Label labels[] = new Label[colN];
		 * 
		 * int j = 0;
		 * 
		 * for (String genre : genreMap.keySet()) {
		 * 
		 * int i = 0;
		 * 
		 * sheet.addCell(new Label(j, i, genre, ARIAL_BOLD_FORMAT));
		 * 
		 * ++i;
		 * 
		 * Set<MovieInfo> movieInfoSet = genreMap.get(genre); Map<String, Set<MovieInfo>> subGenreMap = getSubGenreMap(movieInfoSet);
		 * 
		 * if (subGenreMap.size() != 0) { for (String subGenre : subGenreMap.keySet()) { Set<MovieInfo> subGenreMovieSet = subGenreMap.get(subGenre); sheet.addCell(new Label(j, i, subGenre, ARIAL_BOLD_FORMAT)); ++i; i =
		 * addCellFromMovieInfo(subGenreMovieSet, i, j);
		 * 
		 * } } else { i = addCellFromMovieInfo(movieInfoSet, i, j); } j += colN;
		 * 
		 * } setAutosizeColumn(j, colN);
		 * 
		 * workbook.write(); workbook.close();
		 * 
		 * } catch (WriteException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select("#submenu > ul > li > div >a");
	}

	private String getPageUrl(String genreUrl, int index) {
		return genreUrl;
	}

	protected int addCellFromMovieInfo(Set<MovieInfo> movieInfoSet, int i, int j) {
		/*
		 * Label labels[] = new Label[1]; for (MovieInfo movieInfo : movieInfoSet) { labels[0] = new Label(j, i, movieInfo.getMainTitle(), ARIAL_FORMAT); addCells(labels); ++i; }
		 */
		return i;
	}
}
