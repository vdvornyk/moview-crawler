package com.karthik.wext.site.shawcable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class V12_S2_ShawCableMovieSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V12_S1_ShawCableSubscSite.class);
	public static final String SITE_URL = "http://vod.shaw.ca/";
	public static final String SUB_CATEGORY_URL = "http://catalog.cubovision.it/video/search?field_categorie_tid=CATEGORY_ID&field_categorie_tid_1=SUB_CAT_ID";
	public static final String SUBCAT_POST_URL = "http://catalog.cubovision.it/ajax_get_sottocategoria";
	private static String ENDPOINT_LINK = "http://vod.shaw.ca/CATEGORY/GENRE/page:PAGE_N/limit:48/#content";
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Price };
	@Inject
	@Named("XlsWriter7")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		List<String> linkToSubgenre = new ArrayList<String>();

		for (Element link : genres) {

			String categoryHref = link.attr("href");

			String splited[] = categoryHref.split("/");
			if (splited.length == 3) {
				String linkToSubGenre = SITE_URL + "/" + splited[1] + "/" + splited[2] + "/limit:48/";
				logger.info("Size={} data={}", splited.length, linkToSubGenre);

				try {
					Document subGenre = WextUtils.downloadPage(linkToSubGenre);
					Elements pages = subGenre.select(".pagenation a");
					if (pages.size() != 0) {
						int max = 0;
						for (Element page : pages) {
							try {
								Integer newMax = Integer.parseInt(page.text());
								if (newMax > max) {
									max = newMax;
								}
							} catch (NumberFormatException ex) {
								continue;
							}

						}

						for (int i = 1; i <= max; i++) {
							String pageUrl = ENDPOINT_LINK.replace("CATEGORY", splited[1]).replace("GENRE", splited[2]).replace("PAGE_N", "" + i);
							logger.info("processing genre {} pageEndpoint {}", splited[2], pageUrl);
							PageWithMovies pageWithMovies = new PageWithMovies(splited[2], "", siteName, pageUrl);
							fullPageWithMoviesList.add(pageWithMovies);
						}

					} else {
						logger.info("processing genre {} pageEndpoint {}", splited[2], linkToSubGenre);
						PageWithMovies pageWithMovies = new PageWithMovies(splited[2], "", siteName, linkToSubGenre);
						fullPageWithMoviesList.add(pageWithMovies);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
