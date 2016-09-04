package com.mikelduke.java.ConsoleCommander;

import com.mikelduke.java.ConsoleCommander.ConsoleCommand;

public class TestCommandsObject {
	
	@ConsoleCommand(cmd="1", desc="test method1")
	public void method1() {
		System.out.println("Hi from method1");
	}
	
	@ConsoleCommand(cmd="2", desc="test method2")
	public void method2() {
		System.out.println("Hi from method2");
	}
	
	@ConsoleCommand(cmd="3", desc="test method3")
	public void method3() {
		System.out.println("Hi from method3");
	}
	
	@ConsoleCommand(cmd="a1", desc="method with 1 arg", args={"echo value"})
	public void arg1(String argument) {
		System.out.println("Hi from arg1 " + argument);
	}
	
	@ConsoleCommand(cmd="sleep", desc="method with 1 arg")
	public void sleep1(String argument) throws InterruptedException {
		System.out.println("Hi from sleep " + argument);
		
		Thread.sleep(1000);
		
		System.out.println("End sleep");
	}
}
