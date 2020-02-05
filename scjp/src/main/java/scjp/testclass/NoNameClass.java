package scjp.testclass;

interface Remote{
    public void  test();

}
public class NoNameClass{
    
    public static void main(String argv[]){
        NoNameClass m = new NoNameClass();
        m.go();
    }
    
    public void go(){
        //can create a object from an interface with implemented methods, 
    	//it is a no-name class
        Remote r = new Remote(){
        	
        	// need to implement all interface methods
            public void test(){
                System.out.println("run test method");
                System.out.println("name: " + this.getClass().getName());
                // will print:  scjp.testclass.NoNameClass$1
            } 
        };
        
        r.test();
    }
}

