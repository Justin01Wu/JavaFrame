package scjp.testexception;

//: LostMessage.java
// How an exception( VeryImportantException ) can be lost

class VeryImportantException extends Exception {
    public String toString() {
        return "A very important exception!";
    }
}


class HoHumException extends Exception {
    public String toString() {
        return "A trivial exception";
    }
}


public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) throws Exception {
        LostMessage lm = new LostMessage();
        try {
            lm.f();
        } finally {
            lm.dispose();  // , dispose() throw another exception, will override previous exception
        }
    }
} ///:~
