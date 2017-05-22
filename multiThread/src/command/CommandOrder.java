package command;

import java.util.ArrayList;
import java.util.List;

import pipeLine.DecodingWorker.Operator;
import pipeLine.FullingWorker;

public class CommandOrder {
	private List<Integer> operands;
	private List<String> operators;
	private boolean[] operandsOrder;
	private boolean[] operatorsOrder;
	
	public CommandOrder() {
		this.operands = new ArrayList<Integer>();
		this.operators = new ArrayList<String>();
	}
	
	public void initCommands(FullingWorker prevWorker) {
		this.separateOperandAndOperator(prevWorker);
		this.initOrders();
	}
	
	public void separateOperandAndOperator(FullingWorker prevWorker) {
		String command = prevWorker.getCommandSet().getCommand();
		
		if(command != null) {
			String[] commandArray = command.split(",");
			
			for(int i = 0; i < commandArray.length; i++) {
				if((i + 2) % 2 == 0) this.operands.add(Integer.parseInt(commandArray[i]));	// 피연산자를 넣는다.
				else this.operators.add(commandArray[i]);	// 연산자를 넣는다.
			}
		}
	}
	
	public void initOrders() {
		this.operandsOrder = new boolean[this.operands.size()];
		this.operatorsOrder = new boolean[this.operators.size()];
	}
	
	public synchronized List<String> makeOperators(FullingWorker prevWorker) {
		this.initCommands(prevWorker);
		
		
		return null;
	}
	
	public synchronized List<Integer> makeOperands(FullingWorker prevWorker) {
		
		return null;
	}
	
	public void orderOperators() {
		for(int i = 0; i < this.operators.size(); i++) {
			for(int j = 0; j < Operator.values().length; j++) {
				if(Operator.values()[0].name().equals("multiplication") || Operator.values()[0].name().equals("division")) {
					
				}
			}
		}
	}
	
	
}
