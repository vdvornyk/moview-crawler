package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;

import jxl.write.Label;
import jxl.write.WriteException;

import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter1 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter1() {
		super();
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {
		initWorkbook(xlsData.getSiteName());
		logger.info("Simple Gender, write info to file...{}", outputXlSPath);

		final int colN = 1;
		Label labels[] = new Label[colN];

		int j = 0;

		for (String genre : xlsData.getGenreMap().keySet()) {
			int i = 0;
			sheet.addCell(new Label(j, i, genre, ARIAL_BOLD_FORMAT));
			++i;

			for (MovieInfo movieInfo : xlsData.getGenreMap().get(genre)) {

				labels[0] = new Label(j, i, movieInfo.getMainTitle(), ARIAL_FORMAT);

				addCells(labels);
				++i;
			}
			j += colN;

		}
		setAutosizeColumn(j, colN);

		workbook.write();
		workbook.close();
	}
}
