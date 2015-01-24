package scjp.testclass.inherit;

class MyBase2{
    protected static int staticInt=-1;
    static private int staticHiddenInt=-2;
    int instanceInt=-3;
    private int instanceHiddenInt=-4;
    protected static String myName="";
    public void print(){
        System.out.println("staticInt="+staticInt);
        System.out.println("staticHiddenInt="+staticHiddenInt);
        System.out.println("instanceInt="+instanceInt);
        System.out.println("instanceHiddenInt="+instanceHiddenInt);
        System.out.println("myName="+myName);
    }
    public void set(){
        staticInt = -11;
        myName=this.getClass().getName();
    }
}

class Another extends MyBase2{

}
public class VariableInherit extends MyBase2 {
    protected static int staticInt=1;
    static private int staticHiddenInt=2;
    int instanceInt=3;
    private int instanceHiddenInt=4;

    public void print(){
        System.out.println("staticInt="+staticInt);
        System.out.println("staticHiddenInt="+staticHiddenInt);
        System.out.println("instanceInt="+instanceInt);
        System.out.println("instanceHiddenInt="+instanceHiddenInt);
        System.out.println("myName="+myName);
    }

    public static void main(String args[]){
        
        System.out.println("=======VariableInherit==============");
        VariableInherit child= new VariableInherit();        
        child.print();
        
        System.out.println("=======Another==============");
        Another another= new Another();        
        another.print();
        
        System.out.println("==========after Another.set===========");
        another.set();        
        another.print();
        
        System.out.println("==========VariableInherit again===========");
        child.print();
        
        System.out.println("==========MyBase2===========");
        new MyBase2().print();
        
    }

}
