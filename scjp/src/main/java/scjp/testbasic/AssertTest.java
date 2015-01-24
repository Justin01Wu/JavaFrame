package scjp.testbasic;

// Error and its children are not Exception, So it doesn't need to follow the override rule

public class AssertTest {
    
    public void methodA(int i) throws AssertionError {
        assert i >0:"Invalid Value i must >0";
        try{
            assert i > 10:"Invalid Value i must >10";
        }catch(Exception e){}
        assert i > 10:"Invalid Value i must >10";
        
        if(i <= 1024 )
            throw new IllegalArgumentException("i out of range , i Must >1024");
    }
    
    public static void main(String args[]){
        AssertTest a=new AssertTest();
        a.methodA(1045);
        //a.methodA(1000);
        a.methodA(7);
        a.methodA(-7);
    }
    
    void methodB() throws LinkageError {
    }
    
    void methodC() {}
}

class override extends AssertTest {
    //public void methodA(int i)  {  // this is correct
    //public void methodA(int i) throws AssertionError{ //this is correct
    public void methodA(int i) throws Error { //this is correct
        //public void methodA(int i) throws Exception{  // This is error
    }
    
    //void method() { //this is correct
    //void method() throws ClassFormatError{//this is correct
    // void method() throws   Error{  //this is correct
    void method() throws  AssertionError{
    }
    
    //void methodC() throws Error{}  // this is correct
    //void methodC() throws Exception{}  // this is error
}
