package org.jeecg.modules.rider.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.mapper.PostMapper;
import org.jeecg.modules.rider.post.service.IPostService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 * @Description: 岗位管理
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Override
    public void publishBatch(String ids,Integer status) {
        LambdaUpdateWrapper<Post> updateWrapper = new UpdateWrapper<Post>()
                .lambda()
                .in(Post::getId, Arrays.asList(ids.split(",")))
                .set(Post::getPublishStatus,status);
        this.update(updateWrapper);
    }
}
