package io.github.cheergoivan.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.github.cheergoivan.movie.Movie;
import io.github.cheergoivan.movie.MoviesReader;
import io.github.cheergoivan.settings.Settings;

public class MovieRepositoryGenerator {
	private File localRepository;
	private HtmlProducer htmlProducer;
	private MoviesReader movieReader;
	private final String[] resources={"/template/film.ico","/template/main.css","/template/smallScreen.css"};

	public MovieRepositoryGenerator(File localRepository) {
		super();
		this.localRepository = localRepository;
		this.htmlProducer=new HtmlProducer();
		this.movieReader=new MoviesReader();
	}
	
	public void run() throws IOException{
		createCssFilesIfNotExists();
		List<Movie> movies=movieReader.readAll(localRepository.getAbsolutePath());
		createMoviePages(movies);
	}
	
	private void createMoviePages(List<Movie> movies) throws IOException{
		deleteHtmlPages();
		movies.sort((m1,m2)->Double.compare(m2.getRatings(),m1.getRatings()));
		int moviesPerPage=(int)Settings.moviesPerPage.getValueAsInteger();
		int lastPageIndex=movies.size()/moviesPerPage+1;
		List<Movie> currPageMovies=new LinkedList<>();
		int moviesCount=0;
		Iterator<Movie> iter=movies.iterator();
		while(iter.hasNext()){
			moviesCount++;
			currPageMovies.add(iter.next());
			if(moviesCount%moviesPerPage==0){
				int pageId=moviesCount/moviesPerPage;
				createHtmlFile(currPageMovies,pageId,pageId==lastPageIndex?1:pageId+1);
				currPageMovies.clear();
			}
		}
		if(!currPageMovies.isEmpty())
			createHtmlFile(currPageMovies,lastPageIndex,1);
	}
	
	private void createCssFilesIfNotExists() throws IOException{
		File[] files=localRepository.listFiles();
		for(String file:resources){
			File f=new File(file);
			if(!fileExists(files,f)){
				Files.copy(readResource(file), Paths.get(localRepository.getAbsolutePath()+"/"+f.getName()),StandardCopyOption.REPLACE_EXISTING);
				System.out.println("create file "+file);
			}
		}
	}
	
	private InputStream readResource(String name){
		return this.getClass().getResourceAsStream(name);
	}
	
	private boolean fileExists(File[] files,File target){
		for(File f:files){
			if(f.getName().equals(target.getName())){
				return true;
			}
		}
		return false;
	}
	
	private void createHtmlFile(List<Movie> movies,int pageId,int nextPage) throws IOException{
		System.out.println("create web page "+htmlFileName(pageId));
		String newHtmlFilePath=localRepository.getAbsolutePath()+"/"+htmlFileName(pageId);
		String fileContent=htmlProducer.produceHtmlPage(movies, nextPage);
		File newFile = new File(newHtmlFilePath);
		if(!newFile.exists())
			newFile.createNewFile();
		Files.write(Paths.get(newHtmlFilePath),fileContent.getBytes(),StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	private String htmlFileName(int page){
		return String.valueOf(page)+".html";
	}
	
	private void deleteHtmlPages(){
		File[] files=localRepository.listFiles(f->f.getName().matches("[1-9][0-9]*\\.html$"));
		for(File f:files){
			f.delete();
		}
	}
}
