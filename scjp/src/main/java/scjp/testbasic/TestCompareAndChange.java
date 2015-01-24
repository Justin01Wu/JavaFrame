package scjp.testbasic;

/*What will be printed first when following code runs?
 A. Compiler error
 B. Will print lava
 C. Runtime exception
 D. Will print java
 the answer is D
 */
class TestCompareAndChange {
    public TestCompareAndChange() {
    }

    static int sage;
    static void change(String s) {
        s = s.replace('j', 'l');
    }

    public static void main(String args[]) {
        String s = "java";
        change(s);
        System.out.println(s);

        String john;
        System.out.println("john" == "john" );  // the line output "true"
        System.out.println("john".equals("john") );
        System.out.println(john = "john" );  // the line output "john"
        //if(john="john") System.out.println("aaa");  the line can't be compiled
        boolean flag = false;
        if (flag = true)  System.out.println("true"); // the line output "true"

        //System.out.println("john" = "john" );

        sage = sage + 1;
        System.out.println("The age is " + sage);
        /*
         // Above lines can be complied but following lines can't be compiled
         // because age is local variable
        int age;
        age = age + 1;
        System.out.println("The age is " + age);
*/

    }


}
