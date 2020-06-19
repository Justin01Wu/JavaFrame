package onlinetest;

import java.util.Set;

import junit.framework.TestCase;

public class LetterCombineTest extends TestCase {

    public void  testPermutation(  ){
    	String str = "abcdefghi";
    	
		Set<String> results = LetterCombine.permutation(str);
		assertEquals(results.size(), 9*8*7*6*5*4*3*2);
//		for(String oneLine: results){
//			System.out.println(oneLine);
//		}
		System.out.println("  == >> total: " + results.size());
    }
    
    public void  testPermutationWithDuplicatedChar(  ){
    	String str = "abcdefgg";
    	
		Set<String> results = LetterCombine.permutation(str);
		assertEquals(results.size(), 8*7*6*5*4*3*2/2);
//		for(String oneLine: results){
//			System.out.println(oneLine);
//		}
		System.out.println("  == >> total: " + results.size());
    }
    
    public void  testPermutation2(  ){
    	char chs[]={'a','b','c','d' ,'e','f','g','h', 'i'};  
    	LetterCombine letterCombine = new LetterCombine();
		letterCombine.permutation2(chs, 0);
		assertEquals(letterCombine.getResults().size(), 9*8*7*6*5*4*3*2);
//		for(String oneLine: results){
//			System.out.println(oneLine);
//		}
		System.out.println("  == >> total: " + letterCombine.getResults().size());
    }

    public void  testPermutation2WithDuplicatedChar(  ){
    	char chs[]={'a','b','c','d' ,'e','f','g','g'};  
    	LetterCombine letterCombine = new LetterCombine();
		letterCombine.permutation2(chs, 0);
		assertEquals(letterCombine.getResults().size(), 8*7*6*5*4*3*2/2);
//		for(String oneLine: results){
//			System.out.println(oneLine);
//		}
		System.out.println("  == >> total: " + letterCombine.getResults().size());
    }
}
