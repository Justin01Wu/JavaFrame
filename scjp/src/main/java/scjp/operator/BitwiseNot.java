/*
 What will be the result if you attempt to compile and run the following code ?
  1) Compile time error
 2) compilation and output of 11
 3) Compilation and output of -4
 4) Compilation and output of -5
 The answer is 4
 */
package scjp.operator;

public class BitwiseNot{
    public static void main(String argv[]){
        System.out.println(~4);
        // Bitwise NOT: Inverts each bit of the operand, so each 0 becomes 1 and vice vers
        // 100  -> 1111 1111 1111 1111 1111 1111 1111 1011
        // -(101) = -5
   }
}

