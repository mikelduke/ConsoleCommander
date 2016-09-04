package com.mikelduke.java.ConsoleCommander;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpCommand {
	private static HelpCommandFormatter formatter = new HelpCommandFormatterImpl();
	
	public static void setFormatter(HelpCommandFormatter newFormatter) {
		formatter = newFormatter;
	}
	
	@ConsoleCommand(cmd={"help", "?"}, desc="Show Help", hideFromHelp=true)
	public void showHelp() {
		List<String> helpList = getCommandInfo();
		
		Collections.sort(helpList);
		helpList.add(0, formatter.getHeader());
		helpList.add(helpList.size(), formatter.getFooter());
		for (String line : helpList) {
			System.out.println(line);
		}
	}
	
	private List<String> getCommandInfo() {
		List<String> helpList = new ArrayList<String>();
		
		if (ConsoleCommander.getInstance().getClassList() != null) {
			for (Class<?> clazz : ConsoleCommander.getInstance().getClassList()) {
				Method[] methods = clazz.getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null && !annotation.hideFromHelp()) {
						helpList.add(formatter.formatToString(annotation));
					}
				}
			}
		}
		
		if (ConsoleCommander.getInstance().getObjectList() != null) {
			for (Object o : ConsoleCommander.getInstance().getObjectList()) {
				Method[] methods = o.getClass().getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null && !annotation.hideFromHelp()) {
						helpList.add(formatter.formatToString(annotation));
					}
				}
			}
		}
		
		return helpList;
	}
}
