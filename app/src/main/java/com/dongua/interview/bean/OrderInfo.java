package com.dongua.interview.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @CreateDate: 2018/12/5 下午8:01
 * @Author: Lewis Weng
 * @Description:
 */
public class OrderInfo {
    private String orderId;
    private String orderName;
    @JSONField(name = "returnCouponInfo")
    private List<CouponInfo> couponInfoList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public List<CouponInfo> getCouponInfoList() {
        return couponInfoList;
    }

    public void setCouponInfoList(List<CouponInfo> couponInfoList) {
        this.couponInfoList = couponInfoList;
    }
}


