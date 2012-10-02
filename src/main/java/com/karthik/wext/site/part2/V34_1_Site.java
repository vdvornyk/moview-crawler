package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;

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

public class V34_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price, COLUMN.Year };
	private static final String GENRE_URL = "http://tvguide.tdc.dk/lejfilm/genre/?genre_id=";
	private static final String offset = "&offset=OFF_REPL&sortorder=newest";

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		// LogUtils.logger.info("base={}", baseDocument.toString());
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be.., genre size={}", genres.size());
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		int pCount = 0;
		for (int i = 1; i < genres.size(); i++) {

			Element optionElem = genres.get(i);
			String genreHref = GENRE_URL + optionElem.attr("value");

			String genreName = optionElem.html();
			try {
				Document genrePage = WextUtils.downloadPage(genreHref);
				Integer itemCount = Integer.parseInt(genrePage.select(".g_vod_count").first().html().replaceAll(".* ", "").trim());

				if (itemCount > 24) {
					int pageCount = (int) Math.ceil(itemCount / 24.0);
					for (int j = 0; j < pageCount; j++) {
						try {
							String genrePageHref = genreHref + offset.replace("OFF_REPL", "" + j * 24);
							Document genrePageOffset = WextUtils.downloadPage(genrePageHref);
							Elements filmElems = genrePageOffset.select(".g_vod_list li a");
							for (Element elem : filmElems) {
								String filmHref = elem.attr("href");
								LogUtils.logger.info("href={}, genre={}, fromGenreCount={}, genrePage={} pages={}", new Object[] { filmHref, i, genres.size(), j, pCount++ });
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
					PageWithMovies page = new PageWithMovies(genreName, siteName, genreHref);
					fullPageWithMoviesList.add(page);
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
