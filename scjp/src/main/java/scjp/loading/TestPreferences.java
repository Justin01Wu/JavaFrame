package scjp.loading;

import java.util.Date;
import java.util.prefs.Preferences;

/**
 * Sometimes an Java application need to save data for next running
 * Environment variable only exists in current runtime, Java can't use it to pass data to the next running  
 * Saving into a file is another choice. But sometimes those data are key value data, file saving is costly.
 * Now we have another easy way to save key value data for next running, Java Preferences
 * Please see https://www.vogella.com/tutorials/JavaPreferences/article.html     
 *  or https://dev.to/argherna/the-java-preferences-api-is-a-little-thing-thats-a-huge-benefit-13ac
 */
public class TestPreferences {
	
	private static long defaultTs = -1;  

	public static void main(String[] args) {
		String prefScope =TestPreferences.class.getName();
		Preferences  prefs = Preferences.userRoot().node(prefScope);
		long lastTs = prefs.getLong("lastRunTime", defaultTs);
		if(lastTs > defaultTs) {
			Date last = new Date();
			last.setTime(lastTs);
			System.out.println("last run time: " + last);
		}else {
			System.out.println("didn't find last run time");
		}
		
		Date now = new Date();
		prefs.putLong("lastRunTime", now.getTime());	
		// it will be saved into windows registry Computer\HKEY_CURRENT_USER\Software\JavaSoft\Prefs if it is on windows platform
		
	}
	

}
