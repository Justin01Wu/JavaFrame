package cemc.s3;

import java.util.Set;

public class NeedleSearch {
	
	public static void main(String[] args) {
		
		String needle ="aab";
		String hays = "abacabaa";

		int total  = getTotalOcc(needle, hays);
		System.out.println(total);
	}
	
	public static int getTotalOcc(String needle, String hays) {
		Permutation permutation = new Permutation(needle);
		Set<String> all = permutation.permute();
		
		int total = 0;
		for(String s: all) {
			if(hays.indexOf(s)>=0) {
				total ++;
			}
		}
		return total;
	}

}
