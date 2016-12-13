package io.github.cheergoivan.commandImpl;

import io.github.cheergoivan.command.CommandExecutor;

public class HelpCommand implements CommandExecutor{
	private static final String tab="\t";
	
	@Override
	public void execute(String[] args) throws IllegalArgumentException {
		System.out.println("usage:");
		System.out.println(tab+"set remoteRepository <remote Repository URL>");
		System.out.println(tab+"set localRepository <path of localRepository directory>");
		System.out.println(tab+"set webPageTemplate <directory of UI template>");
		System.out.println(tab+"set webPageTitle <title of the movie web page>");
		System.out.println(tab+"set moviesPerPage <the quantity of movies displayed on one page>");
		System.out.println(tab+"settings (display current settings)");
		System.out.println(tab+"push (push files of local repository to remote repository)");
		System.out.println(tab+"help (display all commands and usage)");
		System.out.println(tab+"exit (exit the program)");
	}

}
