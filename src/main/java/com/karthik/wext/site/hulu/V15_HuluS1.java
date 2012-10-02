package com.karthik.wext.site.hulu;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V15_HuluS1 extends V15_HuluAbstract {
	public static final Logger logger = LoggerFactory.getLogger(V15_HuluAbstract.class);

	public static String BASE_URL = "http://www.hulu.com/browse/search?keyword=&alphabet=All&family_friendly=0&closed_captioned=0&has_free=1&has_huluplus=0&has_hd=0&channel=All&subchannel=&network=&rating=All&display=Full%20length%20movies%20only&decade=All&type=movies&view_as_thumbnail=false&block_num=";

	public static String CSS_SELECTOR = ".info";

	@Override
	protected void step2_CollectGenre() throws IOException {

	}

	@Override
	protected void step3_CollectLinks() throws IOException {
		int indexCount = -1;
		boolean isNotFinish = true;
		String response;
		while (isNotFinish) {
			++indexCount;
			String target = baseUrl + indexCount;

			response = WextUtils.executeGet(target, cookies);

			logger.info("page count={}", indexCount);
			if (response.indexOf("display:none") > 0) {
				isNotFinish = false;
			} else {
				logger.info("processing pageEndpoint {}", target);
				PageWithMovies page = new PageWithMovies("", siteName, target);
				fullPageWithMoviesList.add(page);
			}

		}

	}

	@Override
	protected boolean getIsEpisod() {
		// TODO Auto-generated method stub
		return false;
	}

}
