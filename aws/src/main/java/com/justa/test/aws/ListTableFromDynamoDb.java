package com.justa.test.aws;

import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

public class ListTableFromDynamoDb {

	public static void main(String[] args) {
		System.out.println("Your DynamoDB tables:\n");

		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder
				.standard()
				.withRegion(Regions.US_EAST_1)
				.build();

		ListTablesRequest request;

		boolean more_tables = true;
		String last_name = null;

		while (more_tables) {
			try {
				if (last_name == null) {
					request = new ListTablesRequest().withLimit(10);
				} else {
					request = new ListTablesRequest().withLimit(10).withExclusiveStartTableName(last_name);
				}

				ListTablesResult table_list = ddb.listTables(request);
				List<String> table_names = table_list.getTableNames();

				if (table_names.size() > 0) {
					for (String cur_name : table_names) {
						System.out.format("* %s\n", cur_name);
					}
				} else {
					System.out.println("No tables found!");
					System.exit(0);
				}

				last_name = table_list.getLastEvaluatedTableName();
				if (last_name == null) {
					more_tables = false;
				}

			} catch (AmazonServiceException e) {
				System.err.println(e.getErrorMessage());
				System.exit(1);
			}
		}
		System.out.println("\nDone!");
	}

}
