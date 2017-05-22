package main;

import pipeLine.CurrentWorker;
import pipeLine.DecodingWorker;
import pipeLine.ExecutingWorker;
import pipeLine.FullingWorker;
import pipeLine.SaveWorker;

public class ThreadManager {
	private Executor[] executors = new Executor[4];
	private CurrentWorker[] currentWorkers = new CurrentWorker[4];
	private volatile boolean[] isWorkerFinished = new boolean[4];
	private volatile boolean exit;
	
	public ThreadManager() {
		this.exit = false;

		for(int i = 0; i < executors.length; i++) {
			this.executors[i] = new Executor(i, this);
			this.isWorkerFinished[i] = true;
			
			switch(i) {
				case 0 : 
					currentWorkers[0] = new FullingWorker();
					break;
				case 1 : 
					currentWorkers[1] = new DecodingWorker();
					break;
				case 2 : 
					currentWorkers[2] = new ExecutingWorker();
					break;
				case 3 : 
					currentWorkers[3] = new SaveWorker();
					break;
			}
		}
	}
	
	public CurrentWorker[] getCurrentWorker() {
		return this.currentWorkers;
	}
	
	public void workThread() {
		for(Executor e : this.executors) {
			e.start();
		}
		
		// 모든 워커들이 일이 끝나면 실행한다.
		
		while(!this.exit) {
			if(isAllWorkerReady()) {
				//모든 워커가 사용중이 아님을 선언한다.
				for(int i = 0; i < this.currentWorkers.length; i++) this.currentWorkers[i].setUsingFalse();

				// 다음 워커가 사용중이 아니라면 해당 워커를 세팅한다.
				for(Executor executor : this.executors) {
					if(!this.currentWorkers[executor.getNextWorkerIndex()].isUsing()) {
						executor.increaseWorkerIndex();
						executor.setCurrentWorker(this.currentWorkers[executor.getWorkerIndex()]);
						executor.getCurrentWorker().setUsingTrue();
					}
				}
				
				// 쓰레드 실행한다.
				for(int i = 0; i < this.executors.length; i++){
					this.isWorkerFinished[i] = false;
					this.executors[i].setReady(true);
				}
			}
		}
		if(this.exit) {
			for(Executor executor : this.executors) executor.stop();
			System.out.println("end!!!!!!!!!!!!");
		}
	}
	
	public synchronized void receiveFinishing(int index, boolean exit) {
		if(!isWorkerFinished[index]) {
			isWorkerFinished[index] = true;
		}
		this.exit = exit;
	}
	
	public synchronized boolean isAllWorkerReady() {
		for(boolean isFinished : isWorkerFinished) {
			if(!isFinished) return false;
		}
		return true;
		
	}
	

}
