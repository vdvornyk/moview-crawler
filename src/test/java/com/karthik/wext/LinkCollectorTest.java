package com.karthik.wext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.karthik.wext.site.hulu.V15_HuluS3;
import com.karthik.wext.util.WextUtils;

public class LinkCollectorTest {
	// @Test
	public void V5Test() {
		String result = WextUtils.excutePost("http://catalog.cubovision.it/ajax_get_categoria", "pc_type");
		System.out.println("result=" + result);

		String result2 = WextUtils.excutePost("http://catalog.cubovision.it/ajax_get_sottocategoria", "categoria=18567");
		System.out.println("result=" + result2);
	}

	// @Test
	public void HuluTest() throws IOException {
		String targetURL = "http://www.hulu.com/browse/search?keyword=&alphabet=All&family_friendly=0&closed_captioned=0&has_free=1&has_huluplus=0&has_hd=0&channel=All&subchannel=&network=&rating=All&display=Full%20length%20movies%20only&decade=All&type=movies&view_as_thumbnail=false&block_num=5";
		String response = WextUtils.executeGet(targetURL, null);

		String htmlBlock = response.substring(76, response.length() - 3);

		String escJava = StringEscapeUtils.unescapeJava(htmlBlock);

		Document doc = Jsoup.parse(escJava);
		System.out.println(doc.select(".info > div > div").eq(1).select("a").html());
	}

	@Test
	public void HuluWithPassTest() {
		String targetURL = "https://secure.hulu.com/account/authenticate";
		try {
			WextUtils.executeHeadForSessionId(targetURL, "login=pdigrazia@snl.com; password=snlkagan543;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void VrtTestSession() {
		String targetURL = "http://vtr.com/vod/index.php";
		try {
			Map<String, String> cookies = WextUtils.executeHeadForSessionId(targetURL);
			System.out.println(cookies);

			Document doc = Jsoup.connect("http://vtr.com/vod/pag_cartelera.php").cookies(cookies).data("pagina", "3").post();
			System.out.println(doc.html());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testReflection() throws IllegalArgumentException, IllegalAccessException {
		try {
			Field siteUrlField = V15_HuluS3.class.getDeclaredField("BASE_URL");
			System.out.println("field=" + siteUrlField.get(null));

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
