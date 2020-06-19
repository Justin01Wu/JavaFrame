package scjp.testbasic;

public class TestArray {

  public static void main(String[] args) {
   
	  @SuppressWarnings("unused")
	  long[] x={12L,89L};
   
   int [] y={34,56,567,889};

   int[] z={4};
   //x=y;  //this is error
   z=y;
   System.out.println(z[3]);


  }

}
