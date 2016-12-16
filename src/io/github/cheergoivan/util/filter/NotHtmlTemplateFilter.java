package io.github.cheergoivan.util.filter;

import java.io.File;
import java.io.FilenameFilter;

public class NotHtmlTemplateFilter implements FilenameFilter{
	
	@Override
	public boolean accept(File dir, String name) {
		if("header".equals(name)||"body".equals(name)||"footer".equals(name))
			return false;
		return true;
	}
	
}
