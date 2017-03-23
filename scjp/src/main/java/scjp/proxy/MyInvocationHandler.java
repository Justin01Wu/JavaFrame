package scjp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// it comes from http://www.concretepage.com/java/dynamic-proxy-with-proxy-and-invocationhandler-in-java
public class MyInvocationHandler implements InvocationHandler {

	private ThirdPartyInterface obj; // target object

	public MyInvocationHandler(ThirdPartyInterface obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result;
		
		System.out.println(" current method: "+ method.getName());
		try {
			
			// before start target method
			if (method.getName().indexOf("doA") > -1) {
				System.out.println("...doA Method Executing...");
			}
			result = method.invoke(obj, args);

			// after target method is finished
			System.out.println("...doA Method Executed...");
			System.out.println();
			
		} catch (InvocationTargetException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
