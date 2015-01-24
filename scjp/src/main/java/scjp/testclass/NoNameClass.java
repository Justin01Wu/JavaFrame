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
        //here can put a interface, I don't understand
        // I guess it is a kind of no name class
        Remote r = new Remote(){
            public void test(){
                System.out.println("run test method");
                System.out.println("name: " + this.getClass().getName());
            } 
        };
        
        r.test();
    }
}

