package com.mikelduke.java.ConsoleCommander;

import java.util.Arrays;

public class HelpCommandFormatterImpl implements HelpCommandFormatter {

	@Override
	public String formatToString(ConsoleCommand consoleCommand) {
		return Arrays.toString(consoleCommand.cmd()) + "\t" + consoleCommand.desc() + "\t" + Arrays.toString(consoleCommand.args());
	}

	@Override
	public String getHeader() {
		return "Command\tDescription\tArgs";
	}

	@Override
	public String getFooter() {
		return "";
	}

}
