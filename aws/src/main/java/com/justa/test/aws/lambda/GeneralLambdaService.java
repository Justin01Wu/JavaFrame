package com.justa.test.aws.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.justa.test.aws.message.MyDataDto;

public interface GeneralLambdaService {
	
	@LambdaFunction(functionName = "GeneralFunction:GeneralFunctionTest")  // use alias to avoid version affection
	MyDataDto callGeneralLambda(MyDataDto input);
	// this interface parameters should match GeneralLambda.handleRequest parameters
}
