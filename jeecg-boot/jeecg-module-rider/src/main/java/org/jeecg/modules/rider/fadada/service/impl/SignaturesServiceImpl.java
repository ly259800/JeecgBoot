package org.jeecg.modules.rider.fadada.service.impl;

import com.fasc.open.api.bean.base.BaseRes;
import com.fasc.open.api.bean.common.Actor;
import com.fasc.open.api.bean.common.ActorCorpMember;
import com.fasc.open.api.bean.common.Notification;
import com.fasc.open.api.bean.common.OpenId;
import com.fasc.open.api.enums.common.ActorPermissionEnum;
import com.fasc.open.api.enums.common.IdTypeEnum;
import com.fasc.open.api.enums.common.NotifyWayEnum;
import com.fasc.open.api.enums.corp.CorpAuthScopeEnum;
import com.fasc.open.api.enums.doc.FileTypeEnum;
import com.fasc.open.api.enums.user.UserAuthScopeEnum;
import com.fasc.open.api.enums.user.UserIdentMethodEnum;
import com.fasc.open.api.exception.ApiException;
import com.fasc.open.api.utils.ResultUtil;
import com.fasc.open.api.v5_1.client.*;
import com.fasc.open.api.v5_1.req.corp.CorpIdentInfoReq;
import com.fasc.open.api.v5_1.req.corp.GetCorpAuthResourceUrlReq;
import com.fasc.open.api.v5_1.req.corp.OprIdentInfoReq;
import com.fasc.open.api.v5_1.req.signtask.*;
import com.fasc.open.api.v5_1.req.template.SignTemplateDetailReq;
import com.fasc.open.api.v5_1.req.user.GetUserAuthUrlReq;
import com.fasc.open.api.v5_1.req.user.GetUserIdentityInfoReq;
import com.fasc.open.api.v5_1.req.user.UserIdentInfoReq;
import com.fasc.open.api.v5_1.req.user.UserUnbindReq;
import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import com.fasc.open.api.v5_1.res.common.EUrlRes;
import com.fasc.open.api.v5_1.res.service.AccessTokenRes;
import com.fasc.open.api.v5_1.res.signtask.CreateSignTaskRes;
import com.fasc.open.api.v5_1.res.signtask.OwnerDownloadUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskActorGetUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskDetailRes;
import com.fasc.open.api.v5_1.res.template.DocumentInfo;
import com.fasc.open.api.v5_1.res.template.SignTaskActorInfo;
import com.fasc.open.api.v5_1.res.template.SignTemplateDetailRes;
import com.fasc.open.api.v5_1.res.user.UserIdentityInfoRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.params.service.IRiderParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Slf4j
public class SignaturesServiceImpl implements SignaturesService {

    @Value("${fadada.openCorpId}")
    private String openCorpId;

    @Value("${fadada.mobile}")
    private String mobile;

    @Value("${fadada.redirectUrl}")
    private String redirectUrl;


    @Value("${fadada.businessId}")
    private String businessId;

    @Autowired
    private OpenApiClient openApiClient;

    @Autowired
    private IRiderParamsService riderParamsService;


    @Override
    public ECorpAuthUrlRes getCorpAuthUrl() {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            CorpClient corpClient = new CorpClient(openApiClient);

            //获取授权链接
            GetCorpAuthResourceUrlReq req = new GetCorpAuthResourceUrlReq();
            req.setAccessToken(accessToken);
            //企业在应用中的唯一标识
            req.setClientCorpId(openCorpId);
            //经办人个人用户的法大大帐号，仅限手机号或邮箱
            req.setAccountName(mobile);
            //请求参数
            CorpIdentInfoReq corpIdentInfoReq = new CorpIdentInfoReq();
            //法大大平台上企业主体的名称
            corpIdentInfoReq.setCorpName("");
            //企业统一社会信用代码或各种类型组织的唯一代码
            corpIdentInfoReq.setCorpIdentNo("");
            //企业组织类型
            corpIdentInfoReq.setCorpIdentType("");
            //法定代表人姓名
            corpIdentInfoReq.setLegalRepName("");
            req.setCorpIdentInfo(corpIdentInfoReq);
            //不可修改的企业信息，如不传则表示都可修改。CorpNonEditableInfo枚举值
            List<String> corpNonEditableInfo = new ArrayList<>();
            req.setCorpNonEditableInfo(corpNonEditableInfo);
            //企业经办人信息。仅用于需要经办人实名认证时的信息带入，经办人可根据实际情况修改
            OprIdentInfoReq oprIdentInfo = new OprIdentInfoReq();
            oprIdentInfo.setBankAccountNo("");
            oprIdentInfo.setUserIdentNo("");
            oprIdentInfo.setMobile("");
            oprIdentInfo.setUserIdentType("");
            oprIdentInfo.setUserName("");
            req.setOprIdentInfo(oprIdentInfo);

            //（可选）企业授权范围列表CorpAuthScopeEnum
            req.setAuthScopes(Arrays.asList(new String[]{CorpAuthScopeEnum.IDENT_INFO.getCode(),
                    CorpAuthScopeEnum.SIGN_TASK_INIT.getCode(),
                    CorpAuthScopeEnum.SIGN_TASK_INFO.getCode(),
                    CorpAuthScopeEnum.SIGN_TASK_FILE.getCode(),
                    CorpAuthScopeEnum.SEAL_INFO.getCode(),
                    CorpAuthScopeEnum.ORGANIZATION.getCode(),
                    CorpAuthScopeEnum.TEMPLATE.getCode()
            }));
            //（可选）重定向地址。
            req.setRedirectUrl("http://www.baidu.com");
            BaseRes<ECorpAuthUrlRes> res = corpClient.getCorpAuthUrl(req);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            return res.getData();
        }catch (Exception e){
            log.error("获取企业授权链接失败！",e);
            throw new JeecgBootException("获取企业授权链接失败！");
        }
    }


    private SignTemplateDetailRes signTempalteDetail(String accessToken,String signTemplateId) {
        try {
            // 初始化业务客户端
            TemplateClient templateClient = new TemplateClient(openApiClient);
            SignTemplateDetailReq signTemplateDetailReq = new SignTemplateDetailReq();
            signTemplateDetailReq.setAccessToken(accessToken);
            //（可选）模板归属方，主体类型IdTypeEnum。如果未指定，则表示查询应用的模板。如果指定，则表示查询企业主体有权访问的模板（主体模板和应用模板）
            signTemplateDetailReq.setOwnerId(OpenId.getInstance(IdTypeEnum.CORP.getCode(), openCorpId));
            //签署模板Id  1752073611336144084
            signTemplateDetailReq.setSignTemplateId(signTemplateId);
            BaseRes<SignTemplateDetailRes> res = templateClient.getSignTemplateDetail(signTemplateDetailReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            if (!res.isSuccess()) {
                log.error("获取合同模板详情失败！");
                throw new JeecgBootException("获取合同模板详情失败:"+res.getMsg());
            }
            return res.getData();
        } catch (JeecgBootException e1){
            throw e1;
        } catch (Exception e) {
            log.error("获取合同模板详情失败！",e);
            throw new JeecgBootException("获取合同模板详情失败！");
        }
    }

    @Override
    public SignTaskActorGetUrlRes createWithTemplate(String signTemplateId, RiderCustomer riderCustomer, RiderInterviewDTO riderInterview) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();

            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);

            CreateWithTemplateReq createWithTemplateReq = new CreateWithTemplateReq();
            createWithTemplateReq.setAccessToken(accessToken);
            //该签署任务的发起方。
            createWithTemplateReq.setInitiator(OpenId.getInstance(IdTypeEnum.CORP.getCode(), openCorpId));
            //签署任务主题。长度最大100个字符。
            createWithTemplateReq.setSignTaskSubject("安置单签署");
            //指定签署模板ID。 法大大平台将从该签署模板中复制预先设定的文档、控件和签署方，并对每个签署方指定具体的用户或企业。
            createWithTemplateReq.setSignTemplateId(signTemplateId);
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 添加一个月
            LocalDateTime oneMonthLater = now.plusMonths(1);
            // 转换为时间戳
            ZonedDateTime zdt = oneMonthLater.atZone(ZoneId.systemDefault());
            Long timestamp = zdt.toInstant().toEpochMilli();
            //（可选）任务过期时间。
            createWithTemplateReq.setExpiresTime(timestamp.toString());
            //（可选）签署任务是否自动发起协作流程：false: 不自动发起 true: 自动发起 默认为false。
            createWithTemplateReq.setAutoStart(false);
            //（可选）全部必填控件填写完成后是否自动定稿：false: 不自动定稿 true: 自动定稿 默认为true。
            createWithTemplateReq.setAutoFillFinalize(true);
            //有必要的设置BusinessScene值
            createWithTemplateReq.setBusinessId(businessId);
            //业务ID
            createWithTemplateReq.setTransReferenceId(riderInterview.getId());

            //获取模版详情
            SignTemplateDetailRes signTemplateDetailRes = this.signTempalteDetail(accessToken , signTemplateId);

            //（可选）参与方列表
            createWithTemplateReq.setActors(getSignTemplateActors(signTemplateDetailRes, riderCustomer));

            System.out.println(openApiClient.getJsonStrategy().toJson(createWithTemplateReq));
            BaseRes<CreateSignTaskRes> res = signTaskClient.createWithTemplate(createWithTemplateReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            if (res.isSuccess()){
                String signTaskId = res.getData().getSignTaskId();
                this.fillField(accessToken ,signTaskId,signTemplateDetailRes, riderInterview);
                this.signTaskStart(accessToken , res.getData().getSignTaskId());
                //获取参与方签署链接
                List<SignTaskActorInfo> actors = signTemplateDetailRes.getActors();
                SignTaskActorGetUrlRes actorUrl = null;
                for (SignTaskActorInfo actor : actors) {
                    //获取个人签署链接
                    if(Objects.equals(actor.getActorInfo().getActorType(),IdTypeEnum.PERSON.getCode())){
                        actorUrl = this.getActorUrl(accessToken,signTaskId, actor.getActorInfo().getActorId(), riderCustomer.getId(),false);
                        break;
                    }
                }
                riderInterview.setSignTaskId(signTaskId);
                return actorUrl;
            } else {
                log.error("创建签署任务失败！");
                throw new JeecgBootException("创建签署任务失败:"+res.getMsg());
            }
        }  catch (JeecgBootException e1){
            throw e1;
        }catch (Exception e) {
            log.error("创建签署任务失败！",e);
            throw new JeecgBootException("创建签署任务失败！");
        }

    }

    @Override
    public SignTaskActorGetUrlRes createWithPartnerTemplate(String signTemplateId, RiderCustomer riderCustomer) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();

            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);

            CreateWithTemplateReq createWithTemplateReq = new CreateWithTemplateReq();
            createWithTemplateReq.setAccessToken(accessToken);
            //该签署任务的发起方。
            createWithTemplateReq.setInitiator(OpenId.getInstance(IdTypeEnum.CORP.getCode(), openCorpId));
            //签署任务主题。长度最大100个字符。
            createWithTemplateReq.setSignTaskSubject("安置单签署");
            //指定签署模板ID。 法大大平台将从该签署模板中复制预先设定的文档、控件和签署方，并对每个签署方指定具体的用户或企业。
            createWithTemplateReq.setSignTemplateId(signTemplateId);
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // 添加一个月
            LocalDateTime oneMonthLater = now.plusMonths(1);
            // 转换为时间戳
            ZonedDateTime zdt = oneMonthLater.atZone(ZoneId.systemDefault());
            Long timestamp = zdt.toInstant().toEpochMilli();
            //（可选）任务过期时间。
            createWithTemplateReq.setExpiresTime(timestamp.toString());
            //（可选）签署任务是否自动发起协作流程：false: 不自动发起 true: 自动发起 默认为false。
            createWithTemplateReq.setAutoStart(false);
            //（可选）全部必填控件填写完成后是否自动定稿：false: 不自动定稿 true: 自动定稿 默认为true。
            createWithTemplateReq.setAutoFillFinalize(true);
            //有必要的设置BusinessScene值
            createWithTemplateReq.setBusinessId(businessId);
            //业务ID
            createWithTemplateReq.setTransReferenceId(riderCustomer.getId());

            //获取模版详情
            SignTemplateDetailRes signTemplateDetailRes = this.signTempalteDetail(accessToken , signTemplateId);

            //（可选）参与方列表
            createWithTemplateReq.setActors(getSignTemplateActors(signTemplateDetailRes, riderCustomer));

            System.out.println(openApiClient.getJsonStrategy().toJson(createWithTemplateReq));
            BaseRes<CreateSignTaskRes> res = signTaskClient.createWithTemplate(createWithTemplateReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            if (res.isSuccess()){
                String signTaskId = res.getData().getSignTaskId();
                this.fillField(accessToken ,signTaskId,signTemplateDetailRes, riderCustomer);
                this.signTaskStart(accessToken , res.getData().getSignTaskId());
                //获取参与方签署链接
                List<SignTaskActorInfo> actors = signTemplateDetailRes.getActors();
                SignTaskActorGetUrlRes actorUrl = null;
                for (SignTaskActorInfo actor : actors) {
                    //获取个人签署链接
                    if(Objects.equals(actor.getActorInfo().getActorType(),IdTypeEnum.PERSON.getCode())){
                        actorUrl = this.getActorUrl(accessToken,signTaskId, actor.getActorInfo().getActorId(), riderCustomer.getId(), true);
                        break;
                    }
                }
                riderCustomer.setSignTaskId(signTaskId);
                return actorUrl;
            } else {
                log.error("创建签署任务失败！");
                throw new JeecgBootException("创建签署任务失败:"+res.getMsg());
            }
        }  catch (JeecgBootException e1){
            throw e1;
        }catch (Exception e) {
            log.error("创建签署任务失败！",e);
            throw new JeecgBootException("创建签署任务失败！");
        }
    }

    /**
     * 签署任务--签署模板的参与方列表
     */
    private List<AddActorsTempInfo> getSignTemplateActors(SignTemplateDetailRes signTemplateDetailRes, RiderCustomer riderCustomer) throws ApiException {
        List<AddActorsTempInfo> addActors = new ArrayList<>();

        //参与方一：个人参与方
        //参与方标识：需要与签署模板中保持一致
        String actorId = "娘家人客户";
        //参与方具体名称
        String actorName = riderCustomer.getName();
        //（可选）参与方主体在应用上的OpenId
        String actorOpenId = riderCustomer.getOpenUserId();
        //（可选）参与方主体的法大大号
        String actorFDDId = null;
        //（可选）参与方身份名称匹配信息
        String identNameForMatch = null;
        //（可选）参与方证件号码匹配信息
        String certNoForMatch = null;
        //（可选）法大大送达信息
        Notification notification = Notification.getInstance(true, NotifyWayEnum.MOBILE.getCode(), riderCustomer.getPhone());
        Actor person = getActor(actorId, IdTypeEnum.PERSON.getCode(), actorName, null,
                actorOpenId, actorFDDId,
                null, identNameForMatch, certNoForMatch,
                notification
        );
        String TemplateFieldDocId = signTemplateDetailRes.getDocs().get(0).getDocId().toString();
        //（可选）签署权限参与方关联的签章控件列表。
        List<AddSignFieldInfo> signFields = new ArrayList<>();
        AddSignFieldInfo addSignFieldInfo = getAddSignFieldInfo(TemplateFieldDocId, "signature", "签名", null,true);
        signFields.add(addSignFieldInfo);

        //（可选）签署权限参与方的签署配置信息
        TemplateSignConfigInfoReq signConfigInfo = new TemplateSignConfigInfoReq();
        //(可选）参与方签署序号
        signConfigInfo.setOrderNo(1);
        //（可选）个人参与方或企业参与方经办人的签署方式
        signConfigInfo.setSignerSignMethod("ai_hand_write");
        //（可选）是否要求该参与方将所有文档（不包含附件）阅读至末页才可签署
        signConfigInfo.setReadingToEnd(null);
        //（可选）要求该参与方的最少阅读时间，单位为秒
        signConfigInfo.setReadingTime(null);
        //（可选）允许该参与方使用的身份和意愿确认方式
        signConfigInfo.setVerifyMethods(Arrays.asList("sms", "face"));
        //（可选）企业参与方成员能否通过链接打开签署任务
        signConfigInfo.setJoinByLink(null);
        //（可选）是否暂时阻塞
        signConfigInfo.setBlockHere(false);
        //（可选）是否请求该参与方免验证签
        signConfigInfo.setRequestVerifyFree(false);
        //（可选）要求该参与方必须实名才能查看签署任务，默认true
        signConfigInfo.setIdentifiedView(true);

        AddActorsTempInfo addPerson = new AddActorsTempInfo();
        addPerson.setActor(person);
        addPerson.setSignFields(signFields);
        addPerson.setSignConfigInfo(signConfigInfo);
        addActors.add(addPerson);

        //参与方二：企业参与方
        // actorId必须与签署模板中保持一致，actorType和permission以签署模板中为准，不用传参。
        //（可选）参与方企业成员列表
        ActorCorpMember actorCorpMember = new ActorCorpMember();
        actorCorpMember.setMemberId(null);
        actorCorpMember.setAccountName(mobile);

        //通知方式
        Notification notification1 =  Notification.getInstance(false, NotifyWayEnum.MOBILE.getCode(), mobile);

        Actor corp = getActor("娘家人公司",IdTypeEnum.CORP.getCode() , "娘家人平台", new String[]{ActorPermissionEnum.SIGN.getCode()},
                openCorpId, null, new ActorCorpMember[]{actorCorpMember}
                , null, null,
                notification1
        );

        //（可选）签署权限参与方关联的签章控件列表。
        List<AddSignFieldInfo> signFields2 = new ArrayList<>();
        AddSignFieldInfo addSignFieldInfo2 = getAddSignFieldInfo(TemplateFieldDocId, "seal", "印章", null,true);
        signFields2.add(addSignFieldInfo2);

        //签署权限参与方的签署配置信息
        TemplateSignConfigInfoReq signConfigInfo1 = new TemplateSignConfigInfoReq();
        //（可选）是否暂时阻塞
        signConfigInfo1.setBlockHere(false);
        //（可选）是否请求该参与方免验证签
        signConfigInfo1.setRequestVerifyFree(true);

        AddActorsTempInfo addCorp = new AddActorsTempInfo();
        addCorp.setActor(corp);
        addCorp.setSignFields(signFields2);
        addCorp.setSignConfigInfo(signConfigInfo1);
        addActors.add(addCorp);
        return addActors;
    }


    /**
     * 构造参与方对象信息
     *
     * @param actorId
     * @param actorType
     * @param actorName
     * @param permissions
     * @param actorOpenId
     * @param actorFDDId
     * @param actorCorpMembers
     * @param identNameForMatch
     * @param certNoForMatch
     * @param notification
     * @return
     * @throws ApiException
     */
    public Actor getActor(String actorId, String actorType, String actorName, String[] permissions,
                                 String actorOpenId, String actorFDDId, ActorCorpMember[] actorCorpMembers,
                                 String identNameForMatch, String certNoForMatch, Notification notification) throws ApiException {
        Actor actor = new Actor();
        actor.setActorId(actorId);
        actor.setActorType(actorType);
        actor.setActorName(actorName);
        if (permissions != null) {
            actor.setPermissions(Arrays.asList(permissions));
        }
        actor.setActorOpenId(actorOpenId);
        actor.setActorFDDId(actorFDDId);
        if (actorCorpMembers != null) {
            actor.setActorCorpMembers(Arrays.asList(actorCorpMembers));
        }
        actor.setIdentNameForMatch(identNameForMatch);
        actor.setCertNoForMatch(certNoForMatch);
        actor.setNotification(notification);
        return actor;
    }


    /**
     * 构造参与方-签署控件列表
     *
     * @param fieldDocId
     * @param fieldId
     * @param fieldName
     * @return
     */
    public AddSignFieldInfo getAddSignFieldInfo(String fieldDocId, String fieldId, String fieldName, Long sealId,Boolean moveable) {
        AddSignFieldInfo addSignFieldInfo = new AddSignFieldInfo();
        addSignFieldInfo.setFieldDocId(fieldDocId);
        addSignFieldInfo.setFieldId(fieldId);
        addSignFieldInfo.setFieldName(fieldName);
        addSignFieldInfo.setSealId(sealId);
        addSignFieldInfo.setMoveable(moveable);
        return addSignFieldInfo;
    }


    private void fillField(String accessToken,String signTaskId,SignTemplateDetailRes signTemplateDetailRes, RiderInterviewDTO riderInterview) {
        try {
            // 初始化业务客户端
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);
            FillFieldValuesReq fillFieldValuesReq = new FillFieldValuesReq();
            fillFieldValuesReq.setAccessToken(accessToken);
            //签署任务id，通过创建签署任务接口返回
            fillFieldValuesReq.setSignTaskId(signTaskId);
            String docId = signTemplateDetailRes.getDocs().get(0).getDocId().toString();
            //填写类控件列表
            fillFieldValuesReq.setDocFieldValues(getDocFieldValues(docId,getFiledMap(riderInterview)));
            BaseRes<Void> res = signTaskClient.fillFieldValues(fillFieldValuesReq);
            if (!res.isSuccess()) {
                log.error("填充属性值失败！");
                throw new JeecgBootException("填充属性值失败！"+res.getMsg());
            }
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        }  catch (JeecgBootException e1){
            throw e1;
        }catch (Exception e) {
            log.error("填充属性值失败！",e);
            throw new JeecgBootException("填充属性值失败！");
        }
    }



    private void fillField(String accessToken,String signTaskId,SignTemplateDetailRes signTemplateDetailRes ,RiderCustomer riderCustomer) {
        try {
            // 初始化业务客户端
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);
            FillFieldValuesReq fillFieldValuesReq = new FillFieldValuesReq();
            fillFieldValuesReq.setAccessToken(accessToken);
            //签署任务id，通过创建签署任务接口返回
            fillFieldValuesReq.setSignTaskId(signTaskId);
            String docId = signTemplateDetailRes.getDocs().get(0).getDocId().toString();
            //填写类控件列表
            fillFieldValuesReq.setDocFieldValues(getDocFieldValues(docId,getFiledPartnerMap(riderCustomer)));
            BaseRes<Void> res = signTaskClient.fillFieldValues(fillFieldValuesReq);
            if (!res.isSuccess()) {
                log.error("填充属性值失败！");
                throw new JeecgBootException("填充属性值失败！"+res.getMsg());
            }
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        }  catch (JeecgBootException e1){
            throw e1;
        }catch (Exception e) {
            log.error("填充属性值失败！",e);
            throw new JeecgBootException("填充属性值失败！");
        }
    }


    /**
     * 填写签署任务控件内容--填写控件列表
     */
    private static List<DocFieldValueInfo> getDocFieldValues(String docId,Map<String,String> filedMap) {
        List<DocFieldValueInfo> docFieldValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : filedMap.entrySet()) {
            //填写控件对象
            DocFieldValueInfo field = new DocFieldValueInfo();
            //文档序号。
            field.setDocId(docId);
            //控件编码。仅支持填写类控件。
            field.setFieldId(entry.getKey());
            field.setFieldValue(entry.getValue());
            docFieldValues.add(field);
        }
        return docFieldValues;
    }

    private Map<String,String> getFiledMap(RiderInterviewDTO riderInterview) {
        Map<String,String> filedMap = new HashMap<>();
        filedMap.put("name",riderInterview.getName());
        filedMap.put("sex",riderInterview.getSex() == 1 ? "男":"女");
        filedMap.put("IDCard",riderInterview.getIdCard());
        filedMap.put("phone",riderInterview.getPhone());
        filedMap.put("postName",riderInterview.getSiteName());
        filedMap.put("price",riderInterview.getPrice().toString());
        filedMap.put("payType","微信支付");
        filedMap.put("year", DateUtils.getYear()+"");
        filedMap.put("month", DateUtils.getMonth()+"");
        filedMap.put("day", DateUtils.getDay()+"");
        if(Objects.equals("客房",riderInterview.getCategoryName())){
            filedMap.put("serviceTime", "36个");
        }
        filedMap.put("health","[false,true]");
        filedMap.put("credit","[false,true]");
        filedMap.put("hobby","[false,true]");
        filedMap.put("source","小程序");
        if(StringUtils.isNotEmpty(riderInterview.getContacts())){
            filedMap.put("contacts",riderInterview.getContacts());
        }
        return filedMap;
    }


    private Map<String,String> getFiledPartnerMap(RiderCustomer riderCustomer) {
        RiderParams payment_num = riderParamsService.getByCode("payment_num");

        Map<String,String> filedMap = new HashMap<>();
        filedMap.put("name",riderCustomer.getName());
        filedMap.put("IDCard",riderCustomer.getIdCard());
        filedMap.put("phone",riderCustomer.getPhone());
        //filedMap.put("address","上海市");
        filedMap.put("IDCard1",riderCustomer.getIdCard());
        filedMap.put("phone1",riderCustomer.getPhone());
        //filedMap.put("address1","上海市");
        filedMap.put("price",payment_num.getParamValue());
        filedMap.put("ratio","40%");
        filedMap.put("year", DateUtils.getYear()+"");
        filedMap.put("month", DateUtils.getMonth()+"");
        filedMap.put("day", DateUtils.getDay()+"");
        filedMap.put("startYear", DateUtils.getYear()+"");
        filedMap.put("startMonth", DateUtils.getMonth()+"");
        filedMap.put("startDay", DateUtils.getDay()+"");
        //一年时间
        filedMap.put("endYear", (DateUtils.getYear()+1)+"");
        filedMap.put("endMonth", DateUtils.getMonth()+"");
        filedMap.put("endDay", DateUtils.getDay()+"");
        return filedMap;
    }



    private void signTaskStart(String accessToken,String signTaskId) {
        try {
            // 初始化业务客户端
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);
            SignTaskBaseReq signTaskBaseReq = new SignTaskBaseReq();
            signTaskBaseReq.setAccessToken(accessToken);
            //签署任务id，通过创建签署任务接口返回
            signTaskBaseReq.setSignTaskId(signTaskId);
            BaseRes<Void> res = signTaskClient.start(signTaskBaseReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            if (!res.isSuccess()) {
                log.error("签署任务开始失败！");
                throw new JeecgBootException("签署任务开始失败！"+res.getMsg());
            }
        } catch (JeecgBootException e1){
            throw e1;
        } catch (Exception e) {
            log.error("签署任务开始失败！",e);
            throw new JeecgBootException("签署任务开始失败！");
        }
    }

    private SignTaskActorGetUrlRes getActorUrl(String accessToken,String signTaskId,String actorId,String clientUserId, Boolean partner) {
        try {
            // 初始化业务客户端
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);

            SignTaskActorGetUrlReq signTaskActorGetUrlReq = new SignTaskActorGetUrlReq();
            //参与方在签署任务中被设定的唯一标识
            signTaskActorGetUrlReq.setActorId(actorId);
            //应用系统中唯一确定登录用户身份的标识，如应用系统中该用户标识和法大大的账号存在映射关系，则可以实现免登进入签署页面进行签署
            signTaskActorGetUrlReq.setClientUserId(clientUserId);
            //重定向地址
            signTaskActorGetUrlReq.setRedirectMiniAppUrl(partner ? "/pages/partner/index" : "/pages/user/application");
            //签署任务ID
            signTaskActorGetUrlReq.setSignTaskId(signTaskId);
            signTaskActorGetUrlReq.setAccessToken(accessToken);
            BaseRes<SignTaskActorGetUrlRes> res = signTaskClient.signTaskActorGetUrl(signTaskActorGetUrlReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            if (!res.isSuccess()) {
                log.error("获取参与方签署链接失败！");
                throw new JeecgBootException("获取参与方签署链接失败！"+res.getMsg());
            }
            return res.getData();
        } catch (JeecgBootException e1){
            throw e1;
        } catch (Exception e) {
            log.error("获取参与方签署链接失败！",e);
            throw new JeecgBootException("获取参与方签署链接失败！");
        }
    }

    @Override
    public SignTaskActorGetUrlRes getActorUrlBySignTaskId(String templateId,String signTaskId, String clientUserId, Boolean partner ) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            //获取模版详情
            SignTemplateDetailRes signTemplateDetailRes = this.signTempalteDetail(accessToken , templateId);
            //获取参与方签署链接
            List<SignTaskActorInfo> actors = signTemplateDetailRes.getActors();
            SignTaskActorGetUrlRes actorUrl = null;
            for (SignTaskActorInfo actor : actors) {
                //获取个人签署链接
                if(Objects.equals(actor.getActorInfo().getActorType(),IdTypeEnum.PERSON.getCode())){
                    actorUrl = this.getActorUrl(accessToken,signTaskId, actor.getActorInfo().getActorId(), clientUserId, partner);
                    break;
                }
            }
            return actorUrl;
        } catch (Exception e) {
            log.error("获取参与方签署链接失败！",e);
            throw new JeecgBootException("获取参与方签署链接失败！");
        }
    }

    @Override
    public SignTaskDetailRes getAppDetail(String signTaskId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);

            SignTaskBaseReq signTaskBaseReq = new SignTaskBaseReq();
            signTaskBaseReq.setAccessToken(accessToken);
            //签署任务id，通过创建签署任务接口返回
            signTaskBaseReq.setSignTaskId(signTaskId);
            BaseRes<SignTaskDetailRes> res = signTaskClient.getDetail(signTaskBaseReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            return res.getData();
        } catch (Exception e) {
            log.error("获取签署任务详情失败！",e);
            throw new JeecgBootException("获取签署任务详情失败！");
        }
    }

    @Override
    public OwnerDownloadUrlRes getOwnerDownloadUrl(String signTaskId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);

            GetOwnerDownloadUrlReq req = new GetOwnerDownloadUrlReq();
            req.setAccessToken(accessToken);
            //签署任务发起方或参与方
            req.setOwnerId(OpenId.getInstance(IdTypeEnum.CORP.getCode(), openCorpId));
            //签署任务id，通过创建签署任务接口返回
            req.setSignTaskId(signTaskId);
            //（可选）文档类型FileTypeEnum：doc：签署任务中的文档 。 attach：签署任务中的附件。
            req.setFileType(FileTypeEnum.DOC.getCode());
            //（可选）指定签署任务中的文档序号docId或附件序号attachId。
            req.setId("1");
            BaseRes<OwnerDownloadUrlRes> res = signTaskClient.getOwnerDownloadUrl(req);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            return res.getData();
        } catch (Exception e) {
            log.error("获取签署文档下载地址失败！",e);
            throw new JeecgBootException("获取签署文档下载地址失败！");
        }
    }

    @Override
    public EUrlRes getUserAuthUrl(RiderCustomer riderCustomer,String url) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            UserClient userClient = new UserClient(openApiClient);

            GetUserAuthUrlReq req = new GetUserAuthUrlReq();
            //人用户的法大大帐号，仅限手机号或邮箱
            req.setAccountName("");
            UserIdentInfoReq userIdentInfoReq = new UserIdentInfoReq();
            //个人用户真实姓名
            userIdentInfoReq.setUserName("");
            //证件类型 参考枚举类型 UserIdentTypeEnum
            userIdentInfoReq.setUserIdentType("");
            //证件号
            userIdentInfoReq.setUserIdentNo("");
            //个人手机号
            userIdentInfoReq.setMobile(riderCustomer.getPhone());
            //个人银行账户号
            userIdentInfoReq.setBankAccountNo("");
            //用户实名认证方式 参考枚举类型 UserIdentMethodEnum,暂不支持人工审核方式
            userIdentInfoReq.setIdentMethod(Arrays.asList(new String[]{
                    UserIdentMethodEnum.FACE.getCode(),
                    UserIdentMethodEnum.BANK.getCode(),
                    UserIdentMethodEnum.MOBILE.getCode()
            }));

            req.setUserIdentInfo(userIdentInfoReq);
            //页面中不可编辑的个人信息，不传默认都可编辑
            req.setNonEditableInfo(null);
            //个人用户在应用中的唯一标识
            req.setClientUserId(riderCustomer.getId());
            //业务请求的个人授权范围列表
            req.setAuthScopes(Arrays.asList(new String[]{
                    UserAuthScopeEnum.IDENT_INFO.getCode(),
                    UserAuthScopeEnum.SIGN_TASK_INFO.getCode(),
                    UserAuthScopeEnum.SIGN_TASK_INIT.getCode(),
                    UserAuthScopeEnum.SIGN_TASK_FILE.getCode(),
                    UserAuthScopeEnum.SEAL_INFO.getCode()
            }));
            //重定向地址
            req.setRedirectMiniAppUrl(URLEncoder.encode(url, "UTF-8"));
            req.setAccessToken(accessToken);

            BaseRes<EUrlRes> res = userClient.getUserAuthUrl(req);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            return res.getData();
        } catch (Exception e) {
            log.error("获取个人授权链接失败！",e);
            throw new JeecgBootException("获取个人授权链接失败！");
        }
    }

    @Override
    public void userUnbind(String openUserId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            UserClient userClient = new UserClient(openApiClient);
            UserUnbindReq userUnbindReq = new UserUnbindReq();
            userUnbindReq.setAccessToken(accessToken);
            //法大大平台为该用户在该应用appId范围内分配的唯一标识。
            userUnbindReq.setOpenUserId(openUserId);
            BaseRes<Void> res = userClient.unbind(userUnbindReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        } catch (Exception e) {
            log.error("个人解除授权失败！",e);
            throw new JeecgBootException("个人解除授权失败！");
        }
    }

    @Override
    public UserIdentityInfoRes getIdentityInfo(String openUserId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            UserClient userClient = new UserClient(openApiClient);

            GetUserIdentityInfoReq getUserIdentityInfoReq = new GetUserIdentityInfoReq();
            getUserIdentityInfoReq.setAccessToken(accessToken);
            //法大大平台为该用户在该应用appId范围内分配的唯一标识。
            getUserIdentityInfoReq.setOpenUserId(openUserId);
            BaseRes<UserIdentityInfoRes> res = userClient.getIdentityInfo(getUserIdentityInfoReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
            return res.getData();
        } catch (Exception e) {
            log.error("获取个人认证身份信息失败！",e);
            throw new JeecgBootException("获取个人认证身份信息失败！");
        }
    }
}
