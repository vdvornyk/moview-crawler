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

public class XlsWriterListGenreImpl extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriterListGenreImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {

		initWorkbook(xlsData.getSiteName());
		logger.info("Genre list write info to file...{}", outputXlSPath);

		final int colN = xlsData.getColumnNames().length;
		int j = 0;
		Label labels[] = new Label[colN];

		for (String genre : xlsData.getGenreMap().keySet()) {

			int i = 0;
			sheet.addCell(new Label(j, i++, genre, ARIAL_BOLD_FORMAT));

			addInfoToCell(j, i++, colN, null, xlsData.getColumnNames());

			Set<MovieInfo> movieInfoSet = xlsData.getGenreMap().get(genre);
			for (MovieInfo movieInfo : movieInfoSet) {
				logger.info("movie info={}", movieInfo.toString());
				addInfoToCell(j, i++, colN, movieInfo, xlsData.getColumnNames());
			}
			j += colN;

		}
		setAutosizeColumn(j, colN);

		workbook.write();
		workbook.close();

	}
}
