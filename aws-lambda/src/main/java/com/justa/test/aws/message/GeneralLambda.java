package com.justa.test.aws.message;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GeneralLambda implements RequestHandler<Map<String, Object>,String> {
	
	@Override
	public String handleRequest(Map<String, Object> input, Context context) {
		
		System.out.println("==>input type: " + input.getClass().getName());
		System.out.println("==>input value: " + input);
		return null;
	}

}
