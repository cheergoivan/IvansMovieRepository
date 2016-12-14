package io.github.cheergoivan.movie;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;


public class MoviesReader {
	private final String[] pictureFormats={"jpg","png"};
	
	public List<Movie> readAll(String directory){
		List<Movie> result=new LinkedList<>();
		getFilesOfPattern(new File(directory),file->isPicture(file),file->{
			Movie movie=fileToMovie(file);
			if(movie!=null)
				result.add(movie);
			else
				System.out.println(file.getName()+" is not a correct movie name");
		});
		return result;
	}
	
	private void getFilesOfPattern(File root,FileFilter filter,Consumer<File> consumer) {
		File[] files=root.listFiles();
		for(File file:files){
			if(filter.accept(file)){
				consumer.accept(file);
			}
			if(file.isDirectory()){
				getFilesOfPattern(file,filter,consumer);
			}
		}
	}
	
	private Movie fileToMovie(File file){
		Movie m=new Movie();
		try{
			String[] movieParts=file.getName().substring(0, file.getName().lastIndexOf('.')).split("_");
			if(movieParts.length!=5)
				return null;
			m.setName(movieParts[0]);
			m.setYear(Integer.parseInt(movieParts[1]));
			m.setDirector(movieParts[2]);
			m.setRatings(Double.parseDouble(movieParts[3]));
			m.setDoubanId(Integer.parseInt(movieParts[4]));
			m.setPoster(file.getName());
		}catch(NumberFormatException e){
			return null;
		}
		return m;
	}
	
	private boolean isPicture(File f){
		for(String format:pictureFormats){
			if(f.getName().endsWith("."+format))
				return true;
		}
		return false;
	}
}
