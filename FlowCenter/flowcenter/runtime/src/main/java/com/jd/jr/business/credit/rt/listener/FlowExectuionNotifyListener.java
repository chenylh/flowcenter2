package com.jd.jr.business.credit.rt.listener;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;
import com.jd.jr.business.credit.rt.event.PostFlowMQEvent;
import com.jdjr.fmq.common.message.Message;

@Component
public class FlowExectuionNotifyListener {

	private AsyncEventBus eventBus;
	
	public FlowExectuionNotifyListener(PostMQEventListener postMQEventListener) {
		ScheduledThreadPoolExecutor schedulePool = new ScheduledThreadPoolExecutor(3);
		eventBus = new AsyncEventBus(schedulePool);
		eventBus.register(postMQEventListener);
	}
	
	public void expression(DelegateExecution execution) throws Exception {
		System.out.println("流程监听器" );
		if (execution instanceof ExecutionEntityImpl) {
			List<TaskEntity> taskEntitys = ((ExecutionEntityImpl)execution).getTasks();
			
			for(TaskEntity taskEntity : taskEntitys) {
				Set<IdentityLink> candidates = taskEntity.getCandidates();
				for (IdentityLink idLink : candidates ) {
					String candidateGroup = idLink.getGroupId();

					// 创建消息队列
					Message message = new Message(candidateGroup, taskEntity.getId(), null);
					this.eventBus.post(new PostFlowMQEvent(message));
				}
			}
		}
	}
}