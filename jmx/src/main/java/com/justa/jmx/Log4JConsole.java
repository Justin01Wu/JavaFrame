package com.justa.jmx;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4JConsole implements Log4JConsoleMBean{
	
	private static Logger log = Logger.getLogger(Log4JConsole.class);
	
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
