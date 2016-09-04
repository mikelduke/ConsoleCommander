package com.mikelduke.java.ConsoleCommander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleCommander implements Runnable {
    private static final String CLAZZ = ConsoleCommander.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLAZZ);
	
	private static ConsoleCommander instance = null;
	
	private static String prompt = ">";
	
	private List<Object> objectList = new ArrayList<Object>();
	private List<Class<?>> classList = new ArrayList<Class<?>>();
	
	private BufferedReader br;
	private boolean run = false;
	private boolean initialized = false;
	
	public static ConsoleCommander getInstance() {
		if (instance == null) {
			instance = new ConsoleCommander();
		}
		return instance;
	}
	
	public static void addCommandObject(Object o) {
		getInstance().objectList.add(o);
	}
	
	public static void addCommandObjects(List<Object> list) {
		getInstance().objectList.addAll(list);
	}
	
	public static void addCommandClass(Class<?> clazz) {
		getInstance().classList.add(clazz);
	}
	
	public static void addCommandClasses(List<Class<?>> list) {
		getInstance().classList.addAll(list);
	}
	
	public static void setPrompt(String newPrompt) {
		prompt = newPrompt;
	}
	
	public static void init() {
		if (!getInstance().initialized) {
			getInstance().initialized = true;
			
			getInstance().br = new BufferedReader(new InputStreamReader(System.in));
			
			addDefaultCommands();
			
			getInstance().run = true;
		}
	}
	
	public static void start() {
		if (!getInstance().initialized) {
			init();
		}
		getInstance().run();
	}
	
	private static void addDefaultCommands() {
		if (!ConsoleCommanderUtil.hasCommand("quit") && !ConsoleCommanderUtil.hasCommand("exit")) {
			addCommandObject(new QuitCommand());
		}
		
		if (!ConsoleCommanderUtil.hasCommand("help") && !ConsoleCommanderUtil.hasCommand("?")) {
			addCommandObject(new HelpCommand());
		}
	}
	
	private ConsoleCommander() { }
	
	List<Object> getObjectList() {
		return this.objectList;
	}
	
	List<Class<?>> getClassList() {
		return this.classList;
	}
	
	@Override
	public void run() {
		if (!getInstance().initialized) {
			throw new IllegalStateException(CLAZZ + " has not been initialized");
		}
		
		while (run) {
			System.out.print(prompt);
			
			if (!parseInput()) { //read input and end if error
				System.out.println("Error reading Console");
				run = false;
			}
		}
	}
	
	private boolean parseInput() {
		String input;
		
		try {
			input = br.readLine();
		} catch (IOException e) {
			LOGGER.logp(Level.SEVERE, CLAZZ, "parseInput", "Error Reading from Console", e);
			return false;
		}
		
		handleInput(input);
		
		return true;
	}
	
	private void handleInput(String input) {
		String[] parts = input.split(" ");
		
		if (parts == null || parts.length == 0) {
			LOGGER.logp(Level.SEVERE, CLAZZ, "handleInput", "Invalid Input", input);
			return;
		}
		
		String command = parts[0];
		
		Map<String, List<CommandMapItem>> cmdMap = ConsoleCommanderUtil.getCommandMap();
		List<CommandMapItem> commandMapItemList = cmdMap.get(command);
		if (commandMapItemList == null) {
			System.out.println("Command not found");
		} else {
			try {
				runCommandMapItem(commandMapItemList, input);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void runCommandMapItem(List<CommandMapItem> cmiList, String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//separate args string from cmd
		String[] inputAr = input.split(" ");
		String[] inputArgs = null;
		if (inputAr.length > 1) {
			inputArgs = Arrays.copyOfRange(inputAr, 1, inputAr.length);
			
			LOGGER.logp(Level.FINER, CLAZZ, "runCommandMapItem", "Input Args: " + Arrays.toString(inputArgs));
		}
		
		//find method with matching args count
		int argAmt = 0;
		if (inputArgs != null) {
			argAmt = inputArgs.length;
		}
		
		CommandMapItem cmi = findBestMethod(cmiList, argAmt);
		if (cmi == null) {
			System.out.println("Not enough parameters");
			
			return;
		}
		
		Object object = null;
		
		//see if object needs to be created
		if (cmi.isSingleton()) {
			Class<?> clazz = (Class<?>) cmi.getObject();
			object = clazz.newInstance();
		} else {
			object = cmi.getObject();
		}
		
		//copy to right sized arg array
		Object[] args = null;
		if (cmi.getMethod().getParameterCount() > 0) {
			args = Arrays.copyOfRange(inputArgs, 0, cmi.getMethod().getParameterCount());
			
			LOGGER.logp(Level.FINE, CLAZZ, "runCommandMapItem", "Args for invoke: " + Arrays.toString(args));
		}
		
		Object result = cmi.getMethod().invoke(object, args);
		
		LOGGER.logp(Level.FINE, CLAZZ, "runCommandMapItem", "Executed Command", result);
	}
	
	/**
	 * Attempts to find the best method to use when multiple commands are found.
	 * Selects the method by arguement count to find the one closest to the 
	 * arguements entered without going over.
	 * 
	 * @param cmiList
	 * @param argAmt
	 * @return
	 */
	private CommandMapItem findBestMethod(List<CommandMapItem> cmiList, int argAmt) {
		CommandMapItem foundCmi = null;
		
		for (CommandMapItem cmi : cmiList) {
			if (cmi.getMethod() == null) {
				continue;
			}
			
			if (cmi.getMethod().getParameterCount() == argAmt) {
				foundCmi = cmi;
				break;
			}
			
			if (cmi.getMethod().getParameterCount() <= argAmt) {
				if (foundCmi != null) {
					if (cmi.getMethod().getParameterCount() > foundCmi.getMethod().getParameterCount()) {
						foundCmi = cmi;
					}
				} else {
					foundCmi = cmi;
				}
			}
		}
		
		return foundCmi;
	}
}
