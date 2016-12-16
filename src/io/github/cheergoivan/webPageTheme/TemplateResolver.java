package io.github.cheergoivan.webPageTheme;



import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.github.cheergoivan.movie.Movie;

public class TemplateResolver {
	private String template;
	
	private Document doc;
	
	private Elements movieEntries;
	
	public TemplateResolver(String template){
		this.doc=Jsoup.parse(template);
		this.movieEntries=doc.getElementsByClass("movie");
		this.template=template;
	}
	
	public int quantityOfMoviesRequired(){
		return movieEntries.size(); 
	}
	
	public String fillMovies(List<Movie> movies){
		String newHtml="";
		for(int i=0;i<movieEntries.size();i++){
			String movieEntry=movieEntries.get(i).outerHtml();
			String replacement="";
			if(i<movies.size())
				replacement=fillTemplateWithMovie(movieEntry,movies.get(i));
			newHtml=template.replaceFirst(movieEntry, replacement);
		}
		return newHtml;
	}

	private String fillTemplateWithMovie(String movieEntry,Movie movie){
		return movieEntry.replace("{{name}}", movie.getName())
				.replace("{{year}}", movie.getYear() + "")
				.replace("{{doubanId}}", String.valueOf(movie.getDoubanId()))
				.replace("{{director}}", movie.getDirector())
				.replace("{{ratings}}", String.valueOf(movie.getRatings()))
				.replace("{{poster}}", movie.getPoster());
	}
}
