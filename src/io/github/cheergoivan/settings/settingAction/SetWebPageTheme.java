package io.github.cheergoivan.settings.settingAction;

import java.io.File;
import java.util.function.Consumer;

import io.github.cheergoivan.settings.GlobalSettings;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.FileUtil;
import io.github.cheergoivan.util.filter.MovieFileFilter;

public class SetWebPageTheme implements Consumer<Object>{

	@Override
	public void accept(Object t) {
		String theme=(String)t;
		String localRepoPath=Settings.localRepository.getValueAsString();
		MovieFileFilter movieFilter=new MovieFileFilter();
		//delete resources in local repository
		FileUtil.deleteFilesInDirectory(new File(localRepoPath),
				file->!movieFilter.accept(file)
				&&!".git".equals(file.getName())
				&&!GlobalSettings.themeDir.equals(file.getName()));
		String themePath=localRepoPath+GlobalSettings.themeDir+"/"+theme;
		//copy resource to local repository
		FileUtil.copyChildrenFiles(new File(themePath), 
				(f,n)->!n.equals(GlobalSettings.templateFileName), new File(localRepoPath));
		
	}
}
