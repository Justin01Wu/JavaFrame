package cemc.s3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CharPath {
	
	Map<Character, List<Integer>> charPosition;
	int total;
	int length;
	char[] needle;
	//Set<Integer> finishedEndPos= new HashSet<>();
	
	Set<String> finishedStr = new HashSet<>();
	
	CharPath(String needle, String hays){
		char[] chars = hays.toCharArray();
		charPosition = new HashMap<>();
		int i=0;
		for(char c: chars) {
			List<Integer> positions = charPosition.get(c);
			if(positions == null) {
				positions = new ArrayList<>();
				charPosition.put(c, positions);
			}
			positions.add(i);
			i++;			
		}
		this.length = needle.length();
		this.needle = needle.toCharArray();
		
		this.total=0;
	}
		
	void getTotalOcc( ) {	
		
		char c = needle[0];
		List<Integer> positions = charPosition.get(c);
		if(positions == null) {
			return;
		}

		for(Integer pos: positions) {
			CharNode n = new CharNode();
			n.nPos = 0;
			n.hPos = pos;
			n.c =  c;
			n.left = pos;
			n.right = pos;
			getTotalOcc(n);
		}

	}
	
	void getTotalOcc( CharNode node ) {		
		
		int nPos = node.nPos + 1;
		char c = needle[nPos];
		List<Integer> allPositions = charPosition.get(c);
		if(allPositions == null) {
			return;
		}
		List<Integer> positions = getValidRange(allPositions, node);
		if(positions.isEmpty()) {
			return;
		}

		for(Integer pos: positions) {
			CharNode n = new CharNode();
			n.parent = node;
			n.nPos = nPos;
			n.c =  c;
			n.hPos = pos;
			
			if(nPos == length-1) {
				String str = n.getStringChain();
				if(!finishedStr.contains(str)) {
					total ++;
					print(n);
					finishedStr.add(str);					
				}
				continue;
			}
			n.left = node.left;
			n.right = node.right;
			if(pos <n.left) n.left = pos;
			if(pos >n.right) n.right = pos;
			getTotalOcc(n);
		}
	}
	
	void print(CharNode node){
		
		while(node != null) {
			System.out.print(node.c);
			node =node.parent;
		}
		System.out.println();
	}
	
	static boolean isUsedPosition(int pos, CharNode node){
		while (node != null) {
			if(node.hPos == pos) {
				return true;
			}
			node = node.parent;
		}
		return false;
	}
	
	List<Integer> getValidRange(List<Integer> positions, CharNode node){
		List<Integer> list = new ArrayList<>();
		for(Integer n : positions) {
			if(isUsedPosition(n, node)) {
				continue;
			}
			if(n >= node.left && n<= node.right) {
				list.add(n);
			}else if(n < node.left) {
				if(node.right -n < this.length) {
					list.add(n);
				}
			}else {
				if( n-node.left < this.length) {
					list.add(n);
				}
			}
				
		}
		return list;
		
	}

}
