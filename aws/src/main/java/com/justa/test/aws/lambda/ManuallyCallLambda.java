package com.justa.test.aws.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.justa.test.aws.message.MyDataDto;

public class ManuallyCallLambda {
	public static void main(String args[]) throws Exception {

		GeneralFunctionService generalFunctionService = LambdaInvokerFactory
				.builder()
				.lambdaClient(AWSLambdaClientBuilder.defaultClient())
				.build(GeneralFunctionService.class);

		
		for(int i=0; i<1; i++) {
			MyDataDto dto =  new MyDataDto();
			dto.setId(i+100);
			dto.setDesc("index: "+i);
			dto.setValue2(12345.3d);
			
			MyDataDto output = generalFunctionService.callGeneralFunction(dto);
			System.out.println(output.getCreatedAt());
			
			System.out.println(output.getDesc());  
			// this will be null because it is @JsonIgnore field
		}

	}
}
