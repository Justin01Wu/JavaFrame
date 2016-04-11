package com.justa.jmx;

import org.apache.log4j.Logger;

public class MyThread implements Runnable{
	
	private static Logger log = Logger.getLogger(MyThread.class);
	
	public static final Object threadLocker = new Object();
	
	private int index;
	private int total;
	private int counter;
	private final SystemConfigMBean mBean;
	
	public MyThread(int index, int total, SystemConfigMBean mBean){
		this.index = index;
		this.total = total;
		this.mBean = mBean;
	}
    public void run() {        
    	
        // in case jconsole have time to connect this mbean, we design an infinite loop 
    	boolean stop = false;
        do{
            try {
				Thread.sleep(3000);  // use this to simulate long thread running
			} catch (InterruptedException e) {				
				log.debug(" catch an InterruptedException, ignore it.");			
			}
            
        	String msg =  String.format("thread %d /%d is running at count %d", index, total, counter);
        	counter++;
        	System.out.println(msg);
        	
            stop = (mBean.isStopped());
        }while(!stop);

    }

}
