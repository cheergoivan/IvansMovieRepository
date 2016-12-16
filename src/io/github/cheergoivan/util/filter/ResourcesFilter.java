package io.github.cheergoivan.util.filter;

import java.io.File;
import java.io.FileFilter;

public class ResourcesFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		return false;
	}
	
}
