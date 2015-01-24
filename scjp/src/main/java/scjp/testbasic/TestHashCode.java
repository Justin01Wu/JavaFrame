package scjp.testbasic;

public class TestHashCode {
    
    private String s;
    TestHashCode(String s){
        this.s=s;
    }
    
    public boolean equals(Object o){
        if (! (o instanceof TestHashCode)) return false;
        TestHashCode target =(TestHashCode)o;
        if(this.s.toUpperCase().equals(target.s.toUpperCase()))
            return true;
        return false;
    }
    
    public static void main(String[] args) {
        TestHashCode t1 = new TestHashCode("abc");
        TestHashCode t2 = new TestHashCode("ABC");
        if(t1.equals(t2)){
            System.out.println(t1.hashCode());
            System.out.println(t2.hashCode());
            
            // hashcode is different, 
            // So it is correct code,but breaks some design rules of java
        }
    }
}
