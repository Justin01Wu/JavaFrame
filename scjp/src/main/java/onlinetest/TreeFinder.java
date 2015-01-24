package onlinetest;


public class TreeFinder {		 
    
    public static void main(String[] args){
    	Node small = new Node(7);
    	
    	Node left = new Node(6);
    	
    	Node right = new Node(9);
    	
    	small.left  = left;
    	small.right = right;
    	
    	System.out.print(small.findOnRecursion(9).getValue());
    	
    	Node root = new Node(20);
    	createDeepTree(root);
    	
    	System.out.print(root.findOnRecursion(256).getValue());

    }
    
    public static void createDeepTree(Node node){
    	if(node.getValue() >1){

    		Node left = new Node(node.getValue() -1);
    		node.left = left;
    		createDeepTree(left);
    	}
    	
    	if(node.getValue() <50){

    		Node right = new Node(node.getValue() +1);
    		node.right = right;
    		createDeepTree(right);
    	}
    }
    
    

}