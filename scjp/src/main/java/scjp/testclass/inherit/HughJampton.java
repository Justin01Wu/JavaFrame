/*What will happen when you attempt to compile and run the following code?

 1) Compile time error, a constructor may not be marked private
 2) Compilation and output of HughJampton
 3) Compilation and output of HughJamptonRichmond
 4) Compilation and output of RichmondHughJampton
 5) Compilation, but runtime error
 The answer is 4
*/
package scjp.testclass.inherit;

class Richmond{

 Richmond(){   //   private Richmond(){ will result compile error
    System.out.print("Richmond");
 }
}
/**
 * test doclet 2223
 * @author justin.wu
 *
 */
public class HughJampton extends Richmond{
    private int i;
    public static void main(String argv[]){
        new HughJampton();
       }
    
    @SuppressWarnings("unused")
	private HughJampton(int i){
        this.i = i;

    }

   HughJampton(){
   System.out.print("HughJampton");

    }


}

