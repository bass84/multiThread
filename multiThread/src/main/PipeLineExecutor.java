package main;

public class PipeLineExecutor {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		ThreadManager threadManager = new ThreadManager();
		threadManager.workThread();
		
		long end = System.currentTimeMillis();
		
		System.out.println("총 시간은 = " + (end - start));
	}
	
	
	
}
