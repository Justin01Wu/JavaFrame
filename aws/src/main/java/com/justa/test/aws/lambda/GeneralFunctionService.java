package com.justa.test.aws.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.justa.test.aws.message.MyDataDto;

public interface GeneralFunctionService {
	
	@LambdaFunction(functionName = "GeneralFunction:GeneralFunctionTest")  // use alias to avoid version affection
	MyDataDto callGeneralFunction(MyDataDto input);
	// this interface parameters should match GeneralFunction.handleRequest parameters
}
