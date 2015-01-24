/*
 What will be the result when you attempt to compile this program?
  1) Compile time error referring to a cast problem
 2) A random number between 1 and 10
 3) A random number between 0 and 1
 4) A compile time error about random being an unrecognised method
 The answer is 1
 */

package scjp.testbasic;

public class Rand{
    public static void main(String argv[]){
        int iRand;
        
        iRand = (int)Math.random();  // because this method returns double
   //  iRand = Math.random();  //in order to run other class, use the above line to replace it.
        
        System.out.println(iRand);
    }
}

