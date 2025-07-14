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
import com.fasc.open.api.exception.ApiException;
import com.fasc.open.api.utils.ResultUtil;
import com.fasc.open.api.v5_1.client.*;
import com.fasc.open.api.v5_1.req.corp.CorpIdentInfoReq;
import com.fasc.open.api.v5_1.req.corp.GetCorpAuthResourceUrlReq;
import com.fasc.open.api.v5_1.req.corp.OprIdentInfoReq;
import com.fasc.open.api.v5_1.req.signtask.*;
import com.fasc.open.api.v5_1.req.template.SignTemplateDetailReq;
import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import com.fasc.open.api.v5_1.res.service.AccessTokenRes;
import com.fasc.open.api.v5_1.res.signtask.CreateSignTaskRes;
import com.fasc.open.api.v5_1.res.template.SignTemplateDetailRes;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SignaturesServiceImpl implements SignaturesService {

    @Value("${fadada.openCorpId}")
    private String openCorpId;

    @Value("${fadada.mobile}")
    private String mobile;

    @Autowired
    private OpenApiClient openApiClient;


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


    @Override
    public SignTemplateDetailRes signTempalteDetail(String signTemplateId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();

            TemplateClient templateClient = new TemplateClient(openApiClient);

            SignTemplateDetailReq signTemplateDetailReq = new SignTemplateDetailReq();
            signTemplateDetailReq.setAccessToken(accessToken);
            //（可选）模板归属方，主体类型IdTypeEnum。如果未指定，则表示查询应用的模板。如果指定，则表示查询企业主体有权访问的模板（主体模板和应用模板）
            signTemplateDetailReq.setOwnerId(OpenId.getInstance(IdTypeEnum.CORP.getCode(), openCorpId));
            //签署模板Id  1752073611336144084
            signTemplateDetailReq.setSignTemplateId(signTemplateId);
            BaseRes<SignTemplateDetailRes> signTemplateDetailResBaseRes = templateClient.getSignTemplateDetail(signTemplateDetailReq);
            ResultUtil.printLog(signTemplateDetailResBaseRes, openApiClient.getJsonStrategy());
            return signTemplateDetailResBaseRes.getData();
        } catch (Exception e) {
            log.error("获取合同模板详情失败！",e);
            throw new JeecgBootException("获取合同模板详情失败！");
        }
    }

    @Override
    public void createWithTemplate(String signTemplateId) {
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
            createWithTemplateReq.setSignTaskSubject("安置单签署任务");
            //指定签署模板ID。 法大大平台将从该签署模板中复制预先设定的文档、控件和签署方，并对每个签署方指定具体的用户或企业。
            createWithTemplateReq.setSignTemplateId(signTemplateId);
            //（可选）任务过期时间。
            createWithTemplateReq.setExpiresTime(null);
            //（可选）签署任务是否自动发起协作流程：false: 不自动发起 true: 自动发起 默认为false。
            createWithTemplateReq.setAutoStart(true);
            //（可选）全部必填控件填写完成后是否自动定稿：false: 不自动定稿 true: 自动定稿 默认为true。
            createWithTemplateReq.setAutoFillFinalize(true);
            //（可选）您的业务应用系统中的业务场景信息，用于更好地定义业务场景和签署任务的关系。
            BusinessSceneInfo businessSceneInfo = new BusinessSceneInfo();
            //（可选）业务场景标识。长度最大32字节。指定该签署任务是某个特定业务场景的，参与各方可能对该业务场景有不同的控制逻辑和规则。
            //businessSceneInfo.setBusinessId(businessId);
            //（可选）业务参考号，由应用系统基于自身业务上下文提供。长度最大100个字符。该参数用于应用系统和签署任务建立关联关系，方便业务流程和数据的关联，例如可以是电商场景的订单号。
            //businessSceneInfo.setTransReferenceId(transReferenceId);
            //有必要的设置BusinessScene值
            //createWithTemplateReq.setBusinessScene(null);

            //（可选）参与方列表。
            createWithTemplateReq.setActors(getSignTemplateActors(signTemplateId));

            System.out.println(openApiClient.getJsonStrategy().toJson(createWithTemplateReq));
            BaseRes<CreateSignTaskRes> res = signTaskClient.createWithTemplate(createWithTemplateReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        } catch (Exception e) {
            log.error("创建签署任务失败！",e);
            throw new JeecgBootException("创建签署任务失败！");
        }

    }


    /**
     * 签署任务--签署模板的参与方列表
     */
    private List<AddActorsTempInfo> getSignTemplateActors(String TemplateFieldDocId) throws ApiException {
        List<AddActorsTempInfo> addActors = new ArrayList<>();

        //参与方一：个人参与方
        //参与方标识：需要与签署模板中保持一致
        String actorId = "个人方";
        //参与方具体名称
        String actorName = "个人方名称";
        //（可选）参与方主体在应用上的OpenId
        String actorOpenId = openUserId;
        //（可选）参与方主体的法大大号
        String actorFDDId = null;
        //（可选）参与方身份名称匹配信息
        String identNameForMatch = null;
        //（可选）参与方证件号码匹配信息
        String certNoForMatch = null;
        //（可选）法大大送达信息
        Notification notification = Notification.getInstance(true, NotifyWayEnum.MOBILE.getCode(), mobile);
        Actor person = getActor(actorId, IdTypeEnum.PERSON.getCode(), actorName, null,
                actorOpenId, actorFDDId,
                null, identNameForMatch, certNoForMatch,
                notification
        );

        //（可选）签署权限参与方关联的签章控件列表。
        List<AddSignFieldInfo> signFields = new ArrayList<>();
        AddSignFieldInfo addSignFieldInfo = getAddSignFieldInfo(TemplateFieldDocId, "个人签署控件编码", "个人签署控件名称", null,true);
        signFields.add(addSignFieldInfo);

        //（可选）签署权限参与方的签署配置信息
        TemplateSignConfigInfoReq signConfigInfo = new TemplateSignConfigInfoReq();
        //(可选）参与方签署序号
        signConfigInfo.setOrderNo(1);
        //（可选）个人参与方或企业参与方经办人的签署方式
        signConfigInfo.setSignerSignMethod(null);
        //（可选）是否要求该参与方将所有文档（不包含附件）阅读至末页才可签署
        signConfigInfo.setReadingToEnd(null);
        //（可选）要求该参与方的最少阅读时间，单位为秒
        signConfigInfo.setReadingTime(null);
        //（可选）允许该参与方使用的身份和意愿确认方式
        signConfigInfo.setVerifyMethods(null);
        //（可选）企业参与方成员能否通过链接打开签署任务
        signConfigInfo.setJoinByLink(null);
        //（可选）是否暂时阻塞
        signConfigInfo.setBlockHere(false);
        //（可选）是否请求该参与方免验证签
        signConfigInfo.setRequestVerifyFree(false);
        //（可选）要求该参与方必须实名才能查看签署任务，默认true
        signConfigInfo.setIdentifiedView(false);

        AddActorsTempInfo addPerson = new AddActorsTempInfo();
        addPerson.setActor(person);
        addPerson.setSignFields(signFields);
        addPerson.setSignConfigInfo(signConfigInfo);
        addActors.add(addPerson);

        //参与方二：企业参与方
        // actorId必须与签署模板中保持一致，actorType和permission以签署模板中为准，不用传参。
        //（可选）参与方企业成员列表
        ActorCorpMember actorCorpMember = new ActorCorpMember();
        actorCorpMember.setMemberId(String.valueOf(memberId));

        //通知方式
        Notification notification1 =  Notification.getInstance(false, NotifyWayEnum.MOBILE.getCode(), mobile);

        Actor corp = getActor("企业方",IdTypeEnum.CORP.getCode() , "企业方名称", new String[]{ActorPermissionEnum.SIGN.getCode()},
                openCorpId, null, new ActorCorpMember[]{actorCorpMember}
                , null, null,
                notification1
        );

        //（可选）签署权限参与方关联的签章控件列表。
        List<AddSignFieldInfo> signFields2 = new ArrayList<>();
        AddSignFieldInfo addSignFieldInfo2 = getAddSignFieldInfo(TemplateFieldDocId, "企业签署控件编码", "企业签署控件名称", null,true);
        signFields2.add(addSignFieldInfo2);

        //签署权限参与方的签署配置信息
        TemplateSignConfigInfoReq signConfigInfo1 = new TemplateSignConfigInfoReq();
        //（可选）是否暂时阻塞
        signConfigInfo1.setBlockHere(false);
        //（可选）是否请求该参与方免验证签
        signConfigInfo1.setRequestVerifyFree(false);

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


    @Override
    public void fillField(String signTaskId) {
        try {
            // 初始化业务客户端
            ServiceClient serviceClient = new ServiceClient(openApiClient);
            // 获取accessToken
            BaseRes<AccessTokenRes> accessTokenRes = serviceClient.getAccessToken();
            String accessToken = accessTokenRes.getData().getAccessToken();
            SignTaskClient signTaskClient = new SignTaskClient(openApiClient);
            FillFieldValuesReq fillFieldValuesReq = new FillFieldValuesReq();
            fillFieldValuesReq.setAccessToken(accessToken);
            //签署任务id，通过创建签署任务接口返回
            fillFieldValuesReq.setSignTaskId(signTaskId);
            //填写类控件列表
            fillFieldValuesReq.setDocFieldValues(getDocFieldValues());
            BaseRes<Void> res = signTaskClient.fillFieldValues(fillFieldValuesReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        } catch (Exception e) {
            log.error("填充属性值失败！",e);
            throw new JeecgBootException("填充属性值失败！");
        }
    }


    /**
     * 填写签署任务控件内容--填写控件列表
     */
    private static List<DocFieldValueInfo> getDocFieldValues() {
        List<DocFieldValueInfo> docFieldValues = new ArrayList<>();
        //填写控件对象
        DocFieldValueInfo field = new DocFieldValueInfo();
        //文档序号。
        field.setDocId("1");
        //控件编码。仅支持填写类控件。
        field.setFieldId("控件编码");
        field.setFieldValue("填写的值");
        docFieldValues.add(field);
        return docFieldValues;
    }



    @Override
    public void signTaskStart(String signTaskId) {
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
            BaseRes<Void> res = signTaskClient.start(signTaskBaseReq);
            ResultUtil.printLog(res, openApiClient.getJsonStrategy());
        } catch (Exception e) {
            log.error("签署任务开始失败！",e);
            throw new JeecgBootException("签署任务开始失败！");
        }
    }


}
