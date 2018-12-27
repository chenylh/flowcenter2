package com.jd.jr.business.credit.rt.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jd.jr.business.credit.rt.facade.api.FlowCenterRTFacade;
import com.jd.jr.business.credit.rt.mt.MultiTenantInfoHolder;
import com.jd.jr.jropen.common.api.entity.JroResponse;
import com.jd.jr.jropen.common.api.model.CommonRequest;
import com.jd.jr.jropen.common.api.model.CommonResponse;
import com.jd.jr.jropen.salary.common.config.BizConfig;

/**
 * @author chenyuliang
 * @date 2018年11月15日09:55:32
 */
@Component("flowCenterRTFacadeImpl")
public class FlowCenterRTFacadeImpl implements FlowCenterRTFacade {
	private Logger logger = LoggerFactory.getLogger(FlowCenterRTFacadeImpl.class);

	private ProcessRuntime processRuntime;

	private TaskRuntime taskRuntime;
	
	private MultiTenantInfoHolder tenantInfoHolder;
	
	public FlowCenterRTFacadeImpl(MultiTenantInfoHolder tenantInfoHolder,ProcessRuntime processRuntime,TaskRuntime taskRuntime) {
		this.taskRuntime = taskRuntime;
		this.processRuntime = processRuntime;
		this.tenantInfoHolder = tenantInfoHolder;
	}
	
	/**
	 * 根据流程定义Key开启一个流程实例并设置参数集合Variables
	 * @param req  流程定义Key{String}和Variables{JSONObject}
	 * @return 返回创建的流程实例id
	 */
	public CommonResponse<JSONObject> startProcessInstancesByKey(CommonRequest<JSONObject> req) {
		String userId = tenantInfoHolder.getCurrentUserId();
		logger.info(userId + "服务启动开始流程实例 ");

		JSONObject jsonObject = req.getRequestBody();
		String processDefinitionKey = (String)jsonObject.get("Key");
		
		// 获取流程定义
		ProcessDefinition processDefinition = processRuntime.processDefinition(processDefinitionKey);
		if(processDefinition == null) {
			buildResp(JroResponseCode., respDesc, extRespCode, respMemo, respData)
		}
		
		
		
		Object variables = jsonObject.get("Variables");
		Map<String, Object> varsMap;
		if (variables instanceof JSONObject) {
			varsMap = JSONObject.toJavaObject((JSONObject)variables, Map.class);
		}
		else {
			varsMap = new HashMap<String, Object>();
			varsMap.put("Variables", variables);
		}
		
		ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(processDefinitionKey)
					.withProcessInstanceName("启动流程用户 " + userId).withVariables(varsMap).build());

		Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
		logger.info("获取 " + tasks.getTotalItems() + " 个任务");
		logger.info("获取任务历史个数:" + processEngine.getHistoryService().createHistoricProcessInstanceQuery().count());

		return processInstance;
		
		return null;
	}

	/**
	 * 根据任务id设置认领任务
	 * @param req 任务id{String}和Variables{JSONObject}
	 * @return 返回任务Status
	 */
	public JroResponse<JSONObject> claimTask(CommonRequest<JSONObject> req) {

		return null;
	}

	/**
	 * 根据任务id设置任务完成
	 * @param req 任务id{String}和Variables{JSONObject}
	 * @return 返回任务Status
	 */
	public JroResponse<JSONObject> completeTask(CommonRequest<JSONObject> req) {

		return null;
	}

	protected <T> JroResponse<T> buildResp(String respCode, String respDesc, String extRespCode, String respMemo,
			T respData) {
		JroResponse<T> resp = new JroResponse.Builder<T>().responseModule("flowcenter")
				.responseTime(new Date()).responseCode(respCode).responseDesc(respDesc).extResponseCode(extRespCode)
				.extResponseDesc(null).responseMemo(respMemo).responseData(respData).build();
		logger.info("构造响应完成");
		return resp;
	}
}
