package org.jeecg.modules.rider.post.service.impl;

import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.mapper.PostMapper;
import org.jeecg.modules.rider.post.service.IPostService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 岗位管理
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

}
