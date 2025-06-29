package org.jeecg.modules.rider.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.rider.post.entity.PostDetail;
import org.jeecg.modules.rider.post.mapper.PostDetailMapper;
import org.jeecg.modules.rider.post.service.IPostDetailService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 岗位详情
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Service
public class PostDetailServiceImpl extends ServiceImpl<PostDetailMapper, PostDetail> implements IPostDetailService {


    @Override
    public PostDetail getByPostId(String postId) {
        QueryWrapper<PostDetail> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PostDetail::getPostId,postId).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
