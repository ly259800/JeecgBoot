package org.jeecg.modules.rider.course.service;

import org.jeecg.modules.rider.course.entity.VideoUnlockRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 视频解锁记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
public interface IVideoUnlockRecordService extends IService<VideoUnlockRecord> {

    VideoUnlockRecord queryByUserIdAndCourseId(String userId, String courseId);


}
