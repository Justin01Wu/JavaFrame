package onlinetest;

import java.util.ArrayList;
import java.util.List;

// the famous Fibonacci numbers , but this one has a pattern, so can use pattern to directly get result
public class Kariakoo {
	
	private static List<Integer> list= new ArrayList<Integer>();
	private static List<Integer> steps= new ArrayList<Integer>();
	
	static{
		list.add(0);
		steps.add(0);
		list.add(1);
		steps.add(steps.get(0) + list.get(1));
		list.add(-2);
		steps.add(steps.get(1) + list.get(2));
		for(int i=2;i<10000000;i++){
			int nextValue = list.get(i)-list.get(i-1);
			list.add(i+1, nextValue);
			steps.add(steps.get(i) + list.get(i+1));
		}
	}
	
	static int getPositionAt(int n) {
		return steps.get(n);

	}
	
	public static void main(String[] args) {
		System.out.print(getPositionAt(100000));
	}
}
