package pipeLine;

import java.util.ArrayList;
import java.util.List;

public class DecodingWorker implements CurrentWorker{
	
	private volatile boolean isUsing;
	private volatile List<Integer> operands;	//피연산자
	private volatile List<String> operators;	//연산자
	private volatile boolean isLastCommand;
	
	public DecodingWorker() {
		this.isUsing = false;
		this.isLastCommand = false;
	}
	
	public synchronized boolean isLastCommand() {
		return this.isLastCommand;
	}

	@Override
	public synchronized void doWork(int index, CurrentWorker prevWorker) {
		System.out.println("DecodingWorker doWork - " + index);
		
		this.operands = new ArrayList<Integer>();
		this.operators =  new ArrayList<String>();
		
		// 명령어를 꺼내서 연산자와 피연산자로 구분하여 세팅한다.
		this.separateOperandAndOperator((FullingWorker) prevWorker);
		System.out.println("연산자 = " + operators.toString());
		System.out.println("피연산자 = " + operands.toString());
		
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
	
	public synchronized void separateOperandAndOperator(FullingWorker prevWorker) {
		String command = prevWorker.getCommandSet().getCommand();
		if(command != null) {
			String[] commandArray = command.split(",");
			/*for(int i = 0; i < commandArray.length; i++) {
				if((i + 2) % 2 == 0) this.operands.add(Integer.parseInt(commandArray[i]));	// 피연산자를 넣는다.
				else this.operators.add(commandArray[i]);	// 연산자를 넣는다.
			}*/
		}
	}
	
	

}
