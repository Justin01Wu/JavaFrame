package algorithm.cemc.year2020.s4;


//from https://www.cemc.uwaterloo.ca/contests/computing/2020/ccc/seniorEF.pdf
/*

Problem S4: Swapping Seats
Problem Description
There are N people sitting at a circular table for a long session of negotiations. Each person
belongs to one of the three groups: A, B, or C. A group is happy if all of its members are sitting
contiguously in a block of consecutive seats. You would like to make all groups happy by some
sequence of swap operations. In each swap operation, two people exchange seats with each other.
What is the minimum number of swaps required to make all groups happy?

Input Specification
The input consists of a single line containing N (1 ≤ N ≤ 1 000 000) characters, where each
character is A, B, or C. The i-th character denotes the group of the person initially sitting at the i-th
seat at the table, where seats are numbered in clockwise order.
For 4 of the 15 available marks, the input has no C characters and N ≤ 5 000.
For an additional 4 of the 15 available marks, the input has no C characters.
For an additional 4 of the 15 available marks, N ≤ 5 000.

Output Specification
Output a single integer, the minimum possible number of swaps.

Sample Input
BABCBCACCA

Output for Sample Input
2

Explanation of Output for Sample Input
In one possible sequence, the first swap results in the seating layout AABCBCBCCA. After the
second swap, the layout is AABBBCCCCA.
*/		
public class SwapCalcator {
	private String data;
	
	Present p;
	
	public SwapCalcator(String data){
		this.data = data;
		p =  getAmount(data.toCharArray());
	}

	public static void main(String[] args) {
		String data = "AAABBCBBACAAC";
		//String data = "BCBCBCACCA";	
		
		SwapCalcator calcator = new SwapCalcator(data);
		int minSwap = calcator.calcSwap();
		
		System.out.println("result="+ minSwap);
		 
	}
	
	// please see here for theory:
	//  https://ccc.amorim.ca/docs/2020/s4/
	int calcSwap(){
		int minSwap = Integer.MAX_VALUE;
		
		for(int i=0;i<data.length();i++) {			
			
			// left rotate string one seat every time
			String str = data.substring(i) + data.substring(0, i);
			int total = calcSwapForOneStr(str);
			
			//System.out.println(total);	
			if(total <minSwap) {
				minSwap = total;
			}			
		}
		return minSwap;
	}
	
	private int calcSwapForOneStr(String data) {	
		
		int abcTotal = calSawpAmountOnABC(data);
		// ABC also means BCA, CAB, because the string itself rotate
		
		int acbTotal = calSawpAmountOnACB(data);		
		// ACB also means CBA, BAC, because the string itself rotate
		
		if(abcTotal <= acbTotal) {
			return abcTotal;
		}else {
			return acbTotal;
		}		
	}
	
	private int calSawpAmountOnABC(String data) {		
		int aLeft =0;
		int aRight = aLeft+ p.aAmount;
		
		int bLeft = aRight ;
		int bRight = bLeft + p.bAmount;

		int cLeft = bRight ;
		int cRight = cLeft+ p.cAmount;
		
		String a =  data.substring(aLeft, aRight);
		String b =  data.substring(bLeft, bRight);
		String c =  data.substring(cLeft, cRight);
		
		Present pa = getAmount(a.toCharArray());
		Present pb = getAmount(b.toCharArray());
		Present pc = getAmount(c.toCharArray());
		Present[] presents = {pa, pb, pc};
		
		int total = calSawpAmount(presents);
		return total;
	}
	
	private int calSawpAmountOnACB(String data) {		
		int aLeft =0;
		int aRight = aLeft+ p.aAmount;
		
		int cLeft = aRight ;
		int cRight = cLeft + p.cAmount;

		int bLeft = cRight ;
		int bRight = bLeft+ p.bAmount;
		
		String a =  data.substring(aLeft, aRight);
		String b =  data.substring(bLeft, bRight);
		String c =  data.substring(cLeft, cRight);
		
		Present pa = getAmount(a.toCharArray());
		Present pb = getAmount(b.toCharArray());
		Present pc = getAmount(c.toCharArray());
		Present[] presents = {pa, pb, pc};
		
		int total = calSawpAmount(presents);
		return total;
	}
	
	
	private static int calSawpAmount(Present[] presents) {		
		
		// swap ab
		int bInAGroup = presents[0].bAmount;
		int aInBGroup = presents[1].aAmount;
		SwapResult sawpAB = calSwap(bInAGroup, aInBGroup);
		
		// swap ac
		int cInAGroup = presents[0].cAmount;
		int aInCGroup = presents[2].aAmount;
		SwapResult sawpAC = calSwap(cInAGroup, aInCGroup);

		// swap bc
		int cInBGroup = presents[1].cAmount;
		int bInCGroup = presents[2].bAmount;
		SwapResult sawpBC = calSwap(cInBGroup, bInCGroup);
		
		int total  = sawpAB.total + sawpAC.total + sawpBC.total;
		int remain = sawpAB.remain + sawpAC.remain + sawpBC.remain;
		
		return total + (remain/3)*2;
		
	}
	
	private static SwapResult calSwap(int bInAGroup, int aInBGroup) {
		SwapResult result =  new SwapResult();
		if(bInAGroup >= aInBGroup) {
			result.remain = bInAGroup - aInBGroup;
			result.total =  aInBGroup;
		}else {
			result.remain = aInBGroup - bInAGroup;
			result.total = bInAGroup;	
		}
		return result;
	}
	
	private static Present getAmount(char[] chars) {
		Present present = new Present();
		for(char c : chars) {
			if(c =='A') {
				present.aAmount ++; 
			}else if(c =='B') {
				present.bAmount ++;
			}else {
				present.cAmount ++;
			}
		}
		return present;
	}
}
