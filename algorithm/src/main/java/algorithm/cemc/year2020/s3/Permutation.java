package algorithm.cemc.year2020.s3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// print all permutations of a given string. 
public class Permutation {
	
	private int length;
	private String str;
	private Set<String> all= new HashSet<>();;
	
	public static void main(String[] args) {
		String str = "ABCDE";
		Permutation permutation = new Permutation(str);
		Set<String> all = permutation.permute();
		for(String s: all) {
			System.out.println(s);
		}
		System.out.println(all.size());
	}

	public Permutation(String str){
		this.str = str;
		this.length = str.length();
	}
	
	public Set<String> permute(){
		permute(str, 0, length - 1);
		return all;
	}
	
	/**
	 * permutation function
	 * 
	 * @param str string to calculate permutation for
	 * @param l   starting index
	 * @param r   end index
	 */
	private void permute(String str, int l, int r) {
		if (l == r) {
			all.add(str);
		} else {
			for (int i = l; i <= r; i++) {
				str = swap(str, l, i);
				permute(str, l + 1, r);
				str = swap(str, l, i);
			}
		}
	}

	/**
	 * Swap Characters at position
	 * 
	 * @param a string value
	 * @param i position 1
	 * @param j position 2
	 * @return swapped string
	 */
	public String swap(String a, int i, int j) {
		char temp;
		char[] charArray = a.toCharArray();
		temp = charArray[i];
		charArray[i] = charArray[j];
		charArray[j] = temp;
		return String.valueOf(charArray);
	}
}
