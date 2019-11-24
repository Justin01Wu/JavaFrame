package com.justa.test.aws.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBInstance;

public class CreateDbInstance {

	private static final String sg_name = CreateSecurityGroup.securityGroupName;
	private static final String rds_identifier = "my-rds-db";
	private static final String db_name = "mytestdb";
	private static final String user_name = "masteruser";
	private static final String user_password = "mymasterpassw0rd1!";
	private static final String admin_email = "myemail@myemail.com";

	private static final String rds_endpoint = "";

	public static void main(String[] args) throws IOException {

		AmazonEC2 client = AmazonEC2ClientBuilder.standard().build();

		// We need to get the Security Group ID Number to use in the creation of the RDS
		// Instance
		DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest().withGroupNames(sg_name);
		DescribeSecurityGroupsResult result = client.describeSecurityGroups(request);

		List<String> vpcSecurityGroupIds = new ArrayList<>();
		vpcSecurityGroupIds.add(result.getSecurityGroups().get(0).getGroupId());
		
		// This will create our MariaDB Database
		AmazonRDS rdsClient = AmazonRDSClientBuilder.standard().build();
		CreateDBInstanceRequest dbRequest = new  CreateDBInstanceRequest();
		dbRequest.setDBInstanceIdentifier(rds_identifier);
		dbRequest.setDBName(db_name);
		dbRequest.setDBInstanceClass("db.t2.micro");
		dbRequest.setEngine("mariadb");
		dbRequest.setMasterUsername(user_name);
		dbRequest.setMasterUserPassword(user_password);
		dbRequest.setVpcSecurityGroupIds(vpcSecurityGroupIds);
		dbRequest.setAllocatedStorage(20);
		DBInstance dbResult = rdsClient.createDBInstance(dbRequest);
		
		System.out.println(dbResult);
	}

}
