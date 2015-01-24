// This shows the different override behave between c# and Java
//

package scjp.testclass.inherit;

class Base7{
	Base7(){
        System.out.println("Base7 construct function");
    }
    
	Base7(String a1){
        System.out.println("base constructor with a parameter");
    }
    
    
    void fun(){
        System.out.println("Base7 fun print");
    }
}

public class testoverride2 extends Base7{
    public testoverride2() {
        System.out.println("testoverride construct function");
    }
    
    public testoverride2(String a1) {
        System.out.println("testoverride constructor with a parameter");
    }
    
    
    void fun(){
        System.out.println("testoverride fun print");
    }
    
    public static void main(String[] args) {
        testoverride2 testoverride1 = new testoverride2();
        testoverride1.fun();
        Base7 b=(Base7)testoverride1;
        b.fun();// In fact this line will output "testoverride fun print"
        // this is different between c# and Java ,
        // If C# this line will output "base fun print"
        System.out.println("======================");
        testoverride2 testoverride2 = new testoverride2("aa");
        
    }
    
}
