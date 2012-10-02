package com.karthik.wext.site.roger;

import java.io.IOException;

import jxl.write.WriteException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.karthik.wext.core.AbstractSiteStrategy;
import com.karthik.wext.xls.XlsWriterData;
import com.karthik.wext.xls.XlsWriterInterface;

public abstract class V13_RogerAbstract extends AbstractSiteStrategy {

	public static String SITE_URL;

	@Inject
	@Named("XlsWriter1")
	public XlsWriterInterface xlsWriter;

	@Override
	protected void step5_SaveInfoToXls() throws WriteException, IOException {
		xlsWriter.writeXls(new XlsWriterData(siteName).with(genreMap));
	}
}
