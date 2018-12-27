package com.jd.jr.business.credit.rt.jsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.base.Joiner;
import com.jd.jr.business.credit.rt.FlowCenterApplication;
import com.jd.jr.business.credit.rt.mt.SecurityUtil;
import com.jd.jsf.gd.error.RpcException;
import com.jd.jsf.gd.filter.AbstractFilter;
import com.jd.jsf.gd.msg.Invocation;
import com.jd.jsf.gd.msg.MessageBuilder;
import com.jd.jsf.gd.msg.RequestMessage;
import com.jd.jsf.gd.msg.ResponseMessage;

/**
 * 流程运行时JSF服务权限过滤器
 * 
 * @author chenyuliang
 * @description 校验JSF服务消费者的访问权限
 */
public class AuthFilter extends AbstractFilter {
	private static final long serialVersionUID = 20181115L;

	private static Logger logger = LoggerFactory.getLogger(FlowCenterApplication.class);

	/** 接口隐式参数：appName */
	private static final String PARAM_APP_NAME = ".fcAppName";
	/** 接口隐式参数：appPW */
	private static final String PARAM_APP_PW = ".fcAppPW";

	@Autowired
	protected SecurityUtil securityUtil;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Override
	public ResponseMessage invoke(RequestMessage request) {
		String methodDesc = "流程中心JSF服务权限过滤器";

		String appName = null;
		Object remote = null;
		try {
			Invocation invocation = request.getInvocationBody();
			appName = (String) invocation.getAttachment(PARAM_APP_NAME);
			String appPW = (String) invocation.getAttachment(PARAM_APP_PW);
			remote = invocation.getAttachment("_remote"); // 调用方IP

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					appName, appPW);
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			logger.info(Joiner.on(methodDesc).on("开始校验").on("remote").on(remote.toString()).toString());
			if (authentication.isAuthenticated()) {
				logger.info("校验通过");
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				securityUtil.logInAs(userDetails.getUsername());
			} else {
				logger.info("校验失败");
				RpcException exception = new RpcException("Invalid token! Invocation of " + invocation.getClazzName()
						+ "." + invocation.getMethodName() + " from consumer " + invocation.getAttachment("_remote")
						+ " to provider " + invocation.getAttachment("_local") + " are forbidden by server. ");
				ResponseMessage response = MessageBuilder.buildResponse(request);
				response.setException(exception);
				return response;
			}
		} catch (Exception e) {
			logger.error("校验异常", e);
		}
		return this.getNext().invoke(request);
	}
}
