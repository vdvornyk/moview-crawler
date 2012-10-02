package com.karthik.wext.site.hulu;

import java.io.IOException;

import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public class V15_HuluS4 extends V15_HuluAbstract {
	public static String BASE_URL = "http://www.hulu.com/browse/search?keyword=&alphabet=All&family_friendly=0&closed_captioned=0&has_free=0&has_huluplus=0&has_hd=0&channel=All&subchannel=&network=All&rating=&display=Shows%20with%20full%20episodes%20only&decade=All&type=tv&view_as_thumbnail=false&block_num=BLKNUM&pgid=3";

	public static final String CSS_SELECTOR = ".info";

	@Override
	protected void step2_CollectGenre() throws IOException {
		// TODO set cookies here
		cookies = WextUtils.executeHeadForSessionId(AUTH_URL, CREDENTIALS);

	}

	@Override
	protected void step3_CollectLinks() throws IOException {
		// TODO Auto-generated method stub
		int indexCount = -1;
		boolean isNotFinish = true;
		String response;
		while (isNotFinish) {
			++indexCount;
			String target = baseUrl.replace("BLKNUM", "" + indexCount);

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
		return true;
	}
}
