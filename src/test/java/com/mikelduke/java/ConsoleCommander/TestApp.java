package com.mikelduke.java.ConsoleCommander;

import com.mikelduke.java.ConsoleCommander.ConsoleCommander;

/**
 * Test app using the ConsoleCommander in the same thread
 * 
 * @author Mikel
 *
 */
public class TestApp {
	
	public static void main(String[] args) {
		TestCommandsObject testObj = new TestCommandsObject();
		
		ConsoleCommander.setPrompt(">");
		ConsoleCommander.addCommandObject(testObj);
		
		System.out.println("Enter a command");
		ConsoleCommander.start();
	}
}
