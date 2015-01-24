package i18n;

/**
test how to save non-English character, it has 2 ways:
  (1) the java source code save as UTF-8
  (2) use Unicode escape sequences
 */
import java.math.*;

public class NonEnglishAndMemory {
  static int age=18;
  int sex=12;

  public NonEnglishAndMemory() {
    BigInteger xx=new BigInteger("12345679012345679");
    BigInteger yy=new BigInteger("9999999999999");
    BigInteger zz=xx.multiply(yy);
    System.out.println(zz);

    BigDecimal aa=new BigDecimal("123456789.012345678");
    BigDecimal bb=new BigDecimal("6789012345.6789012345");
    BigDecimal cc=bb.multiply(aa);
    System.out.println(cc);

    char c='阿';  //'阿';  // a Chinese word
    System.out.println(c);

    String s1="1234567890";
    System.out.println(s1.length()*2);  // 字符串实际占用内存
    String s2="\u4E2D\u534E\u4EBA\u6C11\u5171\u548C\u56FD"; //"中华人民共和国";  // a Chinese string on Unicode format
    System.out.print(s2);  // 字符串实际占用内存
    System.out.println( "  memory cost=" + s2.length()*2);  // 字符串实际占用内存

    System.out.println(age);

  }

  int getAge(){
    return age;
  }

  public static void main(String[] args) {
    NonEnglishAndMemory app11 = new NonEnglishAndMemory();
    //sex=16;  静态方法不能使用动态数据
  }
}
