package scjp.operator;

// negative int BinaryString
//  =  1 0000 0000 0000 0000 0000 0000 0000 0000((0) minus respective position int
// sample -8  :  8 ->  1000  that number(0) -1000 
// =11111111111111111111111111111000


public class BinaryInteger {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(0));
        System.out.println(Integer.toBinaryString(8));
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(Integer.toBinaryString(256));
        System.out.println(Integer.toBinaryString(-256));
        System.out.println(Integer.toBinaryString(255));
        System.out.println(Integer.toBinaryString(-255));
        System.out.println(Integer.toBinaryString(-254));
        System.out.println(Integer.toBinaryString(-253));
        
        int a = -5; int b = -2;
        System.out.println(a % b);  // ignore the sign of right-hand side
        System.out.println((-5) % 2);  // keep the sign of left-hand side
        System.out.println(5 % (-2));  // ignore the sign of right-hand side
    }
}
