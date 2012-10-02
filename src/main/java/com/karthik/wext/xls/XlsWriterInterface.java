package com.karthik.wext.xls;

import java.io.IOException;

import jxl.write.WriteException;

public interface XlsWriterInterface {
	public void writeXls(XlsWriterData xlsData) throws IOException, WriteException;
}
