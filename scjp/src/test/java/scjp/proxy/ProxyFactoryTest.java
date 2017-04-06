package scjp.proxy;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import junit.framework.TestCase;

public class ProxyFactoryTest extends TestCase {
	
	public void testCollectionOnProxyObject(){
		Vector<ThirdPartyInterface>  all = new Vector<ThirdPartyInterface>();
		Set<ThirdPartyInterface>  all2 = new HashSet<ThirdPartyInterface>();
		List<ThirdPartyInterface>  all3 = new LinkedList<ThirdPartyInterface>();
		
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		all.addElement(proxy);		
		if (!all.remove(proxy)) {
			System.out.println("     ===>  proxy can't be found in Vector");
		}
		
		all2.add(proxy);
		if (!all2.remove(proxy)) {
			System.out.println("     ===>  proxy can't be found in HashSet");
		}
		
		all3.add(proxy);
		if (!all3.remove(proxy)) {
			System.out.println("     ===>  proxy can't be found in LinkedList");
		}

	}
	
	public void testCollectionOnRealObject(){
		Vector<ThirdPartyInterface>  all = new Vector<ThirdPartyInterface>();
		Set<ThirdPartyInterface>  all2 = new HashSet<ThirdPartyInterface>();
		List<ThirdPartyInterface>  all3 = new LinkedList<ThirdPartyInterface>();
		
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		all.addElement(realObject);		
		if (!all.remove(realObject)) {
			System.out.println("     ===>  realObject can't be found in Vector");
		}
		
		all2.add(realObject);
		if (!all2.remove(realObject)) {
			System.out.println("     ===>  realObject can't be found in HashSet");
		}
		
		all3.add(realObject);
		if (!all3.remove(realObject)) {
			System.out.println("     ===>  realObject can't be found in LinkedList");
		}

	}
	
	public void testHashCodeOnProxyObject(){
		
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);

		
		System.out.println("realObject hashCode = " + realObject.hashCode());
		
		System.out.println("proxy hashCode = " + proxy.hashCode());
		
		assertEquals(realObject.hashCode(), proxy.hashCode());

		
	}
	
	public void testClassNameOnProxyObject(){
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		System.out.println("proxy class name ="  + proxy.getClass().getCanonicalName());
		
		assertNotSame(proxy.getClass().getCanonicalName(), realObject.getClass().getCanonicalName());

	}
	
	public void testIsProxyClass(){
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		assertTrue(	    Proxy.isProxyClass(proxy.getClass()));		
		assertFalse(	Proxy.isProxyClass(realObject.getClass()));


	}
	
	public void testInterface(){
		
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);		
		
		assertTrue(realObject instanceof ThirdPartyClass);
		assertFalse(proxy instanceof ThirdPartyClass);

		assertTrue(realObject instanceof ThirdPartyInterface);
		assertTrue(proxy instanceof ThirdPartyInterface);

		
		
	}
	
	public void testDoA(){
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
		
		proxy.doA(453);
	}
	
	public void testDoB(){
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
	
		proxy.doB();
	}
	
	public void testDoC(){
		ThirdPartyClass realObject = new ThirdPartyClass();
		ThirdPartyInterface proxy = ProxyFactory.newInstance(realObject);
	
		realObject.doC();
		if(proxy instanceof ThirdPartyClass){
			ThirdPartyClass task3  = (ThirdPartyClass)proxy;
			task3.doC();
		}else{
			// will go to here
			System.out.println("no way to execute doC method on proxy becuase it is not on interface");
		}
	}

}
