package scjp.typesafe;

public class Enums {
	
	public static enum LightState3 {
		   RED, YELLOW, GREEN;
		    
		    // the following code is designed for storing enums in databases
		    //  it come from http://mindprod.com/jgloss/enum.html
		    public static final String RED_COLOR="Red";
		    public static final String YELLOW_COLOR="Yel";
		    public static final String GREEN_COLOR="Gre";
		    
		    public static LightState3 getInstance(String value) {
		        if(RED_COLOR.equals(value)){
		            return LightState3.RED; 
		        }
		        if(YELLOW_COLOR.equals(value)){
		            return LightState3.YELLOW; 
		        }
		        if(GREEN_COLOR.equals(value)){
		            return LightState3.GREEN; 
		        }        
		        
		        return null;
		    
		        
		    }
		    
		    //public static LightState3 valueOf(String value) {    
		    //    return null;
		    //}

	}

}
