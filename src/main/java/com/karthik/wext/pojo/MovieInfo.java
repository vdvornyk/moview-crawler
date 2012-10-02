package com.karthik.wext.pojo;

import lombok.Data;

@Data
public class MovieInfo {

	public enum COLUMN {
		Genre, SubGenre, MainTitle, SubTitle, Year, Price, Episod, Actors, AvailableOn, ExpiringOn, DaysLeft, Country, PriceBuy, PriceRent, Season, Rating
	}

	private String mainTitle;
	private String subTitle;
	private String year;
	private String price;
	private String season;
	private String episod;
	private String actors;
	private String genre;
	private String subGenre;
	private String availableOn;
	private String expiringOn;
	private String daysLeft;
	private String priceRent;
	private String priceBuy;

	public void setGenre(String genre) {
		if (this.genre == null) {
			this.genre = genre;
		}
	}

	public void setGenreHard(String genre) {

		this.genre = genre;
	}

	public String getDataByColumn(COLUMN column) {
		switch (column) {
		case Genre:
			return genre;
		case MainTitle:
			return mainTitle;
		case Year:
			return year;
		case Price:
			return price;
		case Episod:
			return episod;
		case Actors:
			return actors;
		case AvailableOn:
			return availableOn;
		case ExpiringOn:
			return expiringOn;
		case DaysLeft:
			return daysLeft;
		case SubGenre:
			return subGenre;
		case Country:
			return actors;
		case PriceBuy:
			return price;
		case PriceRent:
			return actors;
		case Season:
			return season;

		case Rating:
			return actors;
		}
		return null;
	}
}
