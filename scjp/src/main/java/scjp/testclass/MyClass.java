
// Please note that a null reference may be used to access a class (static)
// variable without causing an exception.
// The static variable is called on the class itself and not on the object.

package scjp.testclass;

class MyClass {
    static String myName = "SCJP";
    
    MyClass getMyClass() {
        System.out.println(myName);
        return null;
    }
    
    public static void main(String[ ] args) {
        MyClass newClass=new MyClass();
        System.out.println( newClass.getMyClass().myName );
        newClass.xxx();
    }
    
    private void xxx(){
        System.out.println(MyClass.this.myName);
        //System.out.println(MyClass.null.myName);  //this is a compile error
        //System.out.println((MyClass)null.myName); //this is a compile error
        //System.out.println(null.myName);          //this is a compile error
    }
    
}
