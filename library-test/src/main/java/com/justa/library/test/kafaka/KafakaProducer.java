package com.justa.library.test.kafaka;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

// comes from http://cloudurable.com/blog/kafka-tutorial-kafka-producer/index.html
public class KafakaProducer {

	private static Producer<Long, String> createProducer() {

		Properties props = new Properties();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
		/*
		 * What will happen if the first server is down in the bootstrap list? 
		 * Can the producer still connect to the other Kafka brokers in the cluster?
			The producer will try to contact the next broker in the list. 
			Any of the brokers once contacted, will let the producer know about the entire Kafka cluster. 
			The Producer will connect as long as at least one of the brokers in the list is running. 
		 */
		 
		
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		return new KafkaProducer<>(props);

	}

	static void runProducer(final int sendMessageCount) throws Exception {

		final Producer<Long, String> producer = createProducer();

		long time = System.currentTimeMillis();

		// String msg = getLargeString();
		try {
			for (long index = time; index < time + sendMessageCount; index++) {
				Date date = new Date(index);

				String msg = "Hello Mom " + index + ":" + date;
				// String msg = "{'systemName':'Treaty3.0'}";
				long key = index;

				final ProducerRecord<Long, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, key, msg);

				RecordMetadata metadata = producer.send(record).get();

				long elapsedTime = System.currentTimeMillis() - time;
				System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
						record.key(), record.key(), metadata.partition(), metadata.offset(), elapsedTime);

			}
		} finally {
			producer.flush();
			producer.close();
		}
	}

	public static String getLargeString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 1000; i++) {
			sb.append("dkasjfhasdlasdjlaskfjdsk fhs khasdkhas jjhladfkljadlkjas d;ljasd;asdlas;kdj asd======").append(i);
		}
		return sb.toString();
	}

	public static void main(String... args) throws Exception {
		int messageAmount = 5;
		runProducer(messageAmount);
	}
}
