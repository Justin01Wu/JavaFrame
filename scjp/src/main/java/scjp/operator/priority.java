package scjp.operator;

public class priority {
    public static void main(String[] args) {
        int x=1;
        String y[]={"aaa","bbb","ccc"};
        y[--x]+=".";
        for(int i=0;i<y.length;i++){
            System.out.println(y[i]);
        }
    }
}
