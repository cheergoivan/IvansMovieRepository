package io.github.cheergoivan.commandImpl;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.core.MovieRepositoryGenerator;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.settings.SettingsHandler;
import io.github.cheergoivan.util.GitUtil;
import io.github.cheergoivan.util.StringUtil;

public class PushCommand implements CommandExecutor {
	private static final int attemptedAuthTimes = 3;

	@Override
	public void execute(String[] args) {
		if (!Settings.localRepository.isSetted()) {
			StringUtil.printError("LocalRepository doesn't exist");
			return;
		}
		boolean authFailed = true;
		String localRepoPath = Settings.localRepository.getValueAsString();
		for (int i = 0; i < attemptedAuthTimes && authFailed; i++) {
			try {
				if (needsCredential())
					requireCredential();
				MovieRepositoryGenerator generator = new MovieRepositoryGenerator(new File(localRepoPath));
				try {
					generator.run();
				} catch (IOException e) {
					StringUtil.printError(e.getMessage());
				}
				GitUtil.addAllChanges(localRepoPath);
				GitUtil.commitAll(localRepoPath, "update");
				GitUtil.push(localRepoPath, Settings.username.getValueAsString(), Settings.password.getValueAsString());
				authFailed = false;
			} catch (Exception e) {
				StringUtil.printError(e.getMessage());
				if (authenticateFailed(e)) {
					resetCredential();
				} else {
					authFailed = false;
				}
			}
		}
	}

	private void requireCredential() {
		Console console = System.console();
		String username = console.readLine("username:");
		char[] password = console.readPassword("password:");
		try {
			SettingsHandler.set(Settings.username, username);
			SettingsHandler.set(Settings.password, new String(password));
		} catch (IllegalArgumentException | IOException e) {
			StringUtil.printError(e.getMessage());
		}
	}

	private boolean authenticateFailed(Exception e) {
		return e.getMessage().contains("not authorized");
	}

	private boolean needsCredential() {
		return StringUtil.isEmpty(Settings.username.getValueAsString())
				|| StringUtil.isEmpty(Settings.password.getValueAsString());
	}

	private void resetCredential() {
		try {
			SettingsHandler.set(Settings.password, "");
			SettingsHandler.set(Settings.username, "");
		} catch (IllegalArgumentException | IOException e) {
			StringUtil.printError(e.getMessage());
		}
	}
}
