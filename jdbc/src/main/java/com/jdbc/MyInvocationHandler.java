package com.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

	private Object obj;

	public MyInvocationHandler(Object obj) {
		this.obj = obj;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		try {
			if (method.getName().equals("close")) {
				// can't close,we need to return to connect pool
				System.out.println("...skip close Method Executing...");
				return null;
			} else {
				System.out.println("...execute Method " + method.getName());
				return method.invoke(obj, args);
			}

		} catch (InvocationTargetException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}
}
