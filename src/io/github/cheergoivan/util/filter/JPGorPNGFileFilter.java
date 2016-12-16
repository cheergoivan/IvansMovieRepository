package io.github.cheergoivan.util.filter;

import java.io.File;
import java.io.FileFilter;

public class JPGorPNGFileFilter implements FileFilter{
	
	@Override
	public boolean accept(File pathname) {
		return pathname.getName().matches(".*\\.(jpg|png)");
	}
	
}
