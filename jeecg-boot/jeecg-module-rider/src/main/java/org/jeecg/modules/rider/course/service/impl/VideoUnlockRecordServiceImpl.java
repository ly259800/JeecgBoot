package org.jeecg.modules.rider.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.rider.course.entity.VideoUnlockRecord;
import org.jeecg.modules.rider.course.mapper.VideoUnlockRecordMapper;
import org.jeecg.modules.rider.course.service.IVideoUnlockRecordService;
import org.jeecg.modules.rider.post.entity.PostDetail;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 视频解锁记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Service
public class VideoUnlockRecordServiceImpl extends ServiceImpl<VideoUnlockRecordMapper, VideoUnlockRecord> implements IVideoUnlockRecordService {

    @Override
    public VideoUnlockRecord queryByUserIdAndCourseId(String userId, String courseId) {
        QueryWrapper<VideoUnlockRecord> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(VideoUnlockRecord::getVideoId,courseId).eq(VideoUnlockRecord::getCustomerId,userId).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
