package com.karthik.wext;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.util.WextUtils;

public class ParsePageTest {
	public static final Logger logger = LoggerFactory.getLogger(ParsePageTest.class);
	public static final boolean IS_PROXY = false;

	// @Test
	public void testBigPondMoviesTestSite1() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {

		String URL = "http://bigpondmovies.com/movielist/ForSsrPaged/21/1/Movie/CurrentlyAvailable/Genre-634442689085904613/VideoType-Movie/By-ReleaseDate";
		MoviesFromPageExtractor pageParser = null;// new MoviesFromPageExtractor(SiteName.BigPond_Movies);
		List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(URL);
		logger.info("size={}", movieInfoList.size());

		Assert.assertEquals(21, movieInfoList.size());

		Assert.assertNotNull(movieInfoList.get(0).getPrice());
		Assert.assertNotNull(movieInfoList.get(0).getMainTitle());
		Assert.assertNotNull(movieInfoList.get(0).getYear());

	}

	// @Test
	public void loveFilmTestSite() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {
		String URL = "http://www.lovefilm.com/browse/film/watch-online/action/p2/?rows=50";
		MoviesFromPageExtractor pageParser = null;// new MoviesFromPageExtractor(SiteName.LoveFilm);
		List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(URL);
		logger.info("size={}", movieInfoList);

		Assert.assertEquals(10, movieInfoList.size());
		Assert.assertNotNull(movieInfoList.get(0).getMainTitle());
		Assert.assertNotNull(movieInfoList.get(0).getPrice());
		Assert.assertNotNull(movieInfoList.get(0).getYear());

	}

	// @Test
	public void portugalTelecomTestSite2() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {
		String URL = "http://online.meo.pt/Videoclube/Catalogo";
		MoviesFromPageExtractor pageParser = null;// new MoviesFromPageExtractor(SiteName.PORTUGAL_TELECOM);
		List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(URL);
		logger.info("size={}", movieInfoList.size());

		Assert.assertEquals(15, movieInfoList.size());
		Assert.assertNotNull(movieInfoList.get(0).getMainTitle());

	}

	// @Test
	public void canalPlayTestSite3() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {
		String URL = "http://www.canalplay.com/cinema/action";
		MoviesFromPageExtractor pageParser = null;// new MoviesFromPageExtractor(SiteName.CanalPlay);
		List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(URL);
		logger.info("size={}", movieInfoList.size());

		Assert.assertEquals(32, movieInfoList.size());

		Assert.assertNotNull(movieInfoList.get(0).getMainTitle());

	}

	// @Test
	public void vrtGlobalComTestSite4() throws ClientProtocolException, IOException, ParserConfigurationException, SAXException {
		String URL = "http://vtr.com/vod/index.php/307/Ficha-Pelicula/Amor,-Muerte,-Tarantela-y-Vino";
		MoviesFromPageExtractor pageParser = null;// new MoviesFromPageExtractor(SiteName.VTR_GlobalCom_SA);
		List<MovieInfo> movieInfoList = pageParser.extractMovieInfoFromUrl(URL);
		logger.info("size={}", movieInfoList.size());

		Assert.assertEquals(1, movieInfoList.size());

		Assert.assertNotNull(movieInfoList.get(0).getMainTitle());
		Assert.assertNotNull(movieInfoList.get(0).getYear());

	}

	// @Test
	public void V29PageTest() throws IOException {
		Document genrePage = WextUtils.downloadPage("http://ondemand.upc.at/movie/index/char/Alle");

		Element info = genrePage.select(".filmliste .body .info").get(0);
		LogUtils.logger.info("block={}", info.text().split(" "));

		Element info1 = genrePage.select(".filmliste .body .info").get(1);
		LogUtils.logger.info("block={}", info1.text());

	}

	// @Test
	public void V30PageTest() throws IOException {
		Document genrePage = WextUtils.downloadPage("http://video-a-la-demande.orange.fr/#vod/category/182563");

		for (Element elem : genrePage.select("li:contains(Humour / Spectacles) li a")) {
			LogUtils.logger.info("page={}", elem.html());
			LogUtils.logger.info("page={}", elem.attr("href").split("/")[4]);
		}

	}

	// @Test
	public void V32PageTest() throws IOException {
		Document genrePage = Jsoup.parse(WextUtils.excutePost("http://sundaytv.terra.com.br/Web/Category/MediaList", "area=2&channelId=128&genreId=0&top=0&limit=48&order=ReleaseDateDesc&filter=0&page=1"));
		Element elem = genrePage.select(".videoclube").first();

		try {
			org.jsoup.nodes.Element node = elem.select(".infoLayer h5").last();
			LogUtils.logger.info("page={}", node.text().split("\\|")[0].trim());
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		// LogUtils.logger.info("page={}", elem.select(".infoLayer h5").last().html().split("\\|")[1].trim());

	}

	@Test
	public void V28_PageTest() throws IOException {
		Document genrePage = WextUtils.downloadPage("http://www.videoload.de/c/25/89/99/04/25899904");

		LogUtils.logger.info(genrePage.select("h3:contains(Land)~h3~h3 a").text());
		LogUtils.logger.info(genrePage.select("#filmdetail a[href~=.*lend-popup]~a~div p").text());
	}
}
