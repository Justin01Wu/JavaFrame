package scjp.testclass;

public class TestOverLoad {

    void getX(){}
    //int getX(){return 1;}  //error
    int getX(String s){return 1;}
    //int getX(String s)throws Exception {return 1;} //error
    int getX(int s)throws Exception {return 1;}

    public static void main(String[] args) {
        TestOverLoad testoverload = new TestOverLoad();
    }
}


