package algorithm.cemc.year2020.s3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * 
 * Problem S3: Searching for Strings
Problem Description
You’re given a string N, called the needle, and a string H, called the haystack, both of which
contain only lowercase letters “a”..“z”.
Write a program to count the number of distinct permutations of N which appear as a substring of
H at least once. Note that N can have anywhere between 1 and |N|! distinct permutations in total
– for example, the string “aab” has 3 distinct permutations (“aab”, “aba”, and “baa”).

Input Specification
The first line contains N (1 ≤ |N| ≤ 200 000), the needle string.
The second line contains H (1 ≤ |H| ≤ 200 000), the haystack string.
For 3 of the 15 available marks, |N| ≤ 8 and |H| ≤ 200.
For an additional 2 of the 15 available marks, |N| ≤ 200 and |H| ≤ 200.
For an additional 2 of the 15 available marks, |N| ≤ 2000 and |H| ≤ 2000.

Output Specification
Output consists of one integer, the number of distinct permutations of N which appear as a substring of H.

Sample Input
aab
abacabaa

Output for Sample Input
2

*/
 
public class NeedleSearch3 {
	
	public static void main(String[] args) {
		
		String needle ="abc";
		String hays = "bcbac";
		
		int total = getTotalOcc(needle, hays);
		System.out.println("   total = " + total);
	}
	

	public static int getTotalOcc(String needle, String hays) {
		
		char[] nChars = needle.toCharArray();
		Map<Character, Integer> charOccs = setupCharOccus(nChars, nChars.length);
		
		char[] hChars = hays.toCharArray();
		Map<Character, Integer> hCharOccs = setupCharOccus(hChars, nChars.length);
		
		Set<String> finishedStr = new HashSet<>();
		
		int total = 0;
		for(int i=0;i<= hChars.length-nChars.length;i++) {
			if(compareTwoMaps(hCharOccs, charOccs)) {
				String result =  hays.substring(i,  i+nChars.length);
				if(!finishedStr.contains(result)) {
					//System.out.println(i + " "+ result);
					total ++;
					finishedStr.add(result);
					if(total%1000 ==0) {
						System.out.println(total);
					}
				}				 
			}
			
			if(i == hChars.length-nChars.length) {
				break;
			}
			// adjust hCharOccs for next char
			char oldOne = hChars[i];			
			Integer oldNum =  hCharOccs.get(oldOne);
			oldNum = oldNum -1;
			hCharOccs.put(oldOne, oldNum);
			
			char newOne = hChars[i+ nChars.length];
			Integer newNum =  hCharOccs.get(newOne);
			newNum = newNum + 1;	
			
			hCharOccs.put(newOne, newNum);
			
		}
		return total;		
	}
	
	static boolean compareTwoMaps(Map<Character, Integer> hCharOccs, Map<Character, Integer> nCharOccs) {
		if(hCharOccs.size() != nCharOccs.size()) {
			return false;
		}
		for(char c: hCharOccs.keySet()) {
			Integer l = hCharOccs.get(c);
			Integer r = nCharOccs.get(c);
			if(!r.equals(l) ) {
				return false;
			}

		}
		return true;
	}
	
	private static Map<Character, Integer> setupCharOccus(char[] chars, int nLength) {
		Map<Character, Integer> charOccs = new TreeMap<>();
		
		for(char c ='a'; c<= 'z' ;c++) {
			charOccs.put(c, 0);		
		}
		for(int i=0; i< nLength;i++) {
			char c = chars[i]; 
			Integer x = charOccs.get(c);
			x = x+1;				
			charOccs.put(c, x);		
		}
		return charOccs;
	}

}
