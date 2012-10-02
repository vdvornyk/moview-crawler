package com.karthik.wext.xls.writer;

import static com.karthik.wext.core.LogUtils.logger;

import java.io.IOException;

import jxl.write.WriteException;

import com.karthik.wext.xls.XlsWriterAbstract;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public class XlsWriter4 extends XlsWriterAbstract implements XlsWriterInterface {

	public XlsWriter4() {
		super();
	}

	@Override
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException {
		initWorkbook(xlsData.getSiteName());
		logger.info("write info to file...{}", outputXlSPath);
		// TODO need implement here
	}
}
