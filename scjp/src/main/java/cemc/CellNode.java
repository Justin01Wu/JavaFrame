package cemc;

public class CellNode {
	Integer num;
	Integer x;
	Integer y;
	Integer product;
	CellNode parent;
	
	public String toString() {
		return num +", (" + x + "," + y + "),  " + product;
	}
	

}
