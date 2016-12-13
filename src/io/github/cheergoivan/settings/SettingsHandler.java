package io.github.cheergoivan.settings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SettingsHandler {
	private static String settingsFilePath=System.getProperty("user.dir")+"/movie_repository_settings";
	
	public static void initialize() throws IOException{
		File file=new File(settingsFilePath);
		if(!file.exists()||!file.isFile()){
			createSettingsFile();
		}
		fillGlobalSettings(readFile(settingsFilePath));
	}
	
	public static void set(Settings setting,String value)  throws IllegalArgumentException, IOException{
		if(!setting.getAssertValueType().test(value))
			throw new IllegalArgumentException("illegal setting value");
		Properties prop=readFile(settingsFilePath);
		prop.setProperty(setting.name(), value);
		saveSettingFile(prop);
		setting.setValue(value);
		setting.getSettingAction().accept(value);
	}
	
	public static void set(String key,String value) throws IllegalArgumentException, IOException{
		Settings setting=Settings.getSetting(key);
		if(setting==null)
			throw new IllegalArgumentException("illegal setting option");
		set(setting,value);
	}
	
	private static Properties readFile(String filePath) throws IOException {
		Properties props = new Properties();
		try(InputStream in= new BufferedInputStream(new FileInputStream(filePath))){
			props.load(in);
		}
		return props;
	}
	
	private static void createSettingsFile() throws IOException{
		Properties prop=new Properties();
		for(Settings setting:Settings.values()){
			prop.setProperty(setting.name(), setting.getValueAsString());
		}
		saveSettingFile(prop);
	}
	
	private static void saveSettingFile(Properties prop) throws FileNotFoundException, IOException{
		try(OutputStream out=new FileOutputStream(settingsFilePath)){
			prop.store(out, "update settings");
		}
	}
	
	private static void fillGlobalSettings(Properties prop){
		prop.forEach((k,v)->{
			Settings setting=Settings.getSetting((String)k);
			if(setting!=null){
				setting.setValue(v);
			}
		});
	}
}
