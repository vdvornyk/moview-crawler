package com.karthik.wext.site.hulu;

import com.karthik.wext.core.AbstractSiteStrategy;

public abstract class V15_HuluAbstract extends AbstractSiteStrategy {

	protected static final String CREDENTIALS = "login=pdigrazia@snl.com; password=snlkagan543;";
	protected static final String AUTH_URL = "https://secure.hulu.com/account/authenticate";

	@Override
	protected void step5_SaveInfoToXls() {
		/*
		 * logger.info("write info to file...{}", outputXlSPath); try { final boolean isEpisod = getIsEpisod(); final int COL_N = isEpisod ? 4 : 3;
		 * 
		 * Label labels[] = new Label[COL_N]; final int lastN = labels.length - 1;
		 * 
		 * int j = 0, i = 0; labels[0] = new Label(j, i, COLUMN.MainTitle.toString(), ARIAL_BOLD_FORMAT); labels[1] = new Label(j + 1, i, COLUMN.Year.toString(), ARIAL_BOLD_FORMAT); if (isEpisod) { labels[2] = new Label(j + 2, i,
		 * COLUMN.Episod.toString(), ARIAL_BOLD_FORMAT); } labels[lastN] = new Label(j + lastN, i, COLUMN.Genre.toString(), ARIAL_BOLD_FORMAT); addCells(labels); ++i;
		 * 
		 * for (String genre : genreMap.keySet()) { Set<MovieInfo> movieInfoSet = genreMap.get(genre); for (MovieInfo movieInfo : movieInfoSet) { labels[0] = new Label(j, i, movieInfo.getMainTitle()); labels[1] = new Label(j + 1, i,
		 * movieInfo.getYear()); if (isEpisod) { labels[2] = new Label(j + 2, i, movieInfo.getEpisod()); } labels[lastN] = new Label(j + lastN, i, movieInfo.getGenre()); addCells(labels); ++i; } } j += COL_N; setAutosizeColumn(j, COL_N);
		 * workbook.write(); workbook.close();
		 * 
		 * } catch (WriteException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	protected abstract boolean getIsEpisod();

}
