package pipeLine;

public class SaveWorker implements CurrentWorker{

	private volatile boolean isUsing;
	private volatile boolean isLastCommand;
	
	public SaveWorker() {
		this.isUsing = false;
		this.isLastCommand = false;
	}
	
	@Override
	public synchronized void doWork(int index, CurrentWorker prevWorker) {
		System.out.println("SaveWorker doWork - " + index);
		this.isLastCommand = prevWorker.isLastCommand();
		
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

	@Override
	public synchronized boolean isLastCommand() {
		return this.isLastCommand;
	}

}
