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

public class V23_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.Genre, COLUMN.SubGenre, COLUMN.MainTitle, COLUMN.Year };
	private static final String GNR = "?mode=vod24h&view=list&category=";
	private static final String OFFSET = "&page=";

	@Inject
	@Named("XlsWriter5")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be..");
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);

		for (int i = 0; i < genres.size(); i++) {

			Element genreLink = genres.get(i);
			String genreName = genreLink.html();
			try {
				String genreHref = urlTemplate + genreLink.attr("href");
				LogUtils.logger.info("nextGenreUrl={}", genreHref);
				Document genrePage = WextUtils.downloadPage(genreHref);

				Elements pagination = genrePage.select(".right .pages li a");
				String pageCountStr = pagination.get(pagination.size() - 2).html();
				Integer pageCount = Integer.parseInt(pageCountStr);
				for (int j = 2; j <= pageCount; j++) {
					String pageGenreHref = genreHref + OFFSET + j;
					PageWithMovies page = new PageWithMovies(genreName, siteName, pageGenreHref);
					fullPageWithMoviesList.add(page);
				}

				LogUtils.logger.info("nextGenreUrl={}", genreHref);
				PageWithMovies page = new PageWithMovies(genreName, siteName, genreHref);
				fullPageWithMoviesList.add(page);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(genreMap));
	}
}
