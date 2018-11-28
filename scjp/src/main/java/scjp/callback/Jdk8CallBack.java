package scjp.callback;

public class Jdk8CallBack {
	
	
    public static void main(String[] args){

    	String msg ="call back print this message11";
    	
    	callThisWithMessage(msg, (msg2) -> System.out.println("Message received: "+msg2));    	
    	// Lambda function, it means creating anonymous OnClickListener with method onClick(msg2){System.out.println("Message received: "+msg2}

    }
    
    private static void callThisWithMessage(String msg , OnClickListener callBack) {
    	callBack.onClick(msg);
    }


}
