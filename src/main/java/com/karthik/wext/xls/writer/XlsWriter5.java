package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;

import jxl.write.Label;
import jxl.write.WriteException;

import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter5 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter5() {
		super();
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {
		initWorkbook(xlsData.getSiteName());
		logger.info("write info to file...{}", outputXlSPath);

		final int colN = xlsData.getColumnNames().length;
		int i = 0;
		int j = 0;
		Label labels[] = new Label[colN];

		addInfoToCell(j, i++, colN, null, xlsData.getColumnNames());

		for (MovieInfo movieInfo : xlsData.getFullMovieInfoList()) {
			addInfoToCell(j, i++, colN, movieInfo, xlsData.getColumnNames());
		}

		j += colN;
		setAutosizeColumn(j, colN);
		workbook.write();
		workbook.close();
	}
}
