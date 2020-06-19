package scjp.testclass.inherit;

class thisbase{
    String getX(){
        System.out.println("thisbase.getX");
        return "thisbase.getX";
    }
    static String  getY(){
        System.out.println("thisbase.getY");
        return "thisbase.getY";
    }
    @SuppressWarnings("unused")
	private int getZ(){return 0;}
}

public class testOverride extends thisbase{
    
    //static String getX(){ //error
    // int getX(){  //error
    String getX(){
        System.out.println("testOverride.getX");
        return "testOverride.getX";
    }
    
    //String  getY(){    //error
    static String  getY(){
        System.out.println("testOverride.getY");
        return "testOverride.getY";
    }
    
    static int getZ(){return 1;} // Is this Override?  no
    
    public static void main(String[] args) {
        testOverride testoverride = new testOverride();
        testoverride.getX();
        testoverride.getY();
        
        thisbase base =new thisbase();
        base.getX();
        base.getY();
    }
}
