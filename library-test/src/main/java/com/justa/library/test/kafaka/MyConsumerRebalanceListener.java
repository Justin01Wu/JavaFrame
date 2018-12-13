package com.justa.library.test.kafaka;

import java.util.Arrays;
import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;


// comes from http://blog.empeccableweb.com/wp/2016/11/30/manual-offsets-in-kafka-consumers-example/
public class MyConsumerRebalanceListener implements ConsumerRebalanceListener {
	private KafkaConsumer<Long, String> kafkaConsumer;
	private long startingOffset;
	
	public MyConsumerRebalanceListener(KafkaConsumer<Long, String> kafkaConsumer, long startingOffset) {
		this.kafkaConsumer = kafkaConsumer;
		this.startingOffset =  startingOffset;
	}
	
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.printf("%s topic-partitions are revoked from this consumer\n", Arrays.toString(partitions.toArray()));
    }
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.printf("%s topic-partitions are assigned to this consumer\n", Arrays.toString(partitions.toArray()));
            if(startingOffset == -2) {
                System.out.println("Leaving it alone");
            }else if(startingOffset ==0){
                System.out.println("Setting offset to begining");

                kafkaConsumer.seekToBeginning(partitions);
            }else if(startingOffset == -1){
                System.out.println("Setting it to the end ");

                kafkaConsumer.seekToEnd(partitions);
            }else {
                System.out.println("Resetting offset to " + startingOffset);
                //kafkaConsumer.seek(partitions, startingOffset);
                // TODO finish it
            }
    }

}
