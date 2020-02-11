package com.justa.test.aws.lambda;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.justa.test.aws.message.MyDataDto;

public class ManuallyCallLambda {
	public static void main(String args[]) throws Exception {

		GeneralLambdaService generalLambdaService = LambdaInvokerFactory
				.builder()
				.lambdaClient(AWSLambdaClientBuilder.defaultClient())
				.build(GeneralLambdaService.class);

		
		for(int i=0; i<10; i++) {
			MyDataDto dto =  new MyDataDto();
			dto.setId(i);
			MyDataDto output = generalLambdaService.callGeneralLambda(dto);
			System.out.println(output.getCreatedAt());
		}

	}
}
