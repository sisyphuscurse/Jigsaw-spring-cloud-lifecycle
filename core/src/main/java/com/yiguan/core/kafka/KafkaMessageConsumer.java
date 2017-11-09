package com.yiguan.core.kafka;

import com.yiguan.core.kafka.message.IMessageConsumer;
import com.yiguan.core.kafka.message.IMessageHandler;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class wraps kafka-client utilities to implement Kafka consumer functionality.
 * @author panpanxu
 *
 */
public class KafkaMessageConsumer implements IMessageConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);
	
	private final AtomicBoolean stopped = new AtomicBoolean(false);
	
	private int threadNum;
	private Map<String, Object> configs;
	
	private ExecutorService executor;
	
	/**
	 * 重要：Kafka-client限定KafkaConsumer的初始化，subscribe，poll必须在一个线程里，不然会抛Exception中断消费，所以这是为什么consumer
	 * 不能用注入的方式而直接new KafkaConsumer
	 */
	private Consumer<String, String> consumer;
	
	private final Map<String, IMessageHandler> topicHandlerMap = new HashMap<String, IMessageHandler>();

	public KafkaMessageConsumer(Map<String, Object> configs, int threadNum) {
		this.configs = configs;
		this.threadNum = threadNum;
	}

	public KafkaMessageConsumer(Map<String, Object> configs) {
		this(configs, 5);
	}
	
	@Override
	public void consume(String topic, IMessageHandler handler) {
		topicHandlerMap.put(topic, handler);
	}

	@Override
	public boolean start() {
		if (topicHandlerMap.size() == 0) {
			logger.warn("no consumers!!!");
			return false;
		}
		executor = Executors.newFixedThreadPool(threadNum);
		new Thread(new Runnable() {
			public void run() {
				try {
					consumer = new KafkaConsumer<String, String>(configs);
					consumer.subscribe(Arrays.asList(topicHandlerMap.keySet().toArray(new String[0])));
					while (!stopped.get()) {
						ConsumerRecords<String, String> records = consumer.poll(10000);
						for (final ConsumerRecord<String, String> record : records) {
							executor.submit(new Runnable() {
								public void run() {
									IMessageHandler handler = topicHandlerMap.get(record.topic());
									if (handler == null) {
										logger.error("no handler, topic:{}, message:{}", record.topic(), record.value());
										return;
									}
									try {
										handler.handle(record.topic(), record.value());
									} catch (Exception e) {
										logger.error("handle message failed. topic:{}, message:{}", record.topic(), record.value(), e);
									}
								}
							});
						}
					}
				} catch (WakeupException e) {
					// Ignore exception if closing
					if (!stopped.get()) {
						throw e;
					}
				} finally {
					consumer.close();
				}
			}
		}, "Kafka-consumer-thread").start();
		return true;
	}

	@Override
	public boolean stop() {
		try {
			stopped.set(true);
			consumer.wakeup();
			executor.shutdown();
		} catch (Exception e) {
			logger.error("kafka consumer stop error.", e);
			return false;
		}
		return true;
	}
}
