package io.github.cheergoivan.command;

import io.github.cheergoivan.commandImpl.ConfigSettingsCommand;
import io.github.cheergoivan.commandImpl.DisplaySettingsCommand;
import io.github.cheergoivan.commandImpl.HelpCommand;
import io.github.cheergoivan.commandImpl.PushCommand;

public enum Command {
	Push("push",new PushCommand()),
	Config("set",new ConfigSettingsCommand()),
	ShowConfig("settings",new DisplaySettingsCommand()),
	Help("help",new HelpCommand());
	
	
	private String commandText;
	private CommandExecutor commandExecutor;
	
	Command(String commandText,CommandExecutor commandExecutor){
		this.commandText=commandText;
		this.commandExecutor=commandExecutor;
	}
	
	public static CommandExecutor getCommand(String command){
		for(Command c:Command.values()){
			if(c.commandText.equals(command)){
				return c.commandExecutor;
			}
		}
		return null;
	}
}
