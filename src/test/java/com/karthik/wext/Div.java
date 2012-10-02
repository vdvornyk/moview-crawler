package com.karthik.wext;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.karthik.wext.util.WextUtils;

public class Div {

	// @Test
	public void test() {
		String url = "http://bigpondmovies%2fmovielist%2fForSsrPaged%2f21%2f%24page%2fMovie%2fCurrentlyAvailable%2fGenre-634442690775937061%2fVideoType-Movie%2fBy-ReleaseDate%3fprefix%3dgenre";
		System.out.print(url.replaceAll("%2f", "/"));
	}

	// @Test
	public void test1() throws IOException {
		Document htmlPage = WextUtils.downloadPage("http://www.lovefilm.com/browse/film/watch-online/");
		Elements elements = htmlPage.select(".fl_detail_info");
		List<Element> list = elements.subList(0, elements.size());
		Assert.assertEquals(elements.size(), list.size());
	}

	@Test
	public void testJarPath() {
		System.out.println("jar path=" + WextUtils.JAR_PATH);
	}

}
