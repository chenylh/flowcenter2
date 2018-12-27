package com.jd.jr.business.credit.rt.facade.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程中心（flowcenter）响应码
 * 
 * @author chenyuliang
 * @description
 * @date 2018-11-12 下午11:26:17
 */
public enum FlowCenteResponseCode  {
	// --------------------错误类别：保留错误码--------------------
	/**成功*/
	SUCCESS("00000", "成功"), 
	/**失败*/
	@Deprecated
	FAILURE("55555", "失败"), 
	/**未知*/
	@Deprecated
	UNKNOWN("99999", "未知"), 
	
	
	//--------------------错误类别：数据库错误【01】--------------------
	/**数据库异常*/
	DB_EXCEPTION("JRO0101001", "数据库异常"), 
	/**数据库查询异常*/
	DB_QUERY_EXCEPTION("JRO0101002", "数据库查询异常"), 
	/**数据库插入异常*/
	DB_INSERT_EXCEPTION("JRO0101003", "数据库插入异常"), 
	/**数据库更新异常*/
	DB_UPDATE_EXCEPTION("JRO0101004", "数据库更新异常"), 
	/**数据库删除异常*/
	DB_DELETE_EXCEPTION("JRO0101005", "数据库删除异常"), 
	
	
	//--------------------错误类别：网络错误【02】--------------------
	
	
	
	//--------------------错误类别：操作系统错误【03】--------------------
	
	
	
	//--------------------错误类别：应用系统错误SYS【04】--------------------
	/**未知系统错误*/
	SYS_UNKNOWN_ERROR("JRO0401001", "未知系统错误"), 
	/**系统RPC错误*/
	SYS_RPC_ERROR("JRO0401002", "系统RPC错误"),
	

	//--------------------错误类别：外部系统调用异常【05】--------------------
	// 通用异常【01】
	/**公司内部系统访问受限*/
	COM_INNER_SYS_ACCESS_DENIED("JRO0501001", "公司内部系统访问受限"), 
	
	// 公司其他系统调用异常【02】
	/**企业站系统（ENS）服务异常*/
	COM_INNER_SYS_ENS_ERROR("JRO0502001", "企业站系统（ENS）服务异常"), 
	/**金融开放平台（JRO）服务异常*/
	COM_INNER_SYS_JRO_ERROR("JRO0502002", "金融开放平台（JRO）服务异常"), 
	
	/**企业站账户余额提现服务异常*/
	ENTERPRISE_ACCOUNT_BALANCE_EXTRACT("JRO0502003", "企业站账户余额提现服务异常"), 
	
	/**企业站账户余额充值服务异常*/
	ENTERPRISE_ACCOUNT_BALANCE_RECHARGE("JRO0502004", "企业站账户余额充值服务异常"), 
	
	/**企业站账户余额付款服务异常*/
	ENTERPRISE_ACCOUNT_BALANCE_GATHERING("JRO0502005", "企业站账户余额付款服务异常"), 

	/**企业站账户余额付款个人钱包服务异常*/
	ENTERPRISE_ACCOUNT_BALANCE_TOWALLETPIN("JRO0502019", "企业站账户余额付款个人钱包服务异常"), 

	/**企业站绑卡服务异常*/
	ENTERPRISE_ACCOUNT_BIND_CARD("JRO0502006", "企业站绑卡服务异常"), 

	/**企业站更新账户信息服务异常*/
	ENTERPRISE_ACCOUNT_UPDATE_CARD("JRO0502007", "企业站更新账户信息服务异常"), 

	/**企业站根据企业账户别名查询账户信息服务异常*/
	ENTERPRISE_ACCOUNT_QUERY_CARD_BYALAIS("JRO0502009", "企业站根据企业账户别名查询账户信息服务异常"), 

	/**企业站根据商户号查询账户信息服务异常*/
	ENTERPRISE_ACCOUNT_QUERY_ALLCARD("JRO0502010", "企业站根据商户号查询账户信息服务异常"), 

	/**企业站启用(禁用)账户信息服务异常*/
	ENTERPRISE_ACCOUNT_UPDATE_CARD_STATUS("JRO0502011", "企业站启用(禁用)账户信息服务异常"),

	/**企业站查询交易信息服务异常*/
	ENTERPRISE_ACCOUNT_QUERY_TRADEINFO("JRO0502012", "企业站查询交易信息服务异常"),

	/**企业站查询账户总数总金额服务异常*/
	ENTERPRISE_ACCOUNT_QUERY_TOTALAMOUNT("JRO0502025", "企业站查询账户总数总金额服务异常"),

	/**企业站账户不存在*/
	ENTERPRISE_ACCOUNT_NOT_EXIST("JRO0502026", "企业站账户不存在"),

	/**企业站账户对应秘钥不存在*/
	ENTERPRISE_ACCOUNT_KEY_NOT_EXIST("JRO0502027", "企业站账户对应秘钥不存在"),
	
	/**统一通知中心(UNC)服务异常*/
	COM_INNER_SYS_UNC_ERROR("JRO0502023", "统一通知中心(UNC)服务异常"), 
	/**渠道（BAC）服务异常*/
	COM_INNER_SYS_BAC_ERROR("JRO0502024", "渠道（BAC）服务异常"), 
	/**渠道（JCU）服务异常*/
	COM_INNER_SYS_JCU_ERROR("JRO0502025", "金融企业系统（JCU）服务异常"), 
	/**托管通（P2P）服务异常*/
	COM_INNER_SYS_P2P_ERROR("JRO0502029", "托管通（P2P）服务异常"),

	WLOGIN_RPC_ERROR("JRO0502030", "金融app联合登录rpc异常"),//前端使用

	CC_RPC_ERROR("JRO0502031", "卡中心rpc异常"),

	XJK_RPC_ERROR("JRO0502032", "小金库rpc异常"),

	UC_RPC_ERROR("JRO0502033", "会员中心rpc异常"),

	QYJK_RPC_ERROR("JRO0502034", "企业金库rpc异常"),

	/**交易（AUC）服务异常*/
	COM_INNER_SYS_AUC_ERROR("JRO0502035", "交易（AUC）服务异常"),

	HR_USER_RPC_ERROR("JRO0502036", "京东hr用户系统rpc异常"),

	ENTER_REALNAME_RPC_ERROR("JRO0502037", "企业实名rpc异常"),

	PERSONAL_ACC_RPC_ERROR("JRO0502038", "个人账户输出rpc异常"),

	BASE_AREA_RPC_ERROR("JRO0502038", "四级地址服务RPC异常"),

	PROMISE_DOS_RPC_ERROR("JRO0502039", "京东配送服务RPC异常"),
	/**京东宙斯（JOS）服务异常*/
	COM_INNER_SYS_JOS_ERROR("JRO0502040", "京东宙斯（JOS）服务异常"),
	/**结算系统（FINSETTS）服务异常*/
	COM_INNER_SYS_FINSETTS_ERROR("JRO0502041", "结算系统（FINSETTS）服务异常"),
	/**金融台账（GOP）服务异常*/
	COM_INNER_SYS_GOP_ERROR("JRO0502042", "金融台账（GOP）服务异常"),
	
	SETTLEMENT_RPC_ERROR("JRO0502043", "财务结算系统rpc异常"),
	


	//--------------------错误类别：缓存（redis）异常【06】--------------------
	/**缓存读取异常*/
	CACHE_READ_ERROR("JRO0600001", "缓存读取异常"), 
	/**缓存写入异常*/
	CACHE_WRITE_ERROR("JRO0600002", "缓存写入异常"), 
	/**缓存删除异常*/
	CACHE_DEL_ERROR("JRO0600003", "缓存删除异常"), 
	
	
	
	
	//--------------------错误类别：数据检查错误【10】--------------------
	//**********验签失败SIGN【01】
	/**签名失败*/
	SIGN_FAIL("JRO1001001", "签名失败"), 
	/**签名异常*/
	SIGN_EXCEPTION("JRO1001002", "签名异常"), 
	/**验签失败*/
	SIGN_VERIFY_FAIL("JRO1001003", "验签失败"),
	/**验签异常*/
	SIGN_VERIFY_EXCEPTION("JRO1001004", "验签异常"), 
	/**不支持的签名类型*/
	SIGN_UNSUPPORTED_TYPE("JRO1001005", "不支持的签名类型"),

	/**AKS签名数字证书过期*/
	SIGN_AKS_CERT_EXPIRE("JRO1001006", "公钥加密时证书过期"),

	/**AKS签名数字证书获取失败*/
	SIGN_AKS_CERT_QUERY_ERROR("JRO1001007", "公钥获取失败"),
	//**********解密失败DECRYPT【02】
	/**异步消息（MSG）解密失败*/
	DECRYPT_MSG_FAIL("JRO1002001", "异步消息（MSG）解密失败"), 
	/**不支持的解密类型*/
	DECRYPT_UNSUPPORTED_TYPE("JRO1002002", "不支持的解密类型"), 
	/**解密异常*/
	DECRYPT_EXCEPTION("JRO1002003", "解密异常"),
	
	//**********参数错误PARAM【03】
	/**参数为空*/
	PARAM_NULL("JRO1003001", "参数为空"), 
	/**cookie参数为空*/
	PARAM_COOKIE_NULL("JRO1003002", "从cookie获取参数为空"), 
	/**参数校验失败*/
	PARAM_VALID_FAIL("JRO1003003", "参数校验失败"), 
	/**参数超长*/
	PARAM_STR_OVERLENGTH("JRO1003004", "参数超长"), 
	/**开始时间大于结束时间*/
	PARAM_START_DATE_GREATER_THAN_END_DATE("JRO1003005", "开始时间大于结束时间"),

	BANLANCE_PAY_ORDER_INFO_ERROR_APPID("JRO1003006","企业站余额付款订单信息异常-appid参数不匹配"),
	
	BANLANCE_PAY_ORDER_INFO_ERROR_PARTNER_ID("JRO1003007","企业站余额付款订单信息异常-partnerid参数不匹配"),
	
	BANLANCE_PAY_ORDER_INFO_ERROR_PARTNER_MEMBER_ID("JRO1003008","企业站余额付款订单信息异常-partnerMemberId参数不匹配"),


    /*身份证号码要求18位*/
    PARAM_JSB_CART_NO_LENGTH_NOT18("JRO1003009","江苏银行申请打印流水身份证号码要求18位"),
	
	//**********请求错误REQ【04】
	/**请求为空*/
	REQ_NULL("JRO1004001", "请求为空"), 
	/**请求头部为空*/
	REQ_HEADER_NULL("JRO1004002", "请求头部为空"), 	
	/**请求体为空*/
	REQ_BODY_NULL("JRO1004003", "请求体为空"), 
	/**请求头部参数为空*/
	REQ_HEADER_PARAM_NULL("JRO1004004", "请求头部参数为空"), 
	
	//**********加密失败ENCRYPT【05】
	/**异步消息（MSG）加密失败*/
	ENCRYPT_MSG_FAIL("JRO1005001", "异步消息（MSG）加密失败"),

	//**********cookie拦截器校验失败COOKIE_LOGIN【06】
	/**用户未登录//没有cookie信息*/
	COOKIE_LOGIN_NO_EXIT("JRO1006001", "用户未登录,请先登录"),
	/**用户登录信息不正确//cookie信息不匹配//被篡改*/
	COOKIE_LOGIN_FAIL("JRO1006002", "用户登录信息不正确"),
	/**用户登录未知异常*/
	COOKIE_LOGIN_UNKNOWN("JRO1006003", "用户登录信息校验未知异常"),
	/**用户来源非法*/
	COOKIE_LOGIN_ILLEGITMACY("JRO1006004", "用户来源非法"),
	/**用户登录超时*/
	COOKIE_LOGIN_TIMEOUT("JRO1006005", "用户登录超时"),
	
	
	/**
	 * 用户登录身份验证失效
	 * <br />2017-10-02，wysunmengyong
	 * <br />用户进入业务后，需要进行身份验证，通过后正常访问，失败时，返回该码，需要前端引导用户重新进行身份验证
	 */
	COOKIE_LOGIN_USER_AUTH_FAIL("JRO1006006", "用户登录身份验证失效"),

	/**ERP对应的partnerId未开通*/
	ERP_PARTNER_ERROR("JRO1006007", "商户暂未开通该服务"),
	
	//**********文件处理异常FILE_【07】
	/**文件解析异常*/
	FILE_ANALYSIS_FAIL("JRO1007001", "文件解析异常"),
	/**文件不存在*/
	FILE_DOWNLOAD_EXIT("JRO1007002", "文件下载失败"),
	/**文件上传失败*/
	FILE_UPLOAD_FAIL("JRO1007003", "文件上传失败"),

	//--------------------错误类别：信息配置错误【20】--------------------
	

	//--------------------错误类别：业务逻辑错误【30】--------------------
	//**********个人会员CUST【01】


	
	
	//**********商户MHT【02】
	/**商户信息查询失败*/
	MHT_INFO_QUERY_FAIL("JRO3002001", "商户信息查询失败"),
	/**账户转账，合作商户会员id与合作商户标识ID不匹配*/
	MHT_INFO_MATCHES_FAIL("JRO3002002", "商户转账，合作商户会员ID与合作商户标识ID不匹配"),
	/**此用户未注册*/
	MHT_ENTERPRISE_NOT_REGISTER("JRO3002003", "用户未注册"),
	/**商户注册失败*/
	MHT_REGISTER_FAIL("JRO3002004", "商户注册失败"),
	/**商户未授权注册*/
	MHT_REGISTER_NOT_AUTHORIZE("JRO3002005", "商户未授权注册"),
	/**商户已注册:仅表示开发者会员id重复*/
	MHT_REGISTERED_BEFORE("JRO3002006", "商户已注册"),
	/**商户未实名*/
	MHT_ENTERPRISE_NOT_REALNAME("JRO3002007", "商户未实名"),
	/**商户手机号格式不正确*/
	MHT_MOBILE_NO_LIGAL("JRO3002008", "商户手机号不合法"),
	/**商户账户基本信息不存在*/
	MHT_BASE_INFO_NOT_EXIST("JRO3002009", "商户账户基本信息不存在"), 
	/**商户余额充值调用企业站接口失败*/
	MHT_RECHARGE_FAIL("JRO3002010", "企业余额充值失败"),
	/**商户余额充值金额小于0*/
	MHT_RECHARGE_AMOUNT_NO_LIGAL("JRO3002011", "订单金额不合法"),
	/**商户余额充值订单号重复*/
	MHT_ORDER_NO_REPEAT("JRO3002012", "商户订单号重复"),
	/**商户不支持再实名*/
	MHT_NOT_REALNAME_REPEAT("JRO3002013", "商户待审核或已实名成功"),
	/**商户异步通知消息发送失败*/
	MHT_ASYNC_MSG_SEND_FAIL("JRO3002014", "商户异步通知消息发送失败"),
	/**企业站实名认证失败*/
	MHT_REAL_NAME_ENS_FAIL("JRO3002015", "企业站实名认证失败"),
	/**企业实名上传文件格式不正确*/
	MHT_REAL_NAME_NOT_FILES("JRO3002016", "企业实名上传文件格式不正确"),
	/**未查询到商户会员id*/
	MHT_CUST_ID_NOT_EXIST("JRO3002017", "未查询到商户会员id"),
	/**商户余额不足*/
	MHT_BALANCE_NOT_ENOUGH("JRO3002018", "商户余额不足"),
	/**商户余额充值订单号不存在*/
	MHT_RECHARGE_ORDER_NOT_EXIST("JRO3002019", "商户充值订单号不存在"),

	/**收银台构建参数信息失败*/
	MHT_REAL_BUILD_DATA_FAIL("JRO3002020", "收银台构建参数信息失败"),
	
	/**收款商户不在白名单内*/
	MHT_NOT_EXIST_WHITE_LIST("JRO3002030", "收款商户不在白名单内"),
	/**收款商户不在白名单内 JRO3002031*/
	MHT_IN_PARTNER_MEMBER_ID_ILLEGALITY("JRO3002031", "收款商户无效"),
	
	/**出款商户与收款商户一致*/
	MHT_IN_OUT_BALANCEPAY_CUSTOM("JRO3002032", "出款商户与收款商户一致"),
	
	/**商户号未绑卡*/
	ENTERPRISE_ACCOUNT_UNBIND_CARD("JRO3002033", "商户号未绑卡"), 
	
	/**企业账户状态异常*/
	ENTERPRISE_ACCOUNT_STATUS_ERROR("JRO3002034","企业账户状态异常"),
	
	/**企业账户未设置支付密码*/
	ENTERPRISE_ACCOUNT_NOSET_PAY_PASSWORD("JRO3002022","企业账户未设置支付密码"),
	
	/**商户号绑定多个银行账户*/
	ENTERPRISE_ACCOUNT_BIND_CARD_OVERfLOW("JRO3002023", "商户号绑定多个银行账户"),
	
	/**商户实名驳回原因查询失败*/
	MHT_REAL_REJECT_INFO_QUERY_FAIL("JRO3002035", "商户实名驳回原因查询失败"),
	/**商户绑卡列表查询失败*/
	MHT_BANK_CARD_LIST_QUERY_FAIL("JRO3002036", "商户绑卡列表查询失败"),
	/**商户绑卡失败*/
	MHT_BIND_CARD_FAIL("JRO3002037", "商户绑卡失败"),
	/**商户删卡失败*/
	MHT_DELETE_CARD_FAIL("JRO3002038", "商户删卡失败"),
	/**商户提现失败*/
	MHT_EXTRACT_FAIL("JRO3002039", "商户提现失败"),
	/**商户非个体户*/
	MHT_REALNAME_TYPE_NOT_SELFEMPLOY("JRO3002040", "商户非个体户"),
	/**商户绑卡数已达到最大*/
	MHT_BIND_CARD_NUM_MAX("JRO3002041", "商户绑卡数已达到最大"),
	/**商户此卡已绑*/
	MHT_BANK_CARD_BINDED("JRO3002042", "商户此卡已绑"),
	/**商户此银行卡不存在*/
	MHT_BANK_CARD_NOT_EXIST("JRO3002043", "商户此银行卡不存在"),

	/**商户实名申请单不存在*/
	MHT_REALNAME_APPLY_ID_NOT_EXIST("JRO3002048", "商户实名申请单不存在"),

	/**商户提现不支持非储蓄卡*/
	MHT_EXTRACT_SUPPORT_ONLY_DC("JRO3002060", "商户提现不支持非储蓄卡"),
	
	//**********银行卡CARD【03】
	
	
	
	//**********账户ACC【04】
	ACC_INFO_QUERY_FAIL("JRO3004001", "企业账户信息查询失败"), 
	
	
	//**********风控RISK【05】
	/**请求风控，风控响应报文异常*/
	RISK_RESPONSE_DATA_ERROR("JRO3005001", "请求风控，风控响应报文异常"), 
	/**余额提现被风控拦截*/
	RISK_ACCOUNT_BALANCE_EXTRACT_RISK_INTERCEPT("JRO3005002", "余额提现被风控拦截"), 
	/**余额支付被风控拦截*/
	RISK_ACCOUNT_BALANCE_GATHERING_RISK_INTERCEPT("JRO3005003", "余额支付被风控拦截"),
	/**风控报送失败*/
	RISK_INFORM_FAIL("JRO3005004", "风控报送失败"),
	/**余额支付被风控挂起*/
	RISK_ACCOUNT_BALANCE_GATHERING_RISK_HANG_UP("JRO3005005", "检测到该交易可能存在异常，交易已被挂起，客服将在4小时内联系您处理"),
	/**余额支付被风控拦截-新，避免修改原有程序，新加一个错误码*/
	RISK_ACCOUNT_BALANCE_GATHERING_RISK_INTERCEPT_NEW("JRO3005006", "检测到该交易可能存在异常，请联系客服处理"),

	//**********开发者PARTNER【06】
	/**开发者安全信息不存在*/
	PARTNER_SECURITY_INFO_NOT_EXIST("JRO3006001", "开发者安全信息不存在"), 
	/**开发者不支持此功能*/
	PARTNER_UNSUPPORTED_FUNCTION("JRO3006002", "开发者暂不支持此功能"),
	/**获取开发者数字证书公钥失败*/
	PARTNER_CERT_PUBLICKEY_NOT_EXIST("JRO3006003", "获取开发者数字证书公钥失败"),


	//**********支付密码PAY_PWD_*【07】
	/**支付密码校验失败*/
	PAY_PWD_VALID_FAIL("JRO3007001", "支付密码校验失败"), 
	/**新老支付密码相同*/
	PAY_PWD_NEW_OLD_SAME("JRO3007002", "新老支付密码相同"), 
	/**支付密码格式错误*/
	PAY_PWD_FORMAT_ERROR("JRO3007003", "支付密码格式错误"), 
	/**支付密码已存在*/
	PAY_PWD_EXIST("JRO3007004", "支付密码已存在"), 
	/**支付密码不存在*/
	PAY_PWD_NOT_EXIST("JRO3007005", "支付密码不存在"), 
	/**支付密码更新失败*/
	PAY_PWD_UPDATE_FAIL("JRO3007006", "支付密码更新失败"),
	/**支付密码输入错误次数*/
	PAY_PWD_UPDATE_FAIL_5_TIMES("JRO3007008", "支付密码输入错误次数已达5次，请一小时后再试"),
	/**AKS降级加密解密失败*/
	PAY_AKS_FAIL("JRO3007009", "AKS降级加密解密失败"),

	/**AKS返回值解析失败*/
	PAY_AKS_PARSE_FAIL("JRO3007010", "AKS返回值解析失败"),


	
	/**支付密码错误次数过多，稍后再试*/
	ACCOUNT_BANLANCE_PAY_PASSWORD_OVERRUN("JRO3007011","支付密码错误次数过多，稍后再试"),
	
	/**支付密码错误*/
	ACCOUNT_BANLANCE_PAY_PASSWORD_ERROR("JRO3007012","支付密码错误"),
	
	//**********（短信/图片）校验码CHECK_CODE_*【08】
	CHECK_CODE_INVALID("JRO3008001", "校验码无效"),
	CHECK_CODE_AUTH_CODE_IP_OVERRUN("JRO3008002", "当前IP短信验证码发送太频繁"),
	CHECK_CODE_AUTH_CODE_PHONE_OVERRUN("JRO3008003", "当前手机验证码发送太频繁"),
	
	//**********响应服务错误（响应码相关映射、文案服务）RESP_INFO_*【09】
	/**响应信息未配置*/	
	RESP_INFO_NOT_CONFIG("JRO3009001", "响应信息未配置"), 
	/**响应信息查询失败*/
	RESP_INFO_QUERY_FAIL("JRO3009002", "响应信息查询失败"), 
	
	//**********银行信息错误BANK_【10】
	/**银行信息查询失败*/
	BANK_QUERY_FAIL("JRO3010001", "银行信息查询失败"), 
	/**银行省份信息查询失败*/
	BANK_PROVINCE_QUERY_FAIL("JRO3010002", "银行省份信息查询失败"), 
	/**银行城市信息查询失败*/
	BANK_CITY_QUERY_FAIL("JRO3010003", "银行城市信息查询失败"), 
	/**银行联行信息查询失败*/
	BANK_CNAPS_QUERY_FAIL("JRO3010004", "银行联行信息查询失败"), 
	/**联行号与省市支行名称不匹配*/
	BANK_CNAPS_ADDRESS_NOT_MATCHES("JRO3010005", "联行号与省市支行名称不匹配"),
	/**银行编码查询失败*/
	BANK_CODE_QUERY_FAIL("JRO3010006", "银行编码查询失败"),
	/**银行编码不存在*/
	BANK_CODE_NOT_EXSIT("JRO3010006", "银行编码不存在"),
	/**银行名称未查询到*/
	BANK_NAME_QUERY_FAIL("JRO3010007", "银行名称未查询到"),
	/**银行联行号不存在*/
	BANK_CNAPS_NOT_EXIST("JRO3010007", "银行联行号不存在"),
	/**卡bin查询失败*/
	BANK_CARD_BIN_INFO_QUERY_FAIL("JRO3010010", "卡bin查询失败"),
	/**卡bin不存在*/
	BANK_CARD_BIN_NOT_EXIST("JRO3010011", "未识别到卡，请确认银行卡号"),
	/**光大银行请求超时*/
	BANK_CEB_UNKNOWN_ERROR("JRO3010012","光大银行请求超时"),
	/**江苏银行请求超时*/
	BANK_JIANGSU_UNKNOWN_ERROR("JRO3010013","江苏银行请求超时"),

    BANK_JIANGSU_BUILD_REQSTR_ERROR("JRO3010014","江苏银行请求报文生成失败"),
	
	//**********交易（收单、代收付、会员）通用响应码，用于充值、提现、支付、代付等流程，TRADE_【11】
	/**商户订单号重复*/
	TRADE_MER_ORDER_NO_REPEAT("JRO3011001", "商户订单号重复"), 
	/**订单超时*/
	TRADE_ORDER_EXPIRE("JRO3011002", "订单超时"), 
	/**订单金额无效*/
	TRADE_ORDER_AMT_INVALID("JRO3011003", "订单金额无效"), 
	/**订单信息校验失败*/
	TRADE_ORDER_INFO_VERIFY_FAIL("JRO3011004", "订单信息校验失败"), 
	/**订单不存在*/
    TRADE_ORDER_INFO_NOT_EXISTED("JRO3011005", "订单不存在"),
	/**操作用户与出款方不一致*/
	TRADE_ORDER_OPERATOR_NOT_OUTMHT("JRO3011006", "订单操作用户非出款方"),
	/**订单支付已成功*/
	TRADE_ORDER_PAY_SUCCESS("JRO3011007", "订单已支付成功"),
	/**订单支付处理中*/
	TRADE_ORDER_PAYING("JRO3011008", "订单支付处理中"),
	/**订单已支付失败*/
	TRADE_ORDER_PAY_FAIL("JRO3011009", "订单已支付失败"),
	
	/**订单来源非法*/
	ORDER_INFO_SOURCE_ILLEGITMACY("JRO3011010","订单来源非法"),

	/**出款方入款方不可一样*/
	TRADE_MER_NO_EQUAL("JRO3011011", "出款方入款方不可一样"),

	/**出款方入款方不可一样*/
	TRADE_MER_ACQUIRER_QUERY_FAIL("JRO3011012", "收单交易查询失败"),

	//**********代收付TAD_【12】
	/**代付到卡失败*/
	TAD_DEFRAY2BANK_FAIL("JRO3012001", "代付到卡失败"), 
	/**代付到卡外部订单号重复*/
	TAD_ORDER_NO_REPEAT("JRO3012002", "代付订单号重复"),
	/**代付到卡订单不存在*/
	TAD_DEFRAY2BANK_ORDER_NO_EXIST("JRO3012003", "代付到卡订单不存在"),
	/**代付到余额失败*/
	TAD_DEFRAY2ACC_FAIL("JRO3012004", "代付到余额失败"), 
	/**余额支付订单不存在*/
	TAD_BALANCE_PAY_ORDER_NO_EXIST("JRO3012005","余额支付订单不存在"),
	/**代付到卡白名单不通过*/
	TAD_DEFRAY_WHITE_NOT_PASS("JRO3012006", "代付白名单校验不通过"),
	
	
	//**********会员交易TME_【13】
	/**会员交易订单不存在*/
	TME_TRADE_NO_NOT_EXIST("JRO3013001", "会员交易订单不存在"),
	/**查询会员交易订单失败*/
	TME_TRADE_QUERY_FAIL("JRO3013002", "查询会员交易订单失败"),
	
	//**********金融企业系统JCU_【14】
	/**创建企业金融id失败*/
	JCU_MHT_JRID_CREATED_FAIL("JRO3014001", "创建企业金融id失败"),
	/**查询企业金融id失败*/
	JCU_MHT_JRID_QUERY_FAIL("JRO3014002", "查询企业金融id失败"),
	
	
	//**********存管通P2P_【15】
	/**存管通提现失败*/
	P2P_WITHDRAW_FAIL("JRO3015001", "存管通提现失败"),
	/**存管通提现时代付失败*/
	P2P_WITHDRAW_DEFRAY_FAIL("JRO3015002", "存管通提现时代付失败"),
	/**开发者与存管通级联关系不逊在*/
	P2P_PARTNER_PLATID_RELATION_NOT_EXIT("JRO3015003", "开发者与存管通级联关系不存在"),
	/**提现已成功或者处理中*/
	P2P_WITHDRAW_NOT_REPEAT("JRO3015004", "提现已成功或者处理中"),
	/**存管通回盘文件异常*/
	P2P_SALARY_RESULT_FILE_EXCEPTION("JRO3015005", "存管通回盘文件异常"),

	/**同一工资订单下，多个提现订单，非全部成功*/
	P2P_WITHDRAW_NOT_ALL_SUCCESS("JRO3015006","同一笔订单下的多个提现订单非全部成功"),

	P2P_BIND_CARD_FAIL("JRO3015007","存管通绑卡失败"),

	//***********个人电子户ELE_【16】
	/**个人电子户状态异常*/
	ELE_ACC_STAUTS_EXCEPTION("JRO3016001", "个人电子户状态异常"),
	/**个人电子户开户失败*/
	ELE_ACC_OPEN_FAIL("JRO3016002", "个人电子户开户失败"),
	/**个人电子户信息查询失败*/
	ELE_ACC_INFO_QUERY_FAIL("JRO3016003", "个人电子户信息查询失败"),

	ELE_ACC_AUTO_SELECT_CARD_FAIL("JRO3016004", "个人电子户开户选卡失败"),//前端使用
	/**电子户开户处理中，请稍后重试*/
	ELE_ACC_OPEN_ING("JRO3016005", "电子户开户处理中，请稍后重试"),

	//***********会员中心UC_【17】
	UC_NO_CERT("JRO3017001", "用户未上传身份证信息"),
	/**会员中心查询实名信息失败*/
	UC_GET_USER_ERROR("JRO3017002", "会员中心查询实名信息失败"),


	/**用户未实名用户*/
	UC_NO_AUTH_FAIL("JRO3017004", "用户未实名用户"),//前端使用

	/**非身份证实名用户*/
	UC_NO_IDENTITY_AUTH_FAIL("JRO3017005", "非身份证实名用户"),//前端使用



	//***********代发工资salary_【19】
	SALARY_DATA_NO_ACCOUNT("JRO3019001", "代发工资文件中的数据全部未开通电子户"),

	SALARY_GET_AMOUNT_FAIL("JRO3019002", "查询账户金额失败"),//前端使用

	SALARY_GET_ACCOUNT_FAIL("JRO3019003", "查询账户设置失败"),//前端使用

	SALARY_ERROR_ACCOUNT_DATA("JRO3019004", "用户的发薪类型设置有误"),

	SALARY_ORDER_INFO_NOT_EXIST("JRO3019005", "发薪记录订单不存在"),

	SALARY_ACCOUNT_INFO_NOT_EXIST("JRO3019006", "发薪账户信息不存在"),

	SALARY_ACCOUNT_INFO_STATUS_FAIL("JRO3019007", "发薪账户状态非成功"),
	/**京东Pin和小金库实名信息不一致*/
	SALARY_JDPIN_XJK_REALNAME_DIFF("JRO3019005", "京东Pin和小金库实名信息不一致"),
	/**
	 * “随薪发”AKS安全处理失败
	 * <br />2017-10-13，wysunmengyong，“随薪发”AKS HTTP上行数据解密/下行数据加密失败时，返回前端，执行AKS降级方案
     * <br />【前端使用】前端根据该响应码从服务端响应数据中获取新的公钥，重新向服务端发起请求
	 */
	SALARY_AKS_SEC_FAIL("JRO3019006", "“随薪发”AKS安全处理失败"),
	/**
	 * “随薪发”短信验证码过期
	 * <br />2017-10-21，wysunmengyong，新增，指进行校验时，短信验证码已过期失效
	 */
	SALARY_SMS_CODE_EXPIRE("JRO3019007", "“随薪发”短信验证码过期"),
	/**
	 * “随薪发”短信验证码错误
	 * <br />2017-10-21，新增，wysunmengyong，指进行校验时，输入的短信验证码和下发的短信验证码不一致
	 */
	SALARY_SMS_CODE_WRONG("JRO3019008", "“随薪发”短信验证码错误"),

	/**
	 * “随薪发”短信验证码“验证有效性”过期
	 * <br />2017-11-10，新增，wysunmengyong，指短信验证码校验通过后，本次校验结果的有效性过期
	 */
	SALARY_SMS_CODE_VALID_EXPIRE("JRO3019009", "“随薪发”短信验证码错误"),

	SALARY_ERP_QUERY_PARTNERMEMBERID_NULL("JRO3019010", "“随薪发”根据erp查询主体id为空"),

	SALARY_IDNO_QUERY_ERP_NULL("JRO3019011", "“随薪发”根据身份证查询erp为空"),

	//***********卡中心cc_【20】
	CC_GET_DETAIL_FAIL("JRO3020001", "获取银行卡四要素失败"),

	CC_GET_CARD_FAIL("JRO3020003", "卡中心获取银行卡列表失败"),

	CC_GET_CARD_EMPTY("JRO3020004", "卡中心获取银行卡列表为空"),//前端使用


	//***********四要素鉴权AUC_【21】
	/**验证码发送失败，请稍后再试*/
	AUC_SEND_SMS_FAIL("JRO3021001", "四要素鉴权短信发送失败"),
	/**短信验证失败*/
	AUC_VALID_SMS_FAIL("JRO3021002", "四要素鉴权短信验证失败"),
	/**验证码错误或已过期，请重新获取*/
	AUC_VALID_SMS_CODE_ERROR("JRO3021003", "验证码错误或已过期，请重新获取"),
	/**短信验证码过期,暂时可不用*/
	AUC_VALID_SMS_CODE_EXPIRE("JRO3021004", "四要素鉴权短信验证码不存在或已过期"),
	/**银行卡状态异常，请联系银行处理或绑其他银行卡*/
	AUC_BANK_STATUS_EXCEPTION("JRO3021005", "银行卡状态异常，请联系银行处理或绑其他银行卡"),
	/**银行卡未开通快捷支付业务，请联系银行处理*/
	AUC_BANK_QUICK_NOT_OPEN("JRO3021006", "银行卡未开通快捷支付业务，请联系银行处理"),
	/**银行卡号错误，请重新填写*/
	AUC_BANK_CARD_NO_ERROR("JRO3021007", "银行卡号错误，请重新填写"),
	/**银行卡未预留手机号，请联系银行处理*/
	AUC_BANK_MOBILE_NO_NOT_EXIST("JRO3021008", "银行卡未预留手机号，请联系银行处理"),
	/**手机号错误，请重新填写*/
	AUC_BANK_MOBILE_NO_ERROR("JRO3021009", "手机号错误，请重新填写"),
	/**绑卡信息错误，请重新填写*/
	AUC_BANK_INFO_ERROR("JRO3021010", "绑卡信息错误，请重新填写"),
	/**四要素鉴权失败*/
	AUC_APPLY_AUTHENTICATE_FAIL("JRO3021011", "四要素申请鉴权失败"),
	


    //***********企业理财EF_【20】
    EF_RESULT_IS_NULL("JRO3030001", "返回结果为空"),

    EF_QYJK_QUERY_OPT_EXCEPTION("JRO3030002", "企业金库查询操作异常"),

    EF_QYJK_QUERY_ERROR("JRO3030003", "企业金库查询失败"),

    /**企业实名信息不支持开通企业金库：不支持个体工商户、证件类型非身份证的企业商户开通企业金库*/
    EF_QYJK_NOT_SUPPORT_OPEN("JRO3030004", "企业实名信息不支持开通企业金库"),
    /**未开通企业金库*/
    EF_QYJK_NOT_OPEN("JRO3030005", "未开通企业金库"),
    
    
    //***********erp_【20】
    ERP_GET_EXCEPTION("JRO3031001", "ERP查询出现异常"),
    
    ERP_GET_FAIL("JRO3031002", "ERP查询失败"),
	//ERP_NO_BIND_PIN("JRO3031003","ERP未绑定JDPIN"),

	//ERP_PIN_ALREADY_REALNAME("JRO3031004","ERP绑定的PIN已经实名"),
    
    //********jdme
    ERP_SALARY_UNKONW_ERROR("JRO3032001", "ERP登录出现未知异常"),
	/**
	 * ERP未绑定JDPIN
	 * <br />2017-10-11，wysunmengyong，因对内选Pin流程优化，对前端做兼容，在未筛选出可用的京东Pin供用户选择的时候，返回该响应码，由前端引导用户主动登录京东账号
	 */
	ERP_NOT_BIND_JDPIN("JRO3032002", "ERP未绑定JDPIN"),
    
    ERP_NEED_CHANGE_JDPIN("JRO3032003", "ERP需更换JDPIN"),

    JDPIN_CUSTNO_BIND_FAIL("JRO3032004", "京东账户绑定钱包账户失败"),
    
    ERP_ILLEGITMACY_USER("JRO3032005", "非法ERP账号联系人工处理"),

	JDPIN_NO_BIND_CUSTOMERID("JRO3032006","京东PIN没有绑定钱包ID"),

	IDCARD_LENGTH_CONVERT_ERROR("JRO3032007","身份证15转18异常"),

	JDPIN_ALREADY_BIND_CUSTOMERID("JRO3032008","京东PIN已经绑定该钱包ID"),

	JDPIN_CANNOT_BIND_CUSTOMERID("JRO3032009","京东PIN与该钱包ID不可绑"),
	/**2017-09-01，新增，ERP中JD登录失败*/
	ERP_JD_LOGIN_FAIL("JRO3032010", "ERP中JD登录失败"),

	ERP_ILLEGITMACY_USER_01("JRO3032101", "用户存在多个小金库，白名单没录入"),
	ERP_ILLEGITMACY_USER_02("JRO3032102", "不存在小金库，身份证多个pin，erp绑定的pin在列表内，erp的pin绑定了非本人的钱包ID"),
	ERP_ILLEGITMACY_USER_03("JRO3032103", "不存在小金库，身份证多个pin，都绑定了非本人的钱包ID"),
	ERP_ILLEGITMACY_USER_04("JRO3032104", "不存在小金库，身份证一个pin，绑定了非本人的钱包ID"),
	ERP_ILLEGITMACY_USER_05("JRO3032105", "存在小金库，小金库绑定了非本人的jdpin"),
	ERP_ILLEGITMACY_USER_06("JRO3032106", "存在小金库，身份证多个pin，erp绑定的pin在列表内，erp的pin已经绑定了非小金库的钱包ID或者非本人的钱包ID"),
	ERP_ILLEGITMACY_USER_07("JRO3032107", "存在小金库，身份证多个pin，都已经绑定了非小金库的钱包ID或者非本人的钱包ID"),
	ERP_ILLEGITMACY_USER_08("JRO3032108", "存在小金库，小金库会员ID已经绑定了别的pin或者pin已经绑定了别的会员ID"),
	ERP_ILLEGITMACY_USER_09("JRO3032109", "存在小金库，身份证一个pin，绑定了非小金库的钱包ID或者非本人的钱包ID"),
	/**
	 * ERP和用于激活随薪发的京东Pin实名信息不一致
	 * <br />需要前端重新引导用户选择京东Pin
	 */
	ERP_JDPIN_REALNAME_DIFFERENT("JRO3032110", "ERP和用于激活随薪发的京东Pin实名信息不一致"),
	/**
	 * 京东Pin未实名
	 * <br />2017-10-26，wysunmengyong，新增
	 */
	JDPIN_NOT_REALNAME("JRO3032111", "京东Pin未实名"),
	/**
	 * 京东Pin信息查询失败
	 * <br />2017-10-26，wysunmengyong，新增，泛指任何京东Pin相关信息查询失败（登录要素、实名信息、卡信息等）
	 */
	JDPIN_INFO_QUERY_FAIL("JRO3032112", "京东Pin信息查询失败"),

	//***********app联合登录wlogin_【21】

	// 规则相关RULE【33】，2017-09-11，新增，wysunmengyong
	/**规则key不存在*/
	RULE_KEY_NOT_EXIST("JRO3033001", "规则key不存在"),

	// 小金库相关XJK【34】，2017-10-12，新增，wysunmengyong
	/**小金库未开通*/
	XJK_NOT_OPEN("JRO3034001", "小金库未开通"),
	
	//***********京东宙斯系统JOS_【35】
	/**查询用户常用地址失败*/
	JOS_USER_USUAL_ADDRESS_QUERY_FAIL("JRO3035001", "查询用户常用地址失败"),
	/**删除用户常用地址失败*/
	JOS_USER_USUAL_ADDRESS_DELETE_FAIL("JRO3035002", "删除用户常用地址失败"),
	/**新增用户常用地址失败*/
	JOS_USER_USUAL_ADDRESS_INSERT_FAIL("JRO3035003", "新增用户常用地址失败"),
	/**物流获取运单号失败*/
	JOS_PRE_DELIVERY_ID_GET_FALI("JRO3035004", "物流获取运单号失败"),
	/**物流运单下单失败*/
	JOS_RECEIVE_ORDER_INFO_FAIL("JRO3035005", "物流运单下单失败"),
	/**物流运单号全称跟踪失败*/
	JOS_TRACE_BY_DELIVERY_ID_FAIL("JRO3035006", "物流运单号全称跟踪失败"),


	//***********开放平台物流项目_【36】
	/**物流订单单号重复*/
	EXPRESS_ORDER_NO_REPEAT("JRO3036001", "物流订单单号重复"),
	/**物流订单不存在*/
	EXPRESS_ORDER_NOT_EXIT("JRO3036002", "物流订单不存在"),
	/**物流订单金额不一致*/
	EXPRESS_ORDER_AMOUNT_ERROR("JRO3036003", "物流订单金额不一致"),

	/**物流渠道映射关系不存在*/
	EXPRESS_REFLECT_NOT_EXIT("JRO3036004", "物流渠道映射关系不存在"),
	/**物流用户信息错误*/
	EXPRESS_USER_ERROR("JRO3036005", "物流用户信息错误"),
	/**物流渠道不存在*/
	EXPRESS_MER_CHANNEL_NOT_EXIT("JRO3036006", "物流渠道不存在"),

	/**物流订单状态错误*/
	EXPRESS_ORDER_STATUS_ERROR("JRO3036007", "物流订单状态错误"),

	/**物流订单地址不支持京东配送*/
	EXPRESS_ADDR_NO_JDE_SUPPORT("JRO3036008", "物流订单地址不支持京东配送"),

	/**物流订单已经过期*/
	EXPRESS_ORDER_EXPIRE("JRO3036009", "物流订单已经过期"),
	
	//***********结算系统FINSEETS_【37】
	/**结算系统明细报送失败*/
	FINSEETS_ADD_FEE_DETAIL_FAIL("JRO3037001", "结算系统明细报送失败"),
	
	
	//***********金融台账【JRGOP】_【38】
	/**台账2.0报送失败*/
	JRGOP_ORDERBANK_FAIL("JRO3038001", "台账2.0报送失败"),
	
	
	
	//**********银行前置系统【BANKFRONT】_【39】
	/**工厂模式创建服务失败*/
	BANKFRONT_FACTORY_FAIL("JRO03039001","工厂模式创建服务失败"),
	/**银行响应签名验证不通过*/
	BANKFRONT_VERIFY_FAIL("JRO03039002","银行响应签名验证不通过"),
	/**银行流水号申请失败*/
	BANKFRONT_APPLY_FAIL("JRO03039003","银行流水号申请失败"),
	/**银行流水号确认失败*/
	BANKFRONT_CONFIRM_FAIL("JRO03039004","银行流水号确认失败"),
	


	//***********JROPEN_【40】
	/**直接透传底层错误码描述，不映射，【2017-08-31】目前jropen_hapi_web 支持，其他慎用*/
	JROPEN_TRANSPARENT_ORIGINAL_RESP_MSG("JRO3040101", "直接透传底层错误码描述，不映射"),


	//***********代发_批量代付_【41】
	/**
	 * 直接透传底层错误码描述，不映射，【2017-08-31】目前jropen_hapi_web 支持，其他慎用
	 */
	SXF_BATCH_NO_REPEAT("JRO3041001", "代发商户平台批次号重复"),

	SXF_PAY_NO_REPEAT("JRO3041002", "代发商户支付单号重复"),

	SXF_ANALYSIS_FAIL("JRO3041003", "代发数据校验失败"),

	SXF_FILE_NAME_FAIL("JRO3041004", "代发文件名校验失败"),

	SXF_ORDER_INFO_CROSS("JRO3041005", "代发订单越权"),

	SXF_PAY_NO_NOT_EXIST("JRO3041006", "代发商户支付单不存在"),

	SXF_PAY_NO_ERROR("JRO3041007", "代发商户支付单状态错误"),

	SXF_PAY_NO_CROSS("JRO3041024", "代发支付单越权"),

	SXF_ORDER_INFO_NOT_EXIST("JRO3041008", "代发工资订单信息不存在"),

	SXF_ORDER_INFO_STATUS_CANNOT_REFUND("JRO3041009","代发工资订单信息状态，不满足整批退款"),

	SXF_ORDER_INFO_CANNOT_REFUND("JRO3041010","代发工资订单信息不能退款"),

	SXF_ORDER_INFO_UPDATE_REFUND_FAIL("JRO3041011","代发工资订单信息更新退款信息失败-可重试"),

	SXF_REFUND_ORDER_INFO_NOT_EXIST("JRO3041012", "代发工资退款订单不存在"),

	SXF_REFUND_ORDER_INFO_UPDATE_FAIL("JRO3041013", "代发工资退款订单状态更新失败"),

	SXF_REFUND_ORDER_RPC_STATUS_SUCC("JRO3041014", "RPC返回退款状态-成功"),

	SXF_REFUND_ORDER_RPC_STATUS_PROCESS("JRO3041015", "RPC返回退款状态-处理中"),

	SXF_REFUND_ORDER_RPC_STATUS_FAIL("JRO3041016", "RPC返回退款状态-失败"),

	SXF_REFUND_ORDER_RPC_STATUS_NOT_EXIST("JRO3041017", "RPC返回：退款订单不存在"),

	SXF_REFUND_ORDER_RPC_QUERY_EXCEPTION("JRO3041018", "退款查询接口返回异常"),

	SXF_BATCH_INFO_NOT_EXIST("JRO3041019", "代发工资批次信息不存在"),

	SXF_BATCH_INFO_STATUS_CANNOT_REFUND("JRO3041020","代发工资批次信息状态，不满足整批退款"),
	/*存在正在退款的退款单；以及其他异常情况*/
	SXF_BATCH_INFO_CANNOT_REFUND("JRO3041021","代发工资批次信息不能退款"),

	SXF_BATCH_INFO_UPDATE_REFUND_FAIL("JRO3041022","代发工资批次信息更新退款信息失败-可重试"),

	SXF_ORDER_INFO_STATUS_ERROR("JRO3041023","批量代发订单状态错误"),




	/**
	 * 外部商户：微知
	 * <br />2017-10-10，wysunmengyong，因对内选Pin流程优化，提前从输出微知分支【03_0097_000000_00023910-1.0.0】移入当前分支【03_0097_000000_00024892-1.0.0】
	 */
	WIZI_ILLEGITMACY_USER_01("JRO30500101","存在实名京东pin，当前外部id绑定了未实名的pin"),
	WIZI_ILLEGITMACY_USER_02("JRO30500102","外部id与绑定的京东pin实名不一致,请解绑"),
	WIZI_CANNOT_BIND_PIN_01("JRO30500103","京东pin绑定了其他外部id，更换京东pin"),
	WIZI_CANNOT_BIND_PIN_02("JRO30500104","外部id与京东pin实名不一致，更换京东pin"),

	WIZI_BIND_RELATION_DIFF("JRO30500105","个人账户输出绑定关系与代发工资业务绑定关系不一致"),

	WIZI_SOURCE_ERROR("JRO30500106","请求来源异常"),
	WIZI_NOT_EXIST_BIND_RELATION("JRO30500107","该用户在个人账户输出不存在绑定关系"),
	WIZI_PIN_LIST_EMPTY("JRO30500108","推荐pin列表为空"),

	IDCARD_NOT_LAWFUL("JRO30500109","身份证号码不合法"),
	/**身份证-未满18周岁*/
	IDCARD_UNDER_AGE("JRO30500110","身份证-未满18周岁"),

	WIZI_XJK_PIN_REALNAME_DIFF("JRO30500111","小金库与pin实名不一致"),
	WIZI_IDCARD_EXIST_XJK("JRO30500112","身份证下存在实名小金库，但未关联该京东账户"),
	WIZI_BIND_ERROR("JRO30500113","个人账户输出，绑定操作失败"),




	/**其它错误*/
	OTHER("JRO3000000", "其它错误");

	/** 编码 */
	private String code;
	/** 描述 */
	private String desc;

	public String getCode() {
		return code;
	}

	private void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	private void setDesc(String desc) {
		this.desc = desc;
	}

	private FlowCenteResponseCode(String code, String desc) {
		this.setCode(code);
		this.setDesc(desc);
	}

	@Override
	public String toString() {
		return new StringBuilder().append("JroResponseCode.").append(this.name()).append("{code=")
				.append(this.getCode()).append(", desc=").append(this.getDesc()).append("}").toString();
	}

	// valueof会抛出异常，这个不会
	public static Map<String, FlowCenteResponseCode> codeMap;
	private static Map<String, FlowCenteResponseCode> descMap;

	static {
		codeMap = new HashMap<String, FlowCenteResponseCode>();
		descMap = new HashMap<String, FlowCenteResponseCode>();
		for (FlowCenteResponseCode type : FlowCenteResponseCode.values()) {
			codeMap.put(type.getCode(), type);
			descMap.put(type.getDesc(), type);
		}
	}
}
