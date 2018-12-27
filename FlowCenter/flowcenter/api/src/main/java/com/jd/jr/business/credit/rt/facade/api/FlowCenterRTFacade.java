package com.jd.jr.business.credit.rt.facade.api;


import com.alibaba.fastjson.JSONObject;
import com.jd.jr.jropen.common.api.entity.JroResponse;
import com.jd.jr.jropen.common.api.model.CommonRequest;
import com.jd.jr.jropen.common.api.model.CommonResponse;

/**
 * @author chenyuliang
 * @date 2018年11月15日09:55:32
 */
public interface FlowCenterRTFacade {
    /**
     * 根据流程定义Key开启一个流程实例并设置参数集合Variables
     * @param req 流程定义Key{String}和Variables{JSONObject}
     * @return 返回创建的流程实例id
     */
    CommonResponse<JSONObject> startProcessInstancesByKey(CommonRequest<JSONObject> req);

    /**
     * 根据任务id设置认领任务
     * @param req 任务id{String}和Variables{JSONObject}
     * @return 返回任务Status
     */
    JroResponse<JSONObject> claimTask(CommonRequest<JSONObject> req);
    
    /**
     * 根据任务id设置任务完成
     * @param req 任务id{String}和Variables{JSONObject}
     * @return 返回任务Status
     */
    JroResponse<JSONObject> completeTask(CommonRequest<JSONObject> req);
    
}
