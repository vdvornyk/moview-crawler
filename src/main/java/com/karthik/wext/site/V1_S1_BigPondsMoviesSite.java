package com.karthik.wext.site;

import java.io.IOException;

import jxl.write.WriteException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V1_S1_BigPondsMoviesSite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V1_S1_BigPondsMoviesSite.class);
	private static final String urlTemplate = "http://bigpondmovies.com/movielist/ForSsrPaged/10000/1/Movie/CurrentlyAvailable/Genre-";
	private static final String BASE_URL = "http://bigpondmovies.com";
	private static final String CSS_SELECTOR = ".tooltip";
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Price };

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step3_CollectLinks() {
		int i = 0;
		for (Element genrePageLink : genres) {
			Document genrePage;
			try {
				genrePage = WextUtils.downloadPage(baseUrl + genrePageLink.attr("href"));

				String genreName = genrePageLink.html();

				String genreId = genrePage.select("#genre-proxy").attr("baseur").replaceAll(".*Genre-", "").replaceAll("%2.*", "");
				if (genreId != null) {

					String pageUrl = urlTemplate + genreId;
					logger.info("processing genre {} pageEndpoint {}", genreName, pageUrl);
					PageWithMovies page = new PageWithMovies(genreName, siteName, pageUrl);
					fullPageWithMoviesList.add(page);
				}
				if (SiteConfigs.isDev) {
					if (i++ > 2) {
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select("#MoviesDropDown  > div:eq(1) > div > table:eq(1) >tbody>tr a");
	}
}
