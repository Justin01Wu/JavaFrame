/*
An overriding method can throw an exception
 which original method didn't.

The method must not throw checked exceptions of classes
 that are not possible for the original method.

 EOFException and FileNotFoundException are all subclasss of IOException.
*/

package scjp.testclass.inherit;
import java.io.*;

class overridebase{
    int x;
    int getX() throws IOException{
        System.out.println("run to here..base.getX");
        if (x>2) return x;
        else throw new EOFException("x must >12");
        //return 0;
    }

    void setX(int x){
        System.out.println("run to here..base.setX");
        this.x=x;
    }
}

public class TestOverrideException extends overridebase{
    int x;
        //int getX() throws SQLException{
        // you can't throws SQLException because Base class throws IOException
        // you can throws only subclass of IOException like below
    int getX() throws FileNotFoundException{
        // you can cancel throws Exception like below
        //        int getX() {
        System.out.println("run to here..TestOverrideException.getX");
        if (x>0) return x;
        //else throw new FileNotFoundException("x must >0");
        return 0;
    }

   void setX(int x){
        
        // you can NOT add throws Exception like this
        //void setX(int x) throws FileNotFoundException{        
        
        System.out.println("run to here..TestOverrideException.setX");
        this.x=x;
    }

    public static void main(String[] args) {
        TestOverrideException t = new TestOverrideException();
        overridebase b=t;
        try{
            t.setX(13);
            System.out.println("X="+t.getX());
            System.out.println("X="+b.getX());
            b.setX(-2);
            System.out.println("X="+t.getX());
            System.out.println("X="+b.getX());

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
