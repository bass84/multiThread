package pipeLine;

import java.util.List;

import pipeLine.DecodingWorker.Operator;

public class ExecutingWorker implements CurrentWorker{

	private volatile boolean isUsing;
	private volatile boolean isLastCommand;
	private volatile String result;
	
	public ExecutingWorker() {
		this.isUsing = false;
		this.isLastCommand = false;
	}
	
	public synchronized String getResult() {
		return this.result;
	}
	
	@Override
	public synchronized void doWork(int index, CurrentWorker prevWorker) {
		System.out.println("ExecutingWorker doWork - " + index);
		
		
		this.calculate((DecodingWorker) prevWorker);
		
		this.isLastCommand = prevWorker.isLastCommand();
	}
	
	public void calculate(DecodingWorker decodingWorker) {
		List<Long> operands = decodingWorker.getOperands();
		Operator operator = decodingWorker.getOperator() != null ? decodingWorker.getOperator() : null;
		this.result = "";
		
		System.out.println("연산자 = " + operator);
		System.out.println("피연산자 = " + operands);
		
		if(operands.size() > 0) {
			switch(operator) {
			case addition :
				this.result = operands.get(0) + " + " + operands.get(1) + " = " + operands.get(0) + operands.get(1);
				break;
			case multiplication :
				this.result = operands.get(0) + " * " + operands.get(1) + " = " + operands.get(0) * operands.get(1);
				break;
			case division :
				this.result = operands.get(0) + " / " + operands.get(1) + " = " + operands.get(0).doubleValue() / operands.get(1).doubleValue(); 
				break;
			}
		}
		
		System.out.println(this.result);
		
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
