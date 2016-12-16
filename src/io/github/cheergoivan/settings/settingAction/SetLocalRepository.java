package io.github.cheergoivan.settings.settingAction;

import java.io.IOException;
import java.util.function.Consumer;

import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.GitUtil;
import io.github.cheergoivan.util.StringUtil;
import io.github.cheergoivan.webPageTheme.DefaultTheme;

public class SetLocalRepository implements Consumer<Object>{

	@Override
	public void accept(Object t) {
		String localRepositoryPath=(String)t;
		try {
			GitUtil.cloneRepository(Settings.remoteRepository.getValueAsString(), localRepositoryPath);
		} catch (Exception e) {
			StringUtil.printError(e.getMessage());
		}
		try {
			DefaultTheme.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
