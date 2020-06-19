// this comes from "Think in Java"
// every "blank final" field must be assigned on its ALL constructors  
package scjp.testbasic.flowcontrol;

public class BlankFinal {

    @SuppressWarnings("unused")
	private final int i = 0; // Initialized final
    
    @SuppressWarnings("unused")
	private final int j; // Blank final
    
    @SuppressWarnings("unused")
	private final String p; // Blank final reference
    
    // Blank finals MUST be initialized in the constructor:
    public BlankFinal() {
        j = 1; // Initialize blank final
        p = new String("ss"); // Initialize blank final reference
    }

    public BlankFinal(int x) {
        j = x; // Initialize blank final
        p = new String(""+x); // Initialize blank final reference
    }

    public static void main(String[] args) {
        new BlankFinal();
        new BlankFinal(47);
    }


}
