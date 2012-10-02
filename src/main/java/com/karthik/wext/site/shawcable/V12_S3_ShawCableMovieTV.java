package com.karthik.wext.site.shawcable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V12_S3_ShawCableMovieTV extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V12_S3_ShawCableMovieTV.class);
	public static final String GENRE_URL = "http://catalog.cubovision.it/video/search";
	public static final String SITE_URL = "http://www.mts.ca";
	public static final String SUB_CATEGORY_URL = "http://catalog.cubovision.it/video/search?field_categorie_tid=CATEGORY_ID&field_categorie_tid_1=SUB_CAT_ID";
	public static final String SUBCAT_POST_URL = "http://catalog.cubovision.it/ajax_get_sottocategoria";

	public static String[] categories = { "cinema", "documentari", "kids", "hobby & lifestyle", "serie tv" };
	public Set<String> categorySet = new LinkedHashSet<String>(Arrays.asList(categories));

	@Override
	protected void step3_CollectLinks() {
		Gson gson = new Gson();
		PageWithMovies pageWithMovies;
		Map<String, String> categories;
		Type collectionType = new TypeToken<Map<String, String>>() {
		}.getType();
		for (Element genre : genres) {
			String genreName = genre.text().toLowerCase();
			if (categorySet.contains(genreName)) {
				String genreId = genre.val();
				logger.info("category {} id {}", genreName, genreId);
				String subCatJson = WextUtils.excutePost(SUBCAT_POST_URL, "categoria=" + genreId);

				categories = gson.fromJson(subCatJson, collectionType);
				logger.info("category {} subCat={}", genreName, categories);

				for (String subId : categories.keySet()) {
					try {
						String subGenre = categories.get(subId);
						String countURL = SUB_CATEGORY_URL.replaceAll("CATEGORY_ID", genreId).replaceAll("SUB_CAT_ID", subId);
						Document pageForPageCount = WextUtils.downloadPage(countURL);
						Elements pages = pageForPageCount.select(".item-list>ul>li");
						if (pages.size() != 0) {
							Element page = pages.get(pages.size() - 2);
							int pCount = Integer.parseInt(page.text());
							for (int i = 0; i < pCount; i++) {

								pageWithMovies = new PageWithMovies(genreName, subGenre, siteName, countURL + "&page=" + i);
								fullPageWithMoviesList.add(pageWithMovies);
							}
						} else {
							pageWithMovies = new PageWithMovies(genreName, subGenre, siteName, countURL);
							fullPageWithMoviesList.add(pageWithMovies);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * logger.info("write info to file...{}", outputXlSPath);
		 * 
		 * try { final int colN = 2;
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
		 * if (subGenreMap.size() != 0) { for (String subGenre : subGenreMap.keySet()) { Set<MovieInfo> subGenreMovieSet = subGenreMap.get(subGenre);
		 * 
		 * sheet.addCell(new Label(j, i, subGenre, ARIAL_BOLD_FORMAT)); sheet.addCell(new Label(j, i + 1, COLUMN.Price.toString(), ARIAL_BOLD_FORMAT)); ++i; i = addCellFromMovieInfo(subGenreMovieSet, i, j);
		 * 
		 * } } else { i = addCellFromMovieInfo(movieInfoSet, i, j); } j += colN;
		 * 
		 * } setAutosizeColumn(j, colN); workbook.write(); workbook.close();
		 * 
		 * } catch (WriteException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select("#edit-field-categorie-tid option");
	}

	protected int addCellFromMovieInfo(Set<MovieInfo> movieInfoSet, int i, int j) {
		/*
		 * Label labels[] = new Label[2]; for (MovieInfo movieInfo : movieInfoSet) { labels[0] = new Label(j, i, movieInfo.getMainTitle(), ARIAL_FORMAT); labels[1] = new Label(j + 1, i, movieInfo.getPrice(), ARIAL_FORMAT); addCells(labels); ++i; }
		 * return i;
		 */
		return 0;
	}
}
