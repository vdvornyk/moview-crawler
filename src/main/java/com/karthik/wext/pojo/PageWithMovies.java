package com.karthik.wext.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.karthik.wext.configs.SiteName;

@AllArgsConstructor
public class PageWithMovies {
	@Getter
	private final String genre;

	@Getter
	private String subGenre;

	@Getter
	private final SiteName siteName;
	@Getter
	private final String url;

	@Getter
	private final boolean isPost;

	@Getter
	private final String params;

	@Override
	public String toString() {
		return "PageWithMovies [genre=" + genre + ", siteName=" + siteName + ", url=" + url + "]";
	}

	public PageWithMovies(String genre, SiteName siteName, String url, boolean isPost, String params) {
		this.genre = genre;
		this.siteName = siteName;
		this.url = url;
		this.isPost = isPost;
		this.params = params;
	}

	public PageWithMovies(String genre, SiteName siteName, String url) {
		this.genre = genre;
		this.siteName = siteName;
		this.url = url;
		this.isPost = false;
		this.params = null;
	}

	public PageWithMovies(String genre, String subGenre, SiteName siteName, String url) {
		this.genre = genre;
		this.siteName = siteName;
		this.url = url;
		this.isPost = false;
		this.params = null;
		this.subGenre = subGenre;
	}

}
