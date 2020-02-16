package scjp.algorithm;

import java.util.Date;

/** the first PalindromeCounter2 may have performance when it has to handle a large string,
 * so this try to improve it
 *
 */
public class PalindromeCounter2 {
	public static void main(String argv[]){
		String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		
		Date since  = new Date();
		int result = countPalindromes(input);
		System.out.println(result);
		Date now = new Date();
		System.out.println(result);
		long cost = now.getTime() - since.getTime();
		System.out.println("cost(ms) = " +  cost);

//		boolean b = isPalindromes("abccba");
//		System.out.println(b);

    }
    public static int countPalindromes(String s) {
    	// Write your code here
    	byte[] bytes = s.getBytes();
    	int total = bytes.length;
    	for (int i = 0; i < bytes.length; i++) {
    		for (int j = i+1; j < bytes.length; j++) {
    			if(bytes[i] == bytes[j]) {
					//String sub = s.substring(i,j+1);
					//System.out.println(sub);
    				if(isPalindromes(bytes, i, j )) {
    					total ++;
    				}
    			}
    		}
    	}
    	return total;
    }

	public static boolean isPalindromes(byte[] s, int beg, int end) {
		int max = beg + (end-beg) / 2;
		int step = 0;
		for (int i = beg; i < max; i++) {
			byte left = s[i];
			byte right = s[end - step ];
			if (left != right) {
				return false;
			}
			step ++;
		}
		return true;
	}
}
