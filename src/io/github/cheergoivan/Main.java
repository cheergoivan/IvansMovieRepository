package io.github.cheergoivan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import io.github.cheergoivan.command.Command;
import io.github.cheergoivan.command.CommandExecutor;
import io.github.cheergoivan.commandImpl.HelpCommand;
import io.github.cheergoivan.settings.Settings;
import io.github.cheergoivan.settings.SettingsHandler;
import io.github.cheergoivan.util.GitUtil;
import io.github.cheergoivan.util.StringUtil;
import io.github.cheergoivan.webPageTheme.DefaultTheme;

public class Main {
	private static final String prompt=">>";
	private static final String exitCmd="exit";
	private static final HelpCommand help=new HelpCommand();
	
	public static void main(String[] args){
		initialize();
		help.execute(null);
		Scanner scanner=new Scanner(System.in);
		while(true){
			System.out.print(prompt);
			String input=scanner.nextLine();
			if(exitCmd.equals(input))
				break;
			String[] cmd=StringUtil.splitCmdArgs(input);
			if(cmd.length==0)
				continue;
			handleCmd(cmd);
		}
		scanner.close();
	}
	
	private static void handleCmd(String[] args){
		CommandExecutor command=Command.getCommand(args[0]);
		if(command==null){
			illegalCommand();
		}else{
			String[] arguments=Arrays.copyOfRange(args, 1, args.length);
			try{
				command.execute(arguments);
			}catch(IllegalArgumentException e){
				StringUtil.printError(e.getMessage());
			}
		}
	}
	
	
	private static void illegalCommand(){
		System.err.println("Command not exists,for more information, use help option.");
	}
	
	
	private static void initialize(){
		try {
			SettingsHandler.initialize();
		} catch (IOException e1) {
			StringUtil.printError("fail to initialize settings");
			System.err.println("Casued by:"+e1.getMessage());
			return;
		}
		
		try {
			DefaultTheme.initialize();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(Settings.remoteRepository.isSetted()&&Settings.localRepository.isSetted()){
			System.out.println("connect to remote repository...");
			try {
				GitUtil.disableOutputMessage();
				GitUtil.pullRepository(Settings.localRepository.getValueAsString());
			} catch (Exception e) {
				StringUtil.printError("fail to initialize localRepository");
				System.err.println("Casued by:"+e.getMessage());
				return;
			}finally{
				GitUtil.enableOutputMessage();
			}
			System.out.println("connect to remote repository successfully!");
		}
	}
}
