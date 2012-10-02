package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;

import org.jsoup.nodes.Element;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V30_5_Site extends Abstract_V30_Site {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price, COLUMN.Year };
	private static final String MAIN_GENRE = "Séries TV";
	private static final String SUB_ID = "SUB_ID";

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		genres = baseDocument.select("li:contains(" + MAIN_GENRE + ") li a");
	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be.., genre size={}", genres.size());
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		for (Element elem : genres) {

			String genreId = elem.attr("href").split("/")[4];
			String genreName = elem.text().replaceAll("\\(.*", "").trim();
			String jsonHref = urlTemplate.replace(SUB_ID, genreId);

			LogUtils.logger.info("name={}  id={}", genreName, genreId);
			LogUtils.logger.info("json={}", jsonHref);

			PageWithMovies page = new PageWithMovies(genreName, siteName, jsonHref);
			fullPageWithMoviesList.add(page);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}
}
