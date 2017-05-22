package command;

import java.util.ArrayList;
import java.util.List;

public class CommandSet {

	private volatile List<String> commandList;
	private volatile int index = 0;
	private volatile boolean isLastCommand;
	
	public CommandSet() {
		this.commandList = new ArrayList<String>();
	}
	
	public synchronized int getCommandSetSize() {
		return this.commandList.size();
	}
	
	public synchronized void addCommand(String command) {
		if(command != null)
			this.commandList.add(command);
	}
	
	public synchronized List<String>getCommandList() {
		return this.commandList;
	}
	
	public synchronized boolean isLastCommand() {
		return this.isLastCommand;
	}
	
	public synchronized String getCommand() {
		if(this.commandList.size() > (this.index)) return this.commandList.get(this.index++);
		else if (this.commandList.size() <= (this.index) && this.commandList.size() > 0) {
			this.isLastCommand = true;
		}
		return null;
	}
	
	
}
