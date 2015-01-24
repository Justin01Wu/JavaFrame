package scjp.testclass.inherit;

class MyParent { }
class DerivedOne extends MyParent { }

public class DerivedTwo extends MyParent  {
  public static void main(String[] args) {
    MyParent p = new MyParent();

    if(p instanceof MyParent){
        System.out.println("p is instance of MyParent");
    }
    
    if(p instanceof Object){
        System.out.println("p is instance of Object");
    }    

    
    DerivedOne d1 = new DerivedOne();
    DerivedTwo d2 = new DerivedTwo();
    Object d3=new DerivedTwo();
    
    //d2 = d1;  //  this is a compile error: incompatible type
    
    //d1 = (DerivedOne)d2;  // this is a compile error: inconvertible type
    
    d1=(DerivedOne)d3;   
    // this is correctly compile because Object is the parent of DerivedOne
    // but it get run exception: ClassCastException
    
    
  }
}
