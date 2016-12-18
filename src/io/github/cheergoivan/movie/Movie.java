package io.github.cheergoivan.movie;

public class Movie {
	private String name;
	private int year;
	private String director;
	private double ratings;
	private long doubanId;
	private String poster;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	public long getDoubanId() {
		return doubanId;
	}

	public void setDoubanId(long doubanId) {
		this.doubanId = doubanId;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
}
