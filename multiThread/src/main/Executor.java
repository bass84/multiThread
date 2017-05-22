package main;

import pipeLine.CurrentWorker;

public class Executor extends Thread{
	private volatile boolean ready;
	private volatile CurrentWorker currentWorker;
	private volatile int index;
	private volatile ThreadManager parent;
	private volatile int workerIndex;
	private volatile int commandFinishCount;
	
	public Executor(int index, ThreadManager parent) {
		this.ready = false;
		this.index = index;
		this.parent = parent;
		this.workerIndex = -1;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public int getNextWorkerIndex() {
		if(this.workerIndex != 3) return this.workerIndex + 1;
		else return 0;
	}
	
	public int getPrevWorkerIndex() {
		if(this.workerIndex != 0) return this.workerIndex - 1;
		else return 3;
	}
	
	public void increaseWorkerIndex() {
		if(this.workerIndex != 3) this.workerIndex++;
		else this.workerIndex = 0;
	}
	
	public int getWorkerIndex() {
		return this.workerIndex;
	}
	public void setNextWorker(CurrentWorker currentWorker) {
		this.currentWorker = currentWorker;
	}

	public CurrentWorker getCurrentWorker() {
		return currentWorker;
	}

	public void setCurrentWorker(CurrentWorker currentWorker) {
		this.currentWorker = currentWorker;
	}

	@Override
	public void run() {
		while(!this.isExit()) {
			while(!this.ready);
			
			if(this.currentWorker != null) {
				this.currentWorker.doWork(this.index, this.parent.getCurrentWorker()[this.getPrevWorkerIndex()]);
				
				if(this.currentWorker.isLastCommand()) this.commandFinishCount++;
			}
			
			this.ready = false;
			this.parent.receiveFinishing(this.index, this.isExit());
		}
	}
	
	public synchronized boolean isExit() {
		return this.commandFinishCount == 4;
	}
	
}
