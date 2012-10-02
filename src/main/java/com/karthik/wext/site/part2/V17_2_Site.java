package com.karthik.wext.site.part2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

public class V17_2_Site extends AbstractSiteStrategy {
	private static final Map<String, String> areas = new HashMap<String, String>() {
		{
			put("8", "Mainland");
			put("3", "Hong Kong");
			put("4", "Taiwan");
			put("12", "Japan");
			put("1", "Korea");
			put("2", "United States");
			put("5", "Britain");
			put("13", "France");
			put("6", "Germany");
			put("20", "Thailand");
			put("39", "India");
			put("9", "Italy");
			put("19", "Spain");
			put("24", "Canada");
			put("*", "Other");

		}
	};

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
					genreName = areas.get(genreName);
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
