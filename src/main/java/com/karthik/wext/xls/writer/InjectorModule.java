package com.karthik.wext.xls.writer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.karthik.wext.xls.XlsWriterInterface;

public class InjectorModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter1")).to(XlsWriter1.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter2")).to(XlsWriter2.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter3")).to(XlsWriter3.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter4")).to(XlsWriter4.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter5")).to(XlsWriter5.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter6")).to(XlsWriter6.class);
		bind(XlsWriterInterface.class).annotatedWith(Names.named("XlsWriter7")).to(XlsWriter7.class);

	}
}
