package org.jeecg.modules.rider.fadada.dto;

import lombok.Data;

/**
 * 用户授权回调DTO
 */
@Data
public class UserAuthNotifyDto {

    private String timestamp;


    private String signature;


    private String clientUserId;

    private String openUserId;

    /**
     * 本次授权操作结果：
     * success: 成功；
     * fail: 失败。
     */
    private String authResult;

    private String authFailedReason;

    private String authScope;

}
