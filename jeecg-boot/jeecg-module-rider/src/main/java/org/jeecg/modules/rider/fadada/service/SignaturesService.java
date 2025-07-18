package org.jeecg.modules.rider.fadada.service;

import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import com.fasc.open.api.v5_1.res.common.EUrlRes;
import com.fasc.open.api.v5_1.res.signtask.OwnerDownloadUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskActorGetUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskDetailRes;
import com.fasc.open.api.v5_1.res.template.SignTemplateDetailRes;
import com.fasc.open.api.v5_1.res.user.UserIdentityInfoRes;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;

public interface SignaturesService {


    //获取企业授权链接
    ECorpAuthUrlRes getCorpAuthUrl();


    SignTemplateDetailRes signTempalteDetail(String signTemplateId);


    //创建签署任务（基于签署模板）
    void createWithTemplate(String signTemplateId);


    void fillField(String signTaskId);

    void signTaskStart(String signTaskId);


    SignTaskActorGetUrlRes getActorUrl(String signTaskId, String actorId);


    SignTaskDetailRes getAppDetail(String signTaskId);


    OwnerDownloadUrlRes getOwnerDownloadUrl(String signTaskId);

    EUrlRes getUserAuthUrl(RiderCustomer riderCustomer);

    //获取用户实名信息
    UserIdentityInfoRes getIdentityInfo(String openUserId);


}
