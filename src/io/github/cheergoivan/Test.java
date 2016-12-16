package io.github.cheergoivan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Test {
	private final static String filePath="C:\\workspace\\IvansMovieRepository\\template\\test";

	public static void main(String[] args) throws IOException{
		File file=new File("C:\\workspace\\IvansMovieRepository\\template\\test2");
		if(!file.exists()){
			//file.createNewFile();
		}
		Files.copy(Paths.get(filePath),Paths.get(file.toURI()),StandardCopyOption.REPLACE_EXISTING);
	}
}
