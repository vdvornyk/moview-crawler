package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import jxl.write.Label;
import jxl.write.WriteException;

import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.MovieInfo.COLUMN;
import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter3 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter3() {
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

			// add genre first
			sheet.addCell(new Label(j, i, genre, ARIAL_BOLD_FORMAT));
			COLUMN[] columns = xlsData.getColumnNames();
			addInfoToCell(j + 1, i++, COL_N - 1, null, Arrays.copyOfRange(columns, 1, columns.length));
			// now add name of columns

			Set<MovieInfo> movieInfoSet = xlsData.getGenreMap().get(genre);
			for (MovieInfo movieInfo : movieInfoSet) {

				addInfoToCell(j, i++, COL_N, movieInfo, xlsData.getColumnNames());
			}

			j += COL_N;
		}

		setAutosizeColumn(j, COL_N);
		workbook.write();
		workbook.close();

	}
}
