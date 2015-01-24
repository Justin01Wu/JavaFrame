// this comes from 
// http://java.sun.com/docs/books/jls/second_edition/html/names.doc.html#36191
// this show us :
//  method cannot access the protected members of its parameter , 
// because it is not involved in the implementation of a BaseA (the type of the parameter)

package scjp.testclass.inherit;

import anotherpackage.BaseA;


public class BaseAExt extends BaseA
{
   public void process(BaseA a)
   {
      //a.i = a.i*2;  // this is a compile error 
   }
   
   public void process2(BaseAExt a)
   {
      a.i = a.i*2;  // this can be compiled 
   }
   
   public static void main(String[] args)
   {
      BaseA a = new BaseAExt();
      BaseAExt b = new BaseAExt();
      b.process(a);
      System.out.println( a.getI() );
   }


}
