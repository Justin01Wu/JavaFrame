/* What will happen when you attempt to compile and run the following code?
  1) Comile time error, the first for statement is malformed
 2) Compile time error, the second for statement is malformed
 3) Output of 0 to 9 followed by a single output of "go"
 4) Output of 0 to 9 followed by constant output of "go"
 The answer is 4
 */

package scjp.testbasic.flowcontrol;
public class UnlimitedLoop{
    public static void main(String argv[]){
        UnlimitedLoop an = new UnlimitedLoop();
        an.go();
    }
    public void go(){
        int z=0;
        for(int i=0;i<10; i++,z++){
            System.out.println(z);
        }
        for(;;){
            System.out.println("go");
        }

    }
}

