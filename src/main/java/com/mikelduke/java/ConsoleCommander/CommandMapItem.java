package com.mikelduke.java.ConsoleCommander;

import java.lang.reflect.Method;

class CommandMapItem {
	private ConsoleCommand consoleCommand;
	private Method method;
	private Object object;
	private boolean isSingleton;
	
	CommandMapItem(ConsoleCommand consoleCommand, Method method, Object object, boolean isSingleton) {
		this.consoleCommand = consoleCommand;
		this.method = method;
		this.object = object;
		this.isSingleton = isSingleton;
	}
	
	ConsoleCommand getConsoleCommand() {
		return consoleCommand;
	}
	
	void setConsoleCommand(ConsoleCommand consoleCommand) {
		this.consoleCommand = consoleCommand;
	}
	
	Method getMethod() {
		return method;
	}
	
	void setMethod(Method method) {
		this.method = method;
	}
	
	Object getObject() {
		return object;
	}
	
	void setObject(Object object) {
		this.object = object;
	}
	
	boolean isSingleton() {
		return isSingleton;
	}
	
	void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}
}
