package scjp.algorithm;

/**
 * A palindrome is a word, number, phrase, or other sequence of characters which reads the same backward as forward, 
 * such as madam, racecar.
 * this code counter any combination palindrome
 * like aaa will count as 6: a, a , a, aa, aa, aaa 
 *
 */
public class PalindromeCounter {
	
	public static void main(String argv[]){
		String input = "aaa";
		int result = countPalindromes(input);
		System.out.println(result);

    }

    public static int countPalindromes(String s) {
    	// Write your code here
    	int total = s.length();
    	for (int i = 0; i < s.length(); i++) {
    		for (int j = i+1; j < s.length(); j++) {
    			if(s.charAt(i) == s.charAt(j)) {
    				String sub = s.substring(i, j+1);
    				if(isPalindromes(sub)) {
    					System.out.println(sub);
    					total ++;
    				}
    			}
    		}
    	}
    	return total;
    }

	public static boolean isPalindromes(String s) {
		int max = s.length() / 2;
		for (int i = 0; i < max; i++) {
			char left = s.charAt(i);
			char right = s.charAt(s.length() - i - 1);
			if (left != right) {
				return false;
			}
		}
		return true;
	}
}
