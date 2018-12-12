package com.justa.library.test.kafaka;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

// comes from https://docs.confluent.io/3.0.0/kafka/sasl.html#kafka-sasl-plain-broker
// and https://docs.confluent.io/current/kafka/authentication_sasl/authentication_sasl_plain.html
/*
add this into server.properties:
	listeners=SASL_PLAINTEXT://CA09417D.global.local:9093
	security.inter.broker.protocol=SASL_PLAINTEXT
	sasl.mechanism.inter.broker.protocol=PLAIN
	sasl.enabled.mechanisms=PLAIN
	
	then added  JAAS file kafka_server_jaas.conf for KafaKa server:
		KafkaServer {
		   org.apache.kafka.common.security.plain.PlainLoginModule required
		   username="admin"
		   password="admin-secret"
		   user_admin="admin-secret"
		   user_kafkabroker1="kafkabroker1-secret"
		   user_kafkaclient1="kafkaclient1-secret";
		};
	then set it before start Kafka server:
		set KAFKA_OPTS=-Djava.security.auth.login.config=C:/_program2/kafka_2.12-2.1.0/kafka_server_jaas.conf

	then added  JAAS file KafkaClient.txt for KafaKa client:
	KafkaClient {
		org.apache.kafka.common.security.plain.PlainLoginModule required
		username="kafkaclient1"
		password="kafkaclient1-secret";
	};

	*/
public class KafakaProducerOnSASL {
	
	public static String KAFKA_BROKERS = "CA09417D.global.local:9093";

	private static Producer<Long, String> createProducer() {

		Properties props = new Properties();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
		/*
		 * What will happen if the first server is down in the bootstrap list? 
		 * Can the producer still connect to the other Kafka brokers in the cluster?
			The producer will try to contact the next broker in the list. 
			Any of the brokers once contacted, will let the producer know about the entire Kafka cluster. 
			The Producer will connect as long as at least one of the brokers in the list is running. 
		 */
		 
		// the following 3 lines are for SASL: 
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");	   
		props.put("sasl.mechanism", "PLAIN");  // Looks like it a bug of Kafka, need to manually to set it		
		System.setProperty("java.security.auth.login.config", "C:/_program2/kafka_2.12-2.1.0/KafkaClient.txt");		
		
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

				String msg = "sync msg " + index + ":" + date;
				// String msg = "{'systemName':'Treaty3.0'}";
				long key = index;

				final ProducerRecord<Long, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, key, msg);

				RecordMetadata metadata = producer.send(record).get();

				long elapsedTime = System.currentTimeMillis() - time;
				System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
						record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);

			}
		} finally {
			producer.flush();
			producer.close();
		}
	}
	
	static void runProducerAsnc(final int sendMessageCount) throws InterruptedException {
	    final Producer<Long, String> producer = createProducer();
	    long time = System.currentTimeMillis();
	    final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

	    
	    
	    try {
	        for (long index = time; index < time + sendMessageCount; index++) {
	        	
	        	long key = index;
	        	Date date = new Date(index);
	        	String msg = "Asnc msg " + index + ":" + date;
	            final ProducerRecord<Long, String> record =   new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, key, msg);
	            
	            producer.send(record, (metadata, exception) -> {
	            	/*
	            	 * What does the Callback lambda do?
						The callback gets notified when the request is complete.
	            	 */
	            	
	                long elapsedTime = System.currentTimeMillis() - time;
	                if (metadata != null) {
	                    System.out.printf("sent record(key=%s value=%s) " +
	                                    "meta(partition=%d, offset=%d) time=%d\n",
	                            record.key(), record.value(), metadata.partition(),
	                            metadata.offset(), elapsedTime);
	                } else {
	                    exception.printStackTrace();
	                }
	                countDownLatch.countDown();
	            });
	            
	            
	        }
	        countDownLatch.await(25, TimeUnit.SECONDS);
	    }finally {
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
		
		runProducerAsnc(messageAmount);
		
		runProducer(messageAmount);
	}
}
