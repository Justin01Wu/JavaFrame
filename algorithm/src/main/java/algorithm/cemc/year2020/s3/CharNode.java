package algorithm.cemc.year2020.s3;

public class CharNode {
	
	char c;
	int hPos;
	int nPos;
	int left;
	int right;
	CharNode parent;
	
	int startHPos;
	String chain;
	
	String getStringChain() {
	
		CharNode node = this;
		int length = 1;
		int startHPos = Integer.MAX_VALUE;
		while (node != null) {
			length ++;
			if(node.hPos < startHPos) {
				startHPos = node.hPos; 
			}
			node = node.parent;
		}
		
		node = this;
		char[] chars = new char[length];
		while (node != null) {
			int hPos = node.hPos - startHPos; 
			chars[hPos] = node.c;
			node = node.parent;
		}
		
		this.startHPos =  startHPos;
		this.chain =  String.valueOf(chars);
		return this.chain;
		
	}

}
