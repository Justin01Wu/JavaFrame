/*Given the following code what is the effect :
A) Generate a runtime error
B) Throw an ArrayIndexOutOfBoundsException
C) Print the values: 1, 2, 2, 4
D) Produces no output
 the answer is D
 */
package scjp.testbasic.flowcontrol;

public class TestLoop {
    public static void main(String[] args) {
        
        int a = 5;
        
        loop:for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                if (a == 5) {
                    break loop;
                }
                System.out.println(i * j);
            }
        }
    }
}
