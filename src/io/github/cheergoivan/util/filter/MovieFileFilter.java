package io.github.cheergoivan.util.filter;

import java.io.File;
import java.io.FileFilter;

public class MovieFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		return pathname.getName().matches("(.*)_([\\d]+)_(.*)_([\\d]\\.[\\d])_([\\d]+)\\.(jpg|png)");
	}
	
}
