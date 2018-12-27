package com.jd.jr.business.credit.rt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.SharedExecutorServiceAsyncExecutor;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextManager;
import org.activiti.engine.integration.IntegrationContextService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringCallerRunsRejectedJobsHandler;
import org.activiti.spring.SpringExpressionManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.SpringRejectedJobsHandler;
import org.activiti.spring.boot.ActivitiProperties;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.jd.jr.business.credit.rt.listener.FlowExectuionNotifyListener;
import com.jd.jr.business.credit.rt.listener.PostMQEventListener;
import com.jd.jr.business.credit.rt.mt.MultiTenantInfoHolder;
import com.jd.jr.business.credit.rt.mt.ProcessDefinitionResourceFinder;

@Configuration
@EnableWebSecurity
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(ActivitiProperties.class)
public class RTApplicationConfiguration extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(RTApplicationConfiguration.class);

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService());
	}

	@Bean
	public UserDetailsService myUserDetailsService() {

		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

		String[][] usersGroupsAndRoles = { { "spd", "password", "ROLE_ACTIVITI_USER", "GROUP_BankManagementTeam" },
				{ "wenli", "password", "ROLE_ACTIVITI_USER", "GROUP_RiskManagementTeam" },
				{ "salaboy", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "bizsys", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "ryandawsonuk", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "erdemedeiros", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "tony", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam" },
				{ "system", "password", "ROLE_ACTIVITI_USER" }, { "admin", "password", "ROLE_ACTIVITI_ADMIN" }, };

		for (String[] user : usersGroupsAndRoles) {
			List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
			logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings
					+ "]");
			inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
					authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
		}
		return inMemoryUserDetailsManager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();

	}

	@Bean
	public MultiTenantInfoHolder getTenantInfoHolder() {
		MultiTenantInfoHolder tenantInfoHolder = new MultiTenantInfoHolder();

		tenantInfoHolder.addTenant("alfresco");
		tenantInfoHolder.addUser("alfresco", "spd");
		tenantInfoHolder.addUser("alfresco", "wenli");
		tenantInfoHolder.addUser("alfresco", "salaboy");
		tenantInfoHolder.addUser("alfresco", "bizsys");

		tenantInfoHolder.addTenant("acme");
		tenantInfoHolder.addUser("acme", "raphael");
		tenantInfoHolder.addUser("acme", "john");

		tenantInfoHolder.addTenant("starkindustries");
		tenantInfoHolder.addUser("starkindustries", "tony");

		return tenantInfoHolder;
	}

	@Bean
	public ProcessDefinitionResourceFinder getProcessDefinitionResourceFinder(MultiTenantInfoHolder tenantInfoHolder,
			ActivitiProperties activitiProperties, ResourcePatternResolver resourcePatternResolver) {
		return new ProcessDefinitionResourceFinder(activitiProperties, resourcePatternResolver, tenantInfoHolder);
	}

	@Bean
	@ConditionalOnMissingBean(name = "springProcessEngineConfiguration")
	public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
		return null;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringAsyncExecutor springAsyncExecutor(TaskExecutor taskExecutor) {
		return new SpringAsyncExecutor(taskExecutor, springRejectedJobsHandler());
	}

	@Bean
	public SpringRejectedJobsHandler springRejectedJobsHandler() {
		return new SpringCallerRunsRejectedJobsHandler();
	}

	protected Set<Class<?>> getCustomMybatisMapperClasses(List<String> customMyBatisMappers) {
		Set<Class<?>> mybatisMappers = new HashSet<>();
		for (String customMybatisMapperClassName : customMyBatisMappers) {
			try {
				Class customMybatisClass = Class.forName(customMybatisMapperClassName);
				mybatisMappers.add(customMybatisClass);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Class " + customMybatisMapperClassName + " has not been found.", e);
			}
		}
		return mybatisMappers;
	}

	@Bean
	public ProcessEngine getProcessEngine(MultiTenantInfoHolder tenantInfoHolder) {
		MultiSchemaMultiTenantProcessEngineConfiguration config = new MultiSchemaMultiTenantProcessEngineConfiguration(
				tenantInfoHolder);
		config.setAsyncExecutorActivate(true);
		boolean sharedExecutor = false;
		// 是否共享租户实例
		if (sharedExecutor) {
			config.setAsyncExecutor(new SharedExecutorServiceAsyncExecutor(tenantInfoHolder));
		} else {
			config.setAsyncExecutor(new ExecutorPerTenantAsyncExecutor(tenantInfoHolder));
		}

		config.setJdbcDriver("com.mysql.jdbc.Driver");
		config.setDatabaseType(MultiSchemaMultiTenantProcessEngineConfiguration.DATABASE_TYPE_MYSQL);
		config.setDatabaseSchemaUpdate(MultiSchemaMultiTenantProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		config.registerTenant("alfresco", createDataSource(
				"jdbc:mysql://localhost:3306/alfresco?characterEncoding=UTF-8&useSSL=false", "root", "cyldab"));
		config.registerTenant("acme", createDataSource(
				"jdbc:mysql://localhost:3306/acme?characterEncoding=UTF-8&useSSL=false", "root", "cyldab"));
		config.registerTenant("starkindustries", createDataSource(
				"jdbc:mysql://localhost:3306/starkindustries?characterEncoding=UTF-8&useSSL=false", "root", "cyldab"));

		config.setExpressionManager(new SpringExpressionManager(this.getApplicationContext(), config.getBeans()));

		ProcessEngine processEngine = config.buildProcessEngine();
		
		
		return processEngine;
	}

	private DataSource createDataSource(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
		PooledDataSource ds = new PooledDataSource();
		ds.setUrl(jdbcUrl);
		ds.setUsername(jdbcUsername);
		ds.setPassword(jdbcPassword);
		ds.setDriver("com.mysql.jdbc.Driver");
		return ds;
	}

	@Bean
	@ConditionalOnMissingBean
	public RuntimeService runtimeServiceBean(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	@Bean
	@ConditionalOnMissingBean
	public RepositoryService repositoryServiceBean(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	@Bean
	@ConditionalOnMissingBean
	public TaskService taskServiceBean(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HistoryService historyServiceBean(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

	@Bean
	@ConditionalOnMissingBean
	public ManagementService managementServiceBeanBean(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

	@Bean
	@ConditionalOnMissingBean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	@ConditionalOnMissingBean
	public IntegrationContextManager integrationContextManagerBean(ProcessEngine processEngine) {
		return processEngine.getProcessEngineConfiguration().getIntegrationContextManager();
	}

	@Bean
	@ConditionalOnMissingBean
	public IntegrationContextService integrationContextServiceBean(ProcessEngine processEngine) {
		return processEngine.getProcessEngineConfiguration().getIntegrationContextService();
	}
}
