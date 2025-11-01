package org.jeecg.modules.rider.interview.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.rider.commission.entity.RiderCommission;
import org.jeecg.modules.rider.commission.service.IRiderCommissionService;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.mapper.RiderInterviewMapper;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.service.IPostService;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import org.jeecg.modules.rider.talentpool.service.IFamilyTalentPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Service
public class RiderInterviewServiceImpl extends ServiceImpl<RiderInterviewMapper, RiderInterview> implements IRiderInterviewService {

    @Autowired
    private IPostService postService;

    @Autowired
    private IRiderCustomerService riderCustomerService;

    @Autowired
    private IRiderCommissionService riderCommissionService;

    @Autowired
    private IFamilyTalentPoolService familyTalentPoolService;

    @Override
    public IPage<RiderInterviewDTO> pageList(IPage<RiderInterview> page, Wrapper<RiderInterview> queryWrapper) {
        return this.baseMapper.pageList(page, queryWrapper);
    }

    @Override
    public void passBatch(String ids) {
        QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<RiderInterview>();
        queryWrapper.lambda().in(RiderInterview::getId, Arrays.asList(ids.split(",")));
        List<RiderInterview> list = this.list(queryWrapper);
        list.forEach(x -> {
            if(StringUtils.isNotBlank(x.getApplyUserId())){
                //更新人才库
                LambdaUpdateWrapper<FamilyTalentPool> toolUpdateWrapper = new UpdateWrapper<FamilyTalentPool>()
                        .lambda()
                        .eq(FamilyTalentPool::getReceiver, x.getApplyUserId())
                        .eq(FamilyTalentPool::getPhone, x.getPhone())
                        .set(FamilyTalentPool::getApplyStatus,1);
                familyTalentPoolService.update(toolUpdateWrapper);
            }
            //更新入职状态
            LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                    .lambda()
                    .eq(RiderInterview::getId, x.getId())
                    .set(RiderInterview::getPassStatus,1);
            this.update(updateWrapper);
        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settleBatch(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<RiderInterview> riderInterviews = this.listByIds(idList);
        if(riderInterviews.stream().anyMatch(s-> StringUtils.isEmpty(s.getReference()))){
            throw new JeecgBootException("推广人为空，不能结算！");
        }
        if(riderInterviews.stream().anyMatch(s-> StringUtils.isEmpty(s.getSiteId()))){
            throw new JeecgBootException("岗位为空，不能结算！");
        }
        if(riderInterviews.stream().anyMatch(s-> Objects.equals(s.getPassStatus() , 0))){
            throw new JeecgBootException("不能选择未入职的记录！");
        }
        if(riderInterviews.stream().anyMatch(s-> Objects.equals(s.getSettleStatus() , 1))){
            throw new JeecgBootException("不能选择已结算的记录！");
        }
        //获取站点信息
        List<String> siteIdList = riderInterviews.stream().map(x -> x.getSiteId()).collect(Collectors.toList());
        List<Post> riderSiteList = postService.listByIds(siteIdList);
        Map<String,  Post> riderSiteMap = riderSiteList.stream().collect(Collectors.toMap(Post::getId, Function.identity(), (a, b) -> b));
        //更新为已结算
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .in(RiderInterview::getId, idList)
                .set(RiderInterview::getSettleStatus,"1");
        this.update(updateWrapper);
        //新增佣金审核
        List<RiderCommission> commissionList = riderInterviews.stream().map(x -> {
            RiderCommission riderCommission = new RiderCommission();
            riderCommission.setCustomerId(x.getReference());
            riderCommission.setCustomerPhone(x.getReferencePhone());
            riderCommission.setInterviewId(x.getSiteId());
            riderCommission.setInterviewName(x.getName());
            riderCommission.setInterviewPhone(x.getPhone());
            riderCommission.setAuditStatus(0);
            if(riderSiteMap.containsKey(x.getSiteId())){
                Post riderSite = riderSiteMap.get(x.getSiteId());
                riderCommission.setCommission(riderSite.getCommission().intValue());
            }
            return riderCommission;
        }).collect(Collectors.toList());
        riderCommissionService.saveBatch(commissionList);
    }

    @Override
    public void updatePriceBatch(String ids, BigDecimal price) {
        List<String> idList = Arrays.asList(ids.split(","));
        RiderInterview riderInterview = this.getById(idList.get(0));
        if (riderInterview == null){
            throw new JeecgBootException("报名记录不存在！");
        }
        if(riderInterview.getPayStatus() == 1){
            throw new JeecgBootException("该报名记录已支付，不能修改价格！");
        }
        //获取岗位信息
        Post post = postService.getById(riderInterview.getSiteId());
        if(post == null){
            throw new JeecgBootException("岗位不存在！");
        }
        if (post.getPayType() == 1){
            throw new JeecgBootException("岗位为付费类型，不能修改价格！");
        }
        //更新支付金额
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId, riderInterview.getId())
                .set(RiderInterview::getPrice,price);
        this.update(updateWrapper);
    }

    @Override
    public void confirmTraining(String ids, String trainingTeacher) {
        List<String> idList = Arrays.asList(ids.split(","));
        RiderInterview riderInterview = this.getById(idList.get(0));
        if (riderInterview == null){
            throw new JeecgBootException("报名记录不存在！");
        }
        if(riderInterview.getTrainingStatus() == 1){
            throw new JeecgBootException("该报名记录已培训！");
        }
        //更新支付金额
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId, riderInterview.getId())
                .set(RiderInterview::getTrainingTeacher,trainingTeacher);
        this.update(updateWrapper);
    }

    @Override
    public void handle(RiderInterview riderInterview) {
        //获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId, riderInterview.getId())
                .set(RiderInterview::getJobPosition,riderInterview.getJobPosition())
                .set(RiderInterview::getExpectRegion,riderInterview.getExpectRegion())
                .set(RiderInterview::getVideoUrl,riderInterview.getVideoUrl())
                .set(RiderInterview::getOperatorName,sysUser.getRealname())
                .set(RiderInterview::getMemo,riderInterview.getMemo());
        this.update(updateWrapper);
    }

    @Override
    public void updateSite(RiderInterview riderInterview) {
        Post site = postService.getById(riderInterview.getSiteId());
        if(Objects.isNull(site)){
            throw new JeecgBootException("岗位不存在!");
        }
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId,riderInterview.getId())
                .set(RiderInterview::getSiteId,riderInterview.getSiteId())
                .set(RiderInterview::getSiteName,site.getPostName());
        this.update(updateWrapper);
    }

    @Override
    public List<RiderInterview> queryListByCategory(String phone , List<String> categoryIds) {
        return this.baseMapper.queryListByCategory(phone,categoryIds);
    }
}
