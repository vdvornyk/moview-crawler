package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;
import java.util.Set;

import jxl.write.Label;
import jxl.write.WriteException;

import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter2 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter2() {
		super();
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {

		initWorkbook(xlsData.getSiteName());
		logger.info("write info to file...{}", outputXlSPath);

		final int COL_N = xlsData.getColumnNames().length;
		int j = 0;
		Label labels[] = new Label[COL_N];

		for (String genre : xlsData.getGenreMap().keySet()) {

			int i = 0;
			sheet.addCell(new Label(j, i++, genre, ARIAL_BOLD_FORMAT));

			addInfoToCell(j, i++, COL_N, null, xlsData.getColumnNames());

			Set<MovieInfo> movieInfoSet = xlsData.getGenreMap().get(genre);

			for (MovieInfo movieInfo : movieInfoSet) {
				// logger.info(movieInfo.toString());

				addInfoToCell(j, i++, COL_N, movieInfo, xlsData.getColumnNames());
			}
			j += COL_N;

		}
		setAutosizeColumn(j, COL_N);

		workbook.write();
		workbook.close();

	}

}
