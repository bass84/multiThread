package pipeLine;

import java.util.ArrayList;
import java.util.List;

public class DecodingWorker implements CurrentWorker{
	
	public enum Operator {
		addition("addition")
		, multiplication("multiplication")
		, division("division");
		
		private String value;
		
		private Operator(String value) {
			this.value = value;
		}
		public String getValue() {
			return this.value;
		}
	}
	
	private volatile boolean isUsing;
	private volatile List<Long> operands;	//피연산자
	private volatile Operator operator;	//연산자
	private volatile boolean isLastCommand;
	
	public DecodingWorker() {
		this.isUsing = false;
		this.isLastCommand = false;
	}
	
	public synchronized boolean isLastCommand() {
		return this.isLastCommand;
	}
	
	public synchronized List<Long> getOperands() {
		return this.operands;
	}
	
	public synchronized Operator getOperator() {
		return this.operator;
	}

	@Override
	public synchronized void doWork(int index, CurrentWorker prevWorker) {
		System.out.println("DecodingWorker doWork - " + index);
		
		this.operands = new ArrayList<Long>();
		
		this.separateOperandAndOperator((FullingWorker) prevWorker);
		/*System.out.println("연산자 = " + operator);
		System.out.println("피연산자 = " + operands.toString());*/
		
		this.isLastCommand = prevWorker.isLastCommand();
	}
	
	public void separateOperandAndOperator(FullingWorker prevWorker) {
		String command = prevWorker.getCommandSet().getCommand();
		if(command == null) return;
		
		String[] commandArray = command.split(",");
		for(int i = 0; i < commandArray.length; i++) {
			if((i + 2) % 2 == 0) this.operands.add(Long.parseLong(commandArray[i].trim()));	// 피연산자를 넣는다.
			else this.operator = (Operator.valueOf(commandArray[i]));	// 연산자를 넣는다.
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
