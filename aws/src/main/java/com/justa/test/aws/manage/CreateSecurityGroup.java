package com.justa.test.aws.manage;

import java.io.IOException;
import java.util.Arrays;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;

// create a SecurityGroup for MySQL
public class CreateSecurityGroup {
	
	public static final String securityGroupName = "MySQLGroup";

	public static void main(String[] args) throws IOException {
		
		ClientConfiguration clientConfiguration = new ClientConfiguration();

	    clientConfiguration.setRequestTimeout(5000);
	    clientConfiguration.setRetryPolicy(new RetryPolicy(null, null, 3, true));

		AmazonEC2 client = AmazonEC2ClientBuilder.standard()
				.withClientConfiguration(clientConfiguration)
				.build();		

		CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest()
				.withGroupName(securityGroupName)
				.withDescription("MySQL group");

		CreateSecurityGroupResult result = client.createSecurityGroup(csgr);
		System.out.println(result);

		AuthorizeSecurityGroupIngressRequest request = bindMySQLPermission(securityGroupName);
		client.authorizeSecurityGroupIngress(request);

	}

	private static AuthorizeSecurityGroupIngressRequest bindMySQLPermission(String securityGroupName) {

		IpPermission ipPermission = new IpPermission();

		IpRange ipRange1 = new IpRange().withCidrIp("0.0.0.0/0");

		ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] { ipRange1 }))
			.withIpProtocol("tcp")
			.withFromPort(3306)
			.withToPort(3306);   
		// port range from 3306 to 3306 means it has only one port 
		
		AuthorizeSecurityGroupIngressRequest request = new AuthorizeSecurityGroupIngressRequest();

		request.withGroupName(securityGroupName).withIpPermissions(ipPermission);

		return request;

	}
}
