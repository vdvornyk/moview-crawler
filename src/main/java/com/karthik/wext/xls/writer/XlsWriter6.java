package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import jxl.write.Label;
import jxl.write.WriteException;

import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter6 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter6() {
		super();
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {
		initWorkbook(xlsData.getSiteName());
		logger.info("write info to file...{}", outputXlSPath);

		final int COL_N = xlsData.getColumnNames().length;
		int j = 0;

		for (String genre : xlsData.getGenreMap().keySet()) {

			int i = 0;

			sheet.addCell(new Label(j, i, genre, ARIAL_BOLD_FORMAT));

			++i;

			Set<MovieInfo> movieInfoSet = xlsData.getGenreMap().get(genre);
			Map<String, Set<MovieInfo>> subGenreMap = xlsData.getSubGenreMap(movieInfoSet);

			if (subGenreMap.size() != 0) {
				for (String subGenre : subGenreMap.keySet()) {
					Set<MovieInfo> subGenreMovieSet = subGenreMap.get(subGenre);

					sheet.addCell(new Label(j, i, subGenre, ARIAL_BOLD_FORMAT));
					sheet.addCell(new Label(j, i + 1, COLUMN.Price.toString(), ARIAL_BOLD_FORMAT));
					++i;
					i = addCellFromMovieInfo(subGenreMovieSet, i, j);

				}
			} else {
				i = addCellFromMovieInfo(movieInfoSet, i, j);
			}
			j += COL_N;

		}
		setAutosizeColumn(j, COL_N);
		workbook.write();
		workbook.close();

	}

	private int addCellFromMovieInfo(Set<MovieInfo> movieInfoSet, int i, int j) {

		Label labels[] = new Label[2];
		for (MovieInfo movieInfo : movieInfoSet) {
			labels[0] = new Label(j, i, movieInfo.getMainTitle(), ARIAL_FORMAT);
			labels[1] = new Label(j + 1, i, movieInfo.getPrice(), ARIAL_FORMAT);
			addCells(labels);
			++i;
		}

		return i;
	}
}
