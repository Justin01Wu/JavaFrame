//This class is improved based on  TestString, 
//loop times: 2097150
//it is 99864 times of TestString
package scjp.testbasic.String;

public class TestStringBuffer{
   public static void main(String args[])
   {
       String s="abbbbb";
       StringBuffer sb = new StringBuffer(s);
       System.out.println("JVM MAX MEMORY: "+Runtime.getRuntime().maxMemory()/1024/1024+"M");
       System.out.println("JVM IS USING MEMORY:"+
                          (Runtime.getRuntime().totalMemory()/1024/1024)+
                          "M");
       Runtime.getRuntime().traceMethodCalls(true);

       int count = 0;
       while(true)
       {
           try{
               sb.append(s);
               count++;

           }catch(Exception e)
           {
               System.out.println(e);
           }
           catch(Error o)
           {
               String unit = null;
               int size = sb.length();
               size *= 2;

               int time = 0;
               while(size>1024)
               {
                   size = size/1024;
                   time++;
               }
               switch(time)
               {
                   case 0: unit = "byte";break;
                   case 1: unit = "k"; break;
                   case 2: unit = "M"; break;
                   default : unit = "byte";
               }

               System.out.println("Loop times:"+count);
               System.out.println("StringBuffer length : "+sb.length());               
               System.out.println("StringBuffer has used memory:"+size+unit);
               System.out.println("JVM IS USING MEMORY:"+
                                  (float)Runtime.getRuntime().totalMemory()/1024/1024+
                                  "M");
               System.out.println("MemoryError:"+o);
               break;
           }

       }
   }
}

