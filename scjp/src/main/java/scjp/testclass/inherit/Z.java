package scjp.testclass.inherit;

// What will be printed when you execute the following code?
// the answer is YXYZ
// because (parent.construct) > (this variables initializing) >
// this.constructor
class X
{
        Y b = new Y();
        X()
        {
                System.out.print("X");
        }
}

class Y
{
        Y()
        {
                System.out.print("Y");
        }
}

public class Z extends X
{
        Y y = new Y();

        Z()
        {
                System.out.print("Z");
        }

        public static void main(String[] args)
        {
                new Z();
        }
}
