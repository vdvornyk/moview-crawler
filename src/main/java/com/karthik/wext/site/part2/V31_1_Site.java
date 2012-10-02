package com.karthik.wext.site.part2;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jxl.write.WriteException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V31_1_Site extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V31_1_Site.class);
	public static final String SITE_URL = "http://www.mts.ca";
	public static final String SUB_CATEGORY_URL = "http://catalog.cubovision.it/video/search?field_categorie_tid=CATEGORY_ID&field_categorie_tid_1=SUB_CAT_ID";
	public static final String SUBCAT_POST_URL = "http://catalog.cubovision.it/ajax_get_sottocategoria";

	public static String[] categories = { "cinema", "documentari", "kids", "hobby & lifestyle", "serie tv" };
	public Set<String> categorySet = new LinkedHashSet<String>(Arrays.asList(categories));

	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price };

	@Inject
	@Named("XlsWriter6")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

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
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
