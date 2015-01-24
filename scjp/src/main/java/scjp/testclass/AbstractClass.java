package scjp.testclass;

public abstract class AbstractClass {
  abstract int getX() throws Exception;
  // private abstract int getY();  //An abstract method cannot be private
  // static abstract int getZ();//An abstract method cannot be static
  protected abstract int getA();
  abstract int getB();
  public abstract int getC();
}
