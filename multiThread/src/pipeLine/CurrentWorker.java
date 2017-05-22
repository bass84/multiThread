package pipeLine;

public interface CurrentWorker {

	public void doWork(int index, CurrentWorker prevWorker);
	
	public boolean isUsing();

	public void setUsingTrue();
	
	public void setUsingFalse();
	
	public boolean isLastCommand();
}
