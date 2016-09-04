package com.mikelduke.java.ConsoleCommander;

public interface HelpCommandFormatter {
	public String getHeader();
	public String getFooter();
	public String formatToString(ConsoleCommand consoleCommand);
}
