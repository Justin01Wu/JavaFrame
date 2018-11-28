package scjp.callback;

public class OldWayCallBack {
	
    public static void main(String[] args){
    	
    	String msg ="call back print this message11";

    	callThisWithMessage(msg, new OnClickListener() {
  		  @Override
  		  public void onClick(String  msg) {
  		    System.out.println(msg);
  		  }
  		});
    	
    	OnClickListener callBack = new OnClickListener() {
    		  @Override
      		  public void onClick(String  msg) {
      		    System.out.println(msg);
      		  }
    	};
    	
    	msg ="call back print this message22";
    	
    	callThisWithMessage(msg, callBack);
    	
    }
    
    private static void callThisWithMessage(String msg , OnClickListener callBack) {
    	callBack.onClick(msg);
    }
}
