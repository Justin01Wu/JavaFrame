package scjp.proxy;

import java.lang.reflect.Proxy;
import java.util.Vector;

/** 
 * it comes from http://www.concretepage.com/java/dynamic-proxy-with-proxy-and-invocationhandler-in-java <br/>
 * it is usually used as   <ul>
 * 		<li> AOP programming</li>
 * 		<li> Spring transaction framework</li> 
 * 		<li> or Dynamic Mock Objects for Unit Testing </li>
 * </ul>
 * the target class must have some interfaces,  <br/>
 * then proxy can warp its method and do some magic before and after its original method is executed <br/>
 * proxy can even bypass the original method  if it is necessary 
 * <pre>
 * {@code
		ThirdPartyInterface task = ProxyFactory.newInstance(new ThirdPartyClass());
		task.doA(453);
* }
* </pre>
 * @author justin.wu
 *
 */
public class ProxyFactory {
	
	public static ThirdPartyInterface newInstance(ThirdPartyInterface ob) {
		
		ClassLoader classLoader = ob.getClass().getClassLoader();
		//ClassLoader classLoader = MyInvocationHandler.class.getClassLoader();
		
		Object object =  Proxy.newProxyInstance(
				classLoader,
				new Class<?>[] { ThirdPartyInterface.class },   // must be an interface
				new MyInvocationHandler(ob));
		return (ThirdPartyInterface)object;
	}
	
	public static void main(String[] args) {
		
		Vector<ThirdPartyInterface>  all = new Vector<ThirdPartyInterface>();
		
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		all.addElement(proxy);
		
		if (!all.remove(proxy)) {
			System.out.println("     ===>  proxy can't be found");
		}

		
		System.out.println("realObject hashCode = " + realObject.hashCode());
		
		System.out.println("proxy hashCode = " + proxy.hashCode());
		
		System.out.println("proxy class name ="  + proxy.getClass().getCanonicalName());
		
		if(proxy instanceof ThirdPartyClass){
			System.out.println("proxy is the instanceof ThirdPartyClass " );
		}
		
		if(	Proxy.isProxyClass(proxy.getClass())){
			System.out.println( proxy.getClass().getCanonicalName() + "  is a proxy");
		}
		
		if(	Proxy.isProxyClass(realObject.getClass())){
			System.out.println( realObject.getClass().getCanonicalName() + "  is a proxy");
		}

		
		if(proxy instanceof ThirdPartyInterface){
			System.out.println("proxy is the instanceof ThirdPartyInterface " );
		}
		
		proxy.doA(453);
		proxy.doB();
		if(proxy instanceof ThirdPartyClass){
			ThirdPartyClass task3  = (ThirdPartyClass)proxy;
			task3.doC();
		}else{
			// will go to here
			System.out.println("no way to execute doC method becuase it is not on interface");
		}
	}
}
