package io.github.cheergoivan.settings.settingValuePredicate;

import java.io.File;
import java.util.function.Predicate;

import io.github.cheergoivan.settings.GlobalSettings;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.FileUtil;
import io.github.cheergoivan.util.StringUtil;

public class WebPageThemePredicate  implements Predicate<Object>{
	
	@Override
	public boolean test(Object t) {
		boolean result=true;
		String theme=(String)t;
		String localRepoPath=Settings.localRepository.getValueAsString();
		String themePath=localRepoPath+GlobalSettings.themeDir+"/"+theme;
		File webPageThemeDir=new File(themePath);
		if(!Settings.localRepository.isSetted()){
			StringUtil.printError("please set local respository first");
			result=false;
		}else if(!webPageThemeDir.exists()||!webPageThemeDir.isDirectory()){
			StringUtil.printError("Theme "+theme+" doesn't exist! If you want to add a theme,"
					+ "put the theme in directory "+localRepoPath+GlobalSettings.themeDir);
			result=false;
		}else{
			if(!FileUtil.exists(webPageThemeDir,GlobalSettings.templateFileName)){
				StringUtil.printError("A theme must contain a file called "+GlobalSettings.templateFileName);
				result=false;
			}
		}
		return result;
	}

	
}
