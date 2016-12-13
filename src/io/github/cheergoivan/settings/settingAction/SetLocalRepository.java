package io.github.cheergoivan.settings.settingAction;

import java.io.File;
import java.util.function.Consumer;

import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.GitUtil;
import io.github.cheergoivan.util.StringUtil;

public class SetLocalRepository implements Consumer<Object>{

	@Override
	public void accept(Object t) {
		String localRepositoryPath=(String)t;
		try {
			GitUtil.cloneRepository(Settings.remoteRepository.getValueAsString(), localRepositoryPath);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static boolean isLocalRepositorySetted(){
		File file = new File(Settings.localRepository.getValueAsString());
		if (StringUtil.isEmpty(Settings.localRepository.getValueAsString())||!file.exists()) {
			return false;
		}
		return true;
	}

}
