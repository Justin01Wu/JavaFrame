package scjp.testclass.inherit;

abstract class Base{
     protected static String aStr="Base";
     String getStr(){
         //return aStr;
         return this.aStr;
         //return this.getStr();    // stack overflow
     }

     public void process(){
         if (this.getStr().equals("TestStaticVar")){
             System.out.println("getStr()==TestStaticVar");
         }
         if (aStr.equals("TestStaticVar")){
             System.out.println("aStr==TestStaticVar");
         }
         if (this.getStr().equals("Base")){
             System.out.println("getStr()==Base");
         }
         if (aStr.equals("Base")){
             System.out.println("aStr==Base");
         }         
         
     }
}

public class TestStaticVar extends Base {
     private static String aStr="TestStaticVar";

     public static void main(String[] args){
         TestStaticVar testStaticVar=new TestStaticVar();
         System.out.println(testStaticVar.getStr());
         System.out.println(aStr);
         testStaticVar.process();
     }
}
