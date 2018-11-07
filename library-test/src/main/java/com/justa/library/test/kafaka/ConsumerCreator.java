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

public class ConsumerCreator {

	public static Consumer<Long, String> createConsumer() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
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

		Duration oneSecond = Duration.ofSeconds(1000l);
		while (true) {

			final ConsumerRecords<Long, String> consumerRecords = consumer.poll(oneSecond);
			// 1000 is the time in milliseconds consumer will wait if no record is found at
			// broker.

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

			consumer.commitAsync();
		}
		consumer.close();
		System.out.println("DONE");
	}

	public static void main(String... args) throws Exception {
		runConsumer();
	}
}
