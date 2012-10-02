package com.karthik.wext.site.part2;

import java.io.IOException;

import jxl.write.WriteException;
import lombok.Getter;

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

public class V28_5_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Price, COLUMN.Year };
	private static final String ABENTEUER = "http://www.videoload.de/c/22/41/58/60/22415860";
	private static final String ACTION = "http://www.videoload.de/c/14/83/21/36/14832136";
	private static final String DEUTCHER = "http://www.videoload.de/c/70/97/28/7097280";
	private static final String DRAMA = "http://www.videoload.de/c/14/83/44/94/14834494";
	private static final String EROTIK = "http://www.videoload.de/c/14/85/22/12/14852212";
	private static final String FAMILIE = "http://www.videoload.de/c/14/83/45/10/14834510";
	private static final String FANTASY = "http://www.videoload.de/c/22/41/63/84/22416384";
	private static final String FILMEAB18 = "http://www.videoload.de/c/22/41/63/84/22416384";
	private static final String HORROR = "http://www.videoload.de/c/14/83/45/36/14834536";
	private static final String KLASSIK = "http://www.videoload.de/c/76/85/27/7685274";
	private static final String KOMEDIE = "http://www.videoload.de/c/65/83/65/6583654";
	private static final String KURZFILM = "http://www.videoload.de/c/70/41/19/7041194";
	private static final String QUERR = "http://www.videoload.de/c/19/25/06/04/19250604";
	private static final String ROMANTIK = "http://www.videoload.de/c/20/61/70/08/20617008";
	private static final String SCIENCE = "http://www.videoload.de/c/14/83/46/04/14834604";
	private static final String THRILLER = "http://www.videoload.de/c/14/83/46/14/14834614";
	private static final String WESTERN = "http://www.videoload.de/c/20/68/20/60/20682060";

	private static final String query1 = "?q=*&film_num=50&mg=Abenteuer&sort=title&q=*&film_start=";
	private static final String query2 = "?q=*&film_num=50&mg=Action&sort=title&q=*&film_start=";
	private static final String query3 = "?q=*&film_num=50&mt=TV-Film%2CH-Film&sort=title&q=*&film_start=";
	private static final String query4 = "?q=*&film_num=50&mg=Drama&sort=title&q=*&film_start=";
	private static final String query5 = "?q=*&film_num=50&sort=title&q=*&film_start=";
	private static final String query6 = "?q=*&film_num=50&mt=TV-Film%2CH-Film&sort=title&q=*&film_start=";
	private static final String query7 = "?q=*&film_num=50&mg=fantasy&sort=title&q=*&film_start=";
	private static final String query8 = "?q=*&film_num=50&rat_a=18&sort=title&q=*&film_start=";
	private static final String query9 = "?q=*&film_num=50&mg=Horror&sort=title&q=*&film_start=";
	private static final String query10 = "?q=*&film_num=50&mg=Klassiker&sort=title&q=*&film_start=";
	private static final String query11 = "?q=*&film_num=50&mg=Komödie&sort=title&q=*&film_start=";
	private static final String query12 = "";
	private static final String query13 = "";
	private static final String query14 = "?q=*&film_num=50&mg=romantik&sort=title&q=*&film_start=";
	private static final String query15 = "?q=*&film_num=50&mg=SciFi%2CScienceFiction&sort=title&q=*&film_start=";
	private static final String query16 = "?q=*&film_num=50&mg=Thriller&sort=title&q=*&film_start=";
	private static final String query17 = "?q=*&film_num=50&mg=western&sort=title&q=*&film_start=";

	private enum GENRE {
		Abentuer(ABENTEUER, query1), Action(ACTION, query2), Deutcher(DEUTCHER, query3), Drama(DRAMA, query4), Erotik(EROTIK, query5), Familie(FAMILIE, query6), Fantasy(FANTASY, query7), Filmeab18(FILMEAB18, query8), Horror(HORROR, query9), Klassik(
				KLASSIK, query10), Komedie(KOMEDIE, query11), Kurzfilm(KURZFILM, query12), Querr(QUERR, query13), Romantik(ROMANTIK, query14), Science(SCIENCE, query15), Thriller(THRILLER, query16), Western(WESTERN, query17);

		@Getter
		private String url;

		@Getter
		private String query;

		GENRE(String url, String query) {
			this.url = url;
			this.query = query;
		};

	}

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
		// LogUtils.logger.info("base={}", baseDocument.toString());
		// genres = baseDocument.select(SiteConfigs.getGenderSelector(siteName));
	}

	@Override
	protected void step3_CollectLinks() {
		// LogUtils.logger.info("now collect links will be.., genre size={}", genres.size());
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		GENRE genres[] = GENRE.values();

		for (int i = 0; i < genres.length; i++) {

			String genreHref = genres[i].getUrl();
			String genreName = genres[i].toString();
			try {
				Document genrePage = WextUtils.downloadPage(genreHref);

				Integer itemCount = Integer.parseInt(genrePage.select(".suenu b").first().html());

				if (itemCount > 50) {
					int pageCount = (int) Math.ceil(itemCount / 50.0);
					for (int j = 1; j < pageCount; j++) {
						try {
							String genrePageHref = genreHref + genres[i].getQuery() + j * 50;
							Document genrePageCount = WextUtils.downloadPage(genrePageHref);

							Elements filmElems = genrePageCount.select(".thumb a");
							for (Element elem : filmElems) {
								String filmHref = elem.attr("href");
								LogUtils.logger.info("href={}, pages={}", filmHref, j);
								PageWithMovies page = new PageWithMovies(genreName, siteName, filmHref);
								fullPageWithMoviesList.add(page);
							}

						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				} else {
					LogUtils.logger.info("href={}", genreHref);
					Elements filmElems = genrePage.select(".thumb a");
					for (Element elem : filmElems) {
						String filmHref = elem.attr("href");
						LogUtils.logger.info("href={}, pages={}", filmHref, 0);
						PageWithMovies page = new PageWithMovies(genreName, siteName, filmHref);
						fullPageWithMoviesList.add(page);
					}

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}

		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

}
