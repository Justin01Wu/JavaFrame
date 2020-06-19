
// Please note that a null reference may be used to access a class (static)
// variable without causing an exception.
// The static variable is called on the class itself and not on the object.

package scjp.testclass;

class StaticVariableOnNull {
    static String myName = "SCJP";
    
    StaticVariableOnNull getMyClass() {
        System.out.println(myName);
        return null;
    }
    
    @SuppressWarnings("static-access")
	public static void main(String[ ] args) {
        StaticVariableOnNull newClass=new StaticVariableOnNull();
        
        StaticVariableOnNull y = newClass.getMyClass();
        System.out.println( y.myName );  
        // here y is null, but we can get myName without exception
        
        newClass.print();
    }
    
    @SuppressWarnings("static-access")
	private void print(){
        System.out.println(StaticVariableOnNull.this.myName);
        //System.out.println(MyClass.null.myName);  //this is a compile error: MyClass cannot be resolved to a variable         
        //System.out.println(null.myName);          //this is a compile error: myName cannot be resolved or is not a field
        System.out.println(((StaticVariableOnNull)null).myName);
    }
    
}
