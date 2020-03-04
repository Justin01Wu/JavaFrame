package com.justa.test.aws.lambda;

import java.nio.ByteBuffer;

import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justa.test.aws.message.MyDataDto;

// from https://stackoverflow.com/questions/31714788/can-an-aws-lambda-function-call-another?rq=1
public class AsyncCallLambda {
	
	public static void main(String args[]) throws Exception {
		AWSLambdaAsync awsLambdaAsync = AWSLambdaAsyncClientBuilder.standard().build();

		InvokeRequest invokeRequest = new InvokeRequest();
		
		MyDataDto dto =  new MyDataDto();
		dto.setId(1212);
		
		dto.setDesc("aasasasasa22342");
		dto.setValue2(12345.3d);
		
		ObjectMapper Obj = new ObjectMapper(); 
		  
        String jsonStr = Obj.writeValueAsString(dto);        
        System.out.println(jsonStr);
        
        ByteBuffer payload = ByteBuffer.wrap(jsonStr.getBytes());
		
		invokeRequest.withFunctionName("GeneralFunction:GeneralFunctionTest").withPayload(payload);
		
		InvokeResult invokeResult = awsLambdaAsync.invoke(invokeRequest); 
		String result = new String(invokeResult.getPayload().array());
		System.out.println(result);
	}

}
