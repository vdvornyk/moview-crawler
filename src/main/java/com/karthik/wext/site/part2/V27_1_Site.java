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

public class V27_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price, COLUMN.Year };
	private static final String HOME = "http://yousee.tv";

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

		for (int i = 2; i < genres.size(); i++) {

			Element optionElem = genres.get(i);
			String genreHref = urlTemplate + optionElem.attr("value");
			String genreName = optionElem.html();
			Integer itemCount = Integer.parseInt(genreName.replaceAll(".*\\(", "").replaceAll("\\).*", "").trim());
			if (itemCount > 25) {
				int pageCount = (int) Math.ceil(itemCount / 25.0);
				for (int j = 1; j <= pageCount; j++) {
					try {
						String genrePageHref = genreHref + "/" + j + "/nyeste/";
						Document genrePage = WextUtils.downloadPage(genrePageHref);
						Elements filmElems = genrePage.select("#movie_overview .list div p a");
						for (Element elem : filmElems) {
							String filmHref = HOME + elem.attr("href");
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
				PageWithMovies page = new PageWithMovies(genreName, siteName, genreHref);
				fullPageWithMoviesList.add(page);
			}
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
