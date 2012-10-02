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

public class V20_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year, COLUMN.Price };

	@Inject
	@Named("XlsWriter3")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be..");
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		for (Element genreLink : genres) {
			try {
				String genreHref = urlTemplate + genreLink.attr("href");
				String genreName = genreLink.attr("title");
				LogUtils.logger.info("genreHref={}", genreHref);
				Document genrePage = WextUtils.downloadPage(genreHref);

				Elements elems = genrePage.select(".paginationWrapper .last");
				if (elems.size() > 0) {
					String lastPageTemplate = elems.attr("href");
					String lastPageStr = lastPageTemplate.split("/")[5];
					int lastPage = Integer.parseInt(lastPageStr);
					for (int i = 2; i <= lastPage; i++) {
						String nextGenreUlr = genreHref.replace("/1/", "/" + i + "/");
						LogUtils.logger.info("nextGenreUrl={}", nextGenreUlr);
						PageWithMovies page = new PageWithMovies(genreName, siteName, nextGenreUlr);
						fullPageWithMoviesList.add(page);
					}
				}

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
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
