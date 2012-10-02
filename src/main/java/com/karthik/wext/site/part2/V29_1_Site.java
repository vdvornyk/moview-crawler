package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V29_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Country, COLUMN.Year, COLUMN.Genre, COLUMN.Price };

	@Inject
	@Named("XlsWriter5")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {

	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be.. ");
		String genreHref = SiteConfigs.getSiteBaseUrl(siteName);
		PageWithMovies page = new PageWithMovies("", siteName, genreHref);
		fullPageWithMoviesList.add(page);
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(fullMovieInfoList));
	}
}
