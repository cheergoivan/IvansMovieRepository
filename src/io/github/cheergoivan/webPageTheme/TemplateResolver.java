package io.github.cheergoivan.webPageTheme;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.cheergoivan.movie.Movie;

public class TemplateResolver {
	private final String template;
	
	private Document doc;
	
	private Elements movieEntries;
	
	private int quantityOfMovies;
	
	public TemplateResolver(String template){
		this.template=template;
		this.doc=Jsoup.parse(template);
		this.movieEntries=doc.getElementsByClass("movie");
		quantityOfMovies=movieEntries.size();
	}
	
	public int quantityOfMoviesRequired(){
		return  quantityOfMovies;
	}
	
	public String fillMovies(List<Movie> movies){
		for(int i=0;i<movieEntries.size();i++){
			Element movieEntry=movieEntries.get(i);
			String movieEntryHtml=movieEntry.html();
			if(i<movies.size()){
				String replacement=fillTemplateWithMovie(movieEntryHtml,movies.get(i));
				Elements children=movieEntry.children();
				for(Element e:children)
					e.remove();
				movieEntry.append(replacement);
			}else{
				movieEntry.remove();
			}
		}
		String newHtml=doc.html();
		doc=Jsoup.parse(template);
		movieEntries=doc.getElementsByClass("movie");
		return newHtml;
	}

	private String fillTemplateWithMovie(String movieEntry,Movie movie){
		return movieEntry.replace("@name", movie.getName())
				.replace("@year", movie.getYear() + "")
				.replace("@doubanId", String.valueOf(movie.getDoubanId()))
				.replace("@director", movie.getDirector())
				.replace("@ratings", String.valueOf(movie.getRatings()))
				.replace("@poster", movie.getPoster());
	}
}
