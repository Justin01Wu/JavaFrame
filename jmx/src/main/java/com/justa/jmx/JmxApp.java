package com.justa.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This is a sample of JMX bean 
 * it comes from
 * http://www.journaldev.com/1352/what-is-jmx-mbean-jconsole-tutorial
 * 
 * please use jconsole to connect this app and watch the registered mbean
 *
 */
public class JmxApp {
	
    private static final int DEFAULT_THREAD_AMOUNT = 10;
    private static final String DEFAULT_SCHEMA = "default";
 
    public static void main(String[] args) throws Exception {
        
    	//Get the MBean server
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        
        //register the MBean
        SystemConfig mBean = new SystemConfig(DEFAULT_THREAD_AMOUNT, DEFAULT_SCHEMA);
        
        String domain = "com.justa.jmx";  
        String key = "type";
        String value = "SystemConfig";
        ObjectName objectName = new ObjectName(domain, key, value);
		if(server.isRegistered(objectName)){
			System.out.println("unregister old SystemConfig...");
			server.unregisterMBean(objectName);
		}
		server.registerMBean(mBean, objectName);
        
        // in case jconsole have time to connect this mbean, we design an infinite loop 
    	boolean stop = false;
        do{
            Thread.sleep(3000);
            System.out.println("I am JmxApp, I am alive...");
            System.out.println("   Thread Count = " + mBean.getThreadCount() + " ::: Schema Name = " + mBean.getSchemaName());
            stop = (mBean.getThreadCount() ==0);
        }while(!stop);
        
        System.out.println("  ==>> JmxApp quit because Thread Count= 0");
        
         
    }
}
