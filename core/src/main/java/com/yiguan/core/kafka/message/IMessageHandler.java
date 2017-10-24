package com.yiguan.core.kafka.message;

/**
 * 消息处理者API
 */
public interface IMessageHandler {
	boolean handle(String topic, Object message);
}
