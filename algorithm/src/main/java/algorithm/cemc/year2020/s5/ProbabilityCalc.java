package algorithm.cemc.year2020.s5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProbabilityCalc {
	
	int[] totalChoices;
	int joshChoice;
	int joshChoiceAmount ;
	Map<Integer, Integer> choiceMap;
	Set<String> usedKeys = new HashSet<>();

	public static void main(String[] args) {
		//int[] totalChoices = {1,2,3};		
		int[] totalChoices = {1,2,3, 1,1,2,3};
		//int[] totalChoices = {1,2,1};

		ProbabilityCalc c=  new ProbabilityCalc(totalChoices);
		double x= c.calProbability(null);
		System.out.println(x);	
		
	}
	
	public ProbabilityCalc(int[] totalChoices) {
		this.totalChoices = totalChoices;
		joshChoice = totalChoices[totalChoices.length-1]; 
		// set choice map
		choiceMap = new HashMap<>();
		for(int i = 0; i< totalChoices.length; i++  ) {
			Integer num = totalChoices[i];
			Integer choiceAmount = choiceMap.get(num);
			if(choiceAmount == null) {
				choiceMap.put(num, 1);
			}else {
				choiceMap.put(num, choiceAmount + 1);
			}
		}
		joshChoiceAmount = choiceMap.get(joshChoice);
	}
	
	private double calProbability( Choice previous ) {
		double p = 0.0d;
		
		int pIndex ;
		if(previous == null) {
			// coach choice
			pIndex = 0;
		}else {			
			pIndex = previous.order + 1;
		}		

		int newScope; 
		if(pIndex ==0) {
			// coach choose randomly
			newScope = totalChoices.length ;
		}else {
			int myjoshChoiceAmount = joshChoice(previous);  
			if( myjoshChoiceAmount == joshChoiceAmount ) {
				return 0.0d;
			}			
			if(pIndex == totalChoices.length -1) {
				// last choice
				StringBuffer sb = getKey(previous);
				if(usedKeys.contains(sb.toString())) {
					return 0.0d;
				}else {
					double cp = calcProbOnChain(previous);
					System.out.println(" : " + cp);
					usedKeys.add(sb.toString());
					return cp;					
				}
			}else {
				int myFavourite = totalChoices[pIndex];
				int total = choiceMap.get(myFavourite);

				int usedAmount = getMyFavouriteUsed(previous, myFavourite );
				if(usedAmount == total) {
					newScope = totalChoices.length - pIndex;
				}else {
					newScope = total - usedAmount;
				}
			}
		}


		Set<Integer> allUsed;
		if(previous == null) {
			// coach choice
			allUsed = new HashSet<>();;
		}else {
			allUsed = getAllUsed(previous, totalChoices);
		}
		for(int i= 0;i <totalChoices.length; i++ ) {
			if(allUsed.contains(i)) {
				continue;
			}
			Choice c = new Choice();
			c.choice = i;
			c.order= pIndex;
			c.previous =  previous;
			c.scope = newScope;
			double p2 =   calProbability(c);
			p = p + p2;
			
		}
		return p;
	}
	
	private double calcProbOnChain(Choice choice) {
		double p = 1.0d/choice.scope;
		System.out.print(totalChoices[choice.choice] + " ");
		//System.out.print(totalChoices[choice.choice] + "[" +choice.choice + "] " );
		if (choice.previous == null) {
			return p;
		}else {
			return p * calcProbOnChain(choice.previous);
		}
	}
	
	private StringBuffer getKey(Choice choice) {
		StringBuffer sb;
		if (choice.previous == null) {
			sb =  new StringBuffer();
		}else {
			sb = getKey(choice.previous);
		}
		sb.append(totalChoices[choice.choice]);
		return sb;
	}
	
	private static Set<Integer> getAllUsed(Choice choice, int[] totalChoices) {
		Set<Integer> used;
		if(choice.previous == null) {
			used = new HashSet<>();
			used.add(choice.choice);
		}else {
			used = getAllUsed(choice.previous, totalChoices);
			used.add(choice.choice);
		}
		return used;
	}
	
	private int joshChoice(Choice choice) {
		int amount = 0;
		int number = totalChoices[choice.choice];
		if(number == joshChoice) {
			amount ++;
		}
		if(choice.previous == null) {
			return amount;
		}
		int other = joshChoice(choice.previous);
		return amount + other;
	}
	
	private int getMyFavouriteUsed(Choice choice, int myFavourite  ) {
		
		int usedAmound = getAllOccOnNumber(choice, myFavourite);
		return usedAmound;
	}
	
	private int getAllOccOnNumber(Choice choice, int number) {
		int amount = 0;
		int num = totalChoices[choice.choice];
		if(num == number) {
			amount ++;
		}
		if(choice.previous == null) {
			return amount;
		}
		int other = getAllOccOnNumber(choice.previous, number);
		
		return amount + other;
	}

}
