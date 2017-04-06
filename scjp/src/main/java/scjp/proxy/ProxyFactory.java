package scjp.proxy;

import java.lang.reflect.Proxy;

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

}
