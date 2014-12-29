package com.justa.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

/**
 * This is a sample of JMX bean 
 * it comes from
 * http://www.journaldev.com/1352/what-is-jmx-mbean-jconsole-tutorial
 * 
 * please use jconsole to connect this app and watch the registered mbean
 *
 */
public class JmxApp {
	
	private static Logger log = Logger.getLogger(JmxApp.class);
	
    private static final int DEFAULT_THREAD_AMOUNT = 10;
    private static final String DEFAULT_SCHEMA = "default";
 
    public static void main(String[] args) throws Exception {
        
    	//Get the MBean server
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        
        log.info("please use jconsole to connect this app and watch the registered mbean");
        
        //register the SystemConfig MBean
        SystemConfig mBean = new SystemConfig(DEFAULT_THREAD_AMOUNT, DEFAULT_SCHEMA);        
        String domain = "com.justa.jmx";  
        String key = "type";
        String value = "SystemConfig";
        ObjectName objectName = new ObjectName(domain, key, value);
		if(server.isRegistered(objectName)){
			log.info("unregister old SystemConfig...");
			server.unregisterMBean(objectName);
		}
		server.registerMBean(mBean, objectName);
		
        //register the Log4JConsole MBean
		Log4JConsole log4jmBean = new Log4JConsole();
        value = "Log4jConsole";
        objectName = new ObjectName(domain, key, value);
		if(server.isRegistered(objectName)){
			log.info("unregister old Log4jConsole...");
			server.unregisterMBean(objectName);
		}
		server.registerMBean(log4jmBean, objectName);
        
        // in case jconsole have time to connect this mbean, we design an infinite loop 
    	boolean stop = false;
        do{
            Thread.sleep(3000);
            log.debug("I am JmxApp, I am alive...");
            log.trace("   Thread Count = " + mBean.getThreadCount() + " ::: Schema Name = " + mBean.getSchemaName());
            stop = (mBean.getThreadCount() ==0);
        }while(!stop);
        
        log.info("  ==>> JmxApp quit because Thread Count= 0");
        
         
    }
    
    
}
