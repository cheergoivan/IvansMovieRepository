package io.github.cheergoivan.settings.settingValuePredicate;

import java.io.File;
import java.util.function.Predicate;

import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.StringUtil;

public class LocalRepositoryPredicate  implements Predicate<Object>{

	@Override
	public boolean test(Object t) {
		String localRepositoryPath=(String)t;
		boolean result=false;
		File f=new File(localRepositoryPath);
		if(!f.exists()){
			StringUtil.printError(localRepositoryPath+" does not exist");
		}else if(!Settings.remoteRepository.isSetted()){
			StringUtil.printError("please set remote repository first");
		}else{
			result=true;
		}
		return result;
	}
}
