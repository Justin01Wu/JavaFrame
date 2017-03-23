package scjp.proxy;

public class ThirdPartyClass implements ThirdPartyInterface {
	
	@Override
	public void doA(Integer x){
		System.out.println("    ThirdPartyClass.doA");
		
	}
	
	@Override
	public String doB(){
		System.out.println("    ThirdPartyClass.doB");
		return "doB 1234";
	}
	
	public void doC(){
		System.out.println("    ThirdPartyClass.doC");
	}


}
