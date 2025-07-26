package org.jeecg.modules.rider.fadada.service;

import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import com.fasc.open.api.v5_1.res.common.EUrlRes;
import com.fasc.open.api.v5_1.res.signtask.OwnerDownloadUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskActorGetUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskDetailRes;
import com.fasc.open.api.v5_1.res.template.SignTemplateDetailRes;
import com.fasc.open.api.v5_1.res.user.UserIdentityInfoRes;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;

public interface SignaturesService {


    //获取企业授权链接
    ECorpAuthUrlRes getCorpAuthUrl();


    //创建签署任务（基于签署模板）
    SignTaskActorGetUrlRes createWithTemplate(String signTemplateId, RiderCustomer riderCustomer, RiderInterviewDTO riderInterview);

    SignTaskActorGetUrlRes createWithPartnerTemplate(String signTemplateId, RiderCustomer riderCustomer);


    SignTaskActorGetUrlRes getActorUrlBySignTaskId(String templateId,String signTaskId, String clientUserId, Boolean partner);

    SignTaskDetailRes getAppDetail(String signTaskId);


    OwnerDownloadUrlRes getOwnerDownloadUrl(String signTaskId);

    EUrlRes getUserAuthUrl(RiderCustomer riderCustomer,String url);

    void userUnbind(String openUserId);

    //获取用户实名信息
    UserIdentityInfoRes getIdentityInfo(String openUserId);


}
