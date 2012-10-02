package com.karthik.wext.site.part2;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import jxl.write.WriteException;
import lombok.Data;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.configs.SiteConfigs;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.core.LogUtils;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.util.WextUtils;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class V22_5_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.SubGenre, COLUMN.Season, COLUMN.Episod, COLUMN.Rating, COLUMN.Price };

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {
	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be..");
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);
		try {
			String jsonResult = WextUtils.executeGet(urlTemplate + new Params().toString(), null);
			Container container = new Gson().fromJson(jsonResult, Container.class);
			for (Product product : container.getProducts()) {
				MovieInfo movieInfo = product.getMovieInfo();

				fullMovieInfoList.add(movieInfo);

				String genre = movieInfo.getGenre();
				if (!genreMap.containsKey(genre)) {
					genreMap.put(genre, new HashSet<MovieInfo>());
				} else {
					genreMap.get(genre).add(movieInfo);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void step4_CollectInfoFromLinks() {

	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

	class Params {
		public String category = "all";
		public String filterString = "all";
		public String rating = "G,PG,M,16,18";
		public String section = "movies";
		public String tvod = "true";
		public String start = "0";
		public String end = "500";

		@Override
		public String toString() {
			return "category=" + category + "&filterString=" + filterString + "&rating=" + rating + "&section=" + section + "&tvod=" + tvod + "&start=" + start + "&end=" + end;
		}
	}

	@Data
	class Container {
		private List<Product> products;
		private String rating;
		private String totalProducts;
	}

	@Data
	class Product {

		public MovieInfo getMovieInfo() {
			MovieInfo movieInfo = new MovieInfo();
			movieInfo.setMainTitle(productTitle);
			movieInfo.setGenre(parseGenre());
			movieInfo.setSubGenre(category);
			movieInfo.setSeason(seriesNumber);
			movieInfo.setEpisod(episodeNumber);
			movieInfo.setActors(rating);
			movieInfo.setPrice(priceCode);
			movieInfo.setYear(releaseYear);
			return movieInfo;

		}

		public String parseGenre() {
			if (Character.isDigit(productTitle.charAt(0))) {
				return "[0..9]";
			}
			return "" + productTitle.charAt(0);
		}

		String productTitle; // main title

		String category; // genre

		// season
		String seriesNumber;
		String episodeNumber;
		String releaseYear;

		String priceCode; // price

		String rating;// rating

	}

}
