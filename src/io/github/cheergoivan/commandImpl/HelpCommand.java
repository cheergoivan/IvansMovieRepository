package io.github.cheergoivan.commandImpl;

import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.settings.GlobalSettings;

public class HelpCommand implements CommandExecutor{
	private static final String tab="\t";
	
	@Override
	public void execute(String[] args) throws IllegalArgumentException {
		System.out.println("usage:");
		System.out.println(tab+"set remoteRepository <remote Repository URL>");
		System.out.println(tab+"set localRepository <path of localRepository directory>");
		System.out.println(tab+"set webPageTheme <name of UI theme> (themes are in folder "+GlobalSettings.themeDir+")");
		System.out.println(tab+"set webPageTitle <title of the movie web page>");
		System.out.println(tab+"settings (display current settings)");
		System.out.println(tab+"make (generate web pages without pushing them to server)");
		System.out.println(tab+"push (generate web pages and push files of local repository to remote repository)");
		System.out.println(tab+"help (display all commands and usage)");
		System.out.println(tab+"exit (exit the program)");
	}

}
