package io.github.cheergoivan;


import io.github.cheergoivan.movie.MoviesReader;
import io.github.cheergoivan.util.FileUtil;
import io.github.cheergoivan.webPageTheme.TemplateResolver;

public class Test {
	private static final String testPath="C:\\workspace\\IvansMovieRepository\\template\\template.html";
	private static final String movieDir="C:\\Users\\i321035\\Desktop\\test";
	
	public static void main(String[] args){
		TemplateResolver r=new TemplateResolver(FileUtil.getFileContent(testPath));
		MoviesReader reader=new MoviesReader();
		System.out.println(r.fillMovies(reader.readAll(movieDir)));
	}
}
