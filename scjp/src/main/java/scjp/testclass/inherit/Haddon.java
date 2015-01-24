/*What will happen when you attempt to compile and run the following code?

 1) Compile time error, synchronized can only be used in a class that extends Thread
 2) Compile time error, malformed method getFireStation in class Hall.
 3) Compilation and output of opposite at runtime
 4) Compile time error, code within main method is incorrect.
 The answer is 3
*/
package scjp.testclass.inherit;

abstract class Hall{
    public abstract void getFireStation();
}

public class Haddon extends Hall{

    public static void main(String argv[]){
        new Haddon().getFireStation();

   }
// overriding method can add synchronized modifier
    public synchronized  void getFireStation(){
        System.out.print("opposite");
    }

}

