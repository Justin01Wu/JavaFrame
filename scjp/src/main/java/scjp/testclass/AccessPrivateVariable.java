/*
The sample show :
An object can accesses the private variables of another object
 from the [same] class.

An object can [NOT] accesses the private variables of another object
 from the [different] class.

*/
package scjp.testclass;

public class AccessPrivateVariable {
    private int x;
    public AccessPrivateVariable(int x) {
        this.x=x;
    }

    public int getX(AccessPrivateVariable another){
        return another.x;
    }


        public static void main(String[] args) {
            AccessPrivateVariable a = new AccessPrivateVariable(12);
            AccessPrivateVariable b = new AccessPrivateVariable(34);
            AnotherClass c=new AnotherClass(56);
            System.out.println(b.getX(a));
            System.out.println(c.getX(a));

        }
}


    class AnotherClass{
        private int x;
        public AnotherClass(int x){
            this.x=x;
        }

        public int getX(AccessPrivateVariable another){
            //return another.x;  // This can't be complied because this is
            return 0;
        }
    }
