package scjp.testclass;

/*Given the following code, how many objects will be eligible for garbage
collection on the line with the comment //here
1)   0
2)   1
3)   2
4)   3
5)   4

 The answer is 1) 0

 A reference passed into a method is passed as if it were a copy of a
 pointer pointer rather than the actual object. Thus if that reference
 is assigned to a null it makes no difference to any other copy of that
 pointer. Thus the code within the method findOut makes no difference to
 any other references. Although reference z is assigned to null reference
 y still points to the object so no objects are eligible for garbage collection.

*/

public class ReferencePass {
    public static void main(String argv[]){
        ReferencePass b =new ReferencePass();
        //System.out.println(b.toString());
    }
    
    public ReferencePass() {
        Integer x = new Integer(10);
        findOut(x);
        Integer y = new Integer(99);
        Integer z = y;
        z = null;
        findOut(y);
        //here
    }
    public void  findOut(Integer y){
        y = null;

    }

    //public static native void amethod(){}  // this has a error : native method cannot have a body
}

