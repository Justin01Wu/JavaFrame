
// Can you believe this can be compiled?

package scjp.testclass.inherit;

import java.io.*;
abstract  interface Test1
{
        public void test() throws IOException;
}

public class Test implements Test1
{
        public void test(){}
}
