package com.justa.test.aws.manage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.IpRange;

// create a SecurityGroup for SSH
public class CreateSecurityGroup {

	public static void main(String[] args) throws IOException {

		AmazonEC2 client = AmazonEC2ClientBuilder.standard().build();

		String securityGroupName = "SSHGroup-"+ new Date();

		CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest()
				.withGroupName(securityGroupName)
				.withDescription("My SSH group");

		CreateSecurityGroupResult createSecurityGroupResult = client.createSecurityGroup(csgr);
		System.out.println(createSecurityGroupResult);

		AuthorizeSecurityGroupIngressRequest request = bindSSHPermission(securityGroupName);
		client.authorizeSecurityGroupIngress(request);

	}

	private static AuthorizeSecurityGroupIngressRequest bindSSHPermission(String securityGroupName) {

		IpPermission ipPermission = new IpPermission();

		IpRange ipRange1 = new IpRange().withCidrIp("0.0.0.0/0");

		ipPermission.withIpv4Ranges(Arrays.asList(new IpRange[] { ipRange1 }))
			.withIpProtocol("tcp")
			.withFromPort(22)
			.withToPort(22);
		AuthorizeSecurityGroupIngressRequest request = new AuthorizeSecurityGroupIngressRequest();

		request.withGroupName(securityGroupName).withIpPermissions(ipPermission);

		return request;

	}
}
