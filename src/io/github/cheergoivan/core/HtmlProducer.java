package io.github.cheergoivan.core;

import java.util.List;
import java.util.Scanner;

import io.github.cheergoivan.movie.Movie;
import io.github.cheergoivan.settings.Settings;

public class HtmlProducer {
	private static final String header="/template/header";
	private static final String body="/template/body";
	private static final String footer="/template/footer";
	private String headerTemplate;
	private String bodyTemplate;
	private String footerTemplate;
	
	
	public HtmlProducer(){
		headerTemplate=readResource(header);
		bodyTemplate=readResource(body);
		footerTemplate=readResource(footer);
	}
	
	public String produceHtmlPage(List<Movie> movies,int nextPage){
		StringBuilder sb=new StringBuilder();
		sb.append(produceHeader());
		for(Movie m:movies){
			sb.append(produceBody(m));
		}
		sb.append(produceFooter(nextPage));
		return sb.toString();
	}
	
	private String produceHeader(){
		return headerTemplate.replace("{{webPageTitle}}", Settings.webPageTitle.getValueAsString());
	}
	
	private String produceBody(Movie movie){
		return bodyTemplate.replace("{{name}}", movie.getName())
				.replace("{{year}}", movie.getYear() + "")
				.replace("{{doubanId}}", String.valueOf(movie.getDoubanId()))
				.replace("{{director}}", movie.getDirector())
				.replace("{{ratings}}", String.valueOf(movie.getRatings()))
				.replace("{{poster}}", movie.getPoster());
	}
	
	private String produceFooter(int nextPage){
		return footerTemplate.replace("{{nextPage}}", String.valueOf(nextPage));
	}
	
	
	private String readResource(String name){
		String content="";
		Scanner s=new Scanner(this.getClass().getResourceAsStream(name));
		s.useDelimiter("^");
		content=s.hasNext()? s.next():"";
		s.close();
		return content;
	}
}
