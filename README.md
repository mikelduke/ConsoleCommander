# ConsoleCommander
Annotation based command line app framework for java

Adds a runtime for Java apps that can pick methods to run based on a ConsoleCommand annotation

## Example
@ConsoleCommand(cmd="doSomething", desc="This command does something", args={"a word"})
public void doSomething(String word) {
	System.out.println("Hi from doSomething. You entered " + word);
}
