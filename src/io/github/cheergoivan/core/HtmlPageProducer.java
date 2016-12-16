package io.github.cheergoivan.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import io.github.cheergoivan.movie.Movie;
import io.github.cheergoivan.settings.GlobalSettings;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.FileUtil;
import io.github.cheergoivan.webPageTheme.TemplateResolver;

public class HtmlPageProducer {
	private TemplateResolver resolver;
	private File localRepository;
	
	public HtmlPageProducer() throws IOException{
		String templatePath=Settings.localRepository.getValueAsString()
				+GlobalSettings.themeDir+"/"+Settings.webPageTheme.getValueAsString()
				+"/"+GlobalSettings.templateFileName;
		localRepository=new File(Settings.localRepository.getValueAsString());
		File templateFile=new File(templatePath);
		if(!templateFile.exists())
			throw new IOException("can not load current theme "+Settings.webPageTheme.getValueAsString()
			+" because file "+GlobalSettings.templateFileName+" is not found");
		resolver=new TemplateResolver(FileUtil.getFileContent(templatePath));
	}
	
	
	public void createMoviePages(List<Movie> movies) throws IOException{
		deleteHtmlPages();
		List<Movie> currentPageMovie=new ArrayList<>();
		int moviesPerPage=resolver.quantityOfMoviesRequired(),
				pageId=0;
		int lastPage=movies.size()/moviesPerPage+1;
		for(Movie m:movies){
			currentPageMovie.add(m);
			if(currentPageMovie.size()==moviesPerPage){
				pageId++;
				createHtmlFile(currentPageMovie,pageId,pageId==lastPage?1:pageId+1);
				currentPageMovie.clear();
			}
		}
		if(!currentPageMovie.isEmpty())
			createHtmlFile(currentPageMovie,lastPage,1);
	}

	private String fillWebPageTitle(String html){
		return html.replace("@webPageTitle", Settings.webPageTitle.getValueAsString());
	}
	
	private String fillNextPage(String html,int nextPage){
		return html.replace("@nextPage", String.valueOf(nextPage));
	}
	
	private void createHtmlFile(List<Movie> movies,int pageId,int nextPage) throws IOException{
		
		String html=resolver.fillMovies(movies);
		html=fillWebPageTitle(html);
		html=fillNextPage(html,nextPage);
		String newHtmlFilePath=localRepository.getAbsolutePath()+"/"+htmlFileName(pageId);
		File newFile = new File(newHtmlFilePath);
		if(!newFile.exists())
			newFile.createNewFile();
		Files.write(Paths.get(newHtmlFilePath),html.getBytes("utf-8"),StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("create "+htmlFileName(pageId));
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
