package scjp.proxy;

import java.lang.reflect.Proxy;

/** 
 * <p>it comes from http://www.concretepage.com/java/dynamic-proxy-with-proxy-and-invocationhandler-in-java</p>
 * <p>it is usually used as AOP programming, like spring transaction framework </p>
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
		Object object =  Proxy.newProxyInstance(
				classLoader,
				new Class<?>[] { ThirdPartyInterface.class }, 
				new MyInvocationHandler(ob));
		return (ThirdPartyInterface)object;
	}
	
	public static void main(String[] args) {
		ThirdPartyInterface task = ProxyFactory.newInstance(new ThirdPartyClass());
		task.doA(453);
		task.doB();
	}
}
