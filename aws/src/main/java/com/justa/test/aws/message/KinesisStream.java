package com.justa.test.aws.message;

import java.nio.ByteBuffer;
import java.util.Date;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.ListShardsRequest;
import com.amazonaws.services.kinesis.model.ListShardsResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import com.amazonaws.services.kinesis.model.Shard;
import com.amazonaws.services.kinesis.model.ShardIteratorType;

// create a kinesis stream "donut-sales" and add a records into it
// from official developer exercise 11.3
public class KinesisStream {
	
	private static String kinesisStreamName = "donut-sales";
    public static void main(String[] args)    {
    	
    	System.out.println("starting:" + new Date());
    	
    	AmazonKinesis client = AmazonKinesisClientBuilder.defaultClient();
    	client.createStream(kinesisStreamName, 2);
    	
    	System.out.println("creating:" + new Date());
    	
    	checkCreatingStatus(client);  // need about 1 minute
    	
    	System.out.println("created:" + new Date());
    	
    	writeRecords(client);
		
		readRecords(client, kinesisStreamName);
		readRecords(client, kinesisStreamName);
		// can read same record twice, can use pos to remember what is previous		
		
		System.out.println("read a record:" + new Date());
		
		client.deleteStream(kinesisStreamName);		
		
		System.out.println("deleted the stream:" + new Date());
    	
    	
    }
    
    private static void writeRecords(AmazonKinesis client) {
    	String data = "From KinesisSample (1) at " + new Date();
    	ByteBuffer bb =  ByteBuffer.wrap(data.getBytes());
		client.putRecord(kinesisStreamName, bb, "california");
		
    	data = "From KinesisSample (2) at " + new Date();
    	bb =  ByteBuffer.wrap(data.getBytes());
		client.putRecord(kinesisStreamName, bb, "NewYork");

    	data = "From KinesisSample (3) at " + new Date();
    	bb =  ByteBuffer.wrap(data.getBytes());
		client.putRecord(kinesisStreamName, bb, "NewYork");
		
		System.out.println("added 3 records:" + new Date());
    }
    
    private static void readRecords(AmazonKinesis client, String kinesisStreamName) {
		ListShardsRequest request = new ListShardsRequest();
		
		
		request.setStreamName(kinesisStreamName);

		ListShardsResult result = client.listShards(request);
		for(Shard shard : result.getShards()) {
			String shardId = shard.getShardId();
			System.out.println("shardId:" + shardId);
			GetShardIteratorRequest request2 = new GetShardIteratorRequest();
			request2.setStreamName(kinesisStreamName);
			request2.setShardId(shard.getShardId());
			request2.setShardIteratorType(ShardIteratorType.TRIM_HORIZON);
		    
			GetShardIteratorResult result2 = client.getShardIterator(request2);
			
			GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
			getRecordsRequest.setShardIterator(result2.getShardIterator());
			GetRecordsResult result3 = client.getRecords(getRecordsRequest );
			
			for(Record r: result3.getRecords()) {
				System.out.println("key:" + r.getPartitionKey());
				System.out.println("data:" + r.getData().toString());
			}
		}
    }
    
    private static void checkCreatingStatus(AmazonKinesis client) {
    	DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
    	describeStreamRequest.setStreamName( kinesisStreamName );

    	long startTime = System.currentTimeMillis();
    	long endTime = startTime + ( 10 * 60 * 1000 );
    	while ( System.currentTimeMillis() < endTime ) {
    		System.out.println("waiting 20 s for kinesis stream creating..." );
    	  try {
    	    Thread.sleep(20 * 1000);
    	  } 
    	  catch ( Exception e ) {}
    	  
    	  try {
    	    DescribeStreamResult describeStreamResponse = client.describeStream( describeStreamRequest );
    	    String streamStatus = describeStreamResponse.getStreamDescription().getStreamStatus();
    	    if ( streamStatus.equals( "ACTIVE" ) ) {
    	      break;
    	    }

    	    try {
    	      Thread.sleep( 1000 );  // sleep for one second
    	    }
    	    catch ( Exception e ) {}
    	  }
    	  catch ( ResourceNotFoundException e ) {}
    	}
    	if ( System.currentTimeMillis() >= endTime ) {
    	  throw new RuntimeException( "Stream " + kinesisStreamName + " never went active" );
    	}
    }
}
