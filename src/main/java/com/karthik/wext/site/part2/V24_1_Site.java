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

public class V24_1_Site extends AbstractSiteStrategy {
	private static final COLUMN columnNames[] = new COLUMN[] { COLUMN.MainTitle, COLUMN.Year };
	private static final String FILTER = "http://voyo.nova.cz/bin/eshop/product/filter.php";

	@Inject
	@Named("XlsWriter2")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step2_CollectGenre() {

	}

	@Override
	protected void step3_CollectLinks() {
		LogUtils.logger.info("now collect links will be");
		String urlTemplate = SiteConfigs.getUrlTemplate(siteName);

		Elements elems = baseDocument.select(".item_ul a");
		for (Element elem : elems) {
			String genreName = elem.text();
			String serialHref = urlTemplate + elem.attr("href");
			LogUtils.logger.info("nextGenreUrl={}", serialHref);
			try {
				Document serial = WextUtils.downloadPage(serialHref);
				addLinks(serial, genreName, urlTemplate);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void addLinks(Document genrePage, String genreName, String urlTempl) {
		Elements elems = genrePage.select(".item_ul a");
		for (Element elem : elems) {
			String pageGenreHref = urlTempl + elem.attr("href");
			LogUtils.logger.info("nextGenreUrl={}", pageGenreHref);
			PageWithMovies page = new PageWithMovies(genreName, siteName, pageGenreHref);
			fullPageWithMoviesList.add(page);
		}
	}

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		LogUtils.logger.info("Save to xls will be here");
		xlsWriter.writeXls(new XlsWriterData(siteName).with(columnNames).with(genreMap));
	}

	class Resp {
		public String result;
		public int productsTotal;
	}

	class FilterParam {
		public FilterParam(String genreId, String page) {
			this.genreId = genreId;
			this.page = page;
		}

		private String page;
		private String genreId;

		private String count = "35";
		private String sectionId = "69003";
		private String productId;
		private String showAs = "2013";
		private String urlPath = "/serialy/";
		private String descrLength = "280";
		private String showInTv = "n";
		private String itemTpl = "item_ul.tpl";

		private String resultType = "categories";
		private String disablePagination = "y";
		private String chargeTypeFilter = "yes";
		private String ignoreTreeOrderForEpisodes = "y";
		private String subsiteId = "503";

		private String expireBadge = "n";

		private String seriesFilter = "n";
		private String letterFilter = "false";
		private String letter = "null";

		private String sort = "distributionStartTime";
		private String sortOrder = "DESC";
		private String toggleSortOrder = "0";
		private String templateType = "box";

		private String defaultCount = "35";
		private String defaultCountVoyo1 = "5";
		private String defaultCountLess = "50";
		private String allowTrial = "N";

		private String totalCount = "1024";
		private String loading = "true";
		private String autoloadCount = "0";
		private String autoloadLimit = "1000";
		private String autoloadAllow = "true";
		private String maxPages = "30";
		private String append = "false";
		private String allowTrialBadge = "y";
		private String setNewBadge = "n";
		private String boxId = "9055d130301";

		@Override
		public String toString() {
			return "genreId=" + genreId + "&count=" + count + "&sectionId=" + sectionId + "&productId=" + productId + "&showAs=" + showAs + "&urlPath=" + urlPath + "&descrLength=" + descrLength + "&showInTv=" + showInTv + "&itemTpl=" + itemTpl
					+ "&resultType=" + resultType + "&disablePagination=" + disablePagination + "&chargeTypeFilter=" + chargeTypeFilter + "&ignoreTreeOrderForEpisodes=" + ignoreTreeOrderForEpisodes + "&subsiteId=" + subsiteId + "&expireBadge="
					+ expireBadge + "&seriesFilter=" + seriesFilter + "&letterFilter=" + letterFilter + "&letter=" + letter + "&sort=" + sort + "&sortOrder=" + sortOrder + "&toggleSortOrder=" + toggleSortOrder + "&templateType=" + templateType
					+ "&page=" + page + "&defaultCount=" + defaultCount + "&defaultCountVoyo1=" + defaultCountVoyo1 + "&defaultCountLess=" + defaultCountLess + "&allowTrial=" + allowTrial + "&totalCount=" + totalCount + "&loading=" + loading
					+ "&autoloadCount=" + autoloadCount + "&autoloadLimit=" + autoloadLimit + "&autoloadAllow=" + autoloadAllow + "&maxPages=" + maxPages + "&append=" + append + "&allowTrialBadge=" + allowTrialBadge + "&setNewBadge=" + setNewBadge
					+ "&boxId=" + boxId;
		}

	}
}
