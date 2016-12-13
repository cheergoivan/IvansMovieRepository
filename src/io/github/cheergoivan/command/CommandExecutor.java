package io.github.cheergoivan.command;

public interface CommandExecutor {
	
	public void execute(String[] args) throws IllegalArgumentException;

}
