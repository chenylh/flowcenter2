package com.jd.jr.business.credit.rt.event;

import com.jdjr.fmq.common.message.Message;

/**
 * 消息队列事件
 * @author chenyuliang
 *
 */
public class PostFlowMQEvent {
	private final Message message;

	public PostFlowMQEvent(Message message) {
		this.message = message;
		System.out.println("event message:" + message);
	}

	public Message getMessage() {
		return message;
	}
}