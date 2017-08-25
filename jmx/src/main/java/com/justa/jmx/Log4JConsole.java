package com.justa.jmx;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * this is an advanced JMX sample to show you how to add class, method and parameter description on StandardMBean
 * @author justin.wu
 *
 */
public class Log4JConsole extends StandardMBean implements Log4JConsoleMBean{
	
	private static Logger log = Logger.getLogger(Log4JConsole.class);
	
	public Log4JConsole() throws NotCompliantMBeanException{
		super(Log4JConsoleMBean.class);
	}
	
	/** 
	 * Override StandardMBean method to add customized description for this class 
	 */
    @Override
    protected String getDescription(MBeanInfo mbeanInfo) {
        return "    Tool for view and adjust log level on class  ---Justin Wu";
    }

	/** 
	 * Override StandardMBean method to add customized description for some methods
	 */
    @Override
    protected String getDescription(MBeanOperationInfo mbeanOperationInfo) {

    	if (mbeanOperationInfo.getName().equals("getClassOrPackageLevel")) {
            return "get class or package log level";
        }
        if (mbeanOperationInfo.getName().equals("setClassOrPackageLevel")) {
            return "set class or package log level";
        }


        return super.getDescription(mbeanOperationInfo);
    }

	/** 
	 * Override StandardMBean method to add customized name for some parameter
	 */
    @Override
    protected String getParameterName(MBeanOperationInfo mbeanOperationInfo, MBeanParameterInfo mBeanParameterInfo, int sequence) {
    	
        if (mbeanOperationInfo.getName().equals("getClassOrPackageLevel")) {
        	if(sequence==0){
        		return "loggerPath";	
        	}
            
        }
        
        if (mbeanOperationInfo.getName().equals("setClassOrPackageLevel")) {
        	if(sequence==0){
        		return "loggerPath";	
        	}
        	if(sequence==1){
        		return "levelStr";	
        	}        	
        }
        return super.getParameterName(mbeanOperationInfo, mBeanParameterInfo, sequence);
    }
    
	/** 
	 * Override StandardMBean method to add customized description for some parameter
	 */
    @Override
    protected String getDescription(MBeanOperationInfo mbeanOperationInfo, MBeanParameterInfo mbeanParameterInfo, int sequence) {
    	
        if (mbeanOperationInfo.getName().equals("getClassOrPackageLevel")) {
        	if(sequence==0){
        		// first parameter of getClassOrPackageLevel method
                return "loggerPath can be package name or class full path, for example: " +
                      	 " com.justa.jmx.JmxApp " +
                   	 "or com.justa.jmx";
        	}
            
        }
        
        if (mbeanOperationInfo.getName().equals("setClassOrPackageLevel")) {
        	if(sequence==0){
        		// first parameter of setClassOrPackageLevel method
                return "loggerPath can be package name or class full path, for example: " +
                      	 "   com.justa.jmx.JmxApp " +
                   	 "   or com.justa.jmx";
        	}
        	if(sequence==1){
        		// second parameter of setClassOrPackageLevel method 
                return "must be one of those strings: " +
                      	 "TRACE, DEBUG, INFO, WARN, ERROR, FATAL";
        	}        	
        }

        return super.getDescription(mbeanOperationInfo, mbeanParameterInfo, sequence);
    }
    
	/**
	 * loggerPath can be package name or class full path, for example:
	 *   com.justa.jmx.JmxApp
	 *   or com.justa.jmx
	 */	
	@Override
	public String getClassOrPackageLevel(String loggerPath){	
		
		Logger logger = LogManager.getLogger(loggerPath); 
		if( logger == null){
			String  msg = "class or package not found: " + loggerPath;
			log.info(msg);
			return msg;
		}
		if(logger.getLevel() != null){
			return "real level: " + logger.getLevel().toString();
		}else{
			return "inherited level: " + logger.getEffectiveLevel().toString();
		}		
		
	}

	/**
	 * loggerPath can be package name or class full path, for example:
	 *   com.justa.jmx.JmxApp
	 *   or com.justa.jmx
	 *   levelStr :  must be one of TRACE, DEBUG, INFO, WARN, ERROR, FATAL
	 */
	@Override
	public String setClassOrPackageLevel(String loggerPath, String levelStr){
		
		Logger logger = LogManager.getLogger(loggerPath); 
		if(logger == null){
			String  msg = "class or package not found: " + loggerPath;
			log.info(msg);
			return msg;
		}
		
		if(levelStr == null){
			return "ERROR: need level";
		}
		
		Level level;
		if(levelStr.equalsIgnoreCase("TRACE")){
			level = Level.TRACE;
		}else if(levelStr.equalsIgnoreCase("DEBUG")){
			level = Level.DEBUG;
		}else if(levelStr.equalsIgnoreCase("INFO")){
			level = Level.INFO;
		}else if(levelStr.equalsIgnoreCase("WARN")){
			level = Level.WARN;
		}else if(levelStr.equalsIgnoreCase("ERROR")){
			level = Level.ERROR;
		}else if(levelStr.equalsIgnoreCase("FATAL")){
			level = Level.FATAL;
		}else{
			String  msg = "ERROR: incorrect level String, it must be one of TRACE, DEBUG, INFO, WARN, ERROR, FATAL";
			log.info(msg);
			return msg;
		}
		
		logger.setLevel(level);

		String msg =  String.format("successfully set level %s for %s", level, loggerPath);
		log.info(msg);
		return msg;

		
	}

}
