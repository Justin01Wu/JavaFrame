package scjp.testbasic;

public class testInt {
    public static void main(String[] args) {
        int testValue = (int)(2.1F + 3.4D);
        System.out.println(testValue);
        testValue = (int)(5.5555F);
        System.out.println(testValue);
        testValue = (int)(5.8F);
        System.out.println(testValue);
        
        testValue = (int)(-5.8F);
        System.out.println(testValue);

        testValue = (int)(-5.1F);
        System.out.println(testValue);

        
    }
}
