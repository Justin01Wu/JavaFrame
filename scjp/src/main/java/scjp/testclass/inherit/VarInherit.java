/*
What will happen when you attempt to compile and run the following code?

 1) Compile time error
 2) Compilation and output fo 3.5
 3) Compilation and output of 3
 4) Compilation but no output at runtime

 The answer is 1  :  possible loss precision 
*/
package scjp.testclass.inherit;

class TheBase{
    public int iAcres=3;
    
    public int getValue(){
        return iAcres;
    }
}
public class VarInherit extends TheBase{
    
    public int getValue(){
        return iAcres;
    }    
    
    private int iAcres=4;
    //public int iAcres=4;
    
    //private int iAcres=3.5; //in order to run other class, use the above line to replace it.

        public static void main(String args[]){

        TheBase mf = new VarInherit();
        System.out.println(mf.iAcres);  // the output is 3, because mf is set TheBase
        System.out.println(mf.getValue());  // the output is 4
        
        VarInherit mf2 = new VarInherit();
        System.out.println(mf2.iAcres);  // the output is 4
        System.out.println(mf2.getValue());  // the output is 4

        
    }
}

