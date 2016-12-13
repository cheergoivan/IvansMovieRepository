package io.github.cheergoivan.commandImpl;

import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.settings.Settings;

public class DisplaySettingsCommand implements CommandExecutor{
	
	@Override
	public void execute(String[] args) throws IllegalArgumentException {
		if(args.length!=0)
			throw new IllegalArgumentException("illegal arguments");
		System.out.println("settings:");
		for(Settings setting:Settings.values()){
			if(setting.isVisible()){
				System.out.println("\t"+setting);
			}
		}
	}

}
