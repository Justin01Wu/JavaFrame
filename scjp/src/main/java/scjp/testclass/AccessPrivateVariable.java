/*
The sample show :
An object can accesses the private variables of another object
 from the [same] class.

An object can [NOT] accesses the private variables of another object
 from the [different] class.
 
IDE can directly set breakpoint on instance variable definition: 
   any read or write access from anywhere will stop for it 

*/
package scjp.testclass;

public class AccessPrivateVariable {
	private int x;

	public AccessPrivateVariable(int x) {
		this.x = x;
	}

	public int getX(AccessPrivateVariable another) {
		return another.x;
	}

	public static void main(String[] args) {
		
		AccessPrivateVariable a = new AccessPrivateVariable(12);
		AccessPrivateVariable b = new AccessPrivateVariable(34);		
		System.out.println(b.getX(a));  // actually it return a.x, not itself.x
		
		AnotherClass c = new AnotherClass(56);
		System.out.println(c.getY(a));

	}
}

class AnotherClass {
	private int y;

	public AnotherClass(int y) {
		this.y = y;
	}

	public int getY(AccessPrivateVariable another) {
		// return another.x; // This can't be complied because this is
		return y;
	}
}
