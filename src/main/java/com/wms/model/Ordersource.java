package com.wms.model;

import java.sql.Timestamp;

public class Ordersource {
    
    private Long osId;
    private String osName;
    private String remark;
    private Timestamp updateTime;
    public Long getOsId() {
        return osId;
    }
    public void setOsId(Long osId) {
        this.osId = osId;
    }
    public String getOsName() {
        return osName;
    }
    public void setOsName(String osName) {
        this.osName = osName;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "Ordersource [osId=" + osId + ", osName=" + osName + ", remark="
                + remark + ", updateTime=" + updateTime + "]";
    }
}
