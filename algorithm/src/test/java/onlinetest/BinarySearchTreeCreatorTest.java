package onlinetest;

import junit.framework.TestCase;

public class BinarySearchTreeCreatorTest extends TestCase {

    public void  testNotFound(  ){
		Node root = new Node(7);
		
		BinarySearchTreeCreator.bstree_insert(root, 5);
		BinarySearchTreeCreator.bstree_insert(root, 4);
		BinarySearchTreeCreator.bstree_insert(root, 6);
		BinarySearchTreeCreator.bstree_insert(root, 10);
		BinarySearchTreeCreator.bstree_insert(root, 9);
		
		Node result = root.findOnRecursion(11);
		assertEquals(result, null);
    }

    
    public void  testFoundLeft(){
		Node root = new Node(7);
		
		BinarySearchTreeCreator.bstree_insert(root, 5);
		BinarySearchTreeCreator.bstree_insert(root, 4);
		BinarySearchTreeCreator.bstree_insert(root, 6);
		BinarySearchTreeCreator.bstree_insert(root, 10);
		BinarySearchTreeCreator.bstree_insert(root, 9);
		
		Node result = root.findOnRecursion(4);
		assertEquals(result.toString(), "4");
		
		result = root.findOnStack(4);
		assertEquals(result.toString(), "4");
    }
    
    public void  testFoundRoot(){
    	
		Node root = new Node(7);
		
		BinarySearchTreeCreator.bstree_insert(root, 5);
		BinarySearchTreeCreator.bstree_insert(root, 4);
		BinarySearchTreeCreator.bstree_insert(root, 6);
		BinarySearchTreeCreator.bstree_insert(root, 10);
		BinarySearchTreeCreator.bstree_insert(root, 9);
		
		Node result = root.findOnRecursion(7);
		assertEquals(result.toString(), "7");
		
		result = root.findOnStack(7);
		assertEquals(result.toString(), "7");
    }
    
    public void  testFoundRight(){
		Node root = new Node(7);
		
		BinarySearchTreeCreator.bstree_insert(root, 5);
		BinarySearchTreeCreator.bstree_insert(root, 4);
		BinarySearchTreeCreator.bstree_insert(root, 6);
		BinarySearchTreeCreator.bstree_insert(root, 10);
		BinarySearchTreeCreator.bstree_insert(root, 9);
		
		Node result = root.findOnRecursion(9);
		assertEquals(result.toString(), "9");
		
		result = root.findOnStack(9);
		assertEquals(result.toString(), "9");
    }
    
    public void  testFoundLeftRight(){
		Node root = new Node(7);
		
		BinarySearchTreeCreator.bstree_insert(root, 5);
		BinarySearchTreeCreator.bstree_insert(root, 4);
		BinarySearchTreeCreator.bstree_insert(root, 6);
		BinarySearchTreeCreator.bstree_insert(root, 10);
		BinarySearchTreeCreator.bstree_insert(root, 9);
		
		Node result = root.findOnRecursion(6);
		assertEquals(result.toString(), "6");
		
		result = root.findOnStack(6);
		assertEquals(result.toString(), "6");
    }
    
    public void  testInsertDuplicateValue(){
		Node root = new Node(7);
		
		boolean insertFlag;
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 5);
		assertEquals(insertFlag, true);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 4);
		assertEquals(insertFlag, true);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 6);
		assertEquals(insertFlag, true);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 10);
		assertEquals(insertFlag, true);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 9);
		assertEquals(insertFlag, true);
		
		Node result = root.findOnRecursion(6);
		assertEquals(result.toString(), "6");
		
		result = root.findOnStack(6);
		assertEquals(result.toString(), "6");
		
		
		// now insert duplicate value
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 7);
		assertEquals(insertFlag, false);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 5);
		assertEquals(insertFlag, false);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 4);
		assertEquals(insertFlag, false);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 6);
		assertEquals(insertFlag, false);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 10);
		assertEquals(insertFlag, false);
		
		insertFlag = BinarySearchTreeCreator.bstree_insert(root, 9);
		assertEquals(insertFlag, false);
		
		result = root.findOnRecursion(6);
		assertEquals(result.toString(), "6");
		
		result = root.findOnStack(6);
		assertEquals(result.toString(), "6");
    }
    
    public void  testHugeTreeOnRecursion(){
    	
    	int rootValue = 50000;
    	Node root = new Node(rootValue);
    	
    	boolean insertFlag;
    	
    	for(int i=1; i<= 20000;i++){
    		int newLeftValue = rootValue -i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newLeftValue);
    		assertEquals(insertFlag, true);
    		
    		int newRightValue = rootValue + i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newRightValue);
    		assertEquals(insertFlag, true);
    	}
    	try{
    		Node result  = root.findOnRecursion(34956);
    		fail("should throw StackOverflowError");
    	}catch(StackOverflowError e){
    		
    	}
    	
    }
    
    public void  testHugeTreeOnStack(){
    	
    	int rootValue = 50000;
    	Node root = new Node(rootValue);
    	
    	boolean insertFlag;
    	
    	for(int i=1; i<= 20000;i++){
    		int newLeftValue = rootValue -i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newLeftValue);
    		assertEquals(insertFlag, true);
    		
    		int newRightValue = rootValue + i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newRightValue);
    		assertEquals(insertFlag, true);
    	}

   		Node result  = root.findOnStack(34956);
		assertEquals(result.toString(), "34956");
    	
    }
    
    public void  testCreateHugeTree(){
    	
    	int rootValue = 50000;
    	Node root = new Node(rootValue);
    	
    	boolean insertFlag;
    	
    	for(int i=1; i<= 20000;i++){
    		int newLeftValue = rootValue -i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newLeftValue);
    		assertEquals(insertFlag, true);
    		
    		int newRightValue = rootValue + i;
    		insertFlag = BinarySearchTreeCreator.bstree_insert(root, newRightValue);
    		assertEquals(insertFlag, true);
    	}

   		//Node result  = root.findOnStack(34956);
		//assertEquals(result.toString(), "34956");
    	
    }
}
