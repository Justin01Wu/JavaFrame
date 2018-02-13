package jdktest;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingTest {
	
	private static Logger LOG = Logger.getLogger(LoggingTest.class.getName());
	
    public static void main(String[] args) {
    	LOG.info("start main method...");
    	LOG.warning(" a warning...");
    	LOG.fine(" a fine message...");
    	LOG.finer(" a finer message...");
    	
    	Exception x = new RuntimeException("Jutin test exception");
    	
    	LOG.log( Level.SEVERE, x.toString(), x );
    }

}
