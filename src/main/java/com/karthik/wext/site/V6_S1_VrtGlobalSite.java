package com.karthik.wext.site;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V6_S1_VrtGlobalSite extends AbstractSiteStrategy {
	private static final Logger logger = LoggerFactory.getLogger(V6_S1_VrtGlobalSite.class);
	public static final String GENRE_URL = "http://vtr.com/vod/index.php";
	private static final String SITE_URL = "http://vtr.com/vod/";
	private static final String PAGINA_URL = "http://vtr.com/vod/pag_cartelera.php";
	private static final int pagina = 5;
	public static String BASE_URL = GENRE_URL;
	public static String CSS_SELECTOR = "#detalle_ficha_sv";

	@Override
	protected void step3_CollectLinks() {
		for (int i = 1; i <= pagina; i++) {
			try {
				Document doc = Jsoup.connect(PAGINA_URL).cookies(cookies).data("pagina", "" + i).post();
				Elements links = doc.select(".caratula.sep_caratulas a");
				for (Element link : links) {
					String href = SITE_URL + link.attr("href");
					logger.info("processing pageEndpoint {}", href);
					PageWithMovies pageWithMovies = new PageWithMovies(null, null, siteName, href);
					fullPageWithMoviesList.add(pageWithMovies);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * logger.info("write info to file...{}", outputXlSPath);
		 * 
		 * try { int i = 0; final int colN = 3; Label labels[] = new Label[colN];
		 * 
		 * int j = 0; labels[0] = new Label(j, i, COLUMN.MainTitle.toString(), ARIAL_BOLD_FORMAT); labels[1] = new Label(j + 1, i, COLUMN.Year.toString(), ARIAL_BOLD_FORMAT); labels[2] = new Label(j + 2, i, COLUMN.Actors.toString(),
		 * ARIAL_BOLD_FORMAT);
		 * 
		 * addCells(labels);
		 * 
		 * ++i;
		 * 
		 * for (MovieInfo movieInfo : fullMovieInfoList) { labels[0] = new Label(j, i, movieInfo.getMainTitle(), ARIAL_FORMAT); labels[1] = new Label(j + 1, i, movieInfo.getYear(), ARIAL_FORMAT); labels[2] = new Label(j + 2, i, movieInfo.getActors(),
		 * ARIAL_FORMAT);
		 * 
		 * addCells(labels);
		 * 
		 * ++i; }
		 * 
		 * j += colN; setAutosizeColumn(j, colN); workbook.write(); workbook.close();
		 * 
		 * } catch (WriteException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	@Override
	protected void step2_CollectGenre() {
		// TODO Auto-generated method stub
		try {
			cookies = WextUtils.executeHeadForSessionId(baseUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
