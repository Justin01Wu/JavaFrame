package scjp.testexception;

import java.io.IOException;

import javax.print.PrintException;


public class TestException {
    public static void main(String[] args) {
        try{        
            testXxx();
        }catch(Exception e){
            if(e.getCause()!=null){
                System.out.println("Caused By");
                e.getCause().printStackTrace();
            }else{
                e.printStackTrace();
            }
        }

        try{        
            testXxx2();
        }catch(Exception e){
            if(e.getCause()!=null){
                System.out.println("Caused By");
                e.getCause().printStackTrace();
            }else{
                e.printStackTrace();
            }
        }
        
        try{        
            testXxx3();
        }catch(Exception e){
            if(e.getCause()!=null){
                e.printStackTrace();
                System.out.println("Caused By");
                e.getCause().printStackTrace();
            }else{
                e.printStackTrace();
            }
        }
        
        
    }

    private static void testXxx()throws IOException{
        // has try block or no will produce total the same output
        //try{
            TestException.xxx();
        //}catch(IOException e){
        //    throw e;
        //}
  
    }    

   private static void testXxx2()throws IOException{
       try{
           TestException.xxx();
       }catch(IOException e){
           e.fillInStackTrace();
           throw e;
       }

  }
  
  private static void testXxx3()throws PrintException{
      try{
          TestException.xxx();
      }catch(IOException e){          
          throw new PrintException(e);
          //throw new Exception(e.getMessage());          
      }

  }  

  private static void xxx()throws IOException{
      throw new IOException("original exception");
    }
}
