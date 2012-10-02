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

public class V26_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year };
	private static final String GENRE_TMPL = "http://www.filmotech.com/V2/ES/FX_CatalogoResultados.asp?GE=";
	@Inject
	@Named("XlsWriter2")
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
			String genreHref = GENRE_TMPL + genreLink.attr("id").replace("Opcion_GE_", "");
			String genreName = genreLink.html();
			try {
				Document genrePage = WextUtils.downloadPage(genreHref);
				Element lastPage = genrePage.select(".FS12 .M5 .enlace3").last();

				addLinksFromDoc(genrePage, urlTemplate, genreName);

				if (lastPage != null) {

					for (int j = 2; j <= Integer.parseInt(lastPage.html()); j++) {
						LogUtils.logger.info("href={}, pages={}", genreHref, j);
						String nextPage = genreHref + "&PG=" + j;
						Document nextGenrePage = WextUtils.downloadPage(nextPage);
						addLinksFromDoc(nextGenrePage, urlTemplate, genreName);
					}

				}

				LogUtils.logger.info("href={}, pages={}", genreHref, lastPage == null ? 0 : lastPage.html());

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void addLinksFromDoc(Document genrePage, String urlTemplate, String genreName) {
		Elements genreContent = genrePage.select("#CATCartelesResNEWcar a");
		for (Element el : genreContent) {
			String filmHref = urlTemplate + el.attr("href").replaceAll(".*\\('", "").replaceAll("'\\).*", "");
			PageWithMovies page = new PageWithMovies(genreName, siteName, filmHref);
			fullPageWithMoviesList.add(page);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
