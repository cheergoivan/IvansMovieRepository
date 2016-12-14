package io.github.cheergoivan.command;

import io.github.cheergoivan.commandImpl.ConfigSettingsCommand;
import io.github.cheergoivan.commandImpl.DisplaySettingsCommand;
import io.github.cheergoivan.commandImpl.HelpCommand;
import io.github.cheergoivan.commandImpl.MakeCommand;
import io.github.cheergoivan.commandImpl.PushCommand;

public enum Command {
	PUSH("push",PushCommand.class),
	SET("set",ConfigSettingsCommand.class),
	SETTINGS("settings",DisplaySettingsCommand.class),
	CREATE_MOVIE_REPO("make",MakeCommand.class),
	HELP("help",HelpCommand.class);
	
	
	private String commandText;
	private Class<? extends CommandExecutor> executor;
	
	Command(String commandText,Class<? extends CommandExecutor> executor){
		this.commandText=commandText;
		this.executor=executor;
	}
	
	public static CommandExecutor getCommand(String command){
		for(Command c:Command.values()){
			if(c.commandText.equals(command)){
				try {
					return c.executor.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
