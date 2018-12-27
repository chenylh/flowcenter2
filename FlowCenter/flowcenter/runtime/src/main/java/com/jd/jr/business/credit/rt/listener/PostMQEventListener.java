package com.jd.jr.business.credit.rt.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.jd.jr.business.credit.rt.event.PostFlowMQEvent;
import com.jdjr.fmq.client.producer.MessageProducer;
import com.jdjr.fmq.common.exception.JMQException;

@Component
public class PostMQEventListener {
    private Logger logger = LoggerFactory.getLogger(PostMQEventListener.class);

    @Resource(name="fmqProducer")
	private MessageProducer messageProducer;

    @Subscribe
    public void listen(PostFlowMQEvent event) throws JMQException {
    	logger.info(event.getMessage().toString());
    	messageProducer.send(event.getMessage());
    }
}