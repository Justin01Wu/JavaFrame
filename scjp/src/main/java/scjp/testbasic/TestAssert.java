/*What will happen if you attempt to compile following code with assertions enabled?
 1) Compile time error, getRunningTotal does not return a boolean
 2) Compile time error malformed assert statement
 3) Compilation and output "noAssert" and assert error
  The answer is 3
*/
package scjp.testbasic;

public class TestAssert {

    int iRunningTotal = 0;

    public static void main(String argv[]) {
        TestAssert bb = new TestAssert();
        bb.go("1");
        bb.go("0");
    }

    public void go(String s) {
        int i = Integer.parseInt(s);
        setRunningTotal(i);
        assert (iRunningTotal > 0) : getRunningTotal();
        System.out.println("noAssert");
    }

    public String getRunningTotal() {
        return "Value of iRunningTotal " + iRunningTotal;
    }

    public int setRunningTotal(int i) {
        iRunningTotal += i;
        return iRunningTotal;
    }
}

