package com.karthik.wext.xls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

import com.karthik.wext.configs.SiteName;
import com.karthik.wext.pojo.MovieInfo;
import com.karthik.wext.pojo.MovieInfo.COLUMN;

public class XlsWriterData {
	@Getter
	private List<MovieInfo> fullMovieInfoList;
	@Getter
	private COLUMN[] columnNames;
	@Getter
	private Map<String, HashSet<MovieInfo>> genreMap;
	@Getter
	private SiteName siteName;

	public XlsWriterData(SiteName siteName) {
		this.siteName = siteName;
	}

	public XlsWriterData with(List<MovieInfo> movieInfoList) {
		this.fullMovieInfoList = movieInfoList;
		return this;
	}

	public XlsWriterData with(COLUMN[] columnNames) {
		this.columnNames = columnNames;
		return this;
	}

	public XlsWriterData with(Map<String, HashSet<MovieInfo>> genreMap) {
		this.genreMap = genreMap;
		return this;
	}

	public Map<String, Set<MovieInfo>> getSubGenreMap(Set<MovieInfo> movieInfoSet) {
		Map<String, Set<MovieInfo>> subGenreMap = new HashMap<String, Set<MovieInfo>>();
		for (MovieInfo movieInfo : movieInfoSet) {
			String subGenre = movieInfo.getSubGenre();
			if (movieInfo.getSubGenre() != null) {
				if (subGenreMap.containsKey(subGenre)) {
					subGenreMap.get(subGenre).add(movieInfo);
				} else {
					Set<MovieInfo> hashSet = new HashSet<MovieInfo>();
					hashSet.add(movieInfo);
					subGenreMap.put(subGenre, hashSet);
				}
			}
		}
		if (subGenreMap.size() != 0) {
			return subGenreMap;
		}
		return new HashMap<String, Set<MovieInfo>>();
	}

}
