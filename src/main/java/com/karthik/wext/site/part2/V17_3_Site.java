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
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V17_3_Site extends AbstractSiteStrategy {

	@Inject
	@Named("XlsWriter1")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be..");
		for (Element genreLink : genres) {

			if (genreLink.attr("href").length() > 0) {
				try {
					String genreHref = genreLink.attr("href");
					LogUtils.logger.info("genreHref={}", genreHref);
					Document genrePage = WextUtils.downloadPage(genreHref);
					String genreName = genreHref.replaceAll(".*,", "").replace("/", "");
					Elements elems = genrePage.select(".list-pager-v2 a");
					if (elems.size() > 0) {
						String lastPage = elems.get(elems.size() - 2).html();
						for (int i = 2; i <= Integer.parseInt(lastPage); i++) {

							PageWithMovies page = new PageWithMovies(genreName, siteName, genreHref + "page" + i + "/");
							fullPageWithMoviesList.add(page);
							LogUtils.logger.info("part={} pages={}", genreHref + "page" + i + "/", fullPageWithMoviesList.size());
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

	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(genreMap));
	}

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}
}
