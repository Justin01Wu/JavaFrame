package com.justa.test.aws.lambda;

import java.util.LinkedHashMap;
import java.util.Map;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

public class ManuallyCallLambda {
	public static void main(String args[]) throws Exception {

		GeneralLambdaService generalLambdaService = LambdaInvokerFactory
				.builder()
				.lambdaClient(AWSLambdaClientBuilder.defaultClient())
				.build(GeneralLambdaService.class);

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("test", "abc");
		String output = generalLambdaService.callGeneralLambda(map);
		System.out.println(output);
	}
}
