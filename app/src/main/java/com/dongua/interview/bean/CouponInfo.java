package com.dongua.interview.bean;

/**
 * @CreateDate: 2018/12/5 上午10:51
 * @Author: Lewis Weng
 * @Description: 订单优惠券信息
 */
public class CouponInfo {
    private String couponNo;
    private String couponName;
    private String validStartTime;
    private String validEndTime;
    private String introduce;

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(String validStartTime) {
        this.validStartTime = validStartTime;
    }

    public String getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(String validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
