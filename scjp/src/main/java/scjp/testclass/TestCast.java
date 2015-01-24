package scjp.testclass;

abstract  interface Test1 {  // it is correct
  public void test();
}

class Parent {}

public class TestCast extends Parent {
  public String getChild()
  {
           String name =  "child";
          return name;
  }

  public static void main(String argv[])
  {
          Parent p = new TestCast();
          //Place your code here.....
          // System.out.println(p.getChild());  // this is a compilation error
          //System.out.println((Parent)p.getChild()); // this is a compilation error
          System.out.println(((TestCast)p).getChild());
  }

}
