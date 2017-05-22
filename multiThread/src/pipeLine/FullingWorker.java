package pipeLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import command.CommandSet;

public class FullingWorker implements CurrentWorker{
	private String filePath;
	private volatile CommandSet commandSet;
	private volatile boolean isUsing;
	private volatile boolean isLastCommand;
	
	public FullingWorker() {
		this.filePath = "./src/file/commands.txt";
		this.isUsing = false;
		this.commandSet = new CommandSet();
		this.isLastCommand = false;
	}
	
	public CommandSet getCommandSet() {
		return this.commandSet;
	}
	
	public synchronized boolean isLastCommand() {
		return this.isLastCommand;
	}
	
	@Override
	public synchronized void doWork(int index, CurrentWorker prevWorker){
		System.out.println("FullingWorker doWork - " + index);
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(this.filePath))) {
			int count = 0;

			while(reader.readLine() != null) {
				if(count == this.commandSet.getCommandSetSize()) {
					this.commandSet.addCommand(reader.readLine());
					this.isLastCommand = this.commandSet.isLastCommand();
					break;
				} else count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public synchronized boolean isUsing() {
		return this.isUsing;
	}
	
	@Override
	public synchronized void setUsingTrue() {
		this.isUsing = true;
	}
	
	@Override
	public synchronized void setUsingFalse() {
		this.isUsing = false;
	}
	
 }
