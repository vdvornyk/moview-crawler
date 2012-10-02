package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;
import lombok.Getter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

public class V28_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price, COLUMN.Year };
	private static final String FILME = "http://www.videoload.de/c/18/67/12/38/18671238";
	private static final String SERIEN = "http://www.videoload.de/c/18/67/12/26/18671226";

	private static final String query1 = "?q=*&film_num=50&mt=H-Film%2CTV-Film&sort=title&q=*&film_start=";
	private static final String query2 = "?q=*&film_num=50&mt=Serienstaffel&sort=title&q=*&film_start=";

	private enum GENRE {
		Filme(FILME, query1), Serien(SERIEN, query2);

		@Getter
		private String url;
		@Getter
		private String query;

		GENRE(String url, String query) {
			this.url = url;
			this.query = query;
		};

	}

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
	}

	@Override
	protected void step3_CollectLinks() {
		// LogUtils.logger.info("now collect links will be.., genre size={}", genres.size());
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		GENRE genres[] = GENRE.values();

		for (int i = 0; i < genres.length; i++) {

			String genreHref = genres[i].getUrl();
			String genreName = genres[i].toString();
			try {
				Document genrePage = WextUtils.downloadPage(genreHref);

				Integer itemCount = Integer.parseInt(genrePage.select(".suenu b").first().html());

				if (itemCount > 50) {
					int pageCount = (int) Math.ceil(itemCount / 50.0);
					for (int j = 1; j < pageCount; j++) {
						try {
							String genrePageHref = genreHref + genres[i].getQuery() + j * 50;
							Document genrePageCount = WextUtils.downloadPage(genrePageHref);

							Elements filmElems = genrePageCount.select(".thumb a");
							for (Element elem : filmElems) {
								String filmHref = elem.attr("href");
								LogUtils.logger.info("href={}, pages={}", filmHref, j);
								PageWithMovies page = new PageWithMovies(genreName, siteName, filmHref);
								fullPageWithMoviesList.add(page);
							}
							;
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				} else {
					LogUtils.logger.info("href={}", genreHref);
					Elements filmElems = genrePage.select(".thumb a");
					for (Element elem : filmElems) {
						String filmHref = elem.attr("href");
						LogUtils.logger.info("href={}, pages={}", filmHref, 0);
						PageWithMovies page = new PageWithMovies(genreName, siteName, filmHref);
						fullPageWithMoviesList.add(page);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
