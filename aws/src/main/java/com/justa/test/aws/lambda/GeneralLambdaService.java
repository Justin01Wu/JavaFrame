package com.justa.test.aws.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

public interface GeneralLambdaService {
	
	@LambdaFunction(functionName = "GeneralLambda:GenealLambdaProd")  // use alias to avoid version affection
	String callGeneralLambda(Map<String, Object> input);
	// this interface parameters should match GeneralLambda.handleRequest parameters
}
