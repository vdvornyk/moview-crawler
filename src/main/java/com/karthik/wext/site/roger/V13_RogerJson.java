package com.karthik.wext.site.roger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.Getter;

import com.google.gson.Gson;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.util.WextUtils;

public class V13_RogerJson extends V13_RogerAbstract {
	private static final String urls[] = new String[] { "http://www.rogersondemand.com/d/hPNWtA/json/asset?max=200&template=asset&tt=0&specID=c11b277c-c9e5-4ab0-af1f-9f40009732d6&sort=Title.Name%20ASC%2C%20Name%20ASC&filter=all&layout=grid",
			"http://www.rogersondemand.com/d/rbqGOA/json/asset?max=200&template=asset&tt=0&specID=22f9d9f5-00f0-4449-ab26-9d2e01031ee3&sort=Title.Name%20ASC%2C%20Name%20ASC&filter=all&layout=grid",
			"http://www.rogersondemand.com/d/mQ567Q/json/asset?max=200&template=asset&tt=1&specID=d2ed8556-c95b-49e8-ae5e-9d2e01031ede&sort=Title.Name%20ASC%2C%20Name%20ASC&filter=all&layout=grid",
			"http://www.rogersondemand.com/d/mQ567Q/json/asset?max=200&template=asset&tt=1&specID=fbad288b-3de1-478f-a527-9f40009732df&sort=Title.Name%20ASC%2C%20Name%20ASC&filter=all&layout=grid"

	};
	public String SITE_URL;

	@Override
	protected void step4_CollectInfoFromLinks() {
		List<MovieInfo> movieElements = new ArrayList<MovieInfo>();
		try {
			String response = WextUtils.executeGet(SITE_URL, cookies);
			logger.info("response{}", response);

			Container container = new Gson().fromJson(response, Container.class);
			logger.info("data{}", container);

			for (Node node : container.getAssets()) {
				MovieInfo movieInfo = new MovieInfo();
				movieInfo.setGenre(node.getGenre());
				movieInfo.setMainTitle(node.getName());
				movieElements.add(movieInfo);
			}
			// read here data from json
			// make request, and get json
			// parse json
			// add movieInfo to list

			for (MovieInfo movie : movieElements) {
				String genre = movie.getGenre();

				if (!genreMap.containsKey(genre)) {
					genreMap.put(genre, new HashSet<MovieInfo>());
				}
				genreMap.get(genre).add(movie);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class Container {
		@Getter
		private List<Node> assets;
		// +getter.
	}

	public class Node {
		@Getter
		private String name;
		@Getter
		private String genre;
		// +getters.
	}

	@Override
	protected void step2_CollectGenre() {
		switch (siteName) {
		case S17_Roger_Movies_Free_for_Everyone:
			SITE_URL = urls[0];
			break;
		case S18_Roger_Movies_Free_from_Rgr_Cust:
			SITE_URL = urls[1];
			break;
		case S20_Roger_TV_Free_for_Everyone:
			SITE_URL = urls[2];
			break;
		case S21_Roger_TV_Free_from_Rgr_Cust:
			SITE_URL = urls[3];
			break;
		}
	}

	@Override
	protected void step3_CollectLinks() throws IOException {
		// TODO Auto-generated method stub

	}
}
