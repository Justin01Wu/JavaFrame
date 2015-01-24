package scjp.testbasic.flowcontrol;

public class TestContinue {
    public static void main(String argv[]) {
        
        String myString;
        int x = 100;
        if (x < 100) {
            myString = "x is less than 100";
        }
        if (x > 100) {
            myString = "x is greater than 100";
        }
        //System.out.println(myString.length()); // this is an error
        // because myString might not have been initialized
        
        int i;
        int j;
        outer:for (i = 1; i < 3; i++) {
            inner:
                for (j = 1; j < 3; j++) {
                    if (j == 2) {
                        continue outer;
                    }
                    System.out.println(" i = " + i + "; j = " + j);
                }
        }
    }
}
