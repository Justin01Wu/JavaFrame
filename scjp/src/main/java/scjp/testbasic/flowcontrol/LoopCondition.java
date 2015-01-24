/*
Does this code:
A. Produce a runtime error
B. Produce a compile time error
C. Print out "Total 0"
D. Generate the following as output:
  i = 0 : j = 10
  i = 1 : j = 9
  i = 2 : j = 8
  Total 30

the Answer is C
  Because total=0 ,So condition total>30 is false, quit the loop
*/

package scjp.testbasic.flowcontrol;

public class LoopCondition {

    public static void main(String args[]) {

        int total = 0;
        for (int i = 0, j = 10; total > 30; ++i, --j) {
            System.out.println(" i = " + i + " : j = " + j);
            total += (i + j);
        }

        System.out.println("Total " + total);
    }

}
