package com.justa.library.test.kafaka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

// comes from https://dzone.com/articles/kafka-producer-and-consumer-example
// https://dzone.com/articles/writing-a-kafka-consumer-in-java

public class ConsumerCreator {

	public static Consumer<Long, String> createConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);  // how many messages in one poll
		
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);
		Consumer<Long, String> consumer = new KafkaConsumer<>(props);
		
		consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		
		return consumer;
	}

	static void runConsumer() throws InterruptedException {
		final Consumer<Long, String> consumer = createConsumer();

		final int giveUp = 100;
		int noRecordsCount = 0;

		Duration oneSecond = Duration.ofSeconds(2l);
		while (true) {			
			
			System.out.println("WAITING: ");
			
			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(oneSecond);
			// consumer will wait if no record is found at broker.
			
			System.out.println("GOT ");

			if (consumerRecords.count() == 0) {
				noRecordsCount++;
				if (noRecordsCount > giveUp) {
					// If no message found count is reached to threshold exit loop.
					break;
				} else
					continue;
			}

			// print each record.
			consumerRecords.forEach(record -> {
				System.out.println("Key: " + record.key());
				System.out.println("value: " + record.value());
				System.out.println("partition " + record.partition());
				System.out.println("offset " + record.offset());
				System.out.println("");
			});

			consumer.commitAsync(); // tell Kafaka server I already received those message, Kafaka won't send you again those message in the next poll
		}
		consumer.close();
		System.out.println("DONE");
	}

	public static void main(String... args) throws Exception {
		runConsumer();
	}
}