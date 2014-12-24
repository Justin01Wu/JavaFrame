package com.justa.jmx;

public class SystemConfig implements SystemConfigMBean {
		 
	    private int threadCount;
	    private String schemaName;
	     
	    public SystemConfig(int numThreads, String schema){
	        this.threadCount=numThreads;
	        this.schemaName=schema;
	    }
	     
	    @Override
	    public void setThreadCount(int noOfThreads) {
	    	System.out.println("  ==>> setThreadCount to " + noOfThreads);
	        this.threadCount=noOfThreads;
	    }	 
	 
	    @Override
	    public int getThreadCount() {
	        return this.threadCount;
	    }	 
	 
	    @Override
	    public void setSchemaName(String schemaName) {
	        this.schemaName=schemaName;
	    }
	 
	 
	    @Override
	    public String getSchemaName() {
	        return this.schemaName;
	    }
	     
	    @Override
	    public String doConfig(){
	    	System.out.println("  ==>> doConfig is called");
	        return "No of Threads="+this.threadCount+" and DB Schema Name="+this.schemaName;
	    }
}
