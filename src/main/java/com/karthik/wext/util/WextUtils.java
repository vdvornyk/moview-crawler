package com.karthik.wext.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.karthik.wext.configs.SiteName;

public class WextUtils {

	enum Method {
		HTML, JSON, JS
	}

	public static String JAR_PATH;
	public static String XLS = ".xls";

	static {
		JAR_PATH = WextUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	public static final Logger logger = LoggerFactory.getLogger(WextUtils.class);

	public static String charConvert(String str) {
		return null;
	}

	public static Document downloadPage(String pageUrl) throws IOException {
		Document htmlPage = null;
		IOException ioEx = null;
		if (pageUrl != null) {
			for (int i = 0; i <= 3; i++) {
				try {

					htmlPage = Jsoup.connect(pageUrl).timeout(20000).get();// getHtmlPageContent(pageUrl);

				} catch (IOException ex) {
					ioEx = ex;
				}
			}

			if (htmlPage == null) {
				throw ioEx;
			}
			return htmlPage;
		}
		return null;

	}

	public static String executeGet(String targeURL, Map<String, String> cookies) throws IOException {
		URL url = new URL(targeURL);
		URLConnection con = url.openConnection();
		if (cookies != null) {
			StringBuilder cookieBuilder = new StringBuilder();
			for (String key : cookies.keySet()) {
				cookieBuilder.append(" " + key + "=" + cookies.get(key) + ";");
			}
			con.setRequestProperty("Cookie", cookieBuilder.toString());
		}
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, "UTF-8");
		return body;
	}

	/*
	 * public static String executeAssingGet(String targeURL) throws IOException { AsyncHttpClient asyncHttpClient = new AsyncHttpClient(); Future<Response> f = asyncHttpClient.prepareGet(targeURL).execute(); Response r = null; try { r = f.get(); }
	 * catch (InterruptedException | ExecutionException e) { // TODO Auto-generated catch block e.printStackTrace(); } return r.getResponseBody(); }
	 */

	public static Map<String, String> executeHeadForSessionId(String targeURL) throws IOException {
		Map<String, String> cookieMap = new HashMap<String, String>();
		String cookieSet = executeHeadConnection(targeURL, null);

		String cookies[] = cookieSet.split(";");

		for (String cookieEntry : cookies) {
			String cookieData[] = cookieEntry.split("=");
			cookieMap.put(cookieData[0], cookieData[1]);
		}
		return cookieMap;
	}

	public static Map<String, String> executeHeadForSessionId(String targeURL, String credentials) throws IOException {

		Map<String, String> cookieMap = new HashMap<String, String>();
		String cookieSet = executeHeadConnection(targeURL, credentials);

		String cookies[] = cookieSet.split(";");

		for (String cookieEntry : cookies) {
			if (cookieEntry.contains("=")) {
				String cookieData[] = cookieEntry.split("=");
				cookieMap.put(cookieData[0], cookieData[1]);
			}
		}
		return cookieMap;

	}

	private static String executeHeadConnection(String targetURL, String cookie) throws IOException {
		URL connection = new URL(targetURL);
		HttpURLConnection serverConnection = (HttpURLConnection) connection.openConnection();

		serverConnection.setRequestProperty("method", "HEAD");
		serverConnection.setRequestProperty("Accept-Language", "en-US");
		serverConnection.setRequestProperty("User-Agent", "Gemini");
		serverConnection.setRequestProperty("Connection", "Keep-Alive");
		if (cookie != null) {
			serverConnection.setRequestProperty("Cookie", cookie);
		}

		// "login=pdigrazia@snl.com; password=snlkagan543;"
		serverConnection.setRequestMethod("HEAD");
		serverConnection.connect();
		serverConnection.disconnect();

		return serverConnection.getHeaderField("Set-Cookie");
	}

	public static String excutePost(String targetURL, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	public static Document downloadPage(SiteName siteName, String pageUrl, Map<String, String> cookies) throws IOException {
		Document doc = null;
		if (siteName.toString().toLowerCase().contains("hulu")) {
			String response = null;

			response = WextUtils.executeGet(pageUrl, cookies);

			String htmlBlock = response.substring(76, response.length() - 3);

			String escJava = StringEscapeUtils.unescapeJava(htmlBlock);

			doc = Jsoup.parse(escJava);
		} else {

			doc = downloadPage(pageUrl);

		}
		return doc;
	}

}
