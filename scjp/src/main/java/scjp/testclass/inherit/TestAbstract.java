package scjp.testclass.inherit;


class mybase {
    //mybase(int i){}
	
    @SuppressWarnings("unused")
	private int getX(){return 1;}
}

abstract class Child extends mybase{

    //Child(int c){super(c);};
    Child(int c){}
    static int getX(){return 0;}
    //int getX(){return 99;}  // this is correct
    //static void getX(){}
    static native void getY();
    static native synchronized void getZ();

}

    public class TestAbstract{

        public static void main(String[] args) {
          //Child x=new Child(12);  // error:abstract class can't be initializeds
        }
    }
