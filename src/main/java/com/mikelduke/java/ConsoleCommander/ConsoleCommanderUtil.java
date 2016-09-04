package com.mikelduke.java.ConsoleCommander;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConsoleCommanderUtil {
	
	static boolean hasCommand(String command) {
		List<ConsoleCommand> cmdList = getAllCommands();
		
		for (ConsoleCommand cmd : cmdList) {
			String[] cmds = cmd.cmd();
			
			for (String c : cmds) {
				if (c != null && c.equalsIgnoreCase(command)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	static List<ConsoleCommand> getAllCommands() {
		List<ConsoleCommand> cmdList = new ArrayList<ConsoleCommand>();
		
		if (ConsoleCommander.getInstance().getClassList() != null) {
			for (Class<?> clazz : ConsoleCommander.getInstance().getClassList()) {
				Method[] methods = clazz.getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null) {
						cmdList.add(annotation);
					}
				}
			}
		}
		
		if (ConsoleCommander.getInstance().getObjectList() != null) {
			for (Object o : ConsoleCommander.getInstance().getObjectList()) {
				Method[] methods = o.getClass().getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null) {
						cmdList.add(annotation);
					}
				}
			}
		}
		
		return cmdList;
	}
	
	static Map<String, List<CommandMapItem>> getCommandMap() {
		Map<String, List<CommandMapItem>> cmdMap = new HashMap<String, List<CommandMapItem>>();
		
		if (ConsoleCommander.getInstance().getClassList() != null) {
			for (Class<?> clazz : ConsoleCommander.getInstance().getClassList()) {
				Method[] methods = clazz.getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null) {
						for (String cmd : annotation.cmd()) {
							List<CommandMapItem> commandMapItemList = cmdMap.get(cmd);
							
							if (commandMapItemList == null) {
								commandMapItemList = new ArrayList<CommandMapItem>();
							}
							
							commandMapItemList.add(new CommandMapItem(annotation, m, clazz, true));
							cmdMap.put(cmd, commandMapItemList);
						}
					}
				}
			}
		}
		
		if (ConsoleCommander.getInstance().getObjectList() != null) {
			for (Object o : ConsoleCommander.getInstance().getObjectList()) {
				Method[] methods = o.getClass().getMethods();
				
				for (Method m : methods) {
					ConsoleCommand annotation = m.getAnnotation(ConsoleCommand.class);
					
					if (annotation != null) {
						for (String cmd : annotation.cmd()) {
							List<CommandMapItem> commandMapItemList = cmdMap.get(cmd);
							
							if (commandMapItemList == null) {
								commandMapItemList = new ArrayList<CommandMapItem>();
							}

							commandMapItemList.add(new CommandMapItem(annotation, m, o, false));
							cmdMap.put(cmd, commandMapItemList);
						}
					}
				}
			}
		}
		
		return cmdMap;
	}
}
