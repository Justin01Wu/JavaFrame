/*What will happen when you attempt to compile and run the following code?
 1) Compile time error, a constructor cannot be marked as private
 2) Compilation and output of "Sam Smith"
 3) Compilation and output of "Coors"
 4) Compilation, but runtime error.
 The answer is 3
*/
package scjp.testbasic;

public class ChangeObjectValue{
    public static void main(String argv[]){
        new ChangeObjectValue();
    }
    private ChangeObjectValue(){
        String[] Sa = {"Sam Smith", "John Smith"};
        getName(Sa);
        System.out.print(Sa[0]);
    }

    public void getName(String s[]){
        s[0]="Coors";
    }
}

