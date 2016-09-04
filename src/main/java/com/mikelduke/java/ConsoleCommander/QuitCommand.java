package com.mikelduke.java.ConsoleCommander;

public class QuitCommand {
	
	@ConsoleCommand(cmd={"quit", "exit"}, desc="Quit", hideFromHelp=true)
	public void quit() {
		System.out.println("Exit");
		
		System.exit(0);
	}
}
