package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V32_2_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.PriceBuy, COLUMN.PriceRent };
	private static final String POST_URL = "http://sundaytv.terra.com.br/Web/Category/MediaList";

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {

	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be..");

		String params = "area=0&channelId=128&genreId=0&top=0&limit=48&order=ReleaseDateDesc&filter=1&page=1";
		Document doc = Jsoup.parse(WextUtils.excutePost(POST_URL, params));

		String totalCount = doc.select(".pagination h4").first().text().replaceAll(".* ", "").trim();
		int pageCount = (int) Math.ceil(Integer.parseInt(totalCount) / 48.0);
		for (int i = 1; i <= pageCount; i++) {

			params = "area=0&channelId=128&genreId=0&top=0&limit=48&order=ReleaseDateDesc&filter=1&page=" + i;
			LogUtils.logger.info("added page by i={}", i);
			PageWithMovies page = new PageWithMovies("", siteName, POST_URL, true, params);
			fullPageWithMoviesList.add(page);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
