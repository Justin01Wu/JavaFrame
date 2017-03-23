package scjp.proxy;

public class ThirdPartyClass implements ThirdPartyInterface {
	
	public void doA(Integer x){
		System.out.println("    ThirdPartyClass.doA");
		
	}
	
	public String doB(){
		System.out.println("    ThirdPartyClass.doB");
		return "doB 1234";
	}


}
