package com.karthik.wext.site.part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import lombok.Getter;

import com.google.gson.Gson;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.PageWithMovies;
import com.karthik.wext.util.WextUtils;

public abstract class Abstract_V30_Site extends AbstractSiteStrategy {

	@Override
	protected void step4_CollectInfoFromLinks() {
		final List<MovieInfo> movieElements = new ArrayList<MovieInfo>();

		for (PageWithMovies page : fullPageWithMoviesList) {
			try {
				LogUtils.logger.info("json={}", page.getUrl());
				String jsonResp = WextUtils.executeGet(page.getUrl(), null);
				Container container = new Gson().fromJson(jsonResp, Container.class);
				for (Node node : container.getResponse()) {
					MovieInfo movieInfo = new MovieInfo();
					movieInfo.setGenre(page.getGenre());
					movieInfo.setMainTitle(node.getTitle() + node.getTitle2());
					movieInfo.setYear(node.getProductionDate());

					if (node.getPrices().getBuy() != null) {
						movieInfo.setPrice(node.getPrices().getBuy().getPrice());
					}

					movieElements.add(movieInfo);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		for (MovieInfo movie : movieElements) {
			String genre = movie.getGenre();

			if (!genreMap.containsKey(genre)) {
				genreMap.put(genre, new LinkedHashSet<MovieInfo>());
			}
			genreMap.get(genre).add(movie);
		}

	}

	private class Container {
		@Getter
		private List<Node> response;
	}

	private class Node {
		@Getter
		private String title;
		@Getter
		private String title2;
		@Getter
		private String productionDate;

		@Getter
		private Prices prices;

		private class Prices {
			@Getter
			private Buy buy;

			private class Buy {
				@Getter
				private String price;
			}
		}

	}
}
