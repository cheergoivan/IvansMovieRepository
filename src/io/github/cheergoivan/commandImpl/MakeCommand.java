package io.github.cheergoivan.commandImpl;

import java.io.File;
import java.io.IOException;

import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.core.MovieRepositoryGenerator;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.util.StringUtil;

public class MakeCommand implements CommandExecutor{

	@Override
	public void execute(String[] args) throws IllegalArgumentException {
		String localRepoPath = Settings.localRepository.getValueAsString();
		MovieRepositoryGenerator generator = new MovieRepositoryGenerator(new File(localRepoPath));
		try {
			generator.run();
		} catch (IOException e) {
			StringUtil.printError(e.getMessage());
		}
	}

}
