package scjp.testbasic.String;

public class TestString2 {
    
    public static void main(String[] args) {
        String x = "String1";
        String y = "String1";
        
        if (x == y) {
            System.out.println("x==y");
        }
        
        if (x.equals(y)) {
            System.out.println("x equals y");
        }
        
        String x1 = "String1";
        
        String y1 = "String" ;
        y1=y1+"1";
        
        //y1=y1.intern();  // if add this line, x1==y1
        //y1 = "String" + "1";
        
        if (x1 == y1) {
            System.out.println("x1==y1");
        }
        
        if (x1.equals(y1)) {
            System.out.println("x1 equals y1");
        }
        
        String x2 =new String("String1");
        String y2 = new String("String1");
        
        if (x2 == y2) {
            System.out.println("x2==y2");
        }
        
        if (x2.equals(y2)) {
            System.out.println("x2 equals y2");
        }
        
        
        
    }
    
}
