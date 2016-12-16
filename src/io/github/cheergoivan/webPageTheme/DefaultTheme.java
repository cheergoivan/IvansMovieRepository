package io.github.cheergoivan.webPageTheme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.github.cheergoivan.settings.GlobalSettings;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.settings.settingAction.SetWebPageTheme;
import io.github.cheergoivan.util.FileUtil;

public class DefaultTheme {
	public static void initialize() throws IOException{
		if(Settings.localRepository.isSetted()){
			File themeDir=new File(Settings.localRepository.getValueAsString(),GlobalSettings.themeDir);
			if(!themeDir.exists())
				themeDir.mkdir();
			String theme=GlobalSettings.defaultTheme;
			File defaultThemeDir=new File(themeDir,theme);
			if(!defaultThemeDir.exists()){
				defaultThemeDir.mkdir();
				for(String r:GlobalSettings.defaultThemeResource){
					File f=new File(r);
					File newFile=new File(defaultThemeDir,f.getName());
					Files.copy(FileUtil.getResourceFileContent(r), Paths.get(newFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
				}
				new SetWebPageTheme().accept(GlobalSettings.defaultTheme);
			}
		}
	}
}
