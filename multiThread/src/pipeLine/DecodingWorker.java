package pipeLine;

import java.util.ArrayList;
import java.util.List;

import command.CommandOrder;

public class DecodingWorker implements CurrentWorker{
	
	public enum Operator {
		addition("addition")
		, multiplication("multiplication")
		, division("division");
		
		private String value;
		
		Operator(String value) {
			this.value = value;
		}
		private String getValue() {
			return this.value;
		}
	}
	
	private volatile boolean isUsing;
	private volatile List<Integer> operands;	//피연산자
	private volatile List<String> operators;	//연산자
	private volatile boolean isLastCommand;
	private volatile CommandOrder commandOrder;
	
	public DecodingWorker() {
		this.isUsing = false;
		this.isLastCommand = false;
		this.commandOrder = new CommandOrder();
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
		this.operators = this.commandOrder.makeOperators((FullingWorker) prevWorker);
		this.operands = this.commandOrder.makeOperands((FullingWorker) prevWorker);
		
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
	
	

}
