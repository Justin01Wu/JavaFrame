package onlinetest;

import java.util.HashSet;
import java.util.Set;

/** print all letter combination , for example: 
 *   given "abc", it will print "abc", "acb" "bca" "bac" "cba" 'cab"
 *   Also need to handle duplicate letters, like "abb" = "abb" 
 *   
 *   in order to handle duplicate letters, we change the return of combine() from 'List' to 'Set'
 *   
 *    in order to avoid StackOverFlowException, we should use Stack to replace recursive 
 *   
 *    
 */
public class LetterCombine {
	
	private Set<String> results = new HashSet<String>();
	
	public Set<String> getResults() {
		return results;
	}

	public static void main(String[] args) {
		
		//String str = "abb";
		String str = "abcc";
		
		Set<String> results = permutation(str);
		for(String oneLine: results){
			System.out.println(oneLine);
		}
		System.out.println("  == >> total: " + results.size());
		
		char chs[]={'a','b','c','d'};  
		new LetterCombine().permutation2(chs, 0);
		

	}
	
	/**
	 * use recursion to get all char combination
	 */
	public static Set<String> permutation(String str){
		
		Set<String> list = new HashSet<String>();
		
		if(str.length() == 1){
			// last char, so return with it
			list.add(str);
			return list;
		}
		for(int i=0; i<str.length(); i++){
			char currentChar = str.charAt(i);			
			
			String rest = str.substring(0, i) + str.substring(i + 1, str.length());
			Set<String> restCombines = permutation(rest);
			for(String oneLine: restCombines){
				list.add(currentChar + oneLine);
			}			
		}
		return list;
	}
	
	/**
	in order to improve performance, we can use char[] and swap position to avoid string calculation 
	*/    
    public void permutation2(char[]chars, int currentSwapPosition){  
        if(chars==null||currentSwapPosition<0 ||currentSwapPosition>chars.length){  
            return;  
        }  
        if(currentSwapPosition == chars.length){
            //System.out.println(new String(chars));  
            results.add(new String(chars));
        }else{  
            for(int j = currentSwapPosition; j<chars.length; j++){  
                
            	char temp = chars[j];   //交换前缀,使之产生下一个前缀  
                chars[j]=chars[currentSwapPosition];  
                chars[currentSwapPosition] = temp;  
                permutation2(chars,currentSwapPosition + 1);  
                
                temp = chars[j];    //将前缀换回来,继续做上一个的前缀排列.  
                chars[j]=chars[currentSwapPosition];  
                chars[currentSwapPosition] = temp;  
            }  
        }  
    } 
	
	

}
