package io.github.cheergoivan.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.github.cheergoivan.movie.Movie;
import io.github.cheergoivan.movie.MoviesReader;

public class MovieRepositoryGenerator {
	private File localRepository;
	private MoviesReader movieReader;
	private HtmlPageProducer producer;
	
	public MovieRepositoryGenerator(File localRepository) throws IOException {
		super();
		this.localRepository = localRepository;
		movieReader=new MoviesReader();
		producer=new HtmlPageProducer();
	}
	
	public void run() throws IOException{
		List<Movie> movies=movieReader.readAll(localRepository.getAbsolutePath());
		producer.createMoviePages(movies);
	}
}
