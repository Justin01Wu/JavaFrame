package com.justa.test.aws.message;

import java.util.Date;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

// this lambda is designed for remote caller
public class GeneralFunction implements RequestHandler<MyDataDto, MyDataDto> {
	
	@Override
	public MyDataDto handleRequest(MyDataDto input, Context context) {
		System.out.println("==>input type: " + input.getClass().getName());
		System.out.println("==>input data ID: " + input.getId());
		Date now = new Date();
		input.setValue( new Double(now.getTime()));
		input.setCreatedAt(now);
		
		return input;
	}

}
