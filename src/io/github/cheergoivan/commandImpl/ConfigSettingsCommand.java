package io.github.cheergoivan.commandImpl;

import java.io.IOException;

import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.settings.SettingsHandler;

public class ConfigSettingsCommand implements CommandExecutor{

	@Override
	public void execute(String[] args) throws IllegalArgumentException {
		if(args.length!=2)	throw new IllegalArgumentException("illegal set command,if the setting's value contains blank character,please use \"\" wrapping the value");
		String arg1=args[0],arg2=args[1];
		try {
			SettingsHandler.set(arg1, arg2);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
