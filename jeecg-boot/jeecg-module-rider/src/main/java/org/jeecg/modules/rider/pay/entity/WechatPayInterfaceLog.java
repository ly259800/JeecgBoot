package org.jeecg.modules.rider.pay.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WechatPayInterfaceLog implements Serializable {
    private Integer id;//主键ID

    private String insign;//请求调用sign

    private String injson;//请求入参json

    private Date intime;//请求调用时间

    private String outjson;//接口返回json

    private Date outtime;//接口返回时间

    private String orderid;//订单号

    private String reqesttype;//1:调用工票api;2:调用微信api

    private static final long serialVersionUID = 1L;

}