package com.jd.jr.business.credit.rt.facade.impl;

import com.alibaba.fastjson.JSON;
import com.jd.jr.jropen.common.api.entity.JroRequest;
import com.jd.jr.jropen.common.api.entity.JroResponse;
import com.jd.jr.jropen.common.api.model.CommonRequest;
import com.jd.jr.jropen.common.api.model.CommonResponse;
import com.jd.jr.jropen.common.api.util.StringMaskUtil;
import com.jd.jr.jropen.common.entity.Response;
import com.jd.jr.jropen.common.enums.JroResponseCode;
import com.jd.jr.jropen.common.enums.PersonalBankAccountEnum;
import com.jd.jr.jropen.common.util.LogBuild;
import com.jd.jr.jropen.salary.api.enums.CertAuthStatus;
import com.jd.jr.jropen.salary.api.enums.SalaryErrorType;
import com.jd.jr.jropen.salary.api.facade.JroSalaryAccInfoFacade;
import com.jd.jr.jropen.salary.api.model.*;
import com.jd.jr.jropen.salary.common.context.LogDescConstant;
import com.jd.jr.jropen.salary.common.model.ErpRealnameInfo;
import com.jd.jr.jropen.salary.domain.entity.PartnerPlatIdRelation;
import com.jd.jr.jropen.salary.domain.entity.PersonalBankAccountInfo;
import com.jd.jr.jropen.salary.domain.entity.SalaryAccountInfo;
import com.jd.jr.jropen.salary.jroservice.BizSalaryUserQueryService;
import com.jd.jr.jropen.salary.jroservice.daoservice.PartnerPlatIdRelationDaoService;
import com.jd.jr.jropen.salary.jroservice.daoservice.PersonalBankAccountInfoDaoService;
import com.jd.jr.jropen.salary.jroservice.daoservice.SalaryAccountInfoDaoService;
import com.jd.jr.jropen.salary.jroservice.utilservice.ErpService;
import com.jd.jr.jropen.salary.service.IAksService;
import com.jd.jr.jropen.salary.service.ICustService;
import com.wangyin.commons.util.Logger;
import org.apache.commons.collections.CollectionUtils;import org.apache.commons.lang3.ObjectUtils;import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 代发工资输出微知查询接口-查询用户发薪设置
 *
 * @author wangzongheng
 * @date 2017年9月11日09:54:48
 */
@Component("jroSalaryAccInfoFacadeImpl")
public class JroSalaryAccInfoFacadeImpl extends BaseFacadeImpl implements JroSalaryAccInfoFacade {

    private static Logger logger = new Logger(JroSalaryAccInfoFacadeImpl.class);
    @Resource
    private PersonalBankAccountInfoDaoService personalBankAccountInfoDaoService;
    @Resource
    private SalaryAccountInfoDaoService salaryAccountInfoDaoService;
    @Resource
    private ICustService custService;
    @Resource
    private PartnerPlatIdRelationDaoService partnerPlatIdRelationDaoService;
    @Resource
    private BizSalaryUserQueryService bizSalaryUserQueryService;
    @Resource
    private ErpService erpService;

    @Resource
    private IAksService aksService;
    /**
     * 查询用户发薪设置
     *
     * @return
     */
    @Override
    @Deprecated
    public CommonResponse<SalaryAccInfoRespModel> queryAccInfo(CommonRequest<SalaryAccInfoReqModel> req) {
        String methodDesc = "查询用户发薪设置";
        Response<String> validResp = validRequest(req);
        SalaryAccInfoRespModel respData = new SalaryAccInfoRespModel();
        if (!validResp.isSuccess()) {
            logger.info(LogBuild.getBuilder(methodDesc, "请求参数为空").kv("req", req).build());
            return buildResponse(validResp, null == req ? null : req.getRequestHeader(), respData);
        }
        String partnerPersonId = req.getRequestBody().getPartnerPersonId();
        String partnerMemberId = req.getRequestHeader().getPartnerMemberId();
        if (StringUtils.isBlank(partnerPersonId)) {
            return buildResponse(JroResponseCode.PARAM_NULL, req.getRequestHeader(), respData);
        }
        String partnerId = req.getRequestHeader().getPartnerId();
        logger.info(LogBuild.getBuilder(methodDesc, "开始").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
        try {
            PersonalBankAccountInfo personalBankAccountInfo = null;
            if (StringUtils.isBlank(partnerMemberId)){
                //查询电子户中的realAccId/realAccType
                Response<List<PersonalBankAccountInfo>> personalBankAccountInfoResponse = personalBankAccountInfoDaoService.selectPersonalBankAccountInfoByPartnerIdPersonId(partnerId, partnerPersonId);
                if ((!personalBankAccountInfoResponse.isSuccess()) || CollectionUtils.isEmpty(personalBankAccountInfoResponse.getData())) {
                    logger.info(LogBuild.getBuilder(methodDesc, "没有开通代发账户").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
                }
                personalBankAccountInfo = personalBankAccountInfoResponse.getData().get(0);
                if (checkOpenStatus(methodDesc, respData, partnerPersonId, partnerId, personalBankAccountInfo)){
                    return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
                }
                logger.info(LogBuild.getBuilder(methodDesc, "获取到用户的电子户开户信息").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                        .appendPV("personalBankAccountInfo", personalBankAccountInfoResponse.getData()).build());
            } else {
                Response<PartnerPlatIdRelation> partnerPlatIdRelationResponse = partnerPlatIdRelationDaoService.selectByPartnerIdMemberId(partnerId, partnerMemberId, null);
                if((!partnerPlatIdRelationResponse.isSuccess())|| partnerPlatIdRelationResponse.getData() == null){
                    logger.info(LogBuild.getBuilder(methodDesc, "失败").kv("partnerId", partnerId).appendPV("partnerMemberId", partnerMemberId)
                            .appendPV("resp", partnerPlatIdRelationResponse).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return  buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
                }
                PartnerPlatIdRelation partnerPlatIdRelation = partnerPlatIdRelationResponse.getData();
                Response<PersonalBankAccountInfo> getPersonBankAccountInfoResp = bizSalaryUserQueryService.getPersonBankAccountInfo(partnerId, partnerPersonId, partnerPlatIdRelation.getEleBankCode());
                if ((!getPersonBankAccountInfoResp.isSuccess()) || getPersonBankAccountInfoResp.getData() == null) {
                    logger.info(LogBuild.getBuilder(methodDesc, "没有开通代发账户").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return  buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
                }
                personalBankAccountInfo = getPersonBankAccountInfoResp.getData();
                if (checkOpenStatus(methodDesc, respData, partnerPersonId, partnerId, personalBankAccountInfo)){
                    return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
                }
                logger.info(LogBuild.getBuilder(methodDesc, "获取到用户的电子户开户信息").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                        .appendPV("personalBankAccountInfo", getPersonBankAccountInfoResp.getData()).build());
            }

            //校验是否上传证件照(前提基于已经开通电子户)
            Response<SalaryUserCheckRespModel> getCertAuthStatusResp = custService.getCertAuthStatus(personalBankAccountInfo.getRealAccId(), personalBankAccountInfo.getRealAccType(),
                    partnerPersonId, "127.0.0.1");
            if (!getCertAuthStatusResp.isSuccess()) {
                logger.info(LogBuild.getBuilder(methodDesc, "查询到用户上传证件照信息|用户证件照审核失败").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
            } else if (!getCertAuthStatusResp.getData().getCheckBool() && CertAuthStatus.NOT_UPLOAD.getCode().equals(getCertAuthStatusResp.getData().getCertAuthStatus())) {
                //身份证未上传
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.CERT_NO_PROCESS.getCode());
                return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
            } else if (!getCertAuthStatusResp.getData().getCheckBool() && CertAuthStatus.AUTH_FAIL.getCode().equals(getCertAuthStatusResp.getData().getCertAuthStatus())) {
                // 身份证审核失败
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.CERT_FAIL.getCode());
                return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
            } else if (getCertAuthStatusResp.getData().getCheckBool()) {
                //身份证审核中|审核通过
                respData.setWhetherSalary(Boolean.TRUE);
            }
            // 2018-03-28，wysunmengyong，校验身份证有效期信息
            if(Boolean.TRUE.equals(respData.isWhetherSalary())) {
                Response<Boolean> isCertNoValidDateInfoSupportSalaryResp = custService.isCertNoValidDateInfoSupportSalary(personalBankAccountInfo.getRealAccId(), null);
                boolean whetherSalary = Boolean.TRUE.equals(isCertNoValidDateInfoSupportSalaryResp.getData());
                respData.setWhetherSalary(whetherSalary);
                respData.setSalaryErrorType(whetherSalary ? null : SalaryErrorType.CERT_EXPIRE.getCode());
                logger.info(LogBuild.getBuilder(methodDesc, "校验身份证有效期完成").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                        .appendPV("isCertNoValidDateInfoSupportSalaryResp", isCertNoValidDateInfoSupportSalaryResp).appendPV("respData", respData));
            }

            // 查询发薪账户信息
			Response<List<SalaryAccountInfo>> response = salaryAccountInfoDaoService.queryListByPartnerAndPersonId(partnerId, partnerPersonId);
            if (response.isSuccess() && null != response.getData()) {
                List<SalaryAccountInfo> list = response.getData();
                List<String> lt = new ArrayList<String>();
                for (SalaryAccountInfo salaryAccountInfo : list) {
                    lt.add(salaryAccountInfo.getSalaryAccType());
                }
                respData.setSalaryAccType(lt);
            }
            return buildResponse(JroResponseCode.SUCCESS, req.getRequestHeader(), respData);
        } catch (Exception e) {
            logger.error(LogBuild.getBuilder(methodDesc, LogDescConstant.SYS_UNKNOWN_EXCEPTION).kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                    .appendPV("req", req).appendPV("respData", respData).appendPV("exception", ExceptionUtils.getStackTrace(e)).build());
            return buildResponse(JroResponseCode.SYS_UNKNOWN_ERROR, req.getRequestHeader(), respData);
        }
    }

    public boolean checkOpenStatus(String methodDesc, SalaryAccInfoRespModel respData, String partnerPersonId, String partnerId, PersonalBankAccountInfo personalBankAccountInfo) {
        if ((!PersonalBankAccountEnum.OpenStatus.SUCC.getCode().equals(personalBankAccountInfo.getOpenStatus())) ||
                (!PersonalBankAccountEnum.AccountStatus.NORMAL.getCode().equals(personalBankAccountInfo.getAccountStatus()))) {
            logger.info(LogBuild.getBuilder(methodDesc, "代发账户状态异常").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).appendPV("openStatus", personalBankAccountInfo).build());
            respData.setWhetherSalary(Boolean.FALSE);
            respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
            return true;
        }
        return false;
    }

    /**
     * 新人资后台使用
     * @param req
     * @return
     */
    @Override
    public JroResponse<JroSxfAccountRespModel> getSxfAccountByErpOrPersonId(JroRequest<JroSxfAccountReqModel> req) {
        String methodDesc = "根据erp或partnerPersonId查询账户信息/身份证照片/是否可发薪";
        logger.info(LogBuild.getBuilder(methodDesc, "开始").appendPV("request", JSON.toJSONString(req)).build());
        if (req == null || req.getRequestData() == null || StringUtils.isBlank(req.getRequestData().getPartnerId()) ||StringUtils.isBlank(req.getRequestData().getPartnerMemberId())||
        (StringUtils.isBlank(req.getRequestData().getPartnerPersonId()) && StringUtils.isBlank(req.getRequestData().getErp()))) {
            logger.info(LogBuild.getBuilder(methodDesc, "参数校验失败").appendPV("request", JSON.toJSONString(req)).build());
            return buildResp(JroResponseCode.PARAM_NULL, new JroSxfAccountRespModel());
        }
        JroSxfAccountRespModel respData = new JroSxfAccountRespModel();
        String partnerId = req.getRequestData().getPartnerId();
        String partnerPersonId = req.getRequestData().getPartnerPersonId();
        String partnerMemberId = req.getRequestData().getPartnerMemberId();
        String erp = req.getRequestData().getErp();
        try {
            Response<PartnerPlatIdRelation> partnerPlatIdRelationResponse = partnerPlatIdRelationDaoService.selectByPartnerIdMemberId(partnerId, partnerMemberId, null);
            if ((!partnerPlatIdRelationResponse.isSuccess()) || partnerPlatIdRelationResponse.getData() == null) {
                logger.info(LogBuild.getBuilder(methodDesc, "PartnerPlatIdRelation失败").kv("partnerId", partnerId).appendPV("partnerMemberId", partnerMemberId)
                        .appendPV("resp", partnerPlatIdRelationResponse).build());
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                return buildResp(JroResponseCode.SUCCESS, respData);
            }
            PartnerPlatIdRelation partnerPlatIdRelation = partnerPlatIdRelationResponse.getData();
            logger.info(LogBuild.getBuilder(methodDesc, "获取PartnerPlatIdRelation").appendPV("partnerPlatIdRelation", ObjectUtils.toString(partnerPlatIdRelation)).build());
            setRespDataByPartnerPlatIdRelation(respData, partnerMemberId, partnerPlatIdRelation);
            PersonalBankAccountInfo personalBankAccountInfo = null;
            if (StringUtils.isBlank(partnerPersonId)) {
                logger.info(LogBuild.getBuilder(methodDesc, "partnerPersonId为空-走erp逻辑").appendPV("request", JSON.toJSONString(req)).build());
                //查询身份证号
                Response<ErpRealnameInfo>  erpResp = erpService.getUserIdNumberAllByErp(erp);
                if (!erpResp.isSuccess() || erpResp.getData() == null || StringUtils.isBlank(erpResp.getData().getCertNo())) {
                    logger.info(LogBuild.getBuilder(methodDesc, "查询身份证失败").appendPV("response", JSON.toJSONString(erpResp)).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return buildResp(JroResponseCode.SUCCESS, respData);
                }
                String idNumber = erpResp.getData().getCertNo();
                logger.info(LogBuild.getBuilder(methodDesc, "获取身份证成功").appendPV("idNumber", StringMaskUtil.maskCertNoNew(idNumber)).build());
                Response<PersonalBankAccountInfo> getPersonBankAccountInfoResp = personalBankAccountInfoDaoService.selectPersonalBankAccountInfoByCertNoEighteenEnPersonIdAndEleBankCode(partnerId, aksService.encrypt(idNumber), partnerPlatIdRelation.getEleBankCode());
                if ((!getPersonBankAccountInfoResp.isSuccess()) || getPersonBankAccountInfoResp.getData() == null) {
                    logger.info(LogBuild.getBuilder(methodDesc, "没有开通代发账户").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return buildResp(JroResponseCode.SUCCESS, respData);
                }
                personalBankAccountInfo = getPersonBankAccountInfoResp.getData();
            } else {
                logger.info(LogBuild.getBuilder(methodDesc, "partnerPersonId不为空-走partnerPersonId逻辑").appendPV("request", JSON.toJSONString(req)).build());
                Response<PersonalBankAccountInfo> getPersonBankAccountInfoResp = bizSalaryUserQueryService.getPersonBankAccountInfo(partnerId, partnerPersonId, partnerPlatIdRelation.getEleBankCode());
                if ((!getPersonBankAccountInfoResp.isSuccess()) || getPersonBankAccountInfoResp.getData() == null) {
                    logger.info(LogBuild.getBuilder(methodDesc, "没有开通代发账户").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
                    respData.setWhetherSalary(Boolean.FALSE);
                    respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                    return buildResp(JroResponseCode.SUCCESS, respData);
                }
                personalBankAccountInfo = getPersonBankAccountInfoResp.getData();
            }
            logger.info(LogBuild.getBuilder(methodDesc, "获取personalBankAccountInfo").appendPV("partnerPlatIdRelationResponse", JSON.toJSONString(personalBankAccountInfo)).build());
            setRespDataByPersonalBankAccountInfo(respData, personalBankAccountInfo);


            if ((!PersonalBankAccountEnum.OpenStatus.SUCC.getCode().equals(personalBankAccountInfo.getOpenStatus())) ||
                    (!PersonalBankAccountEnum.AccountStatus.NORMAL.getCode().equals(personalBankAccountInfo.getAccountStatus()))) {
                logger.info(LogBuild.getBuilder(methodDesc, "代发账户状态异常").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).appendPV("openStatus", personalBankAccountInfo).build());
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.NO_ACCOUNT.getCode());
                return buildResp(JroResponseCode.SUCCESS, respData);
            }
            logger.info(LogBuild.getBuilder(methodDesc, "获取到用户的电子户开户信息").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                    .appendPV("personalBankAccountInfo", personalBankAccountInfo).build());
            //校验是否上传证件照(前提基于已经开通电子户)
            Response<SalaryUserCheckRespModel> getCertAuthStatusResp = custService.getCertAuthStatus(personalBankAccountInfo.getRealAccId(), personalBankAccountInfo.getRealAccType(),
                    partnerPersonId, "127.0.0.1");
            if (!getCertAuthStatusResp.isSuccess()) {
                logger.info(LogBuild.getBuilder(methodDesc, "查询到用户上传证件照信息|用户证件照审核失败").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId).build());
            } else if (!getCertAuthStatusResp.getData().getCheckBool() && CertAuthStatus.NOT_UPLOAD.getCode().equals(getCertAuthStatusResp.getData().getCertAuthStatus())) {
                //身份证未上传
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.CERT_NO_PROCESS.getCode());
                return buildResp(JroResponseCode.SUCCESS, respData);
            } else if (!getCertAuthStatusResp.getData().getCheckBool() && CertAuthStatus.AUTH_FAIL.getCode().equals(getCertAuthStatusResp.getData().getCertAuthStatus())) {
                // 身份证审核失败
                respData.setWhetherSalary(Boolean.FALSE);
                respData.setSalaryErrorType(SalaryErrorType.CERT_FAIL.getCode());
                return buildResp(JroResponseCode.SUCCESS, respData);
            } else if (getCertAuthStatusResp.getData().getCheckBool()) {
                //身份证审核中|审核通过
                respData.setWhetherSalary(Boolean.TRUE);
            }
            // 2018-03-28，wysunmengyong，校验身份证有效期信息
            if(Boolean.TRUE.equals(respData.isWhetherSalary())) {
                Response<Boolean> isCertNoValidDateInfoSupportSalaryResp = custService.isCertNoValidDateInfoSupportSalary(personalBankAccountInfo.getRealAccId(), null);
                boolean whetherSalary = Boolean.TRUE.equals(isCertNoValidDateInfoSupportSalaryResp.getData());
                respData.setWhetherSalary(whetherSalary);
                respData.setSalaryErrorType(whetherSalary ? null : SalaryErrorType.CERT_EXPIRE.getCode());
                logger.info(LogBuild.getBuilder(methodDesc, "校验身份证有效期完成").kv("partnerPersonId", partnerPersonId).appendPV("partnerId", partnerId)
                        .appendPV("isCertNoValidDateInfoSupportSalaryResp", isCertNoValidDateInfoSupportSalaryResp).appendPV("respData", respData));
            }
            Response<List<SalaryAccountInfo>> response = salaryAccountInfoDaoService.queryListByPartnerAndPersonId(partnerId, partnerPersonId);
            if (response.isSuccess() && null != response.getData()) {
                List<SalaryAccountInfo> list = response.getData();
                List<String> lt = new ArrayList<String>();
                for (SalaryAccountInfo salaryAccountInfo : list) {
                    lt.add(salaryAccountInfo.getSalaryAccType());
                }
                respData.setSalaryAccType(lt);
            }
            return buildResp(JroResponseCode.SUCCESS, respData);
        } catch (Exception e) {
            logger.error(LogBuild.getBuilder(methodDesc, LogDescConstant.SYS_UNKNOWN_EXCEPTION).appendPV("request", req).appendPV("e", e.toString()).build(), e);
            return buildResp(JroResponseCode.SYS_UNKNOWN_ERROR);
        }
    }


    private void setRespDataByPartnerPlatIdRelation(JroSxfAccountRespModel respData, String partnerMemberId, PartnerPlatIdRelation partnerPlatIdRelation) {
        respData.setPartnerId(partnerPlatIdRelation.getPartnerId());
        respData.setPartnerMemberId(partnerMemberId);
        respData.setPartnerMemberName(partnerPlatIdRelation.getPartnerMemberName());
        respData.setEleBankCode(partnerPlatIdRelation.getEleBankCode());
        respData.setPlatId(partnerPlatIdRelation.getPlatId());
    }

    private void setRespDataByPersonalBankAccountInfo(JroSxfAccountRespModel respData, PersonalBankAccountInfo personalBankAccountInfo) throws Exception {
        respData.setRealAccId(personalBankAccountInfo.getRealAccId());
        respData.setRealAccType(personalBankAccountInfo.getRealAccType());
        respData.setBankCode(personalBankAccountInfo.getBankCode());
        respData.setUserNameEn(personalBankAccountInfo.getUserNameEn());
        respData.setCertType(personalBankAccountInfo.getCertType());
        respData.setCertNoEn(personalBankAccountInfo.getCertNoEn());
        respData.setMobileNoEn(personalBankAccountInfo.getMobileNoEn());
        respData.setCardNoEn(personalBankAccountInfo.getCardNoEn());
        respData.setP2pUserId(personalBankAccountInfo.getP2pUserId());
        respData.setBankUserNo(personalBankAccountInfo.getBankUserNo());
        respData.setOpenStatus(personalBankAccountInfo.getOpenStatus());
        respData.setOpenTime(personalBankAccountInfo.getOpenTime());
        respData.setCertNoEighteenEn(personalBankAccountInfo.getCertNoEighteenEn());
        respData.setAccountStatus(personalBankAccountInfo.getAccountStatus());
        if (StringUtils.isNotBlank(personalBankAccountInfo.getUserNameEn())) {
            respData.setUserNameDecrypt(aksService.decrypt(personalBankAccountInfo.getUserNameEn()));
        }
        if (StringUtils.isNotBlank(personalBankAccountInfo.getCertNoEn())) {
            respData.setCertNoDecrypt(aksService.decrypt(personalBankAccountInfo.getCertNoEn()));
        }
        if (StringUtils.isNotBlank(personalBankAccountInfo.getMobileNoEn())) {
            respData.setMobileNoDecrypt(aksService.decrypt(personalBankAccountInfo.getMobileNoEn()));
        }
        if (StringUtils.isNotBlank(personalBankAccountInfo.getCardNoEn())) {
            respData.setCardNoDecrypt(aksService.decrypt(personalBankAccountInfo.getCardNoEn()));
        }
        if (StringUtils.isNotBlank(personalBankAccountInfo.getCertNoEighteenEn())) {
            respData.setCertNoEighteenDecrypt(aksService.decrypt(personalBankAccountInfo.getCertNoEighteenEn()));
        }
    }
}
