package org.jeecg.modules.rider.fadada.dto;

import lombok.Data;

/**
 * 用户授权回调DTO
 */
@Data
public class UserSignNotifyDto {

    //事件触发时间
    private String eventTime;

    //签署任务ID
    private String signTaskId;

    //task_finished：任务已完成 (签署任务已成功结束)
    private String signTaskStatus;

    //业务参考号
    private String transReferenceId;

}
