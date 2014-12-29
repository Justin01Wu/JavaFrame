package com.justa.jmx;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4JConsole implements Log4JConsoleMBean{
	
	private static Logger log = Logger.getLogger(Log4JConsole.class);
	
	private Class<?> getClass(String classPath){
		// classPath sample: com.validus.util.MessageLoggerTest
		
		if(classPath == null || classPath.isEmpty()){
			System.out.println("classPath is empty");
			return null;
		}		
		Class<?> clazz =  null;
		try {
			clazz  = Class.forName(classPath);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return clazz;
	}
	
	public String getAllLevels(){	
		
		Level level = Level.TRACE;
		
		StringBuilder sb = new StringBuilder();
		sb.append(level.toString()).append(" :").append(level.toInt());
		level = Level.DEBUG;
		sb.append("\n\r  ").append(level.toString()).append(": ").append(level.toInt());
		level = Level.INFO;
		sb.append("\n\r  ").append(level.toString()).append(": ").append(level.toInt());
		level = Level.WARN;
		sb.append("\n\r  ").append(level.toString()).append(": ").append(level.toInt());
		level = Level.ERROR;
		sb.append("\n\r  ").append(level.toString()).append(": ").append(level.toInt());
		level = Level.FATAL;
		sb.append("\n\r  ").append(level.toString()).append(": ").append(level.toInt());
		
		return sb.toString();
		
	}
	
	@Override
	public String getLevel(String classPath){	
		
		// classPath sample: com.validus.util.MessageLoggerTest
		Class<?> clazz =  getClass(classPath);
		if(clazz == null){
			return "class not found:" + classPath;
		}
		
		return LogManager.getLogger(clazz).getEffectiveLevel().toString();
		
	}
	
	@Override
	public int getLevelInt(String classPath){

		// classPath sample: com.validus.util.MessageLoggerTest
		Class<?> clazz =  getClass(classPath);
		if(clazz == null){
			return -1;
		}
		
		return LogManager.getLogger(clazz).getEffectiveLevel().toInt();
		
	}
	
	@Override
	public String setLevel(String classPath, int levelInt){
		

		// classPath sample: com.validus.util.MessageLoggerTest
		Class<?> clazz =  getClass(classPath);
		if(clazz == null){
			return "class not found: " + classPath;
		}
		Level level  = Level.toLevel(levelInt);
		
		LogManager.getLogger(clazz).setLevel(level);
		String msg =  String.format("successfully set level %s for %s", level, classPath);
		log.info(msg);
		return msg;
		
	}

}
