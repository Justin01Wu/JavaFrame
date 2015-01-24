package scjp.testbasic.test;

public class Application1 {
  boolean packFrame = false;

  static void main(String[] arg) {
  short s=0x1c;
  byte dd=-64;
  byte hh=(byte)(dd>>>4);
  int x=-1;
  x=x>>>5;
  System.out.println(s);

  String xx="100";
  String yy="100";
  if (xx==yy){
    System.out.println("Equal");
    }

  int rr=4;
  System.out.println("Value is "+((rr > 4) ? 99.99:9));

  Integer ixx=new Integer(100);
//  if (ixx.equals(100))
//      System.out.println("ixx=100");

  if (ixx.equals("100"))
      System.out.println("ixx=100");

  if (ixx.equals(new Integer(100)))
      System.out.println("ixx=100");

  if (ixx.equals("1g00"))
      System.out.println("ixx=100");

 String sxx=new String("wuyg");
  if (sxx.equals("wuyg"))
      System.out.println("sxx=wuyg");

  if (sxx=="wuyg")
      System.out.println("sxx=wuyg");

  if ("wuyg"==sxx)
      System.out.println("sxx=wuyg");

/*
  int uu=1;
  String[] names={"wuyg","wanghua","wanghui"};
  names[-uu] += ".";
  for(int ii=0;ii<names.length ;ii++){
    System.out.println(names[ii]);
  }
*/


  }


}

