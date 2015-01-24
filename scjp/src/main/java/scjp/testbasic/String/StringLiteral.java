/*
What will happen when you attempt to compile and run the following code?
 1) Compile time error, System.out has no print method
 2) No output at runtime
 3) Output of 0
 4) Compile error, break cannot occur after its target label
 The answer is 3
*/

package scjp.testbasic.String;

public class StringLiteral {

    public static void main(String argv[]) {
        StringLiteral hh = new StringLiteral();
        hh.go();
    }

    public void go() {
        String har = new String("har");
        String ham = new String("har");
        collar:
        for (int i = 0; i < 2; i++) {
            if (har == ham) {
                break collar;   // it will NOT come here
            }
            if (i > 0) {
                continue collar;
            }

            System.out.print(i);
        }
    }
}
