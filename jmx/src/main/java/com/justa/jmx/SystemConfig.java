package com.justa.jmx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * this is a demo how to use ExecutorService to run multiple threads and let jmx to shutdown it
 *
 */
public class SystemConfig implements SystemConfigMBean {
	
		private static final Object threadLocker = new Object();
		private static SystemConfig mBean = new SystemConfig();
		
		private static ExecutorService executorService  = Executors.newFixedThreadPool(2);
		 
	    private boolean stopped = false;
	     
	    public static SystemConfig getInstance(){
	    	return mBean;
	    }
	    
	    public void startThreads(){
 			
			for(int i=0;i<10;i++){
				MyThread job = new MyThread(i, 10, mBean);   // created 10 threads, but pool size is only 2, so only 2 thread will be running
				executorService.execute(job);
			}

			executorService.shutdown();  // this shutdown stop executorService add more new tasks. 
	    }
	    
	    @Override
	    public void stopThreads(){	    	
	    	synchronized (threadLocker) {
	    		
	    		System.out.println("  ==>> set stopped to true to stop all threads");
	    		
	    		this.stopped=true;	// this will send a flag to running threads to let them quit gracefully
	    		executorService.shutdownNow();  // this will shutdown all waiting threads in this executorService
	    		
	    		// those two lines plus together will let app quit, other wise , app run for ever 
			}
	        	    	
	    }
	    
	    @Override
	    public boolean isStopped() {
	    	synchronized (threadLocker) {
	    		return this.stopped;
	    	}
	    }	 	    
}
