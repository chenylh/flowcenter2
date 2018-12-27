package com.jd.jr.business.credit.rt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.spring.autodeployment.AutoDeploymentStrategy;
import org.activiti.spring.autodeployment.ResourceParentFolderAutoDeploymentStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.jd.jr.business.credit.rt.mt.MultiTenantInfoHolder;
import com.jd.jr.business.credit.rt.mt.ProcessDefinitionResourceFinder;
import com.jd.jr.business.credit.rt.mt.SecurityUtil;

@SpringBootApplication
@ComponentScan("com.jd.jr.business")
@ImportResource("classpath:/spring/spring-fmq-producer.xml")
@RestController
public class FlowCenterApplication implements CommandLineRunner, BeanNameAware, InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(FlowCenterApplication.class);

	private String beanName;

	private MultiTenantInfoHolder tenantInfoHolder;

	private ProcessEngine processEngine;

	private ProcessRuntime processRuntime;
	
	private TaskRuntime taskRuntime;

	private final SecurityUtil securityUtil;

	protected String deploymentName = "FlowCenterDeploymentByTenant";

	private ProcessDefinitionResourceFinder processDefinitionResourceFinder;

	private AutoDeploymentStrategy deploymentStrategies = new ResourceParentFolderAutoDeploymentStrategy();

	public FlowCenterApplication(ProcessEngine processEngine, ProcessRuntime processRuntime, SecurityUtil securityUtil,
			MultiTenantInfoHolder tenantInfoHolder, ProcessDefinitionResourceFinder processDefinitionResourceFinder, TaskRuntime taskRuntime) {
		this.taskRuntime = taskRuntime;
		this.processRuntime = processRuntime;
		this.tenantInfoHolder = tenantInfoHolder;
		this.securityUtil = securityUtil;
		this.processDefinitionResourceFinder = processDefinitionResourceFinder;
		this.processEngine = processEngine;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	public String getBeanName(String name) {
		return this.beanName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public static void main(String[] args) {
		SpringApplication.run(FlowCenterApplication.class, args);
	}

	@PostMapping("/deployProcessByTenant")
	public String deployProcessByTenant() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		StringBuilder message = new StringBuilder(">>> 当前租户" + tenantInfoHolder.getCurrentTenantId() + "发布流程定义:");
		try {
			List<Resource> procDefResources = processDefinitionResourceFinder.discoverProcessDefinitionResources();
			if (!procDefResources.isEmpty()) {
				deploymentStrategies.deployResources(deploymentName, procDefResources.toArray(new Resource[0]), processEngine.getRepositoryService());
			}
			message.append(procDefResources.size() + "个:");
			for (Resource resource : procDefResources) {
				message.append(resource.getDescription() + ":");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message.toString();
	}

	@PostMapping("/completeTask")
	public String completeTask(@RequestParam String taskid) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		// 创建借款申请单
		JSONObject loanApplyBill = new JSONObject();
		loanApplyBill.put("loanApplyBill", "BILL000000000001");
		loanApplyBill.put("loanUserId", "USER000000000001");
		loanApplyBill.put("loanMoney", "20000.00");
		loanApplyBill.put("loanApplyDate", "2018-11-11");
		Task task = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskid).withVariable("loanApplyBill", loanApplyBill).build());
		String message = ">>> 完成任务: " + task.getId()+ "状态: " + task.getStatus();
		tenantInfoHolder.clearCurrentUserId();
		tenantInfoHolder.clearCurrentTenantId();
		return message;
	}
	
	@PostMapping("/claimTask")
	public String claimTask(@RequestParam String taskid) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		Task task = taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskid).build());
		String message = ">>> 认领任务: " + task.getId()+ "状态: " + task.getStatus();
		tenantInfoHolder.clearCurrentUserId();
		tenantInfoHolder.clearCurrentTenantId();
		return message;
	}
	
	
	@PostMapping("/startProcessInstancesByKey")
	public String startProcess(@RequestParam String key) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		ProcessInstance processInstance = this.startProcessInstances(key);
		String message = ">>> 创建流程实例: " + processInstance;
		logger.info(message);
		
		tenantInfoHolder.clearCurrentUserId();
		tenantInfoHolder.clearCurrentTenantId();
		return message;
	}

	@GetMapping("/tasks")
	public String gettasks() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		logger.info("> 业务系统检查待处理的任务（补偿服务）.");
		StringBuilder message = new StringBuilder(">>> 当前系统" + tenantInfoHolder.getCurrentUserId() + "待处理任务:");
		
		Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
		if (tasks.getTotalItems() > 0) {
			for (Task t : tasks.getContent()) {
				message.append(t.getProcessInstanceId()).append(">>").append(t.getName()).append(">>").append(t.getId()).append("\n");
			}
		} else {
			message.append("> 目前没有业务系统需要处理的任务.").append("\n");
		}
		
		return message.toString();
	}
	
	
	
	
	@GetMapping("/processDefinitions")
	public String getProcessDefinition() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.securityUtil.logInAs(userDetails.getUsername());
		Page<ProcessDefinition> processDefs = getProcessDefind();
		String message = ">>> 获取流程定义: " + Joiner.on('\n').join(processDefs.getContent());
		return message;
	}

	private Page<ProcessDefinition> getProcessDefind() {
		Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
		logger.info("> 可用的流程定义列表: " + processDefinitionPage.getTotalItems());
		for (ProcessDefinition pd : processDefinitionPage.getContent()) {
			logger.info("\t > 流程定义列表: " + pd);
		}

		return processDefinitionPage;
	}

	private ProcessInstance startProcessInstances(String processDefinitionKey) {
		String userId = tenantInfoHolder.getCurrentUserId();
		logger.info(userId + "用户启动开始流程实例 ");

		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("user", userId);

		ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(processDefinitionKey)
					.withProcessInstanceName("启动流程用户 " + userId).withVariables(vars).build());

		Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
		logger.info("获取 " + tasks.getTotalItems() + " 个任务");
		logger.info("获取任务历史个数:" + processEngine.getHistoryService().createHistoricProcessInstanceQuery().count());

		return processInstance;
	}

	@Override
	public void run(String... args) {
	}

	@Override
	public void destroy() throws Exception {
		if (this.processEngine != null) {
			logger.debug("Closing Commons ProcessEngine");
			this.processEngine.close();
		}
	}
}
