package com.karthik.wext.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class V3_S1_CanalPlaySite extends AbstractSiteStrategy {
	public static final Logger logger = LoggerFactory.getLogger(V3_S1_CanalPlaySite.class);
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year };

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {

		genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		// TODO Auto-generated method stub
		logger.info("baseUrl={}", baseUrl);
		List<String> links = new ArrayList<String>();
		boolean isAddUrl = false;
		for (Element genrePageLink : genres) {

			if (genrePageLink.text().equals("Action")) {
				isAddUrl = true;
			}

			if (isAddUrl) {
				String genreName = genrePageLink.text();
				String pageUrl = genrePageLink.attr("href");

				logger.info("processing... Genre='{}'; PageEndpoint= '{}'", genreName, pageUrl);
				PageWithMovies page = new PageWithMovies(genreName, siteName, pageUrl);
				fullPageWithMoviesList.add(page);

				if (genrePageLink.text().equals("Western")) {
					break;
				}

			}

		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
