package com.karthik.wext.site.part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jxl.write.WriteException;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import com.google.gson.GsonBuilder;
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

public class V18_5_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle };

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

		String reqParams = new GsonBuilder().serializeNulls().create().toJson(new JsonParam(1));
		String jsonResult = WextUtils.excutePost(urlTemplate, reqParams);
		JSONObject obj = (JSONObject) JSONValue.parse(jsonResult);
		Result res = new Result((JSONObject) obj.get("result"));
		LogUtils.logger.info("Result={}", res);
		addInfo(res);
		if (res.getPageCount() > 1) {
			int i = 1;
			int pageCount = res.getPageCount();
			while (i++ < pageCount) {
				reqParams = new GsonBuilder().serializeNulls().create().toJson(new JsonParam(i));
				LogUtils.logger.info("Params = {}", reqParams);

				jsonResult = WextUtils.excutePost(urlTemplate, reqParams);
				obj = (JSONObject) JSONValue.parse(jsonResult);
				res = new Result((JSONObject) obj.get("result"));
				addInfo(res);

			}
		}

	}

	private void addInfo(Result res) {
		for (Item item : res.getItems()) {
			MovieInfo movieInfo = item.toMovieInfo();
			fullMovieInfoList.add(movieInfo);

			String genre = movieInfo.getGenre();
			if (!genreMap.containsKey(genre)) {
				genreMap.put(genre, new HashSet<MovieInfo>());
			} else {
				genreMap.get(genre).add(movieInfo);
			}
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

	class JsonParam {

		public int id = 1;
		public String jsonrpc = "2.0";
		public String method = "getData";

		public List<Param> params;

		public JsonParam(final int page) {
			params = new ArrayList<Param>() {
				{
					add(new Param(page));
				}
			};
		}

	}

	class Param {
		public Param(final int page) {
			this.page = page;
		}

		public String block = "filme";
		public String filter = "";
		public String mainArea = "serie";
		public int page;
		public String pageId = "serie_formateaz_all";
		public String route = "/serie/all";
		public String sort = null;
		public String subNav = "formateaz";

	}

	@Data
	public class Container {
		private Result result;
		private String id;
		private String error;

	}

	@Data
	public class Result {
		public Result(JSONObject object) {
			page = Integer.parseInt(object.get("page").toString());
			pageCount = Integer.parseInt(object.get("pageCount").toString());
			resultCount = Integer.parseInt(object.get("resultCount").toString());
			totalItems = Integer.parseInt(object.get("totalItems").toString());
			items = new ArrayList<Item>();

			for (Object object1 : (JSONArray) object.get("items")) {
				items.add(new Item((JSONObject) object1));
			}
		}

		int page;
		int pageCount;
		int resultCount;
		int totalItems;
		List<Item> items;
	}

	@AllArgsConstructor
	@Data
	public class Item {
		public Item(JSONObject object) {
			title = object.get("title").toString();
			Object episod = object.get("episodeTitle");
			episodeTitle = episod == null ? "" : episod.toString();

		}

		String title;
		String episodeTitle;

		public MovieInfo toMovieInfo() {
			MovieInfo movieInfo = new MovieInfo();
			movieInfo.setMainTitle(title + " " + episodeTitle);

			movieInfo.setGenre(parseGenre());
			return movieInfo;
		}

		public String parseGenre() {
			if (Character.isDigit(title.charAt(0))) {
				return "[0..9]";
			}
			return "" + title.charAt(0);
		}

	}

}
