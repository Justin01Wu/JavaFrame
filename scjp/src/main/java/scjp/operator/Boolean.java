/*What is the result of executing the following fragment of code:
A) true is printed to standard out
B) false is printed to standard out
C) An exception is raised
D) Nothing happens
 the answer is A
*/
package scjp.operator;

public class Boolean {
    public static void main(String[] args) {

        boolean flag = false;
        if (flag = true) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
