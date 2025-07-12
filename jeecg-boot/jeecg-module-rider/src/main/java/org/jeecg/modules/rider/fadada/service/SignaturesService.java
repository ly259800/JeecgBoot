package org.jeecg.modules.rider.fadada.service;

import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import com.fasc.open.api.v5_1.res.template.SignTemplateDetailRes;

public interface SignaturesService {


    //获取企业授权链接
    ECorpAuthUrlRes getCorpAuthUrl();


    SignTemplateDetailRes signTempalteDetail(String signTemplateId);


    //创建签署任务（基于签署模板）
    void createWithTemplate(String signTemplateId);







}
