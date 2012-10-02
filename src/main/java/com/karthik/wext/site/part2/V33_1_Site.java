package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V33_1_Site extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V33_1_Site.class);

	private static String sufix = "/?rows=50";

	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Price };

	@Inject
	@Named("XlsWriter3")
	public XlsWriterInterface xlsWriter;

	protected void step2_CollectGenre() throws IOException {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));

	}

	@Override
	protected void step3_CollectLinks() throws IOException {

		for (Element elem : genres) {
			String genreUrl = elem.attr("href");
			String genreName = elem.text();

			String itemSize = elem.select(".facet_results").html().replaceAll("[(,)]", "").trim();
			logger.info("itemSize={}", itemSize);
			Integer intItemSize = Integer.parseInt(itemSize);
			int pageSize = intItemSize / 50 + 1;
			logger.info("pageSize={}", pageSize);
			for (int pageIndex = 1; pageIndex <= pageSize; pageIndex++) {

				String pageUrl = getPageUrl(genreUrl, pageIndex);
				PageWithMovies page = new PageWithMovies(genreName, siteName, pageUrl);
				fullPageWithMoviesList.add(page);
			}
		}

	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));

	}

	private String getPageUrl(String genreUrl, int index) {
		return genreUrl + "p" + index + sufix;
	}
}
