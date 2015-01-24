package scjp.testclass;

//test final initial example:
public class TestFinalVar {
          final int a=(int)(Math.random()*10);
          static final int b=(int)(Math.random()*10);

          void prt(String tx){
          System.out.println("as to  "+tx+":"+"a="+a+",b="+b);}

         public static void main(String[] args){
           TestFinalVar t1=new TestFinalVar();
           TestFinalVar t2=new TestFinalVar();
           t1.prt("t1");
           t2.prt("t2");

           //b=12;  //error
           //a=13;  //error

           }
  }
